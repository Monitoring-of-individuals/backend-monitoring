package ru.monitoring.service;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.monitoring.clients.ApiCloudClient;
import ru.monitoring.dto.PersonIfoDto;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.gibdd.GibddResponse;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;
import ru.monitoring.dto.rosfinmon.Result;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;
import ru.monitoring.model.ApiCloudBalance;
import ru.monitoring.model.Report;

import java.util.List;

import static ru.monitoring.utils.Constants.API_CLOUD_TOKEN;

@AllArgsConstructor
@Service
@Slf4j
public class SupplierRequestService {

    private final ApiCloudClient apiCloudClient;


    /**
     * Если поставщик не смог связаться получить какой-то ответ от федеральной службы, произошла ошибка в запросе
     * поставщику, например, в ФИО указаны недопустимые символы, токен не прошел проверку, кончились деньки и т.д.,
     * поставщик вернет сообщение с кодом 200, в теле JSON объекта будет указан статус ошибки по номенклатуре поставщика
     * - см. сайт www.api-cloud.ru), и текстовое сообщение об ошибке.<p>
     * Если в запросе от клиента на ресурс поставщика допущена ошибка в URL, произошел разрыв соединения, сайт
     * поставщика не отвечает более установленного времени, попытки повторных автоматических отправок запросов не
     * привели к успешному соединению и т.д., то вернется пустая строка.<p>
     * В случае успешного запроса / ответа от поставщика вернется строка с JSON объектом ответа (в том числе и с
     * ошибкой по номенклатуре поставщика). <p>
     * В приватных методах происходит обработка:<p>
     * - в случае пустой строки возвращается пустой объект соответствующего класса;<p>
     * - в случае валидного JSON объекта возвращается результат парсинга JSON объекта в объект соответствующего класса<p>
     * <p>
     * Дальнейшее развитие класса SupplierRequestService и вспомогательного класса ReportBuilder:<p>
     * - добавить логику обработки ошибок по номенклатуре поставщика;<p>
     * - добавить по необходимости логику сборки отчета.<p>
     */
    public Report getReport(PersonIfoDto personInfo) {

        FsspResponse fsspResponse = getEnfProcessingsCheck(personInfo);
        log.info("Response received {}", fsspResponse);

        InnResponse innResponse = getInnCheck(personInfo);
        log.info("Response received {}", innResponse);

        SelfEmplResponse selfEmplResponse = getSelfEmplCheck(innResponse);
        log.info("Response received {}", selfEmplResponse);

        PassportCheckResponse passportCheckResponse = getPassportCheck(personInfo);
        log.info("Response received {}", passportCheckResponse);

        GibddResponse gibddResponse = getDriverIdCheck(personInfo);
        log.info("Response received {}", gibddResponse);

        RosFinMonResponse rosFinMonResponse = getTerrorExtrCheck(personInfo);
        log.info("Response received {}", rosFinMonResponse);

        BankruptResponse bankruptResponse = getBankruptCheck(innResponse);
        log.info("Response received {}", bankruptResponse);

        return ReportBuilder.builder()
                .addFsspResponse(fsspResponse)
                .addInnResponse(innResponse)
                .addSelfEmplResponse(selfEmplResponse)
                .addPassportCheckResponse(passportCheckResponse)
                .addGibddResponse(gibddResponse)
                .addRosFinMonResponse(rosFinMonResponse)
                .addBankruptResponse(bankruptResponse)
                .build();
    }

    /* Проверка на соответствие проверяемому из списка результатов по признакам физ. лицо и дата рождения,
     т.к. сервис, запрашивающий данные из Росфинмониторинга, использует в параметрах только имя
     (Строка поиска (ФИО / Название организации))*/
    private void checkingEqualityBirthDate(RosFinMonResponse rosFinMonResponse, String birthDay) {
        if (rosFinMonResponse.getFound()) { // если найден результат будет true

            List<Result> results = rosFinMonResponse.getResult().stream()
                    .filter(result -> result.getType().equals("fiz")) // фильтр для выбора только физ лиц.
                    .filter(result -> result.getBirth().equals(birthDay)) // и проверяем на совпадение дат
                    .toList();

            if (results.isEmpty()) { // если список окажется пустым
                rosFinMonResponse.setFound(false); // указываем, что ничего не найдено
                rosFinMonResponse.setCount(0);
            }

            rosFinMonResponse.setResult(results);
        }
    }
    public ApiCloudBalance getApiCloudBalance() {
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "balance");
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/apilk.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, ApiCloudBalance.class) : new ApiCloudBalance();
    }

    /**
     * Методы формирования запросов, передаваемых в клиент.<p>
     * Получаемую строку проверяют на соответствие JSON объекту. Если проверка прошла положительно, производит парсинг
     * и возвращают объект соответствующего класса с данными. <p>
     * Если клиент вернул null и парсинг невозможен - возвращают пустой объект соответствующего класса<p>
     */

    private FsspResponse getEnfProcessingsCheck(PersonIfoDto personInfo) {
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "physical");
        paramMap.add("lastname", personInfo.getLastName());
        paramMap.add("firstname", personInfo.getFirstName());
        paramMap.add("secondname", personInfo.getFatherName());
        paramMap.add("birthdate", personInfo.getDateOfBirth());
        paramMap.add("region", "-1");
        paramMap.add("searchAll", "1");
        paramMap.add("onlyActual", "0"); //0 - все, включая погашенные, 1 - только текущие
        paramMap.add("token", API_CLOUD_TOKEN);

        log.info("Request a report to FSSP by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/fssp.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, FsspResponse.class) : new FsspResponse();
    }

    private InnResponse getInnCheck(PersonIfoDto personInfo) {
        String seriesNumber = personInfo.getPassport().trim();
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "inn");
        paramMap.add("lastname", personInfo.getLastName());
        paramMap.add("firstname", personInfo.getFirstName());
        paramMap.add("secondname", personInfo.getFatherName());
        paramMap.add("birthdate", personInfo.getDateOfBirth());
        paramMap.add("serianomer", seriesNumber);
        paramMap.add("token", API_CLOUD_TOKEN);

        log.info("Request a report to FNS by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/nalog.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, InnResponse.class) : new InnResponse();
    }

    private SelfEmplResponse getSelfEmplCheck(InnResponse inn) {
        if (inn.getInn() == null) {
            SelfEmplResponse response = new SelfEmplResponse();
            response.setMessage("Запрос не выполнен, т.к. ИНН не был найден.");
            log.info("The request (SelfEmplResponse) was not executed.");
            return response;
        }
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "npd");
        paramMap.add("inn", inn.getInn());
        paramMap.add("token", API_CLOUD_TOKEN);

        log.info("Request a report to FNS by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/nalog.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, SelfEmplResponse.class) : new SelfEmplResponse();
    }

    private PassportCheckResponse getPassportCheck(PersonIfoDto personInfo) {
        String series = personInfo.getPassport().substring(0, 4);
        String number = personInfo.getPassport().substring(4);

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "chekpassport");
        paramMap.add("seria", series);
        paramMap.add("nomer", number);
        paramMap.add("token", API_CLOUD_TOKEN);

        log.info("Request a report to MVD by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/mvd.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, PassportCheckResponse.class) : new PassportCheckResponse();
    }

    private GibddResponse getDriverIdCheck(PersonIfoDto personInfo) {
        if (personInfo.getDrivingLicence() == null || personInfo.getDateOfLicence() == null) {
            GibddResponse gibddResponse = new GibddResponse();
            gibddResponse.setMessage("В запросе не были предоставлены полные необходимые данные по водительскому удостоверению");
            log.info("The request to GIBDD was not executed.");
            return gibddResponse;
        }

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "driver");
        paramMap.add("serianomer", personInfo.getDrivingLicence());
        paramMap.add("date", personInfo.getDateOfLicence());
        paramMap.add("token", API_CLOUD_TOKEN);

        log.info("Request a report to GIBDD by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/gibdd.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, GibddResponse.class) : new GibddResponse();
    }

    private RosFinMonResponse getTerrorExtrCheck(PersonIfoDto personInfo) {
        String name = personInfo.getFatherName() != null ? personInfo.getLastName().toUpperCase() + " " +
                personInfo.getFirstName().toUpperCase() + " " + personInfo.getFatherName().toUpperCase() :
                personInfo.getLastName().toUpperCase() + " " + personInfo.getFirstName().toUpperCase();

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "terextr");
        paramMap.add("search", name);
        paramMap.add("token", API_CLOUD_TOKEN);

        log.info("Request a report to FEDSFM by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/fedsfm.php", paramMap);
        RosFinMonResponse rosFinMonResponse = JSON.isValid(response) ? JSON.parseObject(response, RosFinMonResponse.class)
                : new RosFinMonResponse();

        if (rosFinMonResponse.getStatus() != null) {

            checkingEqualityBirthDate(rosFinMonResponse, personInfo.getDateOfBirth());
        }

        return rosFinMonResponse;
    }

    private BankruptResponse getBankruptCheck(InnResponse inn) {
        if (inn.getInn() == null) {
            BankruptResponse response= new BankruptResponse();
            response.setMessage("Запрос не выполнен, т.к. ИНН не был найден.");
            log.info("The request to bankrot.fedresurs was not executed.");
            return response;
        }

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "searchString");
        paramMap.add("string", inn.getInn());
        paramMap.add("legalStatus", "fiz");
        paramMap.add("token", API_CLOUD_TOKEN);
        log.info("Request a report to bankrot.fedresurs by parameters: {}", paramMap);
        String response = apiCloudClient.getApiCloudResponse("/bankrot.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, BankruptResponse.class) : new BankruptResponse();
    }
}