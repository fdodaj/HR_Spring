CREATE TABLE IF NOT EXISTS `request_permission`(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userEntity INT NOT NULL,
    reason VARCHAR(555) NOT NULL,
    from_date DATETIME NOT NULL,
    to_date DATETIME NOT NULL,
    business_days INT NOT NULL,
    request_status VARCHAR(255) NOT NULL,
    date_created DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false

)