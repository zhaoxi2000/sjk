-- MySQL dump 10.13  Distrib 5.5.15, for Linux (x86_64)
--
-- Host: localhost    Database: AndroidMarket
-- ------------------------------------------------------
-- Server version	5.5.15-log

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
-- Table structure for table `Activity`
--

DROP TABLE IF EXISTS `Activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Activity` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Email` varchar(200) NOT NULL DEFAULT '',
  `ActivityCode` char(8) NOT NULL DEFAULT '',
  `UserId` varchar(10) NOT NULL DEFAULT '',
  `SentEmail` tinyint(1) NOT NULL DEFAULT '0',
  `SentEmailDateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GenUserIdTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Ip` varchar(60) NOT NULL DEFAULT '',
  `UUID` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `UserId` (`UserId`),
  KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `App`
--

DROP TABLE IF EXISTS `App`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `App` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MarketAppId` int(10) unsigned NOT NULL COMMENT 'In the table of MarketApp.',
  `MarketName` varchar(20) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `EngName` varchar(45) DEFAULT NULL,
  `Catalog` smallint(5) unsigned NOT NULL,
  `SubCatalog` int(10) unsigned NOT NULL,
  `Size` int(10) unsigned NOT NULL,
  `FreeSize` bigint(20) unsigned NOT NULL DEFAULT '0',
  `DownloadUrl` varchar(200) NOT NULL,
  `LogoUrl` varchar(200) NOT NULL,
  `Description` text NOT NULL,
  `UpdateInfo` text,
  `PublisherShortName` varchar(145) NOT NULL,
  `Version` varchar(125) NOT NULL COMMENT '开发者提供给用户看的字串版本如 1.1.2',
  `Minsdkversion` smallint(6) DEFAULT NULL COMMENT '应用程序支持的 sdk api，是一个整数，目前的范围为 1-13',
  `VersionCode` bigint(20) unsigned NOT NULL COMMENT '应用的内部版本号，值大于等于 0',
  `OSVersion` varchar(80) NOT NULL,
  `Pkname` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'package name',
  `LastUpdateTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `LastFetchTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `DetailUrl` varchar(450) DEFAULT NULL,
  `Price` float(5,2) NOT NULL,
  `Screens` varchar(200) DEFAULT NULL COMMENT '320*480,480*800 适用的分辨率',
  `Models` text,
  `Keywords` text NOT NULL,
  `StarRating` float(3,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '用户评分',
  `ViewCount` int(10) unsigned NOT NULL DEFAULT '0',
  `Downloads` int(10) unsigned NOT NULL DEFAULT '0',
  `DownloadRank` int(10) unsigned NOT NULL DEFAULT '0',
  `Supportpad` smallint(5) unsigned NOT NULL DEFAULT '0',
  `EnumStatus` enum('add','update','delete') NOT NULL,
  `StrImageUrls` text,
  `AuditCatalog` tinyint(1) NOT NULL DEFAULT '0' COMMENT '运营审核过或是修改过分类',
  `Hidden` tinyint(1) NOT NULL DEFAULT '0',
  `PageUrl` varchar(450) DEFAULT NULL COMMENT '我方生成的页面',
  `ShortDesc` varchar(45) DEFAULT '',
  `IndexImgUrl` varchar(200) NOT NULL DEFAULT '',
  `Notice` text,
  `OfficeHomepage` tinytext,
  `Language` varchar(10) NOT NULL DEFAULT '',
  `Audit` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `PathStatus` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT 'SD卡还是手机内存路径的标识.',
  `Permissions` text,
  `SignatureSHA1` char(40) DEFAULT NULL,
  `OfficialSigSHA1` char(40) DEFAULT NULL,
  `ApkScanTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `Advertises` text,
  `AdActionTypes` varchar(45) DEFAULT '',
  `AdPopupTypes` varchar(45) DEFAULT '',
  `Spite` tinyint(4) NOT NULL DEFAULT '-1',
  `AdRisk` tinyint(4) NOT NULL DEFAULT '-1',
  `RealDownload` int(10) unsigned NOT NULL DEFAULT '0',
  `DeltaDownload` int(10) NOT NULL DEFAULT '0' COMMENT '人工增加的下载量',
  `LastDayDownload` int(10) unsigned NOT NULL DEFAULT '0',
  `LastDayDelta` int(10) unsigned NOT NULL DEFAULT '0',
  `LastWeekDownload` int(10) unsigned NOT NULL DEFAULT '0',
  `LastWeekDelta` int(10) unsigned NOT NULL DEFAULT '0',
  `VirusKind` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `VirusBehaviors` text,
  `VirusName` tinytext,
  `Md5` char(32) DEFAULT NULL,
  `AppId` int(10) unsigned NOT NULL DEFAULT '0',
  `ApkId` int(10) unsigned NOT NULL DEFAULT '0',
  `Logo1Url` varchar(200) DEFAULT NULL,
  `Status` int(11) NOT NULL DEFAULT '1',
  `AutoCover` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否自动覆盖 1 是 0 否',
  `ScSta` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '1.渠道商付费 ,  2.渠道商免费. 3....',
  PRIMARY KEY (`Id`),
  KEY `MarketName` (`MarketName`) USING BTREE,
  KEY `Name` (`Name`) USING BTREE,
  KEY `Hidden` (`Hidden`) USING BTREE,
  KEY `LastUpdateTime` (`LastUpdateTime`),
  KEY `Downloads` (`Downloads`),
  KEY `FKMarketAppId` (`MarketAppId`),
  KEY `LastFetchTime` (`LastFetchTime`),
  KEY `DownloadRank` (`DownloadRank`),
  KEY `SignatureSha1` (`SignatureSHA1`),
  KEY `LastDayDelta` (`LastDayDelta`),
  KEY `Catalog_SubIndexs` (`Catalog`,`SubCatalog`) USING BTREE,
  KEY `CatalogIndex` (`Catalog`) USING BTREE,
  KEY `Pk_Sign` (`Pkname`,`SignatureSHA1`),
  KEY `index_name` (`Md5`),
  KEY `Status` (`Status`),
  KEY `LastWeekDelta` (`LastWeekDelta`)
) ENGINE=InnoDB AUTO_INCREMENT=444212 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AppAndTag`
--

DROP TABLE IF EXISTS `AppAndTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AppAndTag` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AppId` int(10) unsigned NOT NULL,
  `TagId` int(10) unsigned NOT NULL,
  `Rank` int(10) unsigned DEFAULT '999999999',
  `TagName` varchar(20) NOT NULL,
  `TagType` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1.专题  2.普通标签',
  PRIMARY KEY (`Id`),
  KEY `TagType` (`TagType`),
  KEY `AppId` (`AppId`),
  KEY `Rank` (`Rank`),
  KEY `TagId` (`TagId`)
) ENGINE=InnoDB AUTO_INCREMENT=2190 DEFAULT CHARSET=utf8 COMMENT='中间表做冗余设计.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AppHistory4Index`
--

DROP TABLE IF EXISTS `AppHistory4Index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AppHistory4Index` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `AppId` int(10) unsigned NOT NULL,
  `AppStatus` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '1: add , 2: update , 3: delete ',
  `CreateTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00' COMMENT '该条记录产生的时间，由aop程序维护',
  `LastOpTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00' COMMENT '最新操作时间，由aop程序维护',
  `LastIndexTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00' COMMENT '最新索引时间，由索引程序维护',
  `IndexStatus` tinyint(3) NOT NULL DEFAULT '0' COMMENT '索引状态,1:已经生成过索引,0:需要重新索引,-1:需要删除索引',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `AppId_UNIQUE` (`AppId`),
  KEY `AppStatus` (`AppStatus`) USING BTREE,
  KEY `IndexStatus` (`IndexStatus`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9116 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BigGamePack`
--

DROP TABLE IF EXISTS `BigGamePack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BigGamePack` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `MarketAppId` int(11) unsigned NOT NULL,
  `Cputype` tinyint(11) unsigned NOT NULL,
  `Url` varchar(200) NOT NULL,
  `Size` int(11) unsigned NOT NULL,
  `FreeSize` bigint(20) unsigned NOT NULL,
  `MarketUpdateTime` datetime DEFAULT NULL,
  `UnsupportPhoneType` varchar(100) DEFAULT NULL COMMENT '不支持的手机型号',
  PRIMARY KEY (`Id`),
  KEY `MarketAppId_inx` (`MarketAppId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Catalog`
--

DROP TABLE IF EXISTS `Catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Catalog` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PId` smallint(5) unsigned NOT NULL,
  `Name` varchar(10) NOT NULL,
  `Rank` int(10) unsigned DEFAULT '4294967295',
  `Keywords` varchar(200) NOT NULL DEFAULT '',
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `SubCatalogName` varchar(10) NOT NULL,
  `TargetCatalog` smallint(5) unsigned NOT NULL,
  `TargetSubCatalog` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Unique` (`Catalog`,`MarketName`,`SubCatalog`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Keyword`
--

DROP TABLE IF EXISTS `Keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Keyword` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `TotalHits` int(10) unsigned NOT NULL,
  `RankType` enum('DOWNLOAD','DOCUMENT','ONLY_NAME_DOCUMENT','ONLY_NAME_DOWNLOAD') NOT NULL,
  `TargetKeyword` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Market`
--

DROP TABLE IF EXISTS `Market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Market` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MarketName` varchar(20) NOT NULL,
  `Rank` int(10) unsigned NOT NULL DEFAULT '999999999',
  `AllowAccessKey` varchar(32) NOT NULL DEFAULT '' COMMENT '允许对方来访问我们的接口时的key.',
  `Downloads` bigint(20) unsigned NOT NULL DEFAULT '0',
  `LoginKey` varchar(36) NOT NULL DEFAULT '' COMMENT '访问对方的key.',
  `Security` varchar(45) NOT NULL DEFAULT '' COMMENT '渠道号或是市场对我们其它标识.',
  `PageSize` int(10) unsigned NOT NULL DEFAULT '100',
  `FullUrl` varchar(450) DEFAULT NULL,
  `FullLastTime` datetime DEFAULT NULL,
  `FullTotalPages` int(10) unsigned NOT NULL DEFAULT '0',
  `FullLastReqCurrentPage` int(10) unsigned NOT NULL DEFAULT '0',
  `IncrementUrl` varchar(450) DEFAULT NULL,
  `IncrementLastTime` datetime DEFAULT NULL,
  `IncrementTotalPages` int(10) unsigned NOT NULL DEFAULT '0',
  `IncrementLastReqCurrentPage` int(10) unsigned NOT NULL DEFAULT '0',
  `Status` enum('DROP','OK') NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `MarketName_UNIQUE` (`MarketName`) USING BTREE,
  KEY `LoginIndexs` (`AllowAccessKey`,`MarketName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MarketApp`
--

DROP TABLE IF EXISTS `MarketApp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MarketApp` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AppId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'In the market.',
  `ApkId` int(10) unsigned NOT NULL DEFAULT '0',
  `MarketName` varchar(20) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Catalog` smallint(5) unsigned NOT NULL,
  `SubCatalog` int(10) unsigned NOT NULL,
  `SubCatalogName` varchar(10) NOT NULL,
  `Size` int(10) unsigned NOT NULL,
  `DownloadUrl` varchar(200) NOT NULL,
  `LogoUrl` varchar(200) NOT NULL,
  `Description` text NOT NULL,
  `UpdateInfo` text,
  `PublisherShortName` varchar(145) NOT NULL,
  `Version` varchar(125) NOT NULL COMMENT '开发者提供给用户看的字串版本如 1.1.2',
  `Minsdkversion` smallint(6) DEFAULT NULL COMMENT '应用程序支持的 sdk api，是一个整数，目前的范围为 1-13',
  `VersionCode` bigint(20) unsigned NOT NULL COMMENT '应用的内部版本号，值大于等于 0',
  `OSVersion` varchar(80) NOT NULL,
  `Pkname` varchar(200) NOT NULL COMMENT 'package name',
  `LastUpdateTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `DetailUrl` varchar(450) DEFAULT NULL,
  `Price` float(5,2) NOT NULL,
  `Screens` varchar(200) DEFAULT NULL COMMENT '320*480,480*800 适用的分辨率',
  `Models` text,
  `Keywords` text NOT NULL,
  `StarRating` float(3,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '用户评分',
  `ViewCount` int(10) unsigned NOT NULL DEFAULT '0',
  `Downloads` int(10) unsigned NOT NULL DEFAULT '0',
  `Supportpad` smallint(5) unsigned NOT NULL DEFAULT '0',
  `EnumStatus` enum('add','update','delete') NOT NULL,
  `StrImageUrls` text,
  `MarketApkScanTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `MarketUpdateTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `AppFetchTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `SignatureSha1` char(40) DEFAULT NULL,
  `Advertises` text,
  `AdActionTypes` varchar(45) DEFAULT NULL,
  `AdPopupTypes` varchar(45) DEFAULT NULL,
  `AdRisk` tinyint(4) NOT NULL DEFAULT '-1',
  `VirusKind` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `VirusBehaviors` text,
  `VirusName` tinytext,
  `Permissions` text,
  `Md5` char(32) DEFAULT NULL,
  `PackageSjdbMd5` char(32) DEFAULT NULL,
  `SignatureMD5` char(32) DEFAULT NULL,
  `QuickMd5` char(32) DEFAULT NULL,
  `FreeSize` bigint(20) unsigned NOT NULL DEFAULT '0',
  `IndexImgUrl` varchar(200) NOT NULL DEFAULT '',
  `Language` varchar(10) NOT NULL DEFAULT '',
  `ShortDesc` varchar(45) DEFAULT '',
  `PathStatus` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `Status` int(11) DEFAULT NULL COMMENT '市场合并逻辑的状态.',
  `ApkStatus` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'Apk扫描行为.  合并行为状态.  1表示数据已拉取,需要APK分析. 2~10APK扫描阶段行为. 10表示已完成全部的APK行为,可以做合并逻辑.',
  `ScSta` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '1.渠道商付费 ,  2.渠道商免费. 3....',
  PRIMARY KEY (`Id`),
  KEY `CatalogIndexs` (`Catalog`,`SubCatalog`) USING BTREE,
  KEY `CatalogName` (`SubCatalogName`) USING BTREE,
  KEY `MarketName` (`MarketName`) USING BTREE,
  KEY `Pkname` (`Pkname`),
  KEY `SignatureSha1` (`SignatureSha1`),
  KEY `Md5` (`Md5`),
  KEY `ApkStatus` (`ApkStatus`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=649403 DEFAULT CHARSET=utf8 COMMENT='App From all the markets';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Metro`
--

DROP TABLE IF EXISTS `Metro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Metro` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL DEFAULT '' COMMENT '广告名称或应用名称',
  `Type` varchar(2) NOT NULL DEFAULT '1' COMMENT 'Win8色块 1大色块 2中色块 3小色块',
  `Pics` text NOT NULL,
  `Hidden` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏 0否 1是',
  `Tabname` varchar(10) NOT NULL,
  `ShortDesc` varchar(200) NOT NULL DEFAULT '' COMMENT '简单描述',
  `Url` varchar(200) NOT NULL,
  `Time` date DEFAULT NULL,
  `Deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 1=是 0=否',
  `OpTime` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Mo_AppAndTag`
--

DROP TABLE IF EXISTS `Mo_AppAndTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mo_AppAndTag` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AppId` int(10) unsigned NOT NULL,
  `TagId` int(10) unsigned NOT NULL,
  `Rank` int(10) unsigned NOT NULL DEFAULT '999999999' COMMENT 'rank r按升序排列.',
  `TagName` varchar(20) NOT NULL,
  `TagType` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1.专题  2.普通标签',
  `ShortDesc` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `TagType` (`TagType`) USING BTREE,
  KEY `AppId` (`AppId`) USING BTREE,
  KEY `Rank` (`Rank`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8 COMMENT='中间表做冗余设计.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Mo_Featured`
--

DROP TABLE IF EXISTS `Mo_Featured`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mo_Featured` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `AppOrTagId` int(10) unsigned NOT NULL COMMENT 'FK: App_Id , Tag_Id',
  `Type` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1是App 2是Tag. ',
  `PicType` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1大图  2小图',
  `Name` varchar(50) NOT NULL DEFAULT '' COMMENT '广告名称或应用名称',
  `Rank` int(10) unsigned NOT NULL DEFAULT '999999999',
  `Pics` text NOT NULL,
  `ShortDesc` varchar(200) NOT NULL DEFAULT '' COMMENT '简单描述',
  `Deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 1=是 0=否',
  `Hidden` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏 0否 1是',
  `BigPics` varchar(200) DEFAULT NULL,
  `OpTime` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Unique` (`Type`,`AppOrTagId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Mo_MixFeatured`
--

DROP TABLE IF EXISTS `Mo_MixFeatured`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mo_MixFeatured` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `AppOrTagId` int(10) unsigned NOT NULL COMMENT 'FK: App_Id , Tag_Id',
  `Type` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1是App 2是Tag. ',
  `PicType` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '图片类型. 显示在手机上某个位置.  不以ABCD定义, 以数字约定.',
  `Name` varchar(50) NOT NULL DEFAULT '' COMMENT '广告名称或应用名称',
  `Rank` int(10) unsigned NOT NULL DEFAULT '999999999' COMMENT '第一名排第一. 第二名排第二.',
  `ShortDesc` varchar(200) NOT NULL DEFAULT '' COMMENT '简单描述',
  `Hidden` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏 0否 1是',
  `OpTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:01',
  `BigPics` varchar(200) NOT NULL DEFAULT '' COMMENT '高分辨率手机下的图片',
  `MediumPics` varchar(200) NOT NULL DEFAULT '' COMMENT '中等分辨率手机下的图片',
  `SmallPics` varchar(200) NOT NULL DEFAULT '' COMMENT '低分辨率手机下的图片',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Unique` (`Type`,`AppOrTagId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='多个色块固定位置.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Mo_Tag`
--

DROP TABLE IF EXISTS `Mo_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mo_Tag` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `TagDesc` varchar(100) NOT NULL DEFAULT '',
  `PId` int(11) NOT NULL DEFAULT '0' COMMENT '上级专题Id ，默认为0',
  `Catalog` smallint(5) unsigned NOT NULL DEFAULT '0',
  `TagType` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1.专题  2.普通标签',
  `BigPics` varchar(200) DEFAULT NULL,
  `MediumPics` varchar(200) DEFAULT NULL,
  `SmallPics` varchar(200) DEFAULT NULL,
  `ImgUrl` varchar(200) NOT NULL DEFAULT '',
  `Rank` int(10) unsigned NOT NULL DEFAULT '999999999' COMMENT '排序值',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name` (`Catalog`,`Name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='专题,热门,主题,各种长短周期定义的内容.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permissions` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Permission` varchar(60) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Behavior` varchar(200) DEFAULT NULL,
  `Spite` varchar(200) DEFAULT NULL,
  `AdRisk` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Permission` (`Permission`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PhoneBasicInfo`
--

DROP TABLE IF EXISTS `PhoneBasicInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PhoneBasicInfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ProductId` varchar(50) NOT NULL,
  `Manufactory` varchar(20) DEFAULT NULL,
  `Brand` varchar(20) NOT NULL,
  `Product` varchar(20) DEFAULT NULL,
  `Type` tinyint(3) DEFAULT NULL,
  `FitDefDriver` tinyint(4) DEFAULT '1' COMMENT '是否适合默认驱动 0否 1是 默认为1',
  `Support` tinyint(1) DEFAULT '1',
  `Alias` varchar(50) DEFAULT NULL,
  `SmallIconPath` varchar(100) DEFAULT NULL,
  `LargeIconPath` varchar(100) DEFAULT NULL,
  `Screen` varchar(100) DEFAULT NULL,
  `LinkType` tinyint(3) DEFAULT NULL,
  `ErrorTips` varchar(100) DEFAULT NULL,
  `CreateUser` varchar(20) NOT NULL,
  `CreateTime` datetime NOT NULL,
  `LastEditUser` varchar(20) NOT NULL,
  `LastEditTime` datetime NOT NULL,
  `SmallX` int(11) unsigned DEFAULT NULL,
  `SmallY` int(11) unsigned DEFAULT NULL,
  `SmallWidth` int(11) unsigned DEFAULT NULL,
  `LargeX` int(11) unsigned DEFAULT NULL,
  `LargeY` int(11) unsigned DEFAULT NULL,
  `SmallHeight` int(11) DEFAULT NULL,
  `TinyIconPath` varchar(100) DEFAULT NULL,
  `Rotate` int(11) unsigned DEFAULT '0' COMMENT '屏幕截图旋转角度',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id` (`Id`) USING BTREE,
  UNIQUE KEY `ProductId` (`ProductId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3364 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PhoneRelations`
--

DROP TABLE IF EXISTS `PhoneRelations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PhoneRelations` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ProductId` varchar(50) NOT NULL,
  `VID` varchar(10) NOT NULL,
  `Pids` varchar(1000) DEFAULT NULL,
  `Names` varchar(600) DEFAULT NULL,
  `Models` varchar(600) DEFAULT NULL,
  `ImeiModels` varchar(600) DEFAULT '',
  `Brand` varchar(600) DEFAULT NULL COMMENT 'adb 获取的brand',
  `Cpu` smallint(11) unsigned DEFAULT '0' COMMENT 'cpu型号',
  `DefaultProduct` varchar(20) NOT NULL,
  `PhoneId` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id_index` (`Id`) USING BTREE,
  KEY `fk_phoneId` (`PhoneId`) USING BTREE,
  KEY `VID_PID_index` (`ProductId`,`VID`) USING BTREE,
  KEY `VID` (`VID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10446 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Rollinfo`
--

DROP TABLE IF EXISTS `Rollinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rollinfo` (
  `AppId` int(10) unsigned NOT NULL,
  `Recommend` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `Rank` int(10) unsigned NOT NULL DEFAULT '0',
  `Catalog` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`AppId`),
  UNIQUE KEY `FK_AppId` (`AppId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ScanHistory`
--

DROP TABLE IF EXISTS `ScanHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScanHistory` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MarketAppId` int(10) unsigned NOT NULL DEFAULT '0',
  `ScanServer` varchar(100) NOT NULL DEFAULT '',
  `StartQueueingTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  `StartDownloadTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  `DownloadCostTime` time NOT NULL DEFAULT '00:00:00',
  `BackupCostTime` time NOT NULL DEFAULT '00:00:00',
  `SafeScanRequestCostTime` time NOT NULL DEFAULT '00:00:00',
  `FinishLocalScanTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  `StartSafeScanTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  `FinishSafeScanTime` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  `ErrorDesc` varchar(1000) NOT NULL DEFAULT '',
  `TryCount` smallint(6) NOT NULL DEFAULT '0',
  `DownloadSpeed` int(10) DEFAULT NULL COMMENT 'Byte/second',
  `BackupSpeed` int(10) DEFAULT NULL COMMENT 'Byte/second',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `MarketApp_Id_UNIQUE` (`MarketAppId`),
  UNIQUE KEY `ScanSvrId_UNIQUE` (`ScanServer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ScreenShot`
--

DROP TABLE IF EXISTS `ScreenShot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScreenShot` (
  `Id` int(10) unsigned NOT NULL,
  `AppId` int(10) unsigned DEFAULT NULL,
  `Url` varchar(200) NOT NULL,
  `Rank` int(10) unsigned DEFAULT NULL,
  `LastUpdateTime` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合并后的软件截图.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SysDictionary`
--

DROP TABLE IF EXISTS `SysDictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SysDictionary` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Desc` varchar(45) DEFAULT NULL,
  `IntValue` int(10) unsigned DEFAULT NULL,
  `Value` varchar(45) DEFAULT NULL,
  `Boolean` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name` (`Name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `TagDesc` varchar(100) DEFAULT NULL,
  `PId` int(11) DEFAULT '0' COMMENT '上级专题Id ，默认为0',
  `Catalog` smallint(5) unsigned NOT NULL DEFAULT '0',
  `TagType` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '1.专题  2.普通标签',
  `ImgUrl` varchar(200) DEFAULT '',
  `Rank` int(10) DEFAULT '999999999' COMMENT '排序值',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name` (`Catalog`,`Name`),
  KEY `PId` (`PId`)
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8 COMMENT='专题,热门,主题,各种长短周期定义的内容.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TimeTable`
--

DROP TABLE IF EXISTS `TimeTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TimeTable` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BeginTime` time NOT NULL,
  `EndTime` time NOT NULL,
  `MaxCount` int(10) unsigned NOT NULL,
  `Probability` int(10) unsigned NOT NULL,
  `CurrentBalance` int(10) unsigned NOT NULL,
  `EndLotteryTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserTopApp`
--

DROP TABLE IF EXISTS `UserTopApp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserTopApp` (
  `Pkname` varchar(200) NOT NULL,
  `Name` varchar(80) NOT NULL DEFAULT '',
  `Rank` int(10) unsigned DEFAULT '999999999',
  PRIMARY KEY (`Pkname`),
  KEY `Rank` (`Rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='最多用户使用最多的应用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `VI_MoTagApps`
--

DROP TABLE IF EXISTS `VI_MoTagApps`;
/*!50001 DROP VIEW IF EXISTS `VI_MoTagApps`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VI_MoTagApps` (
  `Id` int(10) unsigned,
  `MarketName` varchar(20),
  `AppName` varchar(80),
  `AppId` int(10) unsigned,
  `TagId` int(10) unsigned,
  `Rank` int(10) unsigned,
  `TagName` varchar(20),
  `TagType` smallint(5) unsigned,
  `ShortDesc` varchar(45),
  `LogoUrl` varchar(200),
  `Catalog` smallint(5) unsigned,
  `AppCatalog` smallint(5) unsigned,
  `Name` varchar(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VI_TagApps`
--

DROP TABLE IF EXISTS `VI_TagApps`;
/*!50001 DROP VIEW IF EXISTS `VI_TagApps`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VI_TagApps` (
  `Id` int(10) unsigned,
  `AppId` int(10) unsigned,
  `MarketName` varchar(20),
  `AppName` varchar(80),
  `TagId` int(10) unsigned,
  `Catalog` smallint(5) unsigned,
  `AppCatalog` int(10) unsigned,
  `LogoUrl` varchar(200),
  `Rank` int(10) unsigned,
  `TagName` varchar(20),
  `ShortDesc` varchar(45),
  `TagType` smallint(5) unsigned
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `mobile_app_downloadtop200_percent_stat`
--

DROP TABLE IF EXISTS `mobile_app_downloadtop200_percent_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mobile_app_downloadtop200_percent_stat` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `package` varchar(200) DEFAULT NULL,
  `yesterday_count` int(11) DEFAULT NULL,
  `today_count` int(11) DEFAULT NULL,
  `percent` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_date` (`date`) USING BTREE,
  KEY `idx_type` (`type`) USING BTREE,
  KEY `idx_percent` (`percent`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35580 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `temp`
--

DROP TABLE IF EXISTS `temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temp` (
  `id` int(10) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `VI_MoTagApps`
--

/*!50001 DROP TABLE IF EXISTS `VI_MoTagApps`*/;
/*!50001 DROP VIEW IF EXISTS `VI_MoTagApps`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=MERGE */
/*!50013 DEFINER=`market_pro`@`%` SQL SECURITY INVOKER */
/*!50001 VIEW `VI_MoTagApps` AS select `appTag`.`Id` AS `Id`,`app`.`MarketName` AS `MarketName`,`app`.`Name` AS `AppName`,`appTag`.`AppId` AS `AppId`,`appTag`.`TagId` AS `TagId`,`appTag`.`Rank` AS `Rank`,`tag`.`Name` AS `TagName`,`appTag`.`TagType` AS `TagType`,`appTag`.`ShortDesc` AS `ShortDesc`,`app`.`LogoUrl` AS `LogoUrl`,`tag`.`Catalog` AS `Catalog`,`app`.`Catalog` AS `AppCatalog`,`tag`.`Name` AS `Name` from ((`Mo_AppAndTag` `appTag` left join `App` `app` on((`app`.`Id` = `appTag`.`AppId`))) join `Mo_Tag` `tag` on((`tag`.`Id` = `appTag`.`TagId`))) where (`app`.`Hidden` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `VI_TagApps`
--

/*!50001 DROP TABLE IF EXISTS `VI_TagApps`*/;
/*!50001 DROP VIEW IF EXISTS `VI_TagApps`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`113.106.106.131` SQL SECURITY DEFINER */
/*!50001 VIEW `VI_TagApps` AS select `appTag`.`Id` AS `Id`,`appTag`.`AppId` AS `AppId`,`app`.`MarketName` AS `MarketName`,`app`.`Name` AS `AppName`,`appTag`.`TagId` AS `TagId`,`tag`.`Catalog` AS `Catalog`,`app`.`SubCatalog` AS `AppCatalog`,`app`.`LogoUrl` AS `LogoUrl`,`appTag`.`Rank` AS `Rank`,`appTag`.`TagName` AS `TagName`,`app`.`ShortDesc` AS `ShortDesc`,`appTag`.`TagType` AS `TagType` from ((`App` `app` join `AppAndTag` `appTag` on((`app`.`Id` = `appTag`.`AppId`))) join `Tag` `tag` on((`appTag`.`TagId` = `tag`.`Id`))) order by `tag`.`Rank` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-04-08 14:24:45
