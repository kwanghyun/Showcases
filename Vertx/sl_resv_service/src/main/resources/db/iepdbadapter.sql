/*
drop schema  if exists iepadapters ;
CREATE DATABASE  IF NOT EXISTS iepadapters /*!40100 DEFAULT CHARACTER SET utf8 */;
USE iepadapters;
*/
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: iepadapters
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table ADAPTER_DEFN
--

DROP TABLE IF EXISTS ADAPTER_DEFN;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ADAPTER_DEFN (
  adapter_defn_id int NOT NULL,
  adapter varchar(500) NOT NULL,
  adapter_intf varchar(500) NOT NULL,
  adapter_jar_location varchar(500) DEFAULT NULL,
  version varchar(50) NOT NULL,
  created_by varchar(50) NOT NULL,
  created_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  updated_by varchar(50) NOT NULL,
  updated_dt timestamp NOT NULL DEFAULT NULL,
  PRIMARY KEY (adapter_defn_id)
) ;
 CREATE INDEX ADAPTER_INTF_IDX ON ADAPTER_DEFN (adapter_intf);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ADAPTER_DEFN
--


/*!40000 ALTER TABLE ADAPTER_DEFN DISABLE KEYS */;
-- INSERT INTO ADAPTER_DEFN VALUES (1000,'com.cisco.iep.tvguide.rovi.api.impl.RoviTvGuideServiceApiImpl','com.cisco.iep.adapters.tvguide.api.TvGuideServiceApi','file:///opt/cisco/iep/mes/adapters/tvguide-rovi.jar','version1','superadmin','2014-04-28 10:42:19','superadmin','2014-03-07 02:58:53'),(6000,'com.cisco.iep.pme.hl7.impl.PMEHL7Adapter','com.cisco.iep.adapters.hl7.HL7Interface','file:///opt/cisco/iep/mes/adapters/emr-core.jar','version1','superadmin','2014-04-28 10:42:19','superadmin','2014-03-04 06:59:25'),(9000,'com.cisco.iep.adapters.content.sns.impl.SnSContentServiceApiImpl','com.cisco.iep.adapters.content.ContentServiceApi','file:///opt/cisco/iep/mes/adapters/content-sns-1.0.0.jar','version1','superadmin','2014-04-28 10:42:19','superadmin','2014-03-06 20:16:08');
/*!40000 ALTER TABLE ADAPTER_DEFN ENABLE KEYS */;


--
-- Table structure for table ADAPTER_INSTANCE
--

