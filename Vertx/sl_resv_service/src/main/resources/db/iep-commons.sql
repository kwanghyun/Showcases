
/*SET @saved_cs_client     = @@character_set_client;*/
/*SET character_set_client = utf8;*/
CREATE TABLE db_version (
  id SERIAL,
  date TIMESTAMP default NULL,
  description varchar(50) default NULL,
  name varchar(50) NOT NULL,
  service varchar(50) NOT NULL,
  PRIMARY KEY  (id)
) ;
/*SET character_set_client = @saved_cs_client;*/


--
-- Table structure for table locale
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE locale (
  localeCode varchar(11) NOT NULL,
  localeName varchar(50) NOT NULL,
  systemdefault boolean DEFAULT false,
  PRIMARY KEY (localeCode)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table locale
--


/*!40000 ALTER TABLE locale DISABLE KEYS */;
INSERT INTO locale VALUES ('en_US','English',true),('es_US','Spanish',false);
/*!40000 ALTER TABLE locale ENABLE KEYS */;


CREATE TABLE metadata_registry (
  metadata_resourcePk SERIAL,
  service varchar(45) DEFAULT NULL,
  resourceName varchar(45) DEFAULT NULL,
  resourceId varchar(45) DEFAULT NULL,
  resourceContent varchar(4000) DEFAULT NULL,
  metadataId varchar(100) DEFAULT NULL,
  PRIMARY KEY (metadata_resourcePk)
) ;

/*ALTER TABLE metadata_registry 
	ADD UNIQUE INDEX metadata_registry_metadataId_UNIQUE (metadataId ASC);*/
CREATE UNIQUE INDEX metadata_registry_metadataId_UNIQUE ON metadata_registry(metadataId ASC);

CREATE TABLE registry_configuration (
  resource_configurationPk SERIAL,
  metadataId varchar(100) DEFAULT NULL,
  configId varchar(100) DEFAULT NULL,
  configValue varchar(100) DEFAULT NULL,
  PRIMARY KEY (resource_configurationPk)
) ;

CREATE TABLE resourcebundle (
  id varchar(255) NOT NULL,
  service varchar(45) NOT NULL,
  tableName varchar(45) NOT NULL,
  localeCode varchar(45) NOT NULL,
  itemId varchar(100) NOT NULL,
  text varchar(500) NOT NULL,
  PRIMARY KEY (id)
) ;



-- Properties inserts in config_registry for common services
-- commonsparserutil-- 


CREATE TABLE config_registry (
  config_registryPk SERIAL,
  service varchar(30) NOT NULL,
  module varchar(20) NOT NULL,
  configKey varchar(100) NOT NULL UNIQUE,
  configValue text DEFAULT NULL,
  defaultConfigValue text DEFAULT NULL,
  PRIMARY KEY (config_registryPk)
) ;

-- Config Registry Metadata
CREATE TABLE config_registry_metadata (
  config_registry_metadataPk SERIAL,
  configKey varchar(100) NOT NULL UNIQUE,
  display boolean NOT NULL default false,
  modify boolean NOT NULL default false,
  description varchar(256) NOT NULL,
  PRIMARY KEY (config_registry_metadataPk)
) ;


insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','QUESTIONID','questionId');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','QUESTIONTEXT','questionText');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','ANSWER','answer');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','UNITID','unitId');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','AGE','age');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','GENDER','gender');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','ACTION','action');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','PREDEFINED_MESSAGEID','preDefinedMessageId');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','PREDEFINED_MESSAGETEXT','preDefinedMessageText');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','TASKID','taskId');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','TASK_DESCRIPTION','taskDescription');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','ICON_URL','iconURL');
-- insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','THUMBNAIL_URL','ThumbnailURL');
-- insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','RTMP_URI','RtmpURI');
-- insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','HTTP_URI','HttpURI');
-- insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','VIDEO_ID','videoId');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','ACTION_VALUES','ADD,DELETE,,UPDATE,');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','ARCHIVE_LOCATION','/opt/cisco/iep/mes/archive');
insert into config_registry(service,module,configKey,configValue) values('commonsparserutil','MES','ARCHIVE_BASE_URL','/archive/');
--
--


-- common logging--
insert into config_registry(service,module,configKey,configValue) values('common-logging','MES','location.not.found','FAILED_USER');
insert into config_registry(service,module,configKey,configValue) values('common-logging','MES','patient.not.found','FAILED_USER');
insert into config_registry(service,module,configKey,configValue) values('common-logging','MES','patient.data.missing','FAILED_USER');
insert into config_registry(service,module,configKey,configValue) values('common-logging','MES','device.not.found','FAILED_USER');
insert into config_registry(service,module,configKey,configValue) values('common-logging','MES','locationtype.mismatch','FAILED_USER');
insert into config_registry(service,module,configKey,configValue) values('common-logging','MES','patient.data.inconsistent','FAILED_USER');

insert into config_registry(service,module,configKey,configValue) values('common-services','MES','user.age.categories','G=0^PG=0^PG-13=13^R=17^NC-17=18');

-- encryption-- 
insert into config_registry(service,module,configKey,configValue) values('encryption','MES','iep.encryptionKey','cisco!123');
insert into config_registry(service,module,configKey,configValue) values('encryption','MES','iep.isEncryptionEnable','true');

-- weather service --
insert into config_registry(service,module,configKey,configValue) values('weather-service','MES','weather.woeid','2012590063');
insert into config_registry_metadata(configKey,display,modify,description) values('weather.woeid',true,true, 'WOEID of the deployment location');

insert into config_registry(service,module,configKey,configValue) values('weather-service','MES','weather.temperature.unit','f');
insert into config_registry_metadata(configKey,display,modify,description) values('weather.temperature.unit',true,true,'Temperature unit (c or f)');
