package ru.monitoring.service;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
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
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;
import ru.monitoring.model.ApiCloudBalance;
import ru.monitoring.model.Report;

import static ru.monitoring.utils.Constants.API_CLOUD_TOKEN;

@AllArgsConstructor
@Service
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

        InnResponse innResponse = getInnCheck(personInfo);

        SelfEmplResponse selfEmplResponse = getSelfEmplCheck(innResponse);

        PassportCheckResponse passportCheckResponse = getPassportCheck(personInfo);

        GibddResponse gibddResponse = getDriverIdCheck(personInfo);

        RosFinMonResponse rosFinMonResponse = getTerrorExtrCheck(personInfo);

        BankruptResponse bankruptResponse = getBankruptCheck(innResponse);

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
        paramMap.add("secondname", personInfo.getSecondName());
        paramMap.add("birthdate", personInfo.getBirthDate());
        paramMap.add("region", "-1");
        paramMap.add("searchAll", "1");
        paramMap.add("onlyActual", "0"); //TODO проверить - завершенные делопроизводства 0 или 1? И Нужны ли завершенные?
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/fssp.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, FsspResponse.class) : new FsspResponse();
    }

    private InnResponse getInnCheck(PersonIfoDto personInfo) {
        String seriesNumber = personInfo.getPassportSeries().trim() + personInfo.getPassportNumber().trim();
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "inn");
        paramMap.add("lastname", personInfo.getLastName());
        paramMap.add("firstname", personInfo.getFirstName());
        paramMap.add("secondname", personInfo.getSecondName());
        paramMap.add("birthdate", personInfo.getBirthDate());
        paramMap.add("serianomer", seriesNumber);
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/nalog.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, InnResponse.class) : new InnResponse();
    }

    private SelfEmplResponse getSelfEmplCheck(InnResponse inn) {
        if (inn.getInn() == null) {
            return new SelfEmplResponse();
        }
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "npd");
        paramMap.add("inn", inn);
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/nalog.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, SelfEmplResponse.class) : new SelfEmplResponse();
    }

    private PassportCheckResponse getPassportCheck(PersonIfoDto personInfo) {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "chekpassport");
        paramMap.add("seria", personInfo.getPassportSeries());
        paramMap.add("nomer", personInfo.getPassportNumber());
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/mvd.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, PassportCheckResponse.class) : new PassportCheckResponse();
    }

    private GibddResponse getDriverIdCheck(PersonIfoDto personInfo) {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "driver");
        paramMap.add("serianomer", personInfo.getDriverIdSeriesNumber());
        paramMap.add("date", personInfo.getDriverIdDate());
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/gibdd.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, GibddResponse.class) : new GibddResponse();
    }

    private RosFinMonResponse getTerrorExtrCheck(PersonIfoDto personInfo) {
        String name = personInfo.getSecondName() != null ? personInfo.getLastName().toUpperCase() + " " +
                personInfo.getFirstName().toUpperCase() + " " + personInfo.getSecondName() :
                personInfo.getLastName().toUpperCase() + " " + personInfo.getFirstName().toUpperCase();

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "terextr");
        paramMap.add("search", name);
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/fedsfm.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, RosFinMonResponse.class) : new RosFinMonResponse();
    }

    private BankruptResponse getBankruptCheck(InnResponse inn) {
        if (inn.getInn() == null) {
            return new BankruptResponse();
        }

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "searchString");
        paramMap.add("string", inn);
        paramMap.add("legalStatus", "fiz");
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/bankrot.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, BankruptResponse.class) : new BankruptResponse();
    }

    public ApiCloudBalance getApiCloudBalance() {
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "balance");
        paramMap.add("token", API_CLOUD_TOKEN);
        String response = apiCloudClient.getApiCloudResponse("/apilk.php", paramMap);
        return JSON.isValid(response) ? JSON.parseObject(response, ApiCloudBalance.class) : new ApiCloudBalance();
    }
}