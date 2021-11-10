-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema games
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema games
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `games` DEFAULT CHARACTER SET utf8 ;
USE `games` ;

-- -----------------------------------------------------
-- Table `games`.`Player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`Player` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `join_date` DATE NULL,
  `email` VARCHAR(45) NULL,
  `creditCards` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`game` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `releaseDate` DATE NULL,
  `version` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`gamesOwned`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`gamesOwned` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gameID` INT NULL,
  `purchaseDate` DATE NULL,
  `purchasePrice` FLOAT NULL,
  `Player_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_gamesOwned_Player_idx` (`Player_id` ASC) VISIBLE,
  INDEX `fk_gamesOwned_game1_idx` (`game_id` ASC) VISIBLE,
  CONSTRAINT `fk_gamesOwned_Player`
    FOREIGN KEY (`Player_id`)
    REFERENCES `games`.`Player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gamesOwned_game1`
    FOREIGN KEY (`game_id`)
    REFERENCES `games`.`game` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`gamesPlayed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`gamesPlayed` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `playerID` INT NULL,
  `score` INT NULL,
  `Player_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_gamesPlayed_Player1_idx` (`Player_id` ASC) VISIBLE,
  INDEX `fk_gamesPlayed_game1_idx` (`game_id` ASC) VISIBLE,
  CONSTRAINT `fk_gamesPlayed_Player1`
    FOREIGN KEY (`Player_id`)
    REFERENCES `games`.`Player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gamesPlayed_game1`
    FOREIGN KEY (`game_id`)
    REFERENCES `games`.`game` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`CreditCard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`CreditCard` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ccName` VARCHAR(45) NULL,
  `ccNumber` VARCHAR(45) NULL,
  `securityCode` INT NULL,
  `expDate` VARCHAR(45) NULL,
  `Player_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_CreditCard_Player1_idx` (`Player_id` ASC) VISIBLE,
  CONSTRAINT `fk_CreditCard_Player1`
    FOREIGN KEY (`Player_id`)
    REFERENCES `games`.`Player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
