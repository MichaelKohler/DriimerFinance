-- MySQL Script generated by MySQL Workbench
-- 11/05/14 19:27:09
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Mandant
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Mandant
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Mandant` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Mandant` ;

-- -----------------------------------------------------
-- Table `Mandant`.`Kontotyp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Mandant`.`Kontotyp` (
  `idKontotyp` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idKontotyp`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Mandant`.`Konto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Mandant`.`Konto` (
  `idKonto` INT NOT NULL,
  `Name` VARCHAR(250) NOT NULL,
  `fk_KontoTyp` INT NOT NULL,
  `Guthaben` DECIMAL NOT NULL,
  `Kapitalkonto` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idKonto`),
  INDEX `fk_KontoTyp_idx` (`fk_KontoTyp` ASC),
  CONSTRAINT `fk_KontoTyp`
    FOREIGN KEY (`fk_KontoTyp`)
    REFERENCES `Mandant`.`Kontotyp` (`idKontotyp`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Mandant`.`Buchung`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Mandant`.`Buchung` (
  `idBuchung` INT NOT NULL,
  `Datum` DATETIME NOT NULL,
  `fk_SollKonto` INT NOT NULL,
  `fk_HabenKonto` INT NOT NULL,
  `Bezeichnung` LONGTEXT NULL,
  `Betrag` INT NOT NULL,
  `Beleg-Nr` VARCHAR(250) NULL,
  PRIMARY KEY (`idBuchung`),
  INDEX `fk_SollKonto_idx` (`fk_SollKonto` ASC),
  INDEX `fk_HabenKonto_idx` (`fk_HabenKonto` ASC),
  CONSTRAINT `fk_SollKonto`
    FOREIGN KEY (`fk_SollKonto`)
    REFERENCES `Mandant`.`Konto` (`idKonto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HabenKonto`
    FOREIGN KEY (`fk_HabenKonto`)
    REFERENCES `Mandant`.`Konto` (`idKonto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `Mandant`.`Kontotyp`
-- -----------------------------------------------------
START TRANSACTION;
USE `Mandant`;
INSERT INTO `Mandant`.`Kontotyp` (`idKontotyp`, `Name`) VALUES (1, 'Aktiv');
INSERT INTO `Mandant`.`Kontotyp` (`idKontotyp`, `Name`) VALUES (2, 'Passiv');
INSERT INTO `Mandant`.`Kontotyp` (`idKontotyp`, `Name`) VALUES (3, 'Ertrag');
INSERT INTO `Mandant`.`Kontotyp` (`idKontotyp`, `Name`) VALUES (4, 'Aufwand');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Mandant`.`Konto`
-- -----------------------------------------------------
START TRANSACTION;
USE `Mandant`;
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (1000, 'Kasse', 1, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (1020, 'Bank', 1, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (1100, 'Debitoren', 1, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (1200, 'Warenlager', 1, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (2000, 'Kreditoren', 2, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (2451, 'Hypotheken', 2, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (2800, 'Eigenkapital', 2, 0, 1);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (3000, 'Erlös', 3, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (4000, 'Materialaufwand', 4, 0, 0);
INSERT INTO `Mandant`.`Konto` (`idKonto`, `Name`, `fk_KontoTyp`, `Guthaben`, `Kapitalkonto`) VALUES (5000, 'Personalaufwand', 4, 0, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Mandant`.`Buchung`
-- -----------------------------------------------------
START TRANSACTION;
USE `Mandant`;
INSERT INTO `Mandant`.`Buchung` (`idBuchung`, `Datum`, `fk_SollKonto`, `fk_HabenKonto`, `Bezeichnung`, `Betrag`, `Beleg-Nr`) VALUES (1, '2013-09-23', 1100, 3000, 'Verkauf auf Rechnung', 1000, '1');
INSERT INTO `Mandant`.`Buchung` (`idBuchung`, `Datum`, `fk_SollKonto`, `fk_HabenKonto`, `Bezeichnung`, `Betrag`, `Beleg-Nr`) VALUES (2, '2013-09-21', 1000, 1100, 'Einzahlung von Debitor', 1000, '2');

COMMIT;
