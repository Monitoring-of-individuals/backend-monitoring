package ru.monitoring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.monitoring.clients.ApiCloudClient;
import ru.monitoring.dto.fedres_banckrupt.BankruptResponse;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.gibdd.GibddResponse;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;
import ru.monitoring.dto.rosfinmon.RosFinMonResponse;
import ru.monitoring.model.Report;

import static ru.monitoring.utils.Constants.API_CLOUD_TOKEN;

@AllArgsConstructor
@Service
public class SupplierRequestService {

    private final ApiCloudClient apiCloudClient;


    /** Тут пока нет логики обработки входящих ошибок от поставщика (они приходят в теле ответа со статустом 200),
     *  пустых ответов (значит, что по какой-то причине запрос поставщику все же вернул код 5хх или 4хх или произошла
     * ошибка соединения или ошибка по timeout).
     * Пока данный метод носит демонстрационный характер.
     * */
    public Report getReport(String lastname, String firstname, String secondname, String birthdate, String passportSeria,
                            String passportNomer, String driverIdSeriaNomer, String driverIdDate) {

        FsspResponse fsspResponse = getEnfProcessingsCheck(lastname, firstname, secondname, birthdate);

        String serianomer = passportSeria.trim() + passportNomer.trim();
        InnResponse innResponse = getInnCheck(firstname, lastname, secondname, birthdate, serianomer);

        String inn = innResponse.getInn();
        SelfEmplResponse selfEmplResponse = getSelfEmplCheck(inn);

        PassportCheckResponse passportCheckResponse = getPassportCheck(passportSeria, passportNomer);

        GibddResponse gibddResponse = getDriverIdCheck(driverIdSeriaNomer, driverIdDate);

        String name = secondname != null ? lastname.toUpperCase() + " " + firstname.toUpperCase() + " " + secondname :
                lastname.toUpperCase() + " " + firstname.toUpperCase();

        RosFinMonResponse rosFinMonResponse = getTerrorExtrCheck(name);

        BankruptResponse bankruptResponse = getBankruptCheck(inn);

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

    private FsspResponse getEnfProcessingsCheck(String lastname, String firstname, String secondname, String birthdate) {
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "physical");
        paramMap.add("lastname", lastname);
        paramMap.add("firstname", firstname);
        paramMap.add("secondname", secondname);
        paramMap.add("birthdate", birthdate);
        paramMap.add("region", "-1");
        paramMap.add("searchAll", "1");
        paramMap.add("onlyActual", "0"); //TODO проверить - завершенные делопроизводства 0 или 1? И Нужны ли завершенные?
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getFsspEnfProcessings("/fssp.php", paramMap);
    }

    private InnResponse getInnCheck(String firstname, String lastname, String secondname, String birthdate, String serianomer) {
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "inn");
        paramMap.add("lastname", lastname);
        paramMap.add("firstname", firstname);
        paramMap.add("secondname", secondname);
        paramMap.add("birthdate", birthdate);
        paramMap.add("serianomer", serianomer);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getInnByFioAndPassportAndBirthday("/nalog.php", paramMap);
    }

    private SelfEmplResponse getSelfEmplCheck(String inn) {
        if (inn == null) {
            return null;
        }
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "npd");
        paramMap.add("inn", inn);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getSelfEmplByInn("/nalog.php", paramMap);
    }

    private PassportCheckResponse getPassportCheck(String passportSeria, String passportNomer) {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "chekpassport");
        paramMap.add("seria", passportSeria);
        paramMap.add("nomer", passportNomer);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getPassportCheck("/mvd.php", paramMap);
    }

    private GibddResponse getDriverIdCheck(String driverIdSeriaNomer, String driverIdDate) {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "driver");
        paramMap.add("serianomer", driverIdSeriaNomer);
        paramMap.add("date", driverIdDate);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getDriverIdCheck("/gibdd.php", paramMap);
    }

    private RosFinMonResponse getTerrorExtrCheck(String fio) {
        String name = fio.toUpperCase();

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "terextr");
        paramMap.add("search", name);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getTerrorExtrCheck("/fedsfm.php", paramMap);
    }

    private BankruptResponse getBankruptCheck(String inn) {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "searchString");
        paramMap.add("string", inn);
        paramMap.add("legalStatus", "fiz");
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getBankruptCheck("/bankrot.php", paramMap);
    }
}