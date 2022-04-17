CREATE TABLE IF NOT EXISTS `department` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    department_leader INT NOT NULL,
    date_created DATETIME NOT NULL,
    date_deleted DATETIME,
    is_deleted BOOLEAN default false
    );