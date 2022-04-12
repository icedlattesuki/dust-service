CREATE DATABASE IF NOT EXISTS dust;

use dust;

CREATE TABLE IF NOT EXISTS user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    public_address VARCHAR(255) NOT NULL,
    nonce INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    UNIQUE(public_address),
    UNIQUE(name)
);