DROP TABLE IF EXISTS ADAPTER_INSTANCE;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ADAPTER_INSTANCE (
  adapter_instance_id int NOT NULL,
  adapter_defn_id int NOT NULL,
  priority int NOT NULL,
  version varchar(50) NOT NULL,
  created_by varchar(50) NOT NULL,
  created_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  updated_by varchar(50) NOT NULL,
  updated_dt timestamp NOT NULL DEFAULT NULL,
  PRIMARY KEY (adapter_instance_id),
  CONSTRAINT ADAPTER_INSTANCE_ID_UNIQUE UNIQUE (adapter_instance_id),
  CONSTRAINT ADAPTER_DEFN_ID_PRIORITY_UNIQUE UNIQUE (adapter_defn_id,priority)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ADAPTER_INSTANCE
--


/*!40000 ALTER TABLE ADAPTER_INSTANCE DISABLE KEYS */;
-- INSERT INTO ADAPTER_INSTANCE VALUES (1000,1000,1,'1','superadmin','2014-03-07 03:03:06','superadmin','2014-03-07 03:03:06'),(6000,6000,1,'1','superadmin','2014-01-31 12:10:47','superadmin','2014-01-31 12:10:47'),(6001,6000,2,'1','superadmin','2014-01-31 12:10:47','superadmin','2014-01-31 12:10:47'),(9000,9000,1,'1','superadmin','2014-01-31 23:10:47','superadmin','2014-01-31 23:10:47'),(9001,9000,2,'1','superadmin','2014-01-31 23:10:47','superadmin','2014-01-31 23:10:47');
/*!40000 ALTER TABLE ADAPTER_INSTANCE ENABLE KEYS */;


--
-- Table structure for table ADAPTER_PROP_TYPE
--

DROP TABLE IF EXISTS ADAPTER_PROP_TYPE;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ADAPTER_PROP_TYPE (
  prop_type_id int NOT NULL,
  property_type_name varchar(500) NOT NULL,
  created_by varchar(50) NOT NULL,
  created_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  updated_by varchar(50) NOT NULL,
  updated_dt timestamp NOT NULL DEFAULT NULL,
  PRIMARY KEY (prop_type_id)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ADAPTER_PROP_TYPE
--


/*!40000 ALTER TABLE ADAPTER_PROP_TYPE DISABLE KEYS */;
-- INSERT INTO ADAPTER_PROP_TYPE VALUES (0,'Number','superadmin','2014-01-31 23:09:52','superadmin','2014-01-31 23:09:52'),(1,'Character','superadmin','2014-01-31 23:09:52','superadmin','2014-01-31 23:09:52'),(2,'Date','superadmin','2014-01-31 23:09:52','superadmin','2014-01-31 23:09:52');

--
-- Table structure for table ADAPTER_PROP_DEFN
--

DROP TABLE IF EXISTS ADAPTER_PROP_DEFN;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ADAPTER_PROP_DEFN (
  prop_defn_id int NOT NULL,
  adapter_defn_id int NOT NULL,
  property_name varchar(500) NOT NULL,
  property_type_id int NOT NULL,
  property_def_string_val varchar(2000) DEFAULT NULL,
  property_def_num_val int DEFAULT NULL,
  property_def_date_val timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  property_size int DEFAULT '0',
  mandatory char(1) DEFAULT NULL,
  secure char(1) DEFAULT NULL,
  created_by varchar(50) NOT NULL,
  created_dt timestamp NOT NULL DEFAULT NULL,
  updated_by varchar(50) NOT NULL,
  updated_dt timestamp NOT NULL DEFAULT NULL,
  PRIMARY KEY (prop_defn_id),
  CONSTRAINT ADAPTER_PROP_DEFN_UNIQUE UNIQUE (adapter_defn_id,property_name),
  CONSTRAINT ADAPTER_PROP_DEFN_FK FOREIGN KEY (adapter_defn_id) REFERENCES ADAPTER_DEFN (adapter_defn_id) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT ADAPTER_PROP_DEFN_FK2 FOREIGN KEY (property_type_id) REFERENCES ADAPTER_PROP_TYPE (prop_type_id) ON DELETE CASCADE ON UPDATE NO ACTION
) ;
CREATE INDEX ADAPTER_PROP_DEFN_FK_IDX ON ADAPTER_PROP_DEFN (adapter_defn_id);
 CREATE INDEX ADAPTER_PROP_DEFN_FK2_IDX2 ON ADAPTER_PROP_DEFN (property_type_id);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ADAPTER_PROP_DEFN
--


/*!40000 ALTER TABLE ADAPTER_PROP_DEFN DISABLE KEYS */;
-- INSERT INTO ADAPTER_PROP_DEFN VALUES (1001,1000,'locId',1,'null',NULL,'2014-03-11 00:31:55',0,'N','N','superadmin','2014-03-07 03:04:36','superadmin','2014-03-07 03:04:36'),(9000,9000,'locId',1,'null',NULL,'2014-03-10 08:09:42',0,'N','N','superadmin','2014-03-10 08:09:42','superadmin','2014-03-10 08:09:42'),(9001,9000,'username',1,NULL,NULL,'2014-03-10 08:09:42',0,'Y','N','superadmin','2014-03-10 08:09:42','superadmin','2014-03-10 08:09:42'),(9002,9000,'password',1,NULL,NULL,'2014-03-10 08:09:42',0,'Y','N','superadmin','2014-03-10 08:09:42','superadmin','2014-03-10 08:09:42'),(9003,9000,'host',1,NULL,NULL,'2014-03-10 08:09:42',0,'Y','N','superadmin','2014-03-10 08:09:42','superadmin','2014-03-10 08:09:42'),(9004,9000,'port',1,NULL,NULL,'2014-03-10 08:09:42',0,'Y','N','superadmin','2014-03-10 08:09:42','superadmin','2014-03-10 08:09:42'),(9005,9000,'vmFilePath',1,NULL,NULL,'2014-03-10 08:09:42',0,'Y','N','superadmin','2014-03-10 08:09:42','superadmin','2014-03-10 08:09:42');
/*!40000 ALTER TABLE ADAPTER_PROP_DEFN ENABLE KEYS */;



--
-- Table structure for table ADAPTER_PROPERTIES
--

DROP TABLE IF EXISTS ADAPTER_PROPERTIES;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ADAPTER_PROPERTIES (
  property_id int NOT NULL,
  adapter_instance_id int NOT NULL,
  prop_defn_id int NOT NULL,
  property_string_val varchar(2000) DEFAULT NULL,
  property_num_val int DEFAULT NULL,
  property_date_val timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  created_by varchar(50) NOT NULL,
  created_dt timestamp NOT NULL DEFAULT NULL,
  updated_by varchar(50) NOT NULL,
  updated_dt timestamp NOT NULL DEFAULT NULL,
  PRIMARY KEY (property_id),
  CONSTRAINT ADAPTER_PROPERTIES_FK FOREIGN KEY (adapter_instance_id) REFERENCES ADAPTER_INSTANCE (adapter_instance_id),
  CONSTRAINT ADAPTER_PROPERTIES_FK2 FOREIGN KEY (prop_defn_id) REFERENCES ADAPTER_PROP_DEFN (prop_defn_id)
) ;

CREATE INDEX ADAPTER_PROPERTIES_FK_IDX ON ADAPTER_PROPERTIES (adapter_instance_id);
 CREATE INDEX ADAPTER_PROPERTIES_FK2_IDX2 ON ADAPTER_PROPERTIES (prop_defn_id);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table ADAPTER_PROPERTIES
--


/*!40000 ALTER TABLE ADAPTER_PROPERTIES DISABLE KEYS */;
-- INSERT INTO ADAPTER_PROPERTIES VALUES (1000,1000,1001,'1',NULL,'2014-03-11 00:28:48','superadmin','2014-03-07 03:07:12','superadmin','2014-03-07 03:07:12'),(9000,9000,9000,'1',NULL,'2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27'),(9001,9000,9001,'superuser',NULL,'2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27'),(9002,9000,9002,'V!deo!23',NULL,'2014-03-12 12:46:58','superadmin','2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27'),(9003,9000,9003,'bgl14-cle-ucsns1.cisco.com',NULL,'2014-03-12 12:46:57','superadmin','2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27'),(9004,9000,9004,'8443',NULL,'2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27'),(9005,9000,9005,'/',NULL,'2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27','superadmin','2014-03-10 08:13:27');
/*!40000 ALTER TABLE ADAPTER_PROPERTIES ENABLE KEYS */;


/*!40000 ALTER TABLE ADAPTER_PROP_TYPE ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

/*Adding Medication Data */

INSERT INTO ADAPTER_DEFN 
(adapter_defn_id,adapter,adapter_intf,adapter_jar_location,version,created_by,created_dt,updated_by,updated_dt)
VALUES 
(12000,'com.cisco.iep.adapters.drugeducation.lexicomp.api.impl.DrugEducationCachedImpl','com.cisco.iep.adapters.drugeducation.api.DrugEducationService','file:///opt/cisco/iep/mes/adapters/drug-education-lexicomp.jar','version1','superadmin','2015-01-13 07:24:11','superadmin','2015-01-19 02:10:00');

INSERT INTO ADAPTER_INSTANCE 
(adapter_instance_id,adapter_defn_id,priority,version,created_by,created_dt,updated_by,updated_dt)
VALUES 
(12000,12000,1,'1','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00');


INSERT INTO ADAPTER_PROP_DEFN
(prop_defn_id,adapter_defn_id,property_name,property_type_id,property_def_string_val,property_def_num_val,property_def_date_val,property_size,mandatory,secure,created_by,created_dt,updated_by,updated_dt)
VALUES
(12000,12000,'baseUrl',1,NULL,NULL,'2015-01-10 02:10:00',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12001,12000,'username',1,NULL,NULL,'2015-01-10 02:10:00',0,'Y','Y','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12002,12000,'password',1,NULL,NULL,'2015-01-10 02:10:00',0,'Y','Y','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12003,12000,'connectionTimeout',0,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12004,12000,'readTimeout',0,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12005,12000,'assumeAdult',1,NULL,NULL,'2015-01-10 02:10:00',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12006,12000,'ageThresholdForAdult',0,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12007,12000,'sectionsToFetch',1,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12008,12000,'sectionForBrandName',1,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12009,12000,'defaultLocale',1,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-19 02:10:00','superadmin','2015-01-19 02:10:00'),
(12010,12000,'maxCacheEntries',0,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-19 02:10:00','superadmin','2015-01-19 02:10:00'),
(12011,12000,'cacheValidityMins',0,NULL,NULL,'2015-01-10 02:20:39',0,'Y','N','superadmin','2015-01-19 02:10:00','superadmin','2015-01-19 02:10:00');




INSERT INTO ADAPTER_PROPERTIES 
(property_id,adapter_instance_id,prop_defn_id,property_string_val,property_num_val,property_date_val,created_by,created_dt,updated_by,updated_dt) 
VALUES
(12000,12000,12000,'https://webservices.lexi.com/rest/ref/databases/',NULL,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12001,12000,12001,'USER_FOR_SUBSCRIBED_CUSTOMER',NULL,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12002,12000,12002,'PW_FOR_SUBSCRIBED_CUSTOMER',NULL,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12003,12000,12003,'',6000,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12004,12000,12004,'',6000,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12005,12000,12005,'Y',NULL,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12006,12000,12006,'',16,'2015-01-10 02:24:47','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12007,12000,12007,'DRUG_NAME,NOTE,US_BRAND_NAMES,CANADIAN_BRANDS,WARNING,USED_FOR,BEST_WAY_TO_TAKE,COPYRIGHT',NULL,'2015-01-15 00:48:52','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12008,12000,12008,'US_BRAND_NAMES',NULL,'2015-01-15 00:48:52','superadmin','2015-01-10 02:10:00','superadmin','2015-01-10 02:10:00'),
(12009,12000,12009,'en_US',NULL,'2015-01-15 00:48:52','superadmin','2015-01-19 02:10:00','superadmin','2015-01-19 02:10:00'),
(12010,12000,12010,NULL,30,'2015-01-15 00:48:52','superadmin','2015-01-19 02:10:00','superadmin','2015-01-19 02:10:00'),
(12011,12000,12011,NULL,1440,'2015-01-15 00:48:52','superadmin','2015-01-19 02:10:00','superadmin','2015-01-19 02:10:00');

-- Dump completed


-- For the existing tables and sequences, but NOT applicable for 'future' objects
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO adapter;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO adapter;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO adaptercore;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO adaptercore;

-- to set default privileges for future objects
ALTER DEFAULT PRIVILEGES FOR ROLE adapter IN SCHEMA public GRANT ALL ON TABLES TO adapter;
ALTER DEFAULT PRIVILEGES FOR ROLE adapter IN SCHEMA public GRANT ALL ON SEQUENCES TO adapter;
ALTER DEFAULT PRIVILEGES FOR ROLE adaptercore IN SCHEMA public GRANT ALL ON TABLES TO adaptercore;
ALTER DEFAULT PRIVILEGES FOR ROLE adaptercore IN SCHEMA public GRANT ALL ON SEQUENCES TO adaptercore;
