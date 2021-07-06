-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: taptap
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` varchar(255) NOT NULL,
  `game_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `score` int DEFAULT NULL,
  `likes_count` int DEFAULT NULL,
  `comment_at` mediumtext,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES ('1410876048480002049','1410875704693874689','1410875009689227265','这是user01评论的内容111111111111111',10,0,'1625214037401'),('1411293002323742722','1411211008827027458','1410875009689227265','tttttttttttttt',7,0,'1625313446946'),('1411293430490877953','1411211008827027458','1410875032703373313','user02ooooooooooo',9,0,'1625313549039');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dynamic`
--

DROP TABLE IF EXISTS `dynamic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dynamic` (
  `dynamic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `game_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `img_urls` varchar(255) DEFAULT NULL,
  `likes_count` int DEFAULT NULL,
  `publish_at` mediumtext,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dynamic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dynamic`
--

LOCK TABLES `dynamic` WRITE;
/*!40000 ALTER TABLE `dynamic` DISABLE KEYS */;
INSERT INTO `dynamic` VALUES ('1410878418295230466','1410875704693874689','1410875009689227265','user01修改user01的评论','./images/smduck.png',0,'1625214855092','1410875704693874689'),('1411233448886038530','1411233077430087681','1410875009689227265','rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr','http://119.91.130.198/images/dynamic猫咪.png',0,'1625299248334','rrrr'),('1411233780718497794','1411233077430087681','1410875009689227265','rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr','http://119.91.130.198/images/dynamic/猫咪.png',0,'1625299327422','rrrr');
/*!40000 ALTER TABLE `dynamic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `game_id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` int DEFAULT NULL,
  `issuer` varchar(255) DEFAULT NULL,
  `downloads` int DEFAULT NULL,
  `avg_score` double DEFAULT NULL,
  `comment_count` int DEFAULT NULL,
  `interest_count` int DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `display_drawings` varchar(255) DEFAULT NULL,
  `brief_intro` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `heat` double DEFAULT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES ('1410875704693874689','bbbbbbb',99,'ccccccccc',0,10,1,2,'http://119.91.130.198/images/games/1410875704693874689-icon-毛笔.png','http://119.91.130.198/images/games/1410875704693874689-dd-1-猫咪.png|http://119.91.130.198/images/games/1410875704693874689-dd-2-毛笔.png','ddddddddd','互殴 社交',4),('1411211008827027458','aaaaaaaaaaaa',34,'aaaaaaaaaaaa',0,8,2,0,'http://119.91.130.198/images/games/1411211008827027458-icon-毛笔.png','http://119.91.130.198/images/games/1411211008827027458-dd-1-footprint.png|http://119.91.130.198/images/games/1411211008827027458-dd-2-smduck.png','eeeeeee','eee',9),('1411211069732515842','bccb',34,'cc',0,6,0,3,'','','cc',NULL,1),('1411211091928772609','dd',34,'dd',0,0,0,0,'','','dd','社交',6),('1411211113231642625','ee',34,'ee',0,8,0,5,'','','ee','社交',0),('1411211132466720769','ff',34,'ff',0,1,0,0,'','','ff','互殴',23),('1411211151680827394','gg',34,'gg',0,0,0,0,'','','gg','互殴社交',0),('1411211410855215105','hh',34,'hh',0,2,0,8,'','','hh','社交互殴',0),('1411225159691472898','qq',45,'qq',0,0,0,34,'./images/icon/smduck.png','./images/display-drawings/footprint.png','qqqqqqqqqq','互殴 社交',12),('1411233077430087681','ww',999,'ww',0,9,0,10,'http://119.91.130.198/images/icon/蛋.png','http://119.91.130.198/images/display-drawings/河马.png','ww','社交',0),('1411939999829467137','qwe',333,'qwe',0,0,0,0,'http://119.91.130.198/images/games/1411939999829467137-icon-smduck.png','http://119.91.130.198/images/games/1411939999829467137-dd-1-footprint.png|http://119.91.130.198/images/games/1411939999829467137-dd-2-smduck.png','qweqwe','社交 互殴',0);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `game_id` varchar(255) DEFAULT NULL,
  `create_at` date DEFAULT NULL,
  `last_login_at` date DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1410875009689227265','uuuuser01','user01','698d51a19d8a121ce581499d7b701668','http://119.91.130.198/images/avatar/9f6ae08f103a7b7159bf00f82913412e.png',NULL,NULL,NULL),('1410875032703373313','222','user02','698d51a19d8a121ce581499d7b701668',NULL,NULL,NULL,NULL),('1410875032703373333','333','user03','698d51a19d8a121ce581499d7b701668',NULL,NULL,NULL,NULL),('1410875032703373343','444','user04','698d51a19d8a121ce581499d7b701668',NULL,NULL,NULL,NULL),('1410875032703373353','555','user05','698d51a19d8a121ce581499d7b701668',NULL,NULL,NULL,NULL),('1410875032703373364','666','user06','698d51a19d8a121ce581499d7b701668',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_collect_dynamic`
--

DROP TABLE IF EXISTS `user_collect_dynamic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_collect_dynamic` (
  `user_id` varchar(255) NOT NULL,
  `dynamic_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`dynamic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collect_dynamic`
--

LOCK TABLES `user_collect_dynamic` WRITE;
/*!40000 ALTER TABLE `user_collect_dynamic` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_collect_dynamic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_likes_comment`
--

DROP TABLE IF EXISTS `user_likes_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_likes_comment` (
  `comment_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`comment_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_likes_comment`
--

LOCK TABLES `user_likes_comment` WRITE;
/*!40000 ALTER TABLE `user_likes_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_likes_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_likes_dynamic`
--

DROP TABLE IF EXISTS `user_likes_dynamic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_likes_dynamic` (
  `user_id` varchar(255) NOT NULL,
  `dynamic_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`dynamic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_likes_dynamic`
--

LOCK TABLES `user_likes_dynamic` WRITE;
/*!40000 ALTER TABLE `user_likes_dynamic` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_likes_dynamic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_likes_game`
--

DROP TABLE IF EXISTS `user_likes_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_likes_game` (
  `user_id` varchar(255) NOT NULL,
  `game_Id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`game_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_likes_game`
--

LOCK TABLES `user_likes_game` WRITE;
/*!40000 ALTER TABLE `user_likes_game` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_likes_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'taptap'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-06 18:03:35
