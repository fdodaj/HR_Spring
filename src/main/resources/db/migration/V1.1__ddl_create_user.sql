CREATE TABLE IF NOT EXISTS `user` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    birthday DATETIME NOT NULL,
    gender VARCHAR(6) NOT NULL,
    hire_date DATETIME NOT NULL,
    paid_time_off  INTEGER,
    deleted BOOLEAN DEFAULT false,
    leader BOOLEAN DEFAULT false,
    user_role INT NOT NULL,
    user_department INT NULL
    );