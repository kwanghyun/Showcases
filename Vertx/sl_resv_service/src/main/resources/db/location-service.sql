--
-- Table structure for table location
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE location (
  locationPk SERIAL,
  locationId varchar(255) DEFAULT NULL,
  locationType varchar(255) DEFAULT NULL,
  locationName varchar(255) DEFAULT NULL,
  PRIMARY KEY (locationPk)
) ;

CREATE INDEX locationId ON location (locationId ASC);
CREATE INDEX locationType ON location (locationType ASC);

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table department
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE department (
  departmentPk SERIAL,
  departmentName varchar(255) DEFAULT NULL,
  departmentId varchar(255) DEFAULT NULL,
  PRIMARY KEY (departmentPk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table device
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE device (
  devicePk SERIAL,
  name varchar(100) DEFAULT NULL,
  type varchar(100) DEFAULT NULL,
  serialNumber varchar(255) DEFAULT NULL,
  location_locationPk int NOT NULL,
   directoryNumber VARCHAR(45) NULL,
  PRIMARY KEY (devicePk),
  /*KEY fk_device_location1_idx (location_locationPk),*/
  CONSTRAINT fk_device_location1 FOREIGN KEY (location_locationPk) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_device_location1_idx ON device(location_locationPk);
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table emr_location
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE emr_location (
  patient_locationPk SERIAL,
  bed int DEFAULT NULL,
  room int DEFAULT NULL,
  facility int DEFAULT NULL,
  location int DEFAULT NULL,
  building int DEFAULT NULL,
  floor int DEFAULT NULL,
  department int DEFAULT NULL,
  department_location int DEFAULT NULL,
  extension int DEFAULT NULL,
  locationType varchar(45) NOT NULL,
  PRIMARY KEY (patient_locationPk),
/*  KEY fk_patient_location_location1_idx (bed),
  KEY fk_patient_location_location2_idx (room),
  KEY fk_patient_location_location3_idx (facility),
  KEY fk_patient_location_location5_idx (location),
  KEY fk_patient_location_location6_idx (building),
  KEY fk_patient_location_location7_idx (floor),
  KEY fk_emr_location_department1_idx (department),
  KEY fk_emr_location_location1_idx (department_location),
  KEY fk_location8_idx (extension),*/
  CONSTRAINT fk_department1 FOREIGN KEY (department) REFERENCES department (departmentPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_emr_location1 FOREIGN KEY (department_location) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location1 FOREIGN KEY (bed) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location2 FOREIGN KEY (room) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location3 FOREIGN KEY (facility) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location5 FOREIGN KEY (location) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location6 FOREIGN KEY (building) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location7 FOREIGN KEY (floor) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_location8 FOREIGN KEY (extension) REFERENCES location (locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_patient_location_location1_idx ON emr_location(bed);
CREATE INDEX fk_patient_location_location2_idx ON emr_location(room);
CREATE INDEX fk_patient_location_location3_idx ON emr_location(facility);
CREATE INDEX fk_patient_location_location5_idx ON emr_location(location);
CREATE INDEX fk_patient_location_location6_idx ON emr_location(building);
CREATE INDEX fk_patient_location_location7_idx ON emr_location(floor);
CREATE INDEX fk_emr_location_department1_idx ON emr_location(department);
CREATE INDEX fk_emr_location_location1_idx ON emr_location(department_location);
CREATE INDEX fk_location8_idx ON emr_location(extension);




-- -----------------------------------------------------
-- Table structure for  location_has_preferences
-- -----------------------------------------------------

CREATE TABLE  location_has_preferences (
  language VARCHAR(20) NOT NULL,
  fontSize VARCHAR(20) NULL,
  createdDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  updatedDate TIMESTAMP NULL DEFAULT NULL,
  emr_locationPk int NOT NULL,
  PRIMARY KEY (emr_locationPk),
  CONSTRAINT fk_location_has_preferences_emr_location1
    FOREIGN KEY (emr_locationPk)
    REFERENCES emr_location (patient_locationPk)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


INSERT INTO registry_configuration (metadataId,configId,configValue) VALUES 
('LOCATION_ID_FORMAT','LOCATION_ID_FORMAT','ROOM_ID_BED_ID')
;

-- locationdevicemapping-- 

insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','DEVICE_SERIAL_NUM','deviceSerialNumber');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','FACILITY_ID','Facility');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','FACILITY_NAME','Facility');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','BUILDING','Building');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','FLOOR','Floor');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','DEPARTMENT_ID','Department ID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','DEPARTMENT_NAME','Department Name');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','ROOM_ID','Room ID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','ROOM_NAME','Room Name');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','BED_ID','Bed ID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','BED_NAME','Bed Label');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','DEFAULT_FACILITY','FACILITY');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','DEFAULT_BUILIDNG','BUILDING');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','DEFAULT_FLOOR','FLOOR');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','FACILITY','Facility');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','EXTESNION_ID','extension');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','EXTENSION_DESCRIPTION','extensionDescription');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','LOCATION_TYPE','locationType');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','LOCATION_ID','ROOM_ID_BED_ID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','RBLOCATIONID','RBLOCATIONID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','RTLSLOCATIONID','RTLSLOCATIONID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','EMRLOCATIONID','EMRLOCATIONID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','RBSTAFFID','RBSTAFFID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','EMRDOCTORID','EMRDOCTORID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','ADID','ADID');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','THUMBNAIL_URL','ThumbnailURL');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','RTMP_URI','RtmpURI');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','HTTP_URI','HttpURI');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','VIDEO_ID','videoId');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','font.size','medium');
insert into config_registry(service,module,configKey,configValue) values('locationdevicemapping','MES','language','en_US');
