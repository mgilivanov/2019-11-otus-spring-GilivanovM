insert into account_types(id, code, repayment_order, is_credit_account) values (1,  'OVERDUE_PERCENTS',     1, true);
insert into account_types(id, code, repayment_order, is_credit_account) values (2,  'OVERDUE_DEBT',         2, true);

insert into account_types(id, code, repayment_order, is_credit_account) values (3,  'REGULAR_PERCENTS',     3, true);
insert into account_types(id, code, repayment_order, is_credit_account) values (4,  'REGULAR_DEBT',         4, true);

insert into account_types(id, code, repayment_order, is_credit_account) values (5,  'PENALTY_OVERDUE',      5, true);

insert into account_types(id, code, repayment_order, is_credit_account) values (6,  'USUAL_PERCENTS',       6, true);
insert into account_types(id, code, repayment_order, is_credit_account) values (7,  'USUAL_DEBT',           7, true);

insert into account_types(id, code, repayment_order, is_credit_account) values (8,  'UNAPPLIED_PERCENTS',null, true);

insert into account_types(id, code, repayment_order, is_credit_account) values (9,  'SERVICE',           null, true);

insert into account_types(id, code, repayment_order, is_credit_account) values (10, 'BANK_EXTERNAL_DT',  null, false);
insert into account_types(id, code, repayment_order, is_credit_account) values (11, 'BANK_EXTERNAL_KT',  null, false);
insert into account_types(id, code, repayment_order, is_credit_account) values (12, 'BANK_PERCENTS',     null, false);
insert into account_types(id, code, repayment_order, is_credit_account) values (13, 'BANK_PENALTIES',    null, false);

insert into eods(id, date, open_time, status) values (1, to_date('06.05.2020','dd.mm.yyyy'), to_date('06.05.2020','dd.mm.yyyy'), 'OPEN');

insert into clients (id, last_name, middle_name, first_name, passport_id, email) values (1, 'Ivanov', 'Ivanovich', 'Ivan', '12345', 'iii@mail.ru');

insert into accounts(id, account_type, balance) values (1, 10, 0);
insert into accounts(id, account_type, balance) values (2, 11, 0);
insert into accounts(id, account_type, balance) values (3, 12, 0);
insert into accounts(id, account_type, balance) values (4, 13, 0);