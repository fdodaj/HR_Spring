CREATE TABLE `user_department` (
        user_id INT NOT NULL,
        department_id INT NOT NULL,
        PRIMARY KEY(`user_id`, `department_id`)
    );