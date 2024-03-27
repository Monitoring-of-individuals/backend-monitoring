create table if not exists users
(
    id         BIGINT generated by default as identity NOT NULL PRIMARY KEY,
    first_name VARCHAR                                 NOT NULL,
    last_name  VARCHAR                                 NOT NULL,
    email      VARCHAR                                 NOT NULL UNIQUE,
    password   VARCHAR                                 NOT NULL,
    role       VARCHAR                                 NOT NULL
);

create table if not exists reports
(
    id                  BIGINT generated by default as identity NOT NULL PRIMARY KEY,
    user_id             BIGINT                                  NOT NULL,
    person_first_name   VARCHAR                                 NOT NULL,
    person_last_name    VARCHAR                                 NOT NULL,
    person_father_name  VARCHAR,
    person_birthdate    TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    passport            VARCHAR                                 NOT NULL,
    driver_licence      VARCHAR,
    driver_licence_date TIMESTAMP WITHOUT TIME ZONE,
    report              VARCHAR                                 NOT NULL,
    is_success          BOOLEAN                                 NOT NULL,
    report_date_time    TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    report_price        DECIMAL(3,2)
);
