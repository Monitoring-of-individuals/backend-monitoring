package ru.monitoring.dto.fssp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Record {

    @JSONField(name = "debtor_name")
    private String debtorName; // ФИО должника
    @JSONField(name = "debtor_address")
    private String debtorAddress; // Место рождения
    @JSONField(name = "debtor_dob")
    private String debtorDob; // Дата рождения
    @JSONField(name = "process_title")
    private String processTitle; // Номер исполнительного производства
    @JSONField(name = "process_date")
    private String processDate; // Дата начала делопроизводства
    private String recIspDoc;
            // Реквизиты исполнительного документа (вид, дата принятия органом, номер, наименование органа, выдавшего исполнительный документ)
    private String stopIP; // Окончание делопроизводства
    private String subject; // Вид взыскания (Предмет исполнения)
    private String sum; // Сумма непогашенной задолженности
    @JSONField(name = "document_organization")
    private String documentOrganization; // Отдел судебных приставов
    @JSONField(name = "document_type")
    private String documentType; // Предмет исполнения, сумма непогашенной задолженности
    @JSONField(name = "officer_name")
    private String officerName; // Пристав
    // Контакты пристава
    @JSONField(name = "officer_phones")
    private List<List<String>> officerPhones; // Список со списком телефонов
}
