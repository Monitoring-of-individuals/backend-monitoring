package ru.monitoring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
@Getter
@Setter
public class ReportEntity {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "person_last_name")
    private String personLastName;

    @Column(name = "person_first_name")
    private String personFirstName;

    @Column(name = "person_father_name")
    private String personFatherName;

    @Column(name = "person_birthdate")
    private LocalDate personDateOfBirth;

    @Column(name = "passport")
    private String personPassport;

    @Column(name = "driver_licence")
    private String personDrivingLicence;

    @Column(name = "driver_licence_date")
    private LocalDate personDateOfLicence;

    @Column(name = "report")
    private String report;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "report_date_time")
    private LocalDateTime reportDateTime;

    @Column(name = "report_price")
    private BigDecimal reportPrice;
}
