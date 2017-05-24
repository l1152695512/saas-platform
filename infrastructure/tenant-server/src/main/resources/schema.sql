CREATE DATABASE IF NOT EXISTS `tenant` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `tenant`;

CREATE TABLE IF NOT EXISTS `Application` (
  `id` VARCHAR(32) NOT NULL,
  `name` VARCHAR(32) DEFAULT NULL,
  `group` VARCHAR(128) DEFAULT NULL,
  `description` VARCHAR(512) DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `ApplicationDependency` (
  `applicationId` VARCHAR(32) NOT NULL,
  `dependencyApplicationId` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`applicationId`,`dependencyApplicationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Tenant` (
  `id` VARCHAR(32) NOT NULL,
  `name` VARCHAR(32) DEFAULT NULL,
  `description` VARCHAR(512) DEFAULT NULL,
  `createTime` DATETIME  DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `TenantApplication` (
  `tenantId` VARCHAR(32) NOT NULL,
  `applicationId` VARCHAR(32) DEFAULT NULL,
  `startTime` DATETIME DEFAULT NULL,
  `endTime` DATETIME DEFAULT NULL,
  `description` VARCHAR(512) DEFAULT NULL,
  `properties` text DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`tenantId`,`applicationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;