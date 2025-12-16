CREATE DATABASE IF NOT EXISTS healthhubutm;
USE healthhubutm;

-- Category table
CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500)
);

-- Program table
CREATE TABLE IF NOT EXISTS program (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    category VARCHAR(100),
    duration_weeks INT,
    monthly_fee DOUBLE
);