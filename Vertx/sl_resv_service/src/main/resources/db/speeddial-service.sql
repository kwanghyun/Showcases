--
-- Table structure for table speeddial_call_history
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE speeddial_call_history (
  history_id SERIAL,
  contact_no varchar(100) DEFAULT NULL,
  call_time BIGINT DEFAULT NULL,
  created_by varchar(20) DEFAULT NULL,
  created_date timestamp NULL DEFAULT NULL,
  updated_by varchar(20) DEFAULT NULL,
  updated_date timestamp NULL DEFAULT NULL,
  speeddial_id int DEFAULT NULL,
  contact_name varchar(45) DEFAULT 'null',
  user_id varchar(255) DEFAULT NULL,
  call_type varchar(30) DEFAULT NULL,
  speeddial_pk int DEFAULT NULL,
  PRIMARY KEY (history_id)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table speeddial_call_history
--


--
-- Table structure for table speeddial_user_speeddial
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE speeddial_user_speeddial (
  speeddial_pk SERIAL,
  contact_no varchar(100) DEFAULT NULL,
  updated_date timestamp NULL DEFAULT NULL,
  created_date timestamp NULL DEFAULT NULL,
  updated_by varchar(20) DEFAULT NULL,
  crreated_by varchar(20) DEFAULT NULL,
  speeddial_no int DEFAULT NULL,
  entity_id varchar(50) DEFAULT NULL,
  entity_type VARCHAR(45) DEFAULT NULL,
  entity_name varchar(20) DEFAULT NULL,
  PRIMARY KEY (speeddial_pk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


/*ALTER TABLE speeddial_user_speeddial */
CREATE INDEX SPEEDDIAL_idx ON speeddial_user_speeddial (entity_type ASC, entity_id ASC, speeddial_no ASC);
/*ALTER TABLE speeddial_call_history */
CREATE INDEX HISTORY_idx ON speeddial_call_history (user_id ASC, contact_no ASC);

-- speeddialno-- 


INSERT INTO metadata_registry (service,resourceName,resourceId,resourceContent,metadataId) VALUES 
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PHONE','{"appId":"PHONE","appName":"PHONE","appDescription":"Phone call"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PHONE'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','SPEED_DIAL','{"appId":"SPEED_DIAL","appName":"Speeddial","appDescription":"Speeddials on Phone"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#SPEED_DIAL'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PHONE_RINGER_CONFIGURATION','{"appId":"PHONE_RINGER_CONFIGURATION","appName":"Allow Phone Ringer Configuration","appDescription":"Allow Phone Ringer Configuration"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PHONE_RINGER_CONFIGURATION')
;
INSERT INTO registry_configuration (metadataId,configId,configValue) VALUES 
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PHONE','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PHONE','LOCATION_TYPE','GUEST_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#SPEED_DIAL','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PHONE_RINGER_CONFIGURATION','LOCATION_TYPE','PATIENT_ROOM')
;


insert into config_registry(service,module,configKey,configValue) values('speeddialno','MES','speeddialNo.regex','([0-9]|[1-2][0-9]|911)');
insert into config_registry(service,module,configKey,configValue) values('speeddialno','MES','module.speedDial','SpeedDial');
insert into config_registry(service,module,configKey,configValue) values('speeddialno','MES','defaultLocale.speedDial','en_US');
insert into config_registry(service,module,configKey,configValue) values('speeddialno', 'MES', 'speeddialNo.resolution.choice.hi-def', '640x480');
insert into config_registry(service,module,configKey,configValue) values('speeddiaino', 'MES', 'speeddialNo.resolution.choice.low-def', '352x288');
insert into config_registry(service,module,configKey,configValue) values ('speeddialno','MES','whitelist.enable.phone.lookup.ad','true');

--
insert into config_registry_metadata(configKey,display,modify,description) values('speeddialNo.resolution.choice.hi-def',true,true,'Resolution to be used for hi-definition video call');
insert into config_registry_metadata(configKey,display,modify,description) values('speeddialNo.resolution.choice.low-def',true,true,'Resolution to be used for low-definition video call');
