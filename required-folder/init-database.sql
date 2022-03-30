-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for soccer_game
CREATE DATABASE IF NOT EXISTS `soccer_game` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `soccer_game`;

-- Dumping structure for table soccer_game.player
CREATE TABLE IF NOT EXISTS `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `age` int(3) NOT NULL,
  `market_value` varchar(50) NOT NULL,
  `position` int(11) NOT NULL COMMENT 'GOALKEEPERS -> 0\r\nDEFENDERS  ->  1\r\n MIDFIELDERS  ->  2\r\nATTACKERS  -> 3',
  `team_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_player_team` (`team_id`),
  CONSTRAINT `FK_player_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table soccer_game.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table soccer_game.team
CREATE TABLE IF NOT EXISTS `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `FK_team_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table soccer_game.transfer_list
CREATE TABLE IF NOT EXISTS `transfer_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `player_id` bigint(20) NOT NULL,
  `price` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_id` (`player_id`),
  CONSTRAINT `FK_transfer_list_player` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table soccer_game.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_user_role` (`role_id`),
  CONSTRAINT `FK_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
