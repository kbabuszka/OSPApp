DROP TABLE IF EXISTS `firefighter_trainings`;
CREATE TABLE `firefighter_trainings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firefighter_id` int(11) NOT NULL,
  `training_name` int(11) NOT NULL,
  `obtain_date` date DEFAULT NULL,
  `is_expiring` tinyint(4) DEFAULT '0',
  `valid_until` date DEFAULT NULL,
  `certificate_number` varchar(45) DEFAULT NULL,
  `issued_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `firefighter_id` FOREIGN KEY (`firefighter_id`) REFERENCES `firefighters` (`id`),
  CONSTRAINT `training_name` FOREIGN KEY (`training_name`) REFERENCES `training_names` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1028 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `firefighter_trainings`
--

LOCK TABLES `firefighter_trainings` WRITE;
INSERT INTO `firefighter_trainings` VALUES (1,1,1,'2015-04-22',0,NULL,NULL,'KP PSP Wschowa'),(2,1,3,'2007-12-01',0,NULL,'OSP/Z/31','KP PSP Wschowa'),(3,1,5,'2008-10-26',0,NULL,'2/X/08/OSP','KP PSP Wschowa'),(4,1,11,'2009-05-10',1,'2012-05-10','OSP/PSP/29/629/2009','Wielkopolskie Centrum Edukacji Medycznej'),(5,1,8,'2010-10-27',0,NULL,'D/1/2010','KP PSP Wschowa'),(6,1,9,'2013-02-18',0,NULL,'OSPN/4/2013/1/10','KP PSP Wschowa'),(7,1,11,'2013-09-29',1,'2016-09-29','OSP/K/ZOSP/2013/WER/12/02','ZOSP RP Ośrodek Szkoleniowo-Wypoczynkowy'),(8,1,6,'2013-06-29',0,NULL,'OSPRW/4/2013/1/1','KP PSP Wschowa'),(9,1,2,'2015-04-19',0,NULL,'OSPJ/4/2015/8/1','KP PSP Wschowa'),(1004,4,2,'2017-08-24',0,NULL,NULL,NULL),(1021,32,10,NULL,0,NULL,NULL,NULL),(1022,32,6,NULL,0,NULL,NULL,NULL),(1027,31,8,'2019-08-08',0,NULL,NULL,NULL);
UNLOCK TABLES;

--
-- Table structure for table `firefighter_types`
--

DROP TABLE IF EXISTS `firefighter_types`;
CREATE TABLE `firefighter_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `firefighter_types`
--

LOCK TABLES `firefighter_types` WRITE;
INSERT INTO `firefighter_types` VALUES (1,'Zwyczajny'),(2,'Honorowy'),(3,'MDP'),(4,'Wspierający');
UNLOCK TABLES;

--
-- Table structure for table `firefighters`
--

