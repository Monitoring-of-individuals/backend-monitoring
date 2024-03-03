package ru.monitoring.dto.fssp;

import lombok.*;
import ru.monitoring.dto.Inquiry;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Record {

    private String debtor_name; // ФИО должника
    private String debtor_address; // Место рождения
    private String debtor_dob; // Дата рождения
    private String process_title; // Номер исполнительного производства
    private String process_date; // Дата начала делопроизводства
    private String recIspDoc; // Реквизиты исполнительного документа (вид, дата принятия органом, номер, наименование органа, выдавшего исполнительный документ)
    private String stopIP; // Окончание делопроизводства
    private String subject; // Вид взыскания (Предмет исполнения)
    private String sum; // Сумма непогашенной задолженности
    private String document_organization; // Отдел судебных приставов
    private String document_type; // Предмет исполнения, сумма непогашенной задолженности
    private String officer_name; // Пристав
    // Контакты пристава
    private List<List<String>> officer_phones; // Список со списком телефонов
    // Информация о запросе
    private Inquiry inquiry;

}
