CREATE TABLE `books` (
	`id` bigint(20) AUTO_INCREMENT PRIMARY KEY NOT NULL,
	`author` varchar(100),
	`launch_date` datetime(6) NOT NULL,
	`price` decimal(65,2) NOT NULL,
  	`title` longtext
);