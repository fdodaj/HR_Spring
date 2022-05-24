CREATE TABLE IF NOT EXISTS `department` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    department_leader INT NULL,
    deleted BOOLEAN default false
    );