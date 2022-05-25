INSERT INTO role(id, name, description, deleted)
          VALUES   (1, 'ADMIN', 'ADMIN', false),
                   (2, 'HR', 'HR', false),
                   (3, 'PD', 'Pergjegjes departament', false),
                   (4, 'EMPLOYEE', 'EMPLOYEE', false);

INSERT INTO department(id, name, description, department_leader, deleted)
values   (1, 'Java', 'Java', 1, false),
         (2, 'Python', 'Python', 2, false),
         (3, 'c++', 'c++', 3, false),
         (4, 'Javascript', 'Javascript', 1, false);

INSERT INTO user(id, first_name, last_name, email, password, birthday, gender, hire_date, paid_time_off, deleted, user_role, user_department)
               VALUES (1, 'Florian', 'Dodaj', 'admin@gmail.com','$2a$10$fAb5yDYznme2vPNov0KY1eyJfNFo4yteoeC/V1oRhJNgwwvzn52B6', '2022-02-02', 'male', '2022-02-02', 25, false,1, 1),
                      (2, 'John', 'Smith', 'hr@gmail.com', '$2a$10$fAb5yDYznme2vPNov0KY1eyJfNFo4yteoeC/V1oRhJNgwwvzn52B6','2021-01-02', 'male', '2000-02-02',500, false, 2, 2),
                      (3, 'Mary', 'Jane', 'pd@gmail.com', '$2a$10$fAb5yDYznme2vPNov0KY1eyJfNFo4yteoeC/V1oRhJNgwwvzn52B6','2006-01-02', 'female', '2012-02-02', 50, false, 3, 3),
                      (4, 'Marko', 'Obama', 'employee@gmail.com', '$2a$10$fAb5yDYznme2vPNov0KY1eyJfNFo4yteoeC/V1oRhJNgwwvzn52B6','2005-06-02', 'male', '2010-09-02', 200, false, 4, 4);

INSERT INTO request(id, from_date, to_date, reason, business_days, request_status, date_created, deleted, request_user)
values  (1, '2022-02-02', '2022-02-05', 'Vacation', 2, 'Pending', '2022-02-05', false, 1),
        (2, '2022-02-01', '2022-02-07', 'Traveling abroad', 24, 'Pending', '2022-02-05', false, 1),
        (3, '2021-05-02', '2021-05-06', 'Time with family', 2, 'Pending', '2022-02-05', false, 4);

INSERT INTO holiday(id, name, description, date, active, deleted)
VALUES (1, 'Festat e Vitit të Ri', 'Festat e Vitit të Ri - 01 janar', '2019-01-01', 0, 0),
       (2, 'Festat e Vitit të Ri', 'Festat e Vitit të Ri - 02 janar', '2019-01-02', 0, 0),
       (3, 'Dita e Verës', 'Dita e Verës - 14 mars', '2019-03-14', 0, 0),
       (4, 'Dita e Nevruzit', 'Dita e Nevruzit - 22 mars', '2019-03-22', 0, 0),
       (5, 'E diela e Pashkëve Katolike', 'E diela e Pashkëve Katolike - 21 prill', '2019-04-21', 0, 0),
       (6, 'E diela e Pashkëve Ortodokse', 'E diela e Pashkëve Ortodokse - 28 prill', '2019-04-28', 0, 0),
       (7, 'Dita Ndërkombëtare e Punëtorëve', 'Dita Ndërkombëtare e Punëtorëve - 01 maj', '2019-05-01', 0, 0),
       (8, 'Dita e Bajramit të Madh', 'Dita e Bajramit të Madh - 04 qershor', '2019-06-04', 0, 0),
       (9, 'Dita e Kurban Bajramit', 'Dita e Kurban Bajramit - 11 gusht', '2019-08-11', 0, 0),
       (10, 'Dita e Shenjtërimit të Shenjt Terezës', 'Dita e Shenjtërimit të Shenjt Terezës - 05 Shtator', '2019-09-05',0, 0),
       (11, 'Dita Flamurit dhe e Pavarësisë', 'Dita Flamurit dhe e Pavarësisë - 28 nentor', '2019-11-28', 0, 0),
       (12, 'Dita e Çlirimit', 'Dita e Çlirimit - 29 nentor', '2019-11-29', 0, 0),
       (13, 'Dita Kombëtare e Rinisë', 'Dita Kombëtare e Rinisë - 08 dhjetor', '2019-12-08', 0, 0),
       (14, 'Krishtlindjet', 'Krishtlindjet - 25 dhjetor', '2019-12-25', 0, 0);