DROP TABLE IF EXISTS `firefighters`;
CREATE TABLE `firefighters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `secondname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) NOT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `birthplace` varchar(45) DEFAULT NULL,
  `pesel` varchar(45) DEFAULT NULL,
  `address_street` varchar(45) DEFAULT NULL,
  `address_1` varchar(45) DEFAULT NULL,
  `address_2` varchar(45) DEFAULT NULL,
  `address_city` varchar(45) DEFAULT NULL,
  `address_postal_code` varchar(45) DEFAULT NULL,
  `join_date` date DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `in_jot` tinyint(4) DEFAULT NULL,
  `health_exam_date` date DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `pesel_UNIQUE` (`pesel`),
  KEY `type_idx` (`type`),
  CONSTRAINT `firefighter_type` FOREIGN KEY (`type`) REFERENCES `firefighter_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `firefighters`
--

LOCK TABLES `firefighters` WRITE;
INSERT INTO `firefighters` VALUES (1,'Kamil',NULL,'Babuszka','M','1990-04-14','Nowa Sól','90041404235','Chopina','21','1','Sława','67-410','2003-06-10','kamil@osp.slawa.pl','661 771 405',1,'2017-03-02',1),(2,'Marcin',NULL,'Kajda','M',NULL,NULL,NULL,'Waryńskiego',NULL,NULL,'Sława','67-410','2019-07-19',NULL,NULL,1,'2019-09-05',1),(3,'Dawid',NULL,'Błaszczak','M',NULL,NULL,'9900909090',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'2019-07-28',1),(4,'Izabela',NULL,'Dolatkowska','K',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(5,'Mariusz ',NULL,'Drogosz','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(6,'Michał',NULL,'Drogosz','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-07-10',NULL,NULL,1,NULL,1),(7,'Piotr',NULL,'Drogosz','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(8,'Łukasz',NULL,'Dudziak','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(9,'Alina',NULL,'Gawluk','M','2019-07-11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-07-21',NULL,NULL,1,'2019-08-02',1),(10,'Jacek',NULL,'Głodowicz','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(11,'Adam',NULL,'Herkt','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(12,'Krystian',NULL,'Hładczuk','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(13,'Sebastian',NULL,'Hładczuk','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(14,'Kuba',NULL,'Hładczuk','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(15,'Marcin',NULL,'Kajda','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(16,'Mariusz',NULL,'Korczyc','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(17,'Mateusz',NULL,'Kowalczyk','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(18,'Bartłomiej',NULL,'Kuchta','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(19,'Katarzyna',NULL,'Miśkiewicz','K',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(20,'Małgorzata',NULL,'Kajda','K',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(21,'Dominik',NULL,'Poddenek','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(22,'Wioletta',NULL,'Poddenek','K',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(23,'Mateusz',NULL,'Poddenek','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(24,'Szymon',NULL,'Ratuszny','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(25,'Mateusz',NULL,'Reszczyński','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(26,'Krzysztof',NULL,'Rohatyński','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(27,'Sylwia',NULL,'Sobczak','K',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(28,'Bartosz',NULL,'Szulc','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(29,'Mateusz',NULL,'Szulc','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(30,'Paweł',NULL,'Szymanowski','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(31,'Andrzej',NULL,'Wojciech','M','2019-08-08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-08-07',NULL,NULL,1,'2019-08-07',1),(32,'Odyseusz',NULL,'Wąsiak','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(33,'Aureliusz',NULL,'Wąsiak','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(34,'Marcin',NULL,'Wąsiak','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(35,'Michał',NULL,'Żelichowski','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1),(37,'Edmund',NULL,'Baron','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2);
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_type` int(11) NOT NULL,
  `notification_text` mediumtext NOT NULL,
  `notification_date` datetime NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_read` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
INSERT INTO `notifications` VALUES (1,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(2,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(3,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(4,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(5,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(6,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(7,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(8,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(9,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(10,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(11,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(12,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(13,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(14,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(15,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 00:00:00',NULL,0),(16,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:22',NULL,0),(17,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:22',NULL,0),(18,1,'Zbliża się termin badań lekarskich strażaka: Izabela Dolatkowska','2019-07-29 15:21:22',NULL,0),(19,1,'Zbliża się termin badań lekarskich strażaka: Mariusz  Drogosz','2019-07-29 15:21:22',NULL,0),(20,1,'Zbliża się termin badań lekarskich strażaka: Michał Drogosz','2019-07-29 15:21:22',NULL,0),(21,1,'Zbliża się termin badań lekarskich strażaka: Piotr Drogosz','2019-07-29 15:21:22',NULL,0),(22,1,'Zbliża się termin badań lekarskich strażaka: Łukasz Dudziak','2019-07-29 15:21:22',NULL,0),(23,2,'Alina Gawluk nie ma aktualnych badań lekarskich!','2019-07-29 15:21:22',NULL,0),(24,1,'Zbliża się termin badań lekarskich strażaka: Jacek Głodowicz','2019-07-29 15:21:22',NULL,0),(25,1,'Zbliża się termin badań lekarskich strażaka: Adam Herkt','2019-07-29 15:21:22',NULL,0),(26,1,'Zbliża się termin badań lekarskich strażaka: Krystian Hładczuk','2019-07-29 15:21:22',NULL,0),(27,1,'Zbliża się termin badań lekarskich strażaka: Sebastian Hładczuk','2019-07-29 15:21:22',NULL,0),(28,1,'Zbliża się termin badań lekarskich strażaka: Kuba Hładczuk','2019-07-29 15:21:22',NULL,0),(29,1,'Zbliża się termin badań lekarskich strażaka: Marcin Kajda','2019-07-29 15:21:22',NULL,0),(30,1,'Zbliża się termin badań lekarskich strażaka: Mariusz Korczyc','2019-07-29 15:21:22',NULL,0),(31,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Kowalczyk','2019-07-29 15:21:22',NULL,0),(32,1,'Zbliża się termin badań lekarskich strażaka: Bartłomiej Kuchta','2019-07-29 15:21:22',NULL,0),(33,1,'Zbliża się termin badań lekarskich strażaka: Katarzyna Miśkiewicz','2019-07-29 15:21:22',NULL,0),(34,1,'Zbliża się termin badań lekarskich strażaka: Małgorzata Kajda','2019-07-29 15:21:22',NULL,0),(35,1,'Zbliża się termin badań lekarskich strażaka: Dominik Poddenek','2019-07-29 15:21:22',NULL,0),(36,1,'Zbliża się termin badań lekarskich strażaka: Wioletta Poddenek','2019-07-29 15:21:22',NULL,0),(37,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Poddenek','2019-07-29 15:21:22',NULL,0),(38,1,'Zbliża się termin badań lekarskich strażaka: Szymon Ratuszny','2019-07-29 15:21:22',NULL,0),(39,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Reszczyński','2019-07-29 15:21:22',NULL,0),(40,1,'Zbliża się termin badań lekarskich strażaka: Krzysztof Rohatyński','2019-07-29 15:21:22',NULL,0),(41,1,'Zbliża się termin badań lekarskich strażaka: Sylwia Sobczak','2019-07-29 15:21:22',NULL,0),(42,1,'Zbliża się termin badań lekarskich strażaka: Bartosz Szulc','2019-07-29 15:21:22',NULL,0),(43,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Szulc','2019-07-29 15:21:22',NULL,0),(44,1,'Zbliża się termin badań lekarskich strażaka: Paweł Szymanowski','2019-07-29 15:21:22',NULL,0),(45,1,'Zbliża się termin badań lekarskich strażaka: Andrzej Wojciech','2019-07-29 15:21:22',NULL,0),(46,1,'Zbliża się termin badań lekarskich strażaka: Odyseusz Wąsiak','2019-07-29 15:21:22',NULL,0),(47,1,'Zbliża się termin badań lekarskich strażaka: Aureliusz Wąsiak','2019-07-29 15:21:22',NULL,0),(48,1,'Zbliża się termin badań lekarskich strażaka: Marcin Wąsiak','2019-07-29 15:21:22',NULL,0),(49,1,'Zbliża się termin badań lekarskich strażaka: Michał Żelichowski','2019-07-29 15:21:22',NULL,0),(50,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:32',NULL,0),(51,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:32',NULL,0),(52,1,'Zbliża się termin badań lekarskich strażaka: Izabela Dolatkowska','2019-07-29 15:21:32',NULL,0),(53,1,'Zbliża się termin badań lekarskich strażaka: Mariusz  Drogosz','2019-07-29 15:21:32',NULL,0),(54,1,'Zbliża się termin badań lekarskich strażaka: Michał Drogosz','2019-07-29 15:21:32',NULL,0),(55,1,'Zbliża się termin badań lekarskich strażaka: Piotr Drogosz','2019-07-29 15:21:32',NULL,0),(56,1,'Zbliża się termin badań lekarskich strażaka: Łukasz Dudziak','2019-07-29 15:21:32',NULL,0),(57,2,'Alina Gawluk nie ma aktualnych badań lekarskich!','2019-07-29 15:21:32',NULL,0),(58,1,'Zbliża się termin badań lekarskich strażaka: Jacek Głodowicz','2019-07-29 15:21:32',NULL,0),(59,1,'Zbliża się termin badań lekarskich strażaka: Adam Herkt','2019-07-29 15:21:32',NULL,0),(60,1,'Zbliża się termin badań lekarskich strażaka: Krystian Hładczuk','2019-07-29 15:21:32',NULL,0),(61,1,'Zbliża się termin badań lekarskich strażaka: Sebastian Hładczuk','2019-07-29 15:21:32',NULL,0),(62,1,'Zbliża się termin badań lekarskich strażaka: Kuba Hładczuk','2019-07-29 15:21:32',NULL,0),(63,1,'Zbliża się termin badań lekarskich strażaka: Marcin Kajda','2019-07-29 15:21:32',NULL,0),(64,1,'Zbliża się termin badań lekarskich strażaka: Mariusz Korczyc','2019-07-29 15:21:32',NULL,0),(65,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Kowalczyk','2019-07-29 15:21:32',NULL,0),(66,1,'Zbliża się termin badań lekarskich strażaka: Bartłomiej Kuchta','2019-07-29 15:21:32',NULL,0),(67,1,'Zbliża się termin badań lekarskich strażaka: Katarzyna Miśkiewicz','2019-07-29 15:21:32',NULL,0),(68,1,'Zbliża się termin badań lekarskich strażaka: Małgorzata Kajda','2019-07-29 15:21:32',NULL,0),(69,1,'Zbliża się termin badań lekarskich strażaka: Dominik Poddenek','2019-07-29 15:21:32',NULL,0),(70,1,'Zbliża się termin badań lekarskich strażaka: Wioletta Poddenek','2019-07-29 15:21:32',NULL,0),(71,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Poddenek','2019-07-29 15:21:32',NULL,0),(72,1,'Zbliża się termin badań lekarskich strażaka: Szymon Ratuszny','2019-07-29 15:21:32',NULL,0),(73,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Reszczyński','2019-07-29 15:21:32',NULL,0),(74,1,'Zbliża się termin badań lekarskich strażaka: Krzysztof Rohatyński','2019-07-29 15:21:32',NULL,0),(75,1,'Zbliża się termin badań lekarskich strażaka: Sylwia Sobczak','2019-07-29 15:21:32',NULL,0),(76,1,'Zbliża się termin badań lekarskich strażaka: Bartosz Szulc','2019-07-29 15:21:32',NULL,0),(77,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Szulc','2019-07-29 15:21:32',NULL,0),(78,1,'Zbliża się termin badań lekarskich strażaka: Paweł Szymanowski','2019-07-29 15:21:32',NULL,0),(79,1,'Zbliża się termin badań lekarskich strażaka: Andrzej Wojciech','2019-07-29 15:21:32',NULL,0),(80,1,'Zbliża się termin badań lekarskich strażaka: Odyseusz Wąsiak','2019-07-29 15:21:32',NULL,0),(81,1,'Zbliża się termin badań lekarskich strażaka: Aureliusz Wąsiak','2019-07-29 15:21:32',NULL,0),(82,1,'Zbliża się termin badań lekarskich strażaka: Marcin Wąsiak','2019-07-29 15:21:32',NULL,0),(83,1,'Zbliża się termin badań lekarskich strażaka: Michał Żelichowski','2019-07-29 15:21:32',NULL,0),(84,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:42',NULL,0),(85,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:42',NULL,0),(86,1,'Zbliża się termin badań lekarskich strażaka: Izabela Dolatkowska','2019-07-29 15:21:42',NULL,0),(87,1,'Zbliża się termin badań lekarskich strażaka: Mariusz  Drogosz','2019-07-29 15:21:42',NULL,0),(88,1,'Zbliża się termin badań lekarskich strażaka: Michał Drogosz','2019-07-29 15:21:42',NULL,0),(89,1,'Zbliża się termin badań lekarskich strażaka: Piotr Drogosz','2019-07-29 15:21:42',NULL,0),(90,1,'Zbliża się termin badań lekarskich strażaka: Łukasz Dudziak','2019-07-29 15:21:42',NULL,0),(91,2,'Alina Gawluk nie ma aktualnych badań lekarskich!','2019-07-29 15:21:42',NULL,0),(92,1,'Zbliża się termin badań lekarskich strażaka: Jacek Głodowicz','2019-07-29 15:21:42',NULL,0),(93,1,'Zbliża się termin badań lekarskich strażaka: Adam Herkt','2019-07-29 15:21:42',NULL,0),(94,1,'Zbliża się termin badań lekarskich strażaka: Krystian Hładczuk','2019-07-29 15:21:42',NULL,0),(95,1,'Zbliża się termin badań lekarskich strażaka: Sebastian Hładczuk','2019-07-29 15:21:42',NULL,0),(96,1,'Zbliża się termin badań lekarskich strażaka: Kuba Hładczuk','2019-07-29 15:21:42',NULL,0),(97,1,'Zbliża się termin badań lekarskich strażaka: Marcin Kajda','2019-07-29 15:21:42',NULL,0),(98,1,'Zbliża się termin badań lekarskich strażaka: Mariusz Korczyc','2019-07-29 15:21:42',NULL,0),(99,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Kowalczyk','2019-07-29 15:21:42',NULL,0),(100,1,'Zbliża się termin badań lekarskich strażaka: Bartłomiej Kuchta','2019-07-29 15:21:42',NULL,0),(101,1,'Zbliża się termin badań lekarskich strażaka: Katarzyna Miśkiewicz','2019-07-29 15:21:42',NULL,0),(102,1,'Zbliża się termin badań lekarskich strażaka: Małgorzata Kajda','2019-07-29 15:21:42',NULL,0),(103,1,'Zbliża się termin badań lekarskich strażaka: Dominik Poddenek','2019-07-29 15:21:42',NULL,0),(104,1,'Zbliża się termin badań lekarskich strażaka: Wioletta Poddenek','2019-07-29 15:21:42',NULL,0),(105,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Poddenek','2019-07-29 15:21:42',NULL,0),(106,1,'Zbliża się termin badań lekarskich strażaka: Szymon Ratuszny','2019-07-29 15:21:42',NULL,0),(107,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Reszczyński','2019-07-29 15:21:42',NULL,0),(108,1,'Zbliża się termin badań lekarskich strażaka: Krzysztof Rohatyński','2019-07-29 15:21:42',NULL,0),(109,1,'Zbliża się termin badań lekarskich strażaka: Sylwia Sobczak','2019-07-29 15:21:42',NULL,0),(110,1,'Zbliża się termin badań lekarskich strażaka: Bartosz Szulc','2019-07-29 15:21:42',NULL,0),(111,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Szulc','2019-07-29 15:21:42',NULL,0),(112,1,'Zbliża się termin badań lekarskich strażaka: Paweł Szymanowski','2019-07-29 15:21:42',NULL,0),(113,1,'Zbliża się termin badań lekarskich strażaka: Andrzej Wojciech','2019-07-29 15:21:42',NULL,0),(114,1,'Zbliża się termin badań lekarskich strażaka: Odyseusz Wąsiak','2019-07-29 15:21:42',NULL,0),(115,1,'Zbliża się termin badań lekarskich strażaka: Aureliusz Wąsiak','2019-07-29 15:21:42',NULL,0),(116,1,'Zbliża się termin badań lekarskich strażaka: Marcin Wąsiak','2019-07-29 15:21:42',NULL,0),(117,1,'Zbliża się termin badań lekarskich strażaka: Michał Żelichowski','2019-07-29 15:21:42',NULL,0),(118,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:52',NULL,0),(119,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:21:52',NULL,0),(120,1,'Zbliża się termin badań lekarskich strażaka: Izabela Dolatkowska','2019-07-29 15:21:52',NULL,0),(121,1,'Zbliża się termin badań lekarskich strażaka: Mariusz  Drogosz','2019-07-29 15:21:52',NULL,0),(122,1,'Zbliża się termin badań lekarskich strażaka: Michał Drogosz','2019-07-29 15:21:52',NULL,0),(123,1,'Zbliża się termin badań lekarskich strażaka: Piotr Drogosz','2019-07-29 15:21:52',NULL,0),(124,1,'Zbliża się termin badań lekarskich strażaka: Łukasz Dudziak','2019-07-29 15:21:52',NULL,0),(125,2,'Alina Gawluk nie ma aktualnych badań lekarskich!','2019-07-29 15:21:52',NULL,0),(126,1,'Zbliża się termin badań lekarskich strażaka: Jacek Głodowicz','2019-07-29 15:21:52',NULL,0),(127,1,'Zbliża się termin badań lekarskich strażaka: Adam Herkt','2019-07-29 15:21:52',NULL,0),(128,1,'Zbliża się termin badań lekarskich strażaka: Krystian Hładczuk','2019-07-29 15:21:52',NULL,0),(129,1,'Zbliża się termin badań lekarskich strażaka: Sebastian Hładczuk','2019-07-29 15:21:52',NULL,0),(130,1,'Zbliża się termin badań lekarskich strażaka: Kuba Hładczuk','2019-07-29 15:21:52',NULL,0),(131,1,'Zbliża się termin badań lekarskich strażaka: Marcin Kajda','2019-07-29 15:21:52',NULL,0),(132,1,'Zbliża się termin badań lekarskich strażaka: Mariusz Korczyc','2019-07-29 15:21:52',NULL,0),(133,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Kowalczyk','2019-07-29 15:21:52',NULL,0),(134,1,'Zbliża się termin badań lekarskich strażaka: Bartłomiej Kuchta','2019-07-29 15:21:52',NULL,0),(135,1,'Zbliża się termin badań lekarskich strażaka: Katarzyna Miśkiewicz','2019-07-29 15:21:52',NULL,0),(136,1,'Zbliża się termin badań lekarskich strażaka: Małgorzata Kajda','2019-07-29 15:21:52',NULL,0),(137,1,'Zbliża się termin badań lekarskich strażaka: Dominik Poddenek','2019-07-29 15:21:52',NULL,0),(138,1,'Zbliża się termin badań lekarskich strażaka: Wioletta Poddenek','2019-07-29 15:21:52',NULL,0),(139,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Poddenek','2019-07-29 15:21:52',NULL,0),(140,1,'Zbliża się termin badań lekarskich strażaka: Szymon Ratuszny','2019-07-29 15:21:52',NULL,0),(141,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Reszczyński','2019-07-29 15:21:52',NULL,0),(142,1,'Zbliża się termin badań lekarskich strażaka: Krzysztof Rohatyński','2019-07-29 15:21:52',NULL,0),(143,1,'Zbliża się termin badań lekarskich strażaka: Sylwia Sobczak','2019-07-29 15:21:52',NULL,0),(144,1,'Zbliża się termin badań lekarskich strażaka: Bartosz Szulc','2019-07-29 15:21:52',NULL,0),(145,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Szulc','2019-07-29 15:21:52',NULL,0),(146,1,'Zbliża się termin badań lekarskich strażaka: Paweł Szymanowski','2019-07-29 15:21:52',NULL,0),(147,1,'Zbliża się termin badań lekarskich strażaka: Andrzej Wojciech','2019-07-29 15:21:52',NULL,0),(148,1,'Zbliża się termin badań lekarskich strażaka: Odyseusz Wąsiak','2019-07-29 15:21:52',NULL,0),(149,1,'Zbliża się termin badań lekarskich strażaka: Aureliusz Wąsiak','2019-07-29 15:21:52',NULL,0),(150,1,'Zbliża się termin badań lekarskich strażaka: Marcin Wąsiak','2019-07-29 15:21:52',NULL,0),(151,1,'Zbliża się termin badań lekarskich strażaka: Michał Żelichowski','2019-07-29 15:21:52',NULL,0),(152,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:22:02',NULL,0),(153,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-07-29 15:22:02',NULL,0),(154,1,'Zbliża się termin badań lekarskich strażaka: Izabela Dolatkowska','2019-07-29 15:22:02',NULL,0),(155,1,'Zbliża się termin badań lekarskich strażaka: Mariusz  Drogosz','2019-07-29 15:22:02',NULL,0),(156,1,'Zbliża się termin badań lekarskich strażaka: Michał Drogosz','2019-07-29 15:22:02',NULL,0),(157,1,'Zbliża się termin badań lekarskich strażaka: Piotr Drogosz','2019-07-29 15:22:02',NULL,0),(158,1,'Zbliża się termin badań lekarskich strażaka: Łukasz Dudziak','2019-07-29 15:22:02',NULL,0),(159,2,'Alina Gawluk nie ma aktualnych badań lekarskich!','2019-07-29 15:22:02',NULL,0),(160,1,'Zbliża się termin badań lekarskich strażaka: Jacek Głodowicz','2019-07-29 15:22:02',NULL,0),(161,1,'Zbliża się termin badań lekarskich strażaka: Adam Herkt','2019-07-29 15:22:02',NULL,0),(162,1,'Zbliża się termin badań lekarskich strażaka: Krystian Hładczuk','2019-07-29 15:22:02',NULL,0),(163,1,'Zbliża się termin badań lekarskich strażaka: Sebastian Hładczuk','2019-07-29 15:22:02',NULL,0),(164,1,'Zbliża się termin badań lekarskich strażaka: Kuba Hładczuk','2019-07-29 15:22:02',NULL,0),(165,1,'Zbliża się termin badań lekarskich strażaka: Marcin Kajda','2019-07-29 15:22:02',NULL,0),(166,1,'Zbliża się termin badań lekarskich strażaka: Mariusz Korczyc','2019-07-29 15:22:02',NULL,0),(167,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Kowalczyk','2019-07-29 15:22:02',NULL,0),(168,1,'Zbliża się termin badań lekarskich strażaka: Bartłomiej Kuchta','2019-07-29 15:22:02',NULL,0),(169,1,'Zbliża się termin badań lekarskich strażaka: Katarzyna Miśkiewicz','2019-07-29 15:22:02',NULL,0),(170,1,'Zbliża się termin badań lekarskich strażaka: Małgorzata Kajda','2019-07-29 15:22:02',NULL,0),(171,1,'Zbliża się termin badań lekarskich strażaka: Dominik Poddenek','2019-07-29 15:22:02',NULL,0),(172,1,'Zbliża się termin badań lekarskich strażaka: Wioletta Poddenek','2019-07-29 15:22:02',NULL,0),(173,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Poddenek','2019-07-29 15:22:02',NULL,0),(174,1,'Zbliża się termin badań lekarskich strażaka: Szymon Ratuszny','2019-07-29 15:22:02',NULL,0),(175,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Reszczyński','2019-07-29 15:22:02',NULL,0),(176,1,'Zbliża się termin badań lekarskich strażaka: Krzysztof Rohatyński','2019-07-29 15:22:02',NULL,0),(177,1,'Zbliża się termin badań lekarskich strażaka: Sylwia Sobczak','2019-07-29 15:22:02',NULL,0),(178,1,'Zbliża się termin badań lekarskich strażaka: Bartosz Szulc','2019-07-29 15:22:02',NULL,0),(179,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Szulc','2019-07-29 15:22:02',NULL,0),(180,1,'Zbliża się termin badań lekarskich strażaka: Paweł Szymanowski','2019-07-29 15:22:02',NULL,0),(181,1,'Zbliża się termin badań lekarskich strażaka: Andrzej Wojciech','2019-07-29 15:22:02',NULL,0),(182,1,'Zbliża się termin badań lekarskich strażaka: Odyseusz Wąsiak','2019-07-29 15:22:02',NULL,0),(183,1,'Zbliża się termin badań lekarskich strażaka: Aureliusz Wąsiak','2019-07-29 15:22:02',NULL,0),(184,1,'Zbliża się termin badań lekarskich strażaka: Marcin Wąsiak','2019-07-29 15:22:02',NULL,0),(185,1,'Zbliża się termin badań lekarskich strażaka: Michał Żelichowski','2019-07-29 15:22:02',NULL,0),(186,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-08-01 08:00:00',NULL,0),(187,2,'Dawid Błaszczak nie ma aktualnych badań lekarskich!','2019-08-01 08:00:00',NULL,0),(188,1,'Zbliża się termin badań lekarskich strażaka: Izabela Dolatkowska','2019-08-01 08:00:00',NULL,0),(189,1,'Zbliża się termin badań lekarskich strażaka: Mariusz  Drogosz','2019-08-01 08:00:00',NULL,0),(190,1,'Zbliża się termin badań lekarskich strażaka: Michał Drogosz','2019-08-01 08:00:00',NULL,0),(191,1,'Zbliża się termin badań lekarskich strażaka: Piotr Drogosz','2019-08-01 08:00:00',NULL,0),(192,1,'Zbliża się termin badań lekarskich strażaka: Łukasz Dudziak','2019-08-01 08:00:00',NULL,0),(193,2,'Alina Gawluk nie ma aktualnych badań lekarskich!','2019-08-01 08:00:00',NULL,0),(194,1,'Zbliża się termin badań lekarskich strażaka: Jacek Głodowicz','2019-08-01 08:00:00',NULL,0),(195,1,'Zbliża się termin badań lekarskich strażaka: Adam Herkt','2019-08-01 08:00:00',NULL,0),(196,1,'Zbliża się termin badań lekarskich strażaka: Krystian Hładczuk','2019-08-01 08:00:00',NULL,0),(197,1,'Zbliża się termin badań lekarskich strażaka: Sebastian Hładczuk','2019-08-01 08:00:00',NULL,0),(198,1,'Zbliża się termin badań lekarskich strażaka: Kuba Hładczuk','2019-08-01 08:00:00',NULL,0),(199,1,'Zbliża się termin badań lekarskich strażaka: Marcin Kajda','2019-08-01 08:00:00',NULL,0),(200,1,'Zbliża się termin badań lekarskich strażaka: Mariusz Korczyc','2019-08-01 08:00:00',NULL,0),(201,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Kowalczyk','2019-08-01 08:00:00',NULL,0),(202,1,'Zbliża się termin badań lekarskich strażaka: Bartłomiej Kuchta','2019-08-01 08:00:00',NULL,0),(203,1,'Zbliża się termin badań lekarskich strażaka: Katarzyna Miśkiewicz','2019-08-01 08:00:00',NULL,0),(204,1,'Zbliża się termin badań lekarskich strażaka: Małgorzata Kajda','2019-08-01 08:00:00',NULL,0),(205,1,'Zbliża się termin badań lekarskich strażaka: Dominik Poddenek','2019-08-01 08:00:00',NULL,0),(206,1,'Zbliża się termin badań lekarskich strażaka: Wioletta Poddenek','2019-08-01 08:00:00',NULL,0),(207,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Poddenek','2019-08-01 08:00:00',NULL,0),(208,1,'Zbliża się termin badań lekarskich strażaka: Szymon Ratuszny','2019-08-01 08:00:00',NULL,0),(209,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Reszczyński','2019-08-01 08:00:00',NULL,0),(210,1,'Zbliża się termin badań lekarskich strażaka: Krzysztof Rohatyński','2019-08-01 08:00:00',NULL,0),(211,1,'Zbliża się termin badań lekarskich strażaka: Sylwia Sobczak','2019-08-01 08:00:00',NULL,0),(212,1,'Zbliża się termin badań lekarskich strażaka: Bartosz Szulc','2019-08-01 08:00:00',NULL,0),(213,1,'Zbliża się termin badań lekarskich strażaka: Mateusz Szulc','2019-08-01 08:00:00',NULL,0),(214,1,'Zbliża się termin badań lekarskich strażaka: Paweł Szymanowski','2019-08-01 08:00:00',NULL,0),(215,1,'Zbliża się termin badań lekarskich strażaka: Andrzej Wojciech','2019-08-01 08:00:00',NULL,0),(216,1,'Zbliża się termin badań lekarskich strażaka: Odyseusz Wąsiak','2019-08-01 08:00:00',NULL,0),(217,1,'Zbliża się termin badań lekarskich strażaka: Aureliusz Wąsiak','2019-08-01 08:00:00',NULL,0),(218,1,'Zbliża się termin badań lekarskich strażaka: Marcin Wąsiak','2019-08-01 08:00:00',NULL,0),(219,1,'Zbliża się termin badań lekarskich strażaka: Michał Żelichowski','2019-08-01 08:00:00',NULL,0);
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
INSERT INTO `roles` VALUES (1,'Administrator');
UNLOCK TABLES;

--
-- Table structure for table `training_names`
--

DROP TABLE IF EXISTS `training_names`;
CREATE TABLE `training_names` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `training_names`
--

LOCK TABLES `training_names` WRITE;
INSERT INTO `training_names` VALUES (1,'Szkolenie BHP'),(2,'Szkolenie podstawowe strażaków ratowników OSP realizowane jednoetapowo'),(3,'Szkolenie podstawowe strażaków ratowników OSP etap I'),(4,'Szkolenie podstawowe strażaków ratowników OSP etap II'),(5,'Szkolenie Specjalistyczne z zakresu ratownictwa technicznego Strażaków Ratowników OSP'),(6,'Szkolenie Strażaków Ratowników OSP z zakresu działań przeciwpowodziowych oraz ratownictwa na wodach'),(7,'Szkolenie Kierowców - Konserwatorów Sprzętu Ratowniczego OSP'),(8,'Szkolenie dowódców OSP'),(9,'Szkolenie naczelników OSP'),(10,'Szkolenie Komendantów Gminnych ZOSP RP'),(11,'Kurs Kwalifikowanej Pierwszej Pomocy (KPP)');
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
INSERT INTO `user_roles` VALUES (1,1);
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `display_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(60) NOT NULL,
  `active` int(11) DEFAULT NULL,
  `firefighter_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `firefighter_id_UNIQUE` (`firefighter_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  CONSTRAINT `user_firefighter_id` FOREIGN KEY (`firefighter_id`) REFERENCES `firefighters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'kamil','Kamil Babuszka','kamil@osp.slawa.pl','$2a$10$M.Q7fwlheuT81qu4d/E2IOyMUGyzvku4A2wbRFqyhuzqE6wrhb8zu',1,1);
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
CREATE TABLE `vehicles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shortname` varchar(45) DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `model` varchar(45) DEFAULT NULL,
  `registration_plate` varchar(9) DEFAULT NULL,
  `radio_number` varchar(45) DEFAULT NULL,
  `technical_inspection` date DEFAULT NULL,
  `assurance` date DEFAULT NULL,
  `seats` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
INSERT INTO `vehicles` VALUES (1,'Mercedes','Mercedes','Atego 1326','FWS U296','439[F]01',NULL,NULL,6);
UNLOCK TABLES;


-- Dump completed on 2019-09-16 18:27:53
