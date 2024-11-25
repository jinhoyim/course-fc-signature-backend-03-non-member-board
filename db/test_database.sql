CREATE DATABASE IF NOT EXISTS `simple_board_test`;

CREATE USER 'testdbuser'@'%' IDENTIFIED BY 'password';

GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES on simple_board_test.* TO 'testdbuser'@'%';

FLUSH PRIVILEGES;

