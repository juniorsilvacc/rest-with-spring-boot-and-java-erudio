CREATE TABLE `users` (
	`id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY NOT NULL,
	`user_name` VARCHAR(255) DEFAULT NULL,
	`full_name` VARCHAR(255) DEFAULT NULL,
	`password` VARCHAR(255) DEFAULT NULL,
	`account_non_expired` BIT(1) DEFAULT NULL,
	`account_non_locked` BIT(1) DEFAULT NULL,
	`credentials_non_expired` BIT(1) DEFAULT NULL,
	`enabled` BIT(1) DEFAULT NULL,
	
	UNIQUE KEY `uk_user_name` (`user_name`)
);