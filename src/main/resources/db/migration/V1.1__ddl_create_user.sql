CREATE TABLE IF NOT EXISTS `user` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number INTEGER(25) ,
    birthday DATETIME NOT NULL,
    address VARCHAR(255),
    gender VARCHAR(6) NOT NULL,
    hire_date DATETIME NOT NULL,
    paid_time_off  INTEGER,
    user_status VARCHAR(255),
    date_created DATETIME NOT NULL,
    date_deleted DATETIME,
    is_deleted BOOLEAN DEFAULT false,
    user_role INT NOT NULL
    );