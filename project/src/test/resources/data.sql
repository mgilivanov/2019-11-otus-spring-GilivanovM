delete from account_types;
delete from eods;
delete from clients;
delete from accounts;
delete from users;
delete from user_roles;

insert into account_types(code, repayment_order, is_credit_account) values ('OVERDUE_PERCENTS',     1, true);
insert into account_types(code, repayment_order, is_credit_account) values ('OVERDUE_DEBT',         2, true);
insert into account_types(code, repayment_order, is_credit_account) values ('REGULAR_PERCENTS',     3, true);
insert into account_types(code, repayment_order, is_credit_account) values ('REGULAR_DEBT',         4, true);
insert into account_types(code, repayment_order, is_credit_account) values ('PENALTY_OVERDUE',      5, true);
insert into account_types(code, repayment_order, is_credit_account) values ('USUAL_PERCENTS',       6, true);
insert into account_types(code, repayment_order, is_credit_account) values ('USUAL_DEBT',           7, true);
insert into account_types(code, repayment_order, is_credit_account) values ('UNAPPLIED_PERCENTS',null, true);
insert into account_types(code, repayment_order, is_credit_account) values ('SERVICE',           null, true);

insert into eods(date, open_time, status) values (to_date('06.05.2020','dd.mm.yyyy'), to_date('06.05.2020','dd.mm.yyyy'), 'OPEN');

insert into clients (last_name, middle_name, first_name, passport_id, email) values ('Ivanov', 'Ivanovich', 'Ivan', '12345', 'iii@mail.ru');

insert into credits(client_id, application_id, percent_rate, status, sum, reg_payment, next_stmt_date, issue_date, stmt_day, overdue_fee)
values(1, 'app1', 10, 'ACTIVE', 100000, 1550, to_date('06.06.2020','dd.mm.yyyy'), to_date('06.05.2020','dd.mm.yyyy'),6, 100);

INSERT INTO users(username,password,enabled) VALUES ('abs','$2y$12$dcOVTiq6VxS4ZyR4Fovms.MgmidlO/HNqoTHKrWRtOwfcIcWXaWKi', true);
INSERT INTO user_roles (username, role) VALUES ('abs', 'ABS');

INSERT INTO users(username,password,enabled) VALUES ('crm','$2y$12$dcOVTiq6VxS4ZyR4Fovms.MgmidlO/HNqoTHKrWRtOwfcIcWXaWKi', true);
INSERT INTO user_roles (username, role) VALUES ('crm', 'CRM');

INSERT INTO users(username,password,enabled) VALUES ('eod','$2y$12$dcOVTiq6VxS4ZyR4Fovms.MgmidlO/HNqoTHKrWRtOwfcIcWXaWKi', true);
INSERT INTO user_roles (username, role) VALUES ('eod', 'EOD');

INSERT INTO users(username,password,enabled) VALUES ('processing','$2y$12$dcOVTiq6VxS4ZyR4Fovms.MgmidlO/HNqoTHKrWRtOwfcIcWXaWKi', true);
INSERT INTO user_roles (username, role) VALUES ('processing', 'PROCESSING');

INSERT INTO users(username,password,enabled) VALUES ('admin','$2y$12$dcOVTiq6VxS4ZyR4Fovms.MgmidlO/HNqoTHKrWRtOwfcIcWXaWKi', true);
INSERT INTO user_roles (username, role) VALUES ('admin', 'ADMIN');
INSERT INTO user_roles (username, role) VALUES ('admin', 'ABS');
INSERT INTO user_roles (username, role) VALUES ('admin', 'CRM');
INSERT INTO user_roles (username, role) VALUES ('admin', 'EOD');
INSERT INTO user_roles (username, role) VALUES ('admin', 'PROCESSING');