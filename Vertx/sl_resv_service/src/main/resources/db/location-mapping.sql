--
-- Table structure for table rb_location_map
--
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE rb_location_map (
  rb_pme_location_PK SERIAL,
  rb_location_id varchar(100) NOT NULL,
  pme_location_id varchar(255) NOT NULL,
  PRIMARY KEY (rb_pme_location_PK)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table rtls_location_map
--
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE rtls_location_map (
  rtls_pme_location_PK SERIAL,
  rtls_location_id varchar(100) NOT NULL,
  pme_location_id varchar(255) NOT NULL,
  PRIMARY KEY (rtls_pme_location_PK)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table rb_staff_map
--
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE rb_staff_map (
  rb_ad_id_PK SERIAL,
  rb_staff_id varchar(100) NOT NULL,
  pme_ad_id varchar(100) NOT NULL,
  PRIMARY KEY (rb_ad_id_PK)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE emrdoctorid_adid_map (
  emrdoctorIdadidPk SERIAL,
  emrdoctorId varchar(45) DEFAULT NULL,
  pmeadId varchar(45) DEFAULT NULL,
  PRIMARY KEY (emrdoctorIdadidPk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Table structure for table rtls_staff_map


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE rtls_staff_map (
  rtls_ad_id_PK SERIAL,
  rtls_staff_id varchar(100) NOT NULL,
  pme_ad_id varchar(100) NOT NULL,
  PRIMARY KEY (rtls_ad_id_PK)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

