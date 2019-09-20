SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL';

DROP SCHEMA IF EXISTS `greatergoods`;
CREATE SCHEMA IF NOT EXISTS `greatergoods`
  DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE `greatergoods`;

-- -----------------------------------------------------
-- Table `greatergoods`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `greatergoods`.`customer`;

CREATE TABLE IF NOT EXISTS `greatergoods`.`customer`
(
  `customer_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(45)  NOT NULL,
  `email`       VARCHAR(45)  NOT NULL,
  `phone`       VARCHAR(45)  NOT NULL,
  `address`     VARCHAR(45)  NOT NULL,
  `cc_number`   VARCHAR(19)  NOT NULL,
  PRIMARY KEY (`customer_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1001
  COMMENT = 'maintains customer details';


-- -----------------------------------------------------
-- Table `greatergoods`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `greatergoods`.`order`;

CREATE TABLE IF NOT EXISTS `greatergoods`.`order`
(
  `order_id`            INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_id`         INT UNSIGNED NOT NULL,
  `amount`              INT UNSIGNED NOT NULL,
  `date_created`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `confirmation_number` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_customer` (`customer_id` ASC),
  CONSTRAINT `fk_order_customer`
    FOREIGN KEY (`customer_id`)
      REFERENCES `greatergoods`.`customer` (`customer_id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1001
  COMMENT = 'maintains customer order details';


-- -----------------------------------------------------
-- Table `greatergoods`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `greatergoods`.`category`;

CREATE TABLE IF NOT EXISTS `greatergoods`.`category`
(
  `category_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(45)  NOT NULL,
  PRIMARY KEY (`category_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1001
  COMMENT = 'contains product categories, e.g., dairy, meats, etc.';


-- -----------------------------------------------------
-- Table `greatergoods`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `greatergoods`.`product`;

CREATE TABLE IF NOT EXISTS `greatergoods`.`product`
(
  `product_id`  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` INT UNSIGNED NOT NULL,
  `name`        VARCHAR(45)  NOT NULL,
  `price`       INT UNSIGNED NOT NULL,
  `points`      INT UNSIGNED NOT NULL,
  `description` TINYTEXT     NULL,
  `last_update` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
                                      ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  INDEX `fk_product_category` (`category_id` ASC),
  CONSTRAINT `fk_product_category`
    FOREIGN KEY (`category_id`)
      REFERENCES `greatergoods`.`category` (`category_id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1001
  COMMENT = 'contains product details';


-- -----------------------------------------------------
-- Table `greatergoods`.`order_line_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `greatergoods`.`order_line_item`;

CREATE TABLE IF NOT EXISTS `greatergoods`.`order_line_item`
(
  `order_id` INT UNSIGNED      NOT NULL,
  `product_id`        INT UNSIGNED      NOT NULL,
  `quantity`          SMALLINT UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`order_id`, `product_id`),
  INDEX `fk_order_line_item_order` (`order_id` ASC),
  INDEX `fk_order_line_item_product` (`product_id` ASC),
  CONSTRAINT `fk_order_line_item_order`
    FOREIGN KEY (`order_id`)
      REFERENCES `greatergoods`.`order` (`order_id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordered_product_product`
    FOREIGN KEY (`product_id`)
      REFERENCES `greatergoods`.`product` (`product_id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  COMMENT = 'matches products with customer orders and records their quantity';



SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
