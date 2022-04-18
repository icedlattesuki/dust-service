CREATE DATABASE IF NOT EXISTS dust;

use dust;

CREATE TABLE IF NOT EXISTS user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(40) NOT NULL,
    public_address VARCHAR(255) NOT NULL,
    nonce INT NOT NULL,
    UNIQUE(uuid),
    UNIQUE(public_address)
);

CREATE TABLE IF NOT EXISTS user_profile(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_uuid VARCHAR(40) NOT NULL,
    name VARCHAR(255) NOT NULL,
    avatar_key VARCHAR(255),
    UNIQUE(user_uuid),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS steam(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_uuid VARCHAR(40) NOT NULL,
    steam_id VARCHAR(40),
    api_key VARCHAR(255),
    trade_url VARCHAR(255),
    UNIQUE(user_uuid),
    UNIQUE(steam_id)
);