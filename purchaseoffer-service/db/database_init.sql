create database purchaseoffer;
CREATE USER 'ps'@'localhost' IDENTIFIED BY 'ps-pass';
GRANT ALL PRIVILEGES ON *.* TO 'ps'@'localhost' WITH GRANT OPTION;
CREATE USER 'ps'@'%' IDENTIFIED BY 'ps-pass';
GRANT ALL PRIVILEGES ON *.* TO 'ps'@'%' WITH GRANT OPTION;