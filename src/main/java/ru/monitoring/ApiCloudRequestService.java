package ru.monitoring;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.monitoring.apicloudclient.ApiCloudClient;
import ru.monitoring.dto.fssp.FsspResponse;
import ru.monitoring.dto.mvd.PassportCheckResponse;
import ru.monitoring.dto.nalog.InnResponse;
import ru.monitoring.dto.nalog.SelfEmplResponse;

import static ru.monitoring.utils.Constants.API_CLOUD_TOKEN;
import static ru.monitoring.utils.Constants.API_CLOUD_URL;

@AllArgsConstructor
@Service
public class ApiCloudRequestService {

    private final ApiCloudClient apiCloudClient;

    private FsspResponse getEnfProcessings(String lastname, String firstname, String secondname, String birthdate) {
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

    private InnResponse getInn(String firstname, String lastname, String secondname, String birthdate, String serianomer) {
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

    private SelfEmplResponse getSelfEmpl(String inn) {
        if (inn == null) {
            return null;
        }
        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "npd");
        paramMap.add("inn", inn);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getSelfEmplByInn("/nalog.php", paramMap);
    }

    private PassportCheckResponse getPassportCheck(String seria, String nomer) {

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("type", "chekpassport");
        paramMap.add("seria", seria);
        paramMap.add("nomer", nomer);
        paramMap.add("token", API_CLOUD_TOKEN);
        return apiCloudClient.getPassportCheck("/mvd.php", paramMap);
    }

    // ____________________________ Методы отладки _________________________________________

    public void printResult(String lastname, String firstname, String secondname, String birthdate, String passportSeria,
                            String passportNomer) {

        FsspResponse fsspResponse = getEnfProcessings(lastname, firstname, secondname, birthdate);

        String serianomer = passportSeria.trim() + passportNomer.trim();
        InnResponse innResponse = getInn(firstname, lastname, secondname, birthdate, serianomer);

        String inn = innResponse.getInn();
        SelfEmplResponse selfEmplResponse = getSelfEmpl(inn);

        PassportCheckResponse passportCheckResponse = getPassportCheck(passportSeria, passportNomer);

        System.out.println(fsspResponse);
        System.out.println();
        System.out.println(innResponse);
        System.out.println();
        System.out.println(selfEmplResponse);
        System.out.println();
        System.out.println(passportCheckResponse);
    }

    public static void main(String[] args) {

        ApiCloudClient apiCloudClient = new ApiCloudClient(API_CLOUD_URL);
        ApiCloudRequestService apiCloudRequestService = new ApiCloudRequestService(apiCloudClient);
        String lastname = "Иванов";
        String firstname = "Иван";
        String secondname = "Иванович";
        String birthdate = "31.03.1995";
        String seria = "1234";
        String nomer = "567890";

        apiCloudRequestService.printResult(lastname, firstname, secondname, birthdate, seria, nomer);
    }
}