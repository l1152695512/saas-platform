CREATE DATABASE IF NOT EXISTS `saas_survey` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `saas_survey`;
CREATE TABLE IF NOT EXISTS `SURVEY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(32) DEFAULT NULL,
  `SERVICE_ID` varchar(32) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` text,
  `WELCOME_TEXT` text,
  `END_TEXT` text,
  `END_URL` varchar(512) DEFAULT NULL,
  `END_URL_DESCRIPTION` text,
  `TERMINATION_TEXT` text,

  `ACTIVE` bit(1) DEFAULT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `EXPIRY_TIME` datetime DEFAULT NULL,
  `QUESTION_AMOUNT` int(11) DEFAULT NULL,

  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `SURVEY_SETTING` (
  `SURVEY_ID`                   bigint(20) NOT NULL,
  `RESPONSE_LIMIT`              int(11) DEFAULT NULL,
  `TEMPLATE`                    varchar(32) DEFAULT NULL,
  `ACCESS_RULE`                 varchar(32) DEFAULT NULL,
  `FORMAT`                      varchar(32) DEFAULT NULL,
  `LOCALE`                      varchar(32) DEFAULT NULL,
  `ENABLE_ASSESSMENT`           bit(1) DEFAULT NULL,
  `USE_CAPTCHA`                 bit(1) DEFAULT NULL,
  `ALLOW_EDIT_AFTER_COMPLETION` bit(1) DEFAULT NULL,
  `ALLOW_PREV`                  bit(1) DEFAULT NULL,
  `ALLOW_SUSPEND`               bit(1) DEFAULT NULL,
  `AUTO_REDIRECT`               bit(1) DEFAULT NULL,
  `OPEN_QUESTION_SEQ_NO`        bit(1) DEFAULT NULL,
  `OPEN_STATISTICS`             bit(1) DEFAULT NULL,
  `SHOW_GROUP_INFO`             bit(1) DEFAULT NULL,
  `SHOW_PROGRESS`               bit(1) DEFAULT NULL,
  `SHOW_QUESTION_SEQ_NO`        bit(1) DEFAULT NULL,
  `SHOW_WELCOME`                bit(1) DEFAULT NULL,
  PRIMARY KEY (`SURVEY_ID`),
  CONSTRAINT `FK_SURVEY_SETTING_SURVEY_ID` FOREIGN KEY (`SURVEY_ID`) REFERENCES `SURVEY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS  `QUESTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SURVEY_ID` bigint(20) DEFAULT NULL,
  `PARENT_ID` bigint(20) DEFAULT NULL,
  `QUESTION_TYPE` varchar(32) DEFAULT NULL,
  `QUESTION_CODE` varchar(32) DEFAULT NULL,
  `SEQUENCE` int(11) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `IMAGE` varchar(512) DEFAULT NULL,
  `HELP` text,
  `CONTENT` longtext,
  `MANDATORY` bit(1) DEFAULT NULL,
  `DEFAULT_ANSWER` varchar(255) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_5kyc51n6mk6pnk3c03a0grl8g` (`QUESTION_CODE`),
  KEY `FK_QUESTION_PARENT_ID` (`PARENT_ID`),
  KEY `FK_QUESTION_SURVEY_ID` (`SURVEY_ID`),
  CONSTRAINT `FK_QUESTION_SURVEY_ID` FOREIGN KEY (`SURVEY_ID`) REFERENCES `SURVEY` (`ID`),
  CONSTRAINT `FK_QUESTION_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `QUESTION_LOGIC` (
  `TARGET_QUESTION_ID` bigint(20) NOT NULL,
  `SOURCE_QUESTION_ID` bigint(20) NOT NULL,
  `ACTION` varchar(255) DEFAULT NULL,
  `CONDITIONS` text,
  PRIMARY KEY (`SOURCE_QUESTION_ID`,`TARGET_QUESTION_ID`),
  KEY `FK_QUESTION_LOGIC_TARGET_QUESTION_ID` (`TARGET_QUESTION_ID`),
  CONSTRAINT `FK_QUESTION_LOGIC_SOURCE_QUESTION_ID` FOREIGN KEY (`SOURCE_QUESTION_ID`) REFERENCES `QUESTION` (`ID`),
  CONSTRAINT `FK_QUESTION_LOGIC_TARGET_QUESTION_ID` FOREIGN KEY (`TARGET_QUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `QUOTA` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SURVEY_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `ACTIVE` bit(1) DEFAULT NULL,
  `MESSAGE` text,
  `AUTO_LOAD_URL` bit(1) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  `URL_DESCRIPTION` varchar(255) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUOTA_SURVEY_ID` (`SURVEY_ID`),
  CONSTRAINT `FK_QUOTA_SURVEY_ID` FOREIGN KEY (`SURVEY_ID`) REFERENCES `SURVEY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `MEMBER_QUOTA` (
  `QUOTA_ID` bigint(20) NOT NULL,
  `QUESTION_ID` bigint(20) NOT NULL,
  `CODE` varchar(32) NOT NULL,
  PRIMARY KEY (`CODE`,`QUESTION_ID`,`QUOTA_ID`),
  KEY `FK_MEMBER_QUOTA_QUESTION_ID` (`QUESTION_ID`),
  KEY `FK_MEMBER_QUOTA_QUOTA_ID` (`QUOTA_ID`),
  CONSTRAINT `FK_MEMBER_QUOTA_QUOTA_ID` FOREIGN KEY (`QUOTA_ID`) REFERENCES `QUOTA` (`ID`),
  CONSTRAINT `FK_MEMBER_QUOTA_QUESTION_ID` FOREIGN KEY (`QUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `SURVEY_RESPONSE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SURVEY_ID` bigint(20) DEFAULT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  `SERVICE_ID` varchar(32) DEFAULT NULL,
  `DEVICE_ID` varchar(32) DEFAULT NULL,
  `IP_ADDRESS` varchar(32) DEFAULT NULL,
  `LAST_QUESTION_ID` bigint(20) DEFAULT NULL,
  `SUBMITTED` bit(1) DEFAULT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `SUBMIT_TIME` datetime DEFAULT NULL,
  `TERMINATION_TIME` datetime DEFAULT NULL,
  `INTERVIEW_TIME` bigint(20) DEFAULT NULL,

  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SURVEY_RESPONSE_SURVEY_ID` (`SURVEY_ID`),
  CONSTRAINT `FK_SURVEY_RESPONSE_SURVEY_ID` FOREIGN KEY (`SURVEY_ID`) REFERENCES `SURVEY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `RESPONSE_ITEM` (
  `RESPONSE_ID` bigint(20) NOT NULL,
  `QUESTION_ID` bigint(20) NOT NULL,
  `CODE` varchar(512) DEFAULT NULL,
  `INTERVIEW_TIME` bigint(20) DEFAULT NULL,
  `SUBMIT_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`QUESTION_ID`,`RESPONSE_ID`),
  KEY `FK_RESPONSE_ITEM_RESPONSE_ID` (`RESPONSE_ID`),
  CONSTRAINT `FK_RESPONSE_ITEM_RESPONSE_ID` FOREIGN KEY (`RESPONSE_ID`) REFERENCES `SURVEY_RESPONSE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;