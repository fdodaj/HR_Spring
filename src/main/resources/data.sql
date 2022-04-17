INSERT INTO role (id, name, description, is_deleted)
VALUES (1, 'admin', 'admin', false),
       (2, 'userEntity', 'userEntity', false),
       (3, 'department_leader','department_leader', false);

INSERT INTO request (id, name, reason, from_date, to_date, business_days, date_created, is_deleted)
VALUES (1, 'leje', 'kot', now(), now(), 5, now(), false);

INSERT INTO user (id, first_name, last_name, email, password, phone_number, birthday, address, gender, hire_date, pto,
                  user_status, is_deleted, role, request)
VALUES (1, 'flori', 'dodaj', 'asdasd@gma', '12345', 1234, now(), '9avenue', 'male', now(), 1, 'asdas',
        false, 1, 1);