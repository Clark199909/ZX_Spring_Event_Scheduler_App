USE `scheduler`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `type`;

CREATE TABLE `type`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `type` varchar(80) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `type` (type)
VALUES 
('personal'),('partner'),('team');

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `status` varchar(80) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `status` (status)
VALUES 
('unconfirmed'),('upcoming'),('finished');

DROP TABLE IF EXISTS `meeting`;

CREATE TABLE `meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `initializer` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  
  KEY `FK_USER_idx` (`initializer`),
  
  CONSTRAINT `FK_USER_07` 
  FOREIGN KEY (`initializer`) 
  REFERENCES `user`(`id`),
  
  KEY `FK_TYPE_idx` (`type_id`),
  
  CONSTRAINT `FK_TYPE` 
  FOREIGN KEY (`type_id`) 
  REFERENCES `type`(`id`),
  
  KEY `FK_STATUS_idx` (`status_id`),
  
  CONSTRAINT `FK_STATUS` 
  FOREIGN KEY (`status_id`) 
  REFERENCES `status`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users_meetings`;

CREATE TABLE `users_meetings` (
  `user_id` int(11) NOT NULL,
  `meeting_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`meeting_id`),
  
  KEY `FK_MEETING_idx` (`meeting_id`),
  
  CONSTRAINT `FK_USER_06` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_MEETING` FOREIGN KEY (`meeting_id`) 
  REFERENCES `meeting` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
