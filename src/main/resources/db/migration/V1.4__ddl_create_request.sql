CREATE TABLE IF NOT EXISTS `request`(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reason VARCHAR(555) NOT NULL,
    from_date DATETIME NOT NULL,
    to_date DATETIME NOT NULL,
    business_days INT NOT NULL,
    request_status VARCHAR(255) NOT NULL,
    date_created VARCHAR(255) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    user INT not null
)