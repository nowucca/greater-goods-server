--
-- EXECUTE THIS SCRIPT FROM THE COMMAND LINE
-- USING AN ADMINISTRATIVE MYSQL USER.
--
--   $ mysql -u root -p < create.sql
--
-- Creates a database and a user with all privileges
-- on that database for the project.
--

DROP SCHEMA IF EXISTS `greatergoods`;
CREATE SCHEMA IF NOT EXISTS `greatergoods`
    DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE USER IF NOT EXISTS 'greatergoods'@'%' IDENTIFIED BY 'greatergoods';
GRANT ALL PRIVILEGES ON greatergoods.* to 'greatergoods'@'%';
FLUSH PRIVILEGES;
