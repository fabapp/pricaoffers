create database contact;
CREATE USER 'cs'@'localhost' IDENTIFIED BY 'cs-pass';
GRANT ALL PRIVILEGES ON *.* TO 'cs'@'localhost' WITH GRANT OPTION;
CREATE USER 'cs'@'%' IDENTIFIED BY 'cs-pass';
GRANT ALL PRIVILEGES ON *.* TO 'cs'@'%' WITH GRANT OPTION;