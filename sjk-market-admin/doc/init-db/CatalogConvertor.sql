-- MySQL dump 10.13  Distrib 5.5.27, for Win64 (x86)
--
-- Host: 10.20.239.23    Database: AndroidMarket
-- ------------------------------------------------------
-- Server version	5.5.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CatalogConvertor`
--

DROP TABLE IF EXISTS `CatalogConvertor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CatalogConvertor` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MarketName` varchar(20) NOT NULL,
  `Catalog` smallint(5) unsigned NOT NULL,
  `SubCatalog` int(10) unsigned NOT NULL,
  `TargetCatalog` smallint(5) unsigned NOT NULL,
  `TargetSubCatalog` int(10) unsigned NOT NULL,
  `SubCatalogName` varchar(10) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Unique` (`Catalog`,`MarketName`,`SubCatalog`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CatalogConvertor`
--

LOCK TABLES `CatalogConvertor` WRITE;
/*!40000 ALTER TABLE `CatalogConvertor` DISABLE KEYS */;
INSERT INTO `CatalogConvertor` VALUES (1,'eoemarket',1,15,1,14,'系统工具'),(2,'eoemarket',1,8,1,9,'生活娱乐'),(3,'eoemarket',1,5,1,4,'社区交友'),(4,'eoemarket',1,10,1,3,'网络通信'),(5,'eoemarket',1,37,1,6,'小说漫画'),(6,'eoemarket',1,9,1,8,'铃声视频'),(7,'eoemarket',1,4,1,7,'桌面美化'),(8,'eoemarket',1,36,1,20,'商务财经'),(9,'eoemarket',2,31,2,45,'飞行射击'),(10,'eoemarket',2,19,2,44,'动作冒险'),(11,'eoemarket',2,22,2,42,'益智休闲'),(12,'eoemarket',2,32,2,46,'角色扮演'),(13,'eoemarket',2,21,2,41,'棋牌天地'),(14,'eoemarket',2,20,2,43,'经营策略'),(15,'eoemarket',2,33,2,47,'体育竞技'),(16,'eoemarket',2,35,2,49,'网络游戏'),(17,'AppChina',1,307,1,3,'通话通讯'),(18,'AppChina',1,308,1,4,'社交网络'),(19,'AppChina',1,311,1,5,'新闻资讯'),(20,'AppChina',1,312,1,6,'图书阅读'),(21,'AppChina',1,303,1,7,'动态壁纸'),(22,'AppChina',1,309,1,7,'主题插件'),(23,'AppChina',1,306,1,8,'影音播放'),(24,'AppChina',1,305,1,9,'便捷生活'),(25,'AppChina',1,314,1,10,'网购支付'),(26,'AppChina',1,310,1,11,'拍摄美化'),(27,'AppChina',1,302,1,12,'浏览器'),(28,'AppChina',1,301,1,13,'输入法'),(29,'AppChina',1,304,1,14,'系统工具'),(30,'AppChina',1,313,1,16,'办公学习'),(31,'AppChina',1,315,1,21,'金融理财'),(32,'AppChina',2,419,2,41,'棋牌桌游'),(33,'AppChina',2,411,2,42,'益智'),(34,'AppChina',2,413,2,43,'策略'),(35,'AppChina',2,414,2,44,'动作冒险'),(36,'AppChina',2,412,2,45,'射击'),(37,'AppChina',2,417,2,46,'角色扮演'),(38,'AppChina',2,415,2,47,'赛车竞速'),(39,'AppChina',2,416,2,48,'模拟经营'),(40,'AppChina',2,421,2,49,'音乐游戏'),(41,'AppChina',2,420,2,46,'虚拟养成'),(42,'AppChina',2,418,2,47,'体育运动'),(43,'AppChina',2,423,2,48,'辅助工具'),(44,'AppChina',2,422,2,49,'对战格斗');
/*!40000 ALTER TABLE `CatalogConvertor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-20 17:30:57
