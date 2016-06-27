
INSERT INTO db_version (date, description, name, service) VALUES (now(),'PME DB Version','1.0.0 (1.0.97)','CPC');

--
-- Table structure for table diagnosis
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE diagnosis (
  dialgnosisid SERIAL,
  code varchar(245) DEFAULT NULL,
  name varchar(245) DEFAULT NULL,
  method varchar(20) DEFAULT NULL,
  PRIMARY KEY (dialgnosisid)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TABLE patient_alias (
  patient_alias_pk SERIAL,
  patient_id varchar(255) NOT NULL,
  last_name varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  middle_name varchar(255) DEFAULT NULL,
  name_suffix varchar(32) DEFAULT NULL,
  title_prefix varchar(32) DEFAULT NULL,
  degree varchar(32) DEFAULT NULL,
  name_type varchar(32) NOT NULL DEFAULT 'UNKNOWN_TYPE',
  PRIMARY KEY (patient_alias_pk)
  /*KEY ALIAS_PATIENTID_idx (patient_id)*/
) ;
CREATE INDEX ALIAS_PATIENTID_idx ON patient_alias(patient_id);

--
-- Table structure for table patient_location
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient_location (
  patient_locationPk SERIAL,
  bed varchar(255) DEFAULT NULL,
  room varchar(255) DEFAULT NULL,
  facility varchar(255) DEFAULT NULL,
  department varchar(255) DEFAULT NULL,
  location varchar(255) DEFAULT NULL UNIQUE,
  building varchar(255) DEFAULT NULL,
  floor varchar(255) DEFAULT NULL,
  extension varchar(255) DEFAULT NULL,
  PRIMARY KEY (patient_locationPk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table user
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE user_info (
  userPk SERIAL,
  firstName varchar(255) DEFAULT NULL,
  role varchar(45) DEFAULT NULL,
  externalRefId varchar(255) DEFAULT NULL,
  lastName varchar(255) DEFAULT NULL,
  displayName varchar(255) DEFAULT NULL,
  title varchar(20) DEFAULT NULL,
  id varchar(255) NOT NULL UNIQUE,
  PRIMARY KEY (userPk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table patient
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient (
  patientPk SERIAL,
  emrRefId varchar(255) DEFAULT NULL,
  patientId varchar(255) NOT NULL UNIQUE,
  user_id int NOT NULL UNIQUE,
  primaryDoctorFirstName varchar(45) DEFAULT NULL,
  primaryDoctorLastName varchar(45) DEFAULT NULL,
  primaryDoctorTitle varchar(45) DEFAULT NULL,
  primaryNurseFirstname varchar(45) DEFAULT NULL,
  primaryNurseLastName varchar(45) DEFAULT NULL,
  primaryNurseTitle varchar(10) DEFAULT NULL,
  patient_location int NOT NULL UNIQUE,
  nurseId varchar(20) DEFAULT NULL,
  emrDoctorId varchar(20) DEFAULT NULL,
  admittedTime timestamp NULL DEFAULT NULL,
  dischargeTime timestamp NULL DEFAULT NULL,
  estimatedDischargeTime timestamp NULL DEFAULT NULL,
  age varchar(45) NULL DEFAULT NULL,
  gender varchar(45) NULL DEFAULT NULL,
  patientType varchar(32) NOT NULL DEFAULT 'NOT_SPECIFIED',
  preferredAlias int DEFAULT NULL,
  messageSequence bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (patientPk),
  -- CONSTRAINT patientId_UNIQUE UNIQUE (patientId),
  /*KEY fk_patient_user1_idx (user),
  KEY fk_patient_patient_location2_idx (patient_location),
  KEY fk_patient_patient_alias_idx (preferredAlias),*/
  CONSTRAINT fk_patient_patient_alias FOREIGN KEY (preferredAlias) REFERENCES patient_alias (patient_alias_pk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_patient_patient_location2 FOREIGN KEY (patient_location) REFERENCES patient_location (patient_locationPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_patient_user1 FOREIGN KEY (user_id) REFERENCES user_info (userPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_patient_user1_idx ON patient(user_id);
CREATE INDEX fk_patient_patient_location2_idx ON patient(patient_location);
CREATE INDEX fk_patient_patient_alias_idx ON patient(preferredAlias);
--
-- Table structure for table patient_has_diagnosis
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient_has_diagnosis (
  patient_patientPk int NOT NULL,
  diagnosis_dialgnosisid int NOT NULL,
  /*KEY fk_patient_has_diagnosis_patient1_idx (patient_patientPk),
  KEY fk_patient_has_diagnosis_diagnosis1_idx (diagnosis_dialgnosisid),*/
  CONSTRAINT fk_patient_has_diagnosis_diagnosis1 FOREIGN KEY (diagnosis_dialgnosisid) REFERENCES diagnosis (dialgnosisid) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_patient_has_diagnosis_patient1 FOREIGN KEY (patient_patientPk) REFERENCES patient (patientPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_patient_has_diagnosis_patient1_idx ON patient_has_diagnosis(patient_patientPk);
CREATE INDEX fk_patient_has_diagnosis_diagnosis1_idx ON patient_has_diagnosis(diagnosis_dialgnosisid);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table patient_has_preferences
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient_has_preferences (
  patient_patientPk int NOT NULL,
  language varchar(100) DEFAULT NULL,
  show_notifications BOOLEAN DEFAULT FALSE,
  show_footer BOOLEAN DEFAULT FALSE,
  font_size varchar(10) DEFAULT NULL,
  name_display_format varchar(45) NOT NULL DEFAULT 'FIRST_NAME_ONLY',
  phone_ringer BOOLEAN DEFAULT FALSE,
  video_call BOOLEAN DEFAULT FALSE,
  presence_do_not_disturb BOOLEAN DEFAULT FALSE,
  privacy_data_unlock_duration varchar(10) NULL,
  privacy_data_lock BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (patient_patientPk),
  CONSTRAINT fk_patient_has_preferences_patient1 FOREIGN KEY (patient_patientPk) REFERENCES patient (patientPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;




--
-- Table structure for table survey_optiongroup
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_optiongroup (
  optionGroupPk SERIAL,
  optionGroupId varchar(50) NOT NULL,
  iconURL varchar(255) DEFAULT NULL,
  createdBy varchar(50) DEFAULT NULL,
  lastModifiedBy varchar(50) DEFAULT NULL,
  lastModifiedAt timestamp NULL DEFAULT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (optionGroupPk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table survey_optiongroup
--


/*!40000 ALTER TABLE survey_optiongroup DISABLE KEYS */;
INSERT INTO survey_optiongroup(optionGroupId,iconURL,createdBy,lastModifiedBy,lastModifiedAt,deleted) VALUES('mainOptionGroup','http://dummy.com',NULL,NULL,NULL,false),('painscoreRating1','http://dummy.com',NULL,NULL,NULL,false),('yesNo1','http://dummy.com',NULL,NULL,NULL,false),('painscoreRating2','http://dummy.com',NULL,NULL,NULL,false);
/*!40000 ALTER TABLE survey_optiongroup ENABLE KEYS */;




--
-- Table structure for table survey_question
--



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_question (
  questionPk SERIAL,
  questionId varchar(50) NOT NULL,
  iconURL varchar(255) DEFAULT NULL,
  multiSelect BOOLEAN DEFAULT FALSE,
  createdBy varchar(50) DEFAULT NULL,
  lastModifiedBy varchar(50) DEFAULT NULL,
  lastModifiedAt timestamp NULL DEFAULT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  locationId varchar(255) DEFAULT NULL,
  optiongroup_optionGroupPk int NOT NULL,
  age VARCHAR(45) NOT NULL DEFAULT 'G',
  gender VARCHAR(45) NOT NULL DEFAULT 'all',
  PRIMARY KEY (questionPk),
 /* KEY fk_question_optiongroup_idx (optiongroup_optionGroupPk),*/
  CONSTRAINT fk_question_optiongroup1 FOREIGN KEY (optiongroup_optionGroupPk) REFERENCES survey_optiongroup (optionGroupPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;
CREATE INDEX fk_question_optiongroup_idx ON survey_question(optiongroup_optionGroupPk);
--
-- Dumping data for table survey_question
--


/*!40000 ALTER TABLE survey_question DISABLE KEYS */;

INSERT INTO survey_question(questionId,iconURL,multiSelect,createdBy,lastModifiedBy,lastModifiedAt,deleted,locationId,optiongroup_optionGroupPk,age,gender)
VALUES ('question1','http://dummy.com',false,NULL,NULL,NULL,false,NULL,1,'G','all'),('question2','http://dummy.com',false,NULL,NULL,NULL,false,NULL,1,'G','all'),('question3','http://dummy.com',false,NULL,NULL,NULL,false,NULL,1,'G','all'),('question4','http://dummy.com',false,NULL,NULL,NULL,false,NULL,1,'G','all'),('question5','http://dummy.com',false,NULL,NULL,NULL,false,NULL,4,'G','all'),('question6','http://dummy.com',false,NULL,NULL,NULL,false,NULL,1,'G','all'),('dischargeQuestId3','http//dummy.com/',false,NULL,NULL,'2014-08-24 23:26:27',false,NULL,3,'G','all'),('dischargeQuestId4','http//dummy.com/',false,NULL,NULL,'2014-08-24 23:27:44',false,NULL,3,'G','all'),('dischargeQuestId5','http//dummy.com/',false,NULL,NULL,'2014-08-24 23:28:21',false,NULL,3,'G','all'),('dischargeQuestId6','http//dummy.com/',false,NULL,NULL,'2014-08-24 23:28:51',false,NULL,3,'G','all');
/*!40000 ALTER TABLE survey_question ENABLE KEYS */;




--
-- Table structure for table survey_department_has_question
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_department_has_question (
  departmentQuestionPk SERIAL,
  departmentId varchar(255) NOT NULL,
  Survey_questionPk int NOT NULL,
  PRIMARY KEY (departmentQuestionPk),
  /*KEY fk_survey_department_has_questions_survey_question1_idx (Survey_questionPk),*/
  CONSTRAINT fk_survey_department_has_questions_survey_question1 FOREIGN KEY (Survey_questionPk) REFERENCES survey_question (questionPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX fk_survey_department_has_questions_survey_question1_idx ON survey_department_has_question(Survey_questionPk);
--
-- Dumping data for table survey_department_has_question
--
INSERT INTO survey_department_has_question(departmentId,Survey_questionPk) VALUES ('0',1),('0',2),('0',3),('0',4),('0',5),('0',6),('0',7),('0',8),('0',9),('0',10);


--
-- Table structure for table survey_survey
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;


CREATE TABLE survey_survey (
  surveyPk SERIAL,
  surveyId varchar(50) NOT NULL UNIQUE,
  iconURL varchar(255) DEFAULT NULL,
  surveyOrder int DEFAULT NULL,
  isRecurring BOOLEAN DEFAULT FALSE,
  createdBy varchar(50) DEFAULT NULL,
  lastModifiedBy varchar(50) DEFAULT NULL,
  lastModifiedAt timestamp NULL DEFAULT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  type VARCHAR(45) NOT NULL DEFAULT 'standard',
  age VARCHAR(10) NOT NULL DEFAULT 'G',
  gender VARCHAR(45) NOT NULL DEFAULT 'all',
  PRIMARY KEY (surveyPk)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table survey_survey
--


/*!40000 ALTER TABLE survey_survey DISABLE KEYS */;
INSERT INTO survey_survey(surveyId,iconURL,surveyOrder,isRecurring,createdBy,lastModifiedBy,lastModifiedAt,deleted,type,age,gender)
VALUES ('discharge1','Discharge',1,FALSE,'xyz','xyz',NULL,FALSE,'discharge','G','all'),('wellness1','Wellness',2,FALSE,NULL,NULL,NULL,FALSE,'standard','G','all'),('cleanliness1','Cleanliness',3,FALSE,NULL,NULL,NULL,FALSE,'standard','G','all'),('satisfaction1','Satisfaction',4,FALSE,NULL,NULL,NULL,FALSE,'standard','G','all'),('insurance1','Insurance',5,FALSE,NULL,NULL,NULL,FALSE,'standard','G','all'),('safety1','Safety',6,FALSE,NULL,NULL,NULL,FALSE,'standard','G','all'),('painscore1','PainScore',7,FALSE,NULL,NULL,NULL,FALSE,'painscore','G','all'),('dischargeCheckList1','DischargeCheckList',8,FALSE,NULL,NULL,NULL,FALSE,'discharge','G','all');
/*!40000 ALTER TABLE survey_survey ENABLE KEYS */;




--
-- Table structure for table survey_department_has_survey
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_department_has_survey (
  departmentSurveyPk SERIAL,
  departmentId varchar(255) NOT NULL,
  Survey_surveyPk int NOT NULL,
  PRIMARY KEY (departmentSurveyPk),
  /*KEY fk_department_has_survey_survey_survey1_idx (Survey_surveyPk),*/
  CONSTRAINT fk_department_has_survey_survey_survey1 FOREIGN KEY (Survey_surveyPk) REFERENCES survey_survey (surveyPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX fk_department_has_survey_survey_survey1_idx ON survey_department_has_survey(Survey_surveyPk);
--
-- Dumping data for table survey_department_has_survey
--


/*!40000 ALTER TABLE survey_department_has_survey DISABLE KEYS */;
INSERT INTO survey_department_has_survey(departmentId,Survey_surveyPk) VALUES ('0',1),('0',2),('0',3),('0',4),('0',5),('0',6),('0',7),('0',8);
/*!40000 ALTER TABLE survey_department_has_survey ENABLE KEYS */;


--
-- Table structure for table survey_option
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_option (
  optionPk SERIAL,
  optionId varchar(50) NOT NULL,
  iconURL varchar(255) DEFAULT NULL,
  descriptive BOOLEAN DEFAULT NULL,
  optionOrder int DEFAULT NULL,
  createdBy varchar(50) DEFAULT NULL,
  lastModifiedBy varchar(50) DEFAULT NULL,
  lastModifiedAt timestamp NULL DEFAULT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  optiongroup_optionGroupPk int NOT NULL,
  PRIMARY KEY (optionPk),
  /*KEY fk_option_optiongroup_idx (optiongroup_optionGroupPk),*/
  CONSTRAINT fk_option_optiongroup1 FOREIGN KEY (optiongroup_optionGroupPk) REFERENCES survey_optiongroup (optionGroupPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;
CREATE INDEX fk_option_optiongroup_idx ON survey_option(optiongroup_optionGroupPk);
--
-- Dumping data for table survey_option
--


/*!40000 ALTER TABLE survey_option DISABLE KEYS */;
INSERT INTO survey_option(optionId,iconURL,descriptive,optionOrder,createdBy,lastModifiedBy,lastModifiedAt,deleted,optiongroup_optionGroupPk) VALUES ('option1','http://dummy',FALSE,1,NULL,NULL,NULL,FALSE,1),('option2','http://dummy',FALSE,2,NULL,NULL,NULL,FALSE,1),('option3','http://dummy',FALSE,3,NULL,NULL,NULL,FALSE,1),('option4','http://dummy',FALSE,4,NULL,NULL,NULL,FALSE,1),('option5','http://dummy',FALSE,5,NULL,NULL,NULL,FALSE,1),('option6','http://dummy',FALSE,1,NULL,NULL,NULL,FALSE,3),('option7','http://dummy',FALSE,2,NULL,NULL,NULL,FALSE,3),('option8','http://dummy',FALSE,1,NULL,NULL,NULL,FALSE,2),('option9','http://dummy',FALSE,2,NULL,NULL,NULL,FALSE,2),('option10','http://dummy',FALSE,3,NULL,NULL,NULL,FALSE,2),('option11','http://dummy',FALSE,4,NULL,NULL,NULL,FALSE,2),('option12','http://dummy',FALSE,5,NULL,NULL,NULL,FALSE,2),('option13','http://dummy',FALSE,6,NULL,NULL,NULL,FALSE,2),('option14','http://dummy',FALSE,3,NULL,NULL,NULL,FALSE,3),('painscoreOption1','PainScore_0',FALSE,0,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption2','PainScore_1',FALSE,1,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption3','PainScore_2',FALSE,2,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption4','PainScore_3',FALSE,3,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption5','PainScore_4',FALSE,4,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption6','PainScore_5',FALSE,5,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption7','PainScore_6',FALSE,6,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption8','PainScore_7',FALSE,7,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption9','PainScore_8',FALSE,8,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption10','PainScore_9',FALSE,9,NULL,NULL,'2014-04-27 20:12:07',FALSE,4),('painscoreOption11','PainScore_10',FALSE,10,NULL,NULL,'2014-04-27 20:12:07',FALSE,4);
/*!40000 ALTER TABLE survey_option ENABLE KEYS */;



--
-- Table structure for table survey_survey_has_question
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_survey_has_question (
  Survey_surveyPk int NOT NULL,
  Question_questionPk int NOT NULL,
  questionOrder int DEFAULT NULL,
  PRIMARY KEY (Survey_surveyPk,Question_questionPk),
  /*KEY fk_Survey_has_Question_Question_idx (Question_questionPk),
  KEY fk_Survey_has_Question_Survey_idx (Survey_surveyPk),*/
  CONSTRAINT fk_Survey_has_Question_Question1 FOREIGN KEY (Question_questionPk) REFERENCES survey_question (questionPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_Survey_has_Question_Survey1 FOREIGN KEY (Survey_surveyPk) REFERENCES survey_survey (surveyPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX fk_Survey_has_Question_Question_idx ON survey_survey_has_question(Question_questionPk);
CREATE INDEX fk_Survey_has_Question_Survey_idx ON survey_survey_has_question(Survey_surveyPk);
--
-- Dumping data for table survey_survey_has_question
--


/*!40000 ALTER TABLE survey_survey_has_question DISABLE KEYS */;
INSERT INTO survey_survey_has_question VALUES (1,1,1),(1,2,2),(1,3,3),(1,4,4),(2,6,1),(3,1,1),(3,2,2),(4,1,3),(4,2,1),(4,3,2),(4,4,4),(5,1,1),(5,2,2),(6,1,1),(6,2,2),(7,5,1),(8,7,1),(8,8,2),(8,9,3),(8,10,4);
/*!40000 ALTER TABLE survey_survey_has_question ENABLE KEYS */;


--
-- Table structure for table survey_usersurvey
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_usersurvey (
  userSurveyPk SERIAL,
  lastAttemptedAt timestamp NULL DEFAULT NULL,
  completedAt timestamp NULL DEFAULT NULL,
  survey_surveyPk int NOT NULL,
  userId varchar(255) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT FALSE,
  startDateTime timestamp NULL DEFAULT NULL,
  endDateTime timestamp NULL DEFAULT NULL,
  PRIMARY KEY (userSurveyPk),
  /*KEY fk_survey_surveyPk_idx (survey_surveyPk),*/
  CONSTRAINT fk_PatientSurvey_Survey1 FOREIGN KEY (survey_surveyPk) REFERENCES survey_survey (surveyPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;
CREATE INDEX fk_survey_surveyPk_idx ON survey_usersurvey(survey_surveyPk);
--
-- Dumping data for table survey_usersurvey
--


/*!40000 ALTER TABLE survey_usersurvey DISABLE KEYS */;
/*!40000 ALTER TABLE survey_usersurvey ENABLE KEYS */;


--
-- Table structure for table survey_answer
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE survey_answer (
  answerPk SERIAL,
  AnswerText varchar(500) DEFAULT NULL,
  answeredAt timestamp NULL DEFAULT NULL,
  Option_optionPk int DEFAULT NULL,
  Question_questionPk int NOT NULL,
  usersurvey_userSurveyPk int NOT NULL,
  PRIMARY KEY (answerPk),
  /*KEY fk_Answer_Option_idx (Option_optionPk),
  KEY fk_Answer_Question_idx (Question_questionPk),
  KEY fk_answer_usersurvey_idx (usersurvey_userSurveyPk),*/
  CONSTRAINT fk_Answer_Option1 FOREIGN KEY (Option_optionPk) REFERENCES survey_option (optionPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_Answer_Question1 FOREIGN KEY (Question_questionPk) REFERENCES survey_question (questionPk) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_answer_usersurvey1 FOREIGN KEY (usersurvey_userSurveyPk) REFERENCES survey_usersurvey (userSurveyPk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX fk_Answer_Option_idx ON survey_answer(Option_optionPk);
CREATE INDEX fk_Answer_Question_idx ON survey_answer(Question_questionPk);
CREATE INDEX fk_answer_usersurvey_idx ON survey_answer(usersurvey_userSurveyPk);
--
-- Dumping data for table survey_answer
--


/*!40000 ALTER TABLE survey_answer DISABLE KEYS */;
/*!40000 ALTER TABLE survey_answer ENABLE KEYS */;



-- Dump completed on 2014-11-26 15:33:53


--
-- END SURVEY DB SCRIPT
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed







/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient_request (
  patientRequestId SERIAL,
  taskId varchar(45) DEFAULT NULL,
  patientId varchar(255) DEFAULT NULL,
  requestTime timestamp NULL DEFAULT NULL,
  completed BOOLEAN DEFAULT FALSE,
  completeTime timestamp NULL DEFAULT NULL,
  completedBy varchar(100) DEFAULT NULL,
  escalated BOOLEAN DEFAULT FALSE,
  patientRequestImageUrl varchar(500) DEFAULT NULL,
  taskDescription text,
  userFeedbackResults text,
  PRIMARY KEY (patientRequestId)
 /*KEY REQUEST_Idx1 (patientId)*/
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX REQUEST_Idx1 ON patient_request(patientId);


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient_message (
  messageId SERIAL,
  preDefinedMessageId varchar(45) DEFAULT NULL,
  patientId varchar(255) DEFAULT NULL,
  patientMessageText text,
  patientMessageTime timestamp NULL DEFAULT NULL,
  nurseStaffId varchar(45) DEFAULT NULL,
  nurseMessageText text,
  nurseMessageTime timestamp NULL DEFAULT NULL,
  ack BOOLEAN DEFAULT FALSE,
  escalated BOOLEAN DEFAULT FALSE,
  completed BOOLEAN DEFAULT FALSE,
  completeTime timestamp NULL DEFAULT NULL,
  PRIMARY KEY (messageId)
  /*KEY MESSAGE_idx1 (patientId)*/
) ;
 CREATE INDEX MESSAGE_idx1 ON patient_message (patientId);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- DDL for patient_goal 
--


CREATE TABLE patient_goal (
  goal_id SERIAL,
  patient_id VARCHAR(255) NULL,
  description text NULL,
  created_by VARCHAR(255) NULL,
  createdDate TIMESTAMP(0) NULL DEFAULT CURRENT_TIMESTAMP,
  updatedDate TIMESTAMP(0) NULL DEFAULT null,
  status VARCHAR(20) NULL,
  completedOn TIMESTAMP(0) NULL DEFAULT null,
  updated_by VARCHAR(255) NULL ,
  escalated BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (goal_id));

  

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE patient_schedule (
  id SERIAL,
  appointmentId varchar(20) DEFAULT NULL,
  reason varchar(250) DEFAULT NULL,
  emrRefId varchar(255) DEFAULT NULL,
  duration varchar(20) DEFAULT NULL,
  durationUnit varchar(20) DEFAULT NULL,
  resourceGroup text,
  fromTimeEpoch bigint NOT NULL DEFAULT 0,
  messageSequence bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (id)
  -- KEY SCHEDULE_idx1 (emrRefId)
) ;
CREATE INDEX SCHEDULE_idx1 ON patient_schedule(emrRefId);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Food Ordering
--



CREATE TABLE MENULOCK (
  id SERIAL,
  mrn varchar(55) NOT NULL,
  mealid varchar(10) NOT NULL,
  mealdate varchar(20) NOT NULL,
  lockstarttime varchar(100) NOT NULL,
  lockcreatedon date  NULL,
  PRIMARY KEY (id)
) ;




/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE master_metadata (
  id SERIAL,
  metadataType varchar(45) DEFAULT NULL,
  metadataText TEXT,
  locale varchar(45) DEFAULT NULL,
  unitId varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table master_metadata
--


/*!40000 ALTER TABLE master_metadata DISABLE KEYS */;
INSERT INTO master_metadata(metadataType,metadataText,locale,unitId) VALUES ('MESSAGES','{"preDefinedMessages":[{"preDefinedMessageId":"0-1","preDefinedMessageText":"¿Cuánto tiempo estaré en el hospital?","age":"G","gender":"all"},{"preDefinedMessageId":"0-2","preDefinedMessageText":"Cuándo visitar a mi médico?","age":"G","gender":"all"},{"preDefinedMessageId":"0-3","preDefinedMessageText":"¿Cuándo voy a tener pruebas?","age":"G","gender":"all"},{"preDefinedMessageId":"004","preDefinedMessageText":"¿A qué hora se descarga?","age":"G","gender":"all"},{"preDefinedMessageId":"0-5","preDefinedMessageText":"Cuando las comidas se sirven?","age":"G","gender":"all"},{"preDefinedMessageId":"0-6","preDefinedMessageText":"Cuando se abra la tienda de regalos?","age":"G","gender":"all"}]}','es_US','0'),
('MESSAGES','{\n  "preDefinedMessages": [\n    {\n      "preDefinedMessageId": "0-1",\n      "preDefinedMessageText": "How long will I be at the hospital?",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "preDefinedMessageId": "0-2",\n      "preDefinedMessageText": "When will my doctor visit?",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "preDefinedMessageId": "0-3",\n      "preDefinedMessageText": "When will I have tests?",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "preDefinedMessageId": "0-4",\n      "preDefinedMessageText": "What time is discharge?",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "preDefinedMessageId": "0-5",\n      "preDefinedMessageText": "When are meals served?",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "preDefinedMessageId": "0-6",\n      "preDefinedMessageText": "When is the gift shop open?",\n      "age": "G",\n      "gender": "all"\n    }\n  ]\n}','en_US','0'),
('REQUESTS','{\r\n  "requests": [\r\n    {\r\n      "taskId": "0-1",\r\n      "taskDescription": "My Room is too hot",\r\n      "iconURL": "",\r\n      "age": "G",\r\n      "gender": "all"\r\n    },\r\n    {\r\n      "taskId": "0-2",\r\n      "taskDescription": "My Room is too cold",\r\n      "iconURL": "",\r\n      "age": "G",\r\n      "gender": "all"\r\n    },\r\n    {\r\n      "taskId": "0-3",\r\n      "taskDescription": "Bring me an ice pack",\r\n      "iconURL": "",\r\n      "age": "G",\r\n      "gender": "all"\r\n    },\r\n    {\r\n      "taskId": "0-4",\r\n      "taskDescription": "Bed comfort level",\r\n      "iconURL": "",\r\n      "age": "G",\r\n      "gender": "all"\r\n    },\r\n    {\r\n      "taskId": "0-5",\r\n      "taskDescription": "Hearing and headset",\r\n      "iconURL": "",\r\n      "age": "G",\r\n      "gender": "all"\r\n    },\r\n    {\r\n      "taskId": "0-6",\r\n      "taskDescription": "I would like to see a Chaplain",\r\n      "iconURL": "",\r\n      "age": "G",\r\n      "gender": "all"\r\n    }\r\n  ]\r\n}','en_US','0'),
('REQUESTS','{"requests":[{"taskId":"0-1","taskDescription":"Mi habitación está demasiado caliente","iconURL":"","age":"G","gender":"all"},{"taskId":"0-2","taskDescription":"MyHabitación es demasiado fría","iconURL":"","age":"G","gender":"all"},{"taskId":"0-3","taskDescription":"Tráeme una bolsa de hielo","iconURL":"","age":"G","gender":"all"},{"taskId":"0-4","taskDescription":"Cama nivel de comodidad","iconURL":"","age":"G","gender":"all"},{"taskId":"0-5","taskDescription":"Audición y auriculares","iconURL":"","age":"G","gender":"all"},{"taskId":"0-6","taskDescription":"Me gustaría ver a un capellán","iconURL":"","age":"G","gender":"all"}]}','es_US','0'),
('FAQS','{"faq":[{"questionId":"01","question":"¿Cuáles son las horas para la tienda de regalos?","answer":"La tienda de regalos permanecerá abierta entre las 9 am a 6 pm los todos los días de la semana","age":"G","gender":"all"},{"questionId":"02","question":"Cuando las comidas se sirven?","answer":"Las comidas se servirán en base a la que su condición médica y se selecciona por nuestra nutricionista a instancias de su médico. El durante las comidas regulares de desayuno, el almuerzo y la cena se sirven.","age":"G","gender":"all"},{"questionId":"03","question":"¿Cuándo puedo pedir comidas?","answer":"Puede pedir comidas hasta 2 horas antes de la hora de la comida. Por favor, utilice la sección de pedidos de alimentos de su televisor para ordenar comidas extras para sus invitados.","age":"G","gender":"all"},{"questionId":"04","question":"¿Cómo puedo pedir un capellán?","answer":"Si a usted le gustaría ver a un capellán, o asistir a un servicio de capilla, por favor pregunte a un miembro del personal de la sala ayuda se comunique con ellos. Capellanía ofrece un servicio de 24 horas de guardia en los hospitales para situaciones urgentes. Por favor, pregunte al personal para ponerse en contacto con el capellán deber a través de centralita.","age":"G","gender":"all"}]}','es_US','0'),
('FAQS','{\n  "faq": [\n    {\n      "questionId": "0-1",\n      "question": "What are the hours for the gift shop?",\n      "answer": "The gift shop will remain open between 9 AM to 6 PM on all days of the week",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "questionId": "0-2",\n      "question": "When are meals served?",\n      "answer": "Meals will be served based on the your medical condition and is selected by our nutritionist after consulting with your doctor. The are served during regular meal times of breakfast, lunch and dinner. ",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "questionId": "0-3",\n      "question": "When can I order meals?",\n      "answer": "You can order meals up to 2 hours prior to the time of the meal. Please use the food ordering section of your TV set to order extra meals for your guests.",\n      "age": "G",\n      "gender": "all"\n    },\n    {\n      "questionId": "0-4",\n      "question": "How do I ask for a Chaplain?",\n      "answer": "If you would like to see a chaplain, or attend a chapel service, please ask a member of ward staff help you contact them. Chaplaincy provides a 24 hour on-call service within the hospitals for urgent situations. Please ask staff to contact the duty chaplain via switchboard.",\n      "age": "G",\n      "gender": "all"\n    }\n  ]\n}','en_US','0');/*!40000 ALTER TABLE master_metadata ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-14 12:14:39

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-14 15:12:34

CREATE TABLE patient_history (
  id SERIAL,
  patient_id varchar(255) NOT NULL,
  patient_data text,
  createdDate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updatedDate timestamp NULL DEFAULT NULL,
  createdBy varchar(20) DEFAULT 'admin',
  updatedBy varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
  -- KEY PATIENTHISTORY_idx (patient_id)
) ;
CREATE INDEX PATIENTHISTORY_idx ON patient_history (patient_id);

CREATE TABLE patient_notification (
  id SERIAL,
  patient_id VARCHAR(255) NOT NULL,
  notification_id VARCHAR(255) NOT NULL,
  notification_event TEXT NOT NULL,
  created_date TIMESTAMP NULL,
  updated_date TIMESTAMP NULL,
  PRIMARY KEY (id)
  );
CREATE INDEX NOTIFICATION_PATIENTID_idx ON patient_notification(patient_id ASC);

--
-- DB Indexes
--

/*ALTER TABLE patient_goal */
CREATE INDEX GOAL_idx ON patient_goal (patient_id ASC, status ASC, createdDate ASC);
/*ALTER TABLE user */
CREATE INDEX USER_idx ON user_info (id ASC);
/*ALTER TABLE patient_message */
CREATE INDEX MESSAGE_idx ON patient_message (patientId ASC);
/*ALTER TABLE patient_request */
CREATE INDEX REQUEST_Idx ON patient_request (patientId ASC);
/*ALTER TABLE patient_schedule */
CREATE INDEX SCHEDULE_idx ON patient_schedule (emrRefId ASC);
/*ALTER TABLE patient */
-- DROP INDEX patientId_UNIQUE;
CREATE UNIQUE INDEX patientId_UNIQUE ON patient (patientId ASC, emrRefId ASC);
/*ALTER TABLE device */
CREATE INDEX DEVICE_idx ON device (serialNumber ASC);




/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE meal_name_map (
  id_PK SERIAL,
  mealitemid varchar(100) NOT NULL,
  mealname varchar(100) NOT NULL,
  imageURL varchar(250) DEFAULT NULL,
  active BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id_PK)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE meal_image_map (
  id_PK SERIAL,
  foodkey varchar(100),
  foodname varchar(100),
  imageURL varchar(250) DEFAULT NULL,
  PRIMARY KEY (id_PK)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table staff_room_visit
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE staff_room_visit (
  idstaff_room_visit SERIAL,
  patientId varchar(255) NOT NULL,
  enterTimestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  exitTimestamp timestamp NULL DEFAULT NULL,
  rtlsStaffId varchar(45) NOT NULL,
  pmeStaffId varchar(45) DEFAULT NULL,
  rtlsLocationId varchar(45) DEFAULT NULL,
  pmeLocationId varchar(255) DEFAULT NULL,
  PRIMARY KEY (idstaff_room_visit)
) ;


--
-- Table structure for table prescribed_drug
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE prescribed_drug (
  prescribed_drugPk SERIAL,
  placerOrderNumber varchar(45) NOT NULL,
  txnTimestamp timestamp NULL DEFAULT NULL,
  patientId varchar(255) NOT NULL,
  medicationId varchar(45) NOT NULL,
  medicationName varchar(160) DEFAULT NULL,
  codingSystem varchar(45) NOT NULL,
  messageSequence bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (prescribed_drugPk),
  CONSTRAINT PRESCRIPTION_Idx2 UNIQUE (placerOrderNumber),
  -- KEY PRESCRIPTION_Idx1 (patientId),
  CONSTRAINT fk_patient FOREIGN KEY (patientId) 
  		REFERENCES patient (patientId) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX PRESCRIPTION_Idx1 ON prescribed_drug(patientId);
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table drug_component
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE drug_component (
  drug_componentPk SERIAL,
  parentPrescription int NOT NULL,
  drugId varchar(45) NOT NULL,
  drugName varchar(160) DEFAULT NULL,
  codingSystem varchar(45) NOT NULL,
  PRIMARY KEY (drug_componentPk)
  /*KEY fk_drugId_prescriptionPk_idx (parentPrescription)*/
) ;
CREATE INDEX fk_drugId_prescriptionPk_idx ON drug_component(parentPrescription);
/*!40101 SET character_set_client = @saved_cs_client */;






CREATE TABLE emr_staff_member (
  emr_staff_member_pk SERIAL,
  emr_staff_id varchar(64) NOT NULL,
  last_name varchar(64) DEFAULT NULL,
  first_name varchar(64) DEFAULT NULL,
  middle_name varchar(64) DEFAULT NULL,
  name_suffix varchar(16) DEFAULT NULL,
  title_prefix varchar(16) DEFAULT NULL,
  degree varchar(16) DEFAULT NULL,
  PRIMARY KEY (emr_staff_member_pk),
  CONSTRAINT emr_staff_id_UNIQUE UNIQUE (emr_staff_id)
) ;



CREATE TABLE emr_staff_assignment (
  emr_staff_assignment_pk SERIAL,
  emr_staff_id varchar(64) NOT NULL,
  patient_id varchar(255) NOT NULL,
  role varchar(32) NOT NULL DEFAULT 'OTHER_PROVIDER',
  role_description varchar(100) DEFAULT NULL,
  start_timestamp_epoch bigint DEFAULT NULL,
  end_timestamp_epoch bigint DEFAULT NULL,
  PRIMARY KEY (emr_staff_assignment_pk),
  -- KEY fk_emr_staff_assignment_emr_staff_idx (emr_staff_id),
  -- KEY fk_emr_staff_assignment_patient_idx (patient_id),
  CONSTRAINT fk_emr_staff_assignment_emr_staff FOREIGN KEY (emr_staff_id) REFERENCES emr_staff_member (emr_staff_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_emr_staff_assignment_patient FOREIGN KEY (patient_id) REFERENCES patient (patientId) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_emr_staff_assignment_emr_staff_idx ON emr_staff_assignment(emr_staff_id);
CREATE INDEX fk_emr_staff_assignment_patient_idx ON emr_staff_assignment(patient_id);



--
-- Dumping data for table rb_staff_map
--


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-25 20:44:15

-- SET foreign_key_checks = 0;



/*!40000 ALTER TABLE rb_staff_map DISABLE KEYS */;
INSERT INTO rb_staff_map(rb_staff_id,pme_ad_id) VALUES ('rbstaff1','nurse1'),('rbstaff2','nurse2'),('rbstaff3','nurse3'),('rbstaff11','nurse11'),('rbstaff12','nurse12'),('rbstaff13','nurse13'),('rbstaff21','nurse21'),('rbstaff22','nurse22'),('rbstaff23','nurse23');
/*!40000 ALTER TABLE rb_staff_map ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


/*!40000 ALTER TABLE meal_image_map DISABLE KEYS */;
INSERT INTO meal_image_map (foodkey, foodname, imageURL) VALUES ('290','Appetizers','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/appetizers-left-nav.png'), ('291', 'Beverages','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Beverages_Icon_Left.png'), ('3', 'Juice','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Beverages_Icon_Left.png'),('296', 'Desserts','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Desserts_Icon_left.png'), ('354', 'Entrees&Sandwiches&Salads','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Entrees_Icon_Left.png'),('357', 'Sandwiches','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Entrees_Icon_Left.png'),('343', 'Hot Pizza','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Entrees_Icon_Left.png'),('292', 'Entrees','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Entrees_Icon_Left.png'),('302', 'EntreeCondiments','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Entrees_Icon_Left.png'), ('293', 'Sides&Soups&Salads','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/appetizers-left-nav.png'),('294', 'Starches','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/appetizers-left-nav.png'),('349', 'Sides','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/appetizers-left-nav.png'),('286', 'Breads','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/appetizers-left-nav.png'),('304', 'Vegetables','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/appetizers-left-nav.png');
/*!40000 ALTER TABLE meal_image_map ENABLE KEYS */;



/*!40000 ALTER TABLE meal_name_map DISABLE KEYS */;
INSERT INTO meal_name_map (mealitemid, mealname, imageURL, active) VALUES ('1','Breakfast','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Breakfast_Icon.png',TRUE), ('3', 'Lunch','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Lunch_Icon.png',TRUE), ('5', 'Dinner','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/Dinner_Icon.png',TRUE), ('11', '10 AM Snack','',TRUE),('13', '2 PM Snack','',TRUE), ('15', 'Bedtime Snack','',TRUE);
/*!40000 ALTER TABLE meal_name_map ENABLE KEYS */;



/*!40000 ALTER TABLE emrdoctorid_adid_map DISABLE KEYS */;
INSERT INTO emrdoctorid_adid_map(emrdoctorId,pmeadId) VALUES ('emrdoctor1','doctor1'),('emrdoctor2','doctor2'),('emrdoctor3','doctor3'),('emrdoctor4','doctor4'),('emrdoctor5','doctor5');
/*!40000 ALTER TABLE emrdoctorid_adid_map ENABLE KEYS */;


--
-- Table structure for table staff_image_map
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE staff_image_map (
  staff_image_mapPK SERIAL,
  directory_user_id varchar(100) NOT NULL,
  image_url varchar(255) NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  job_title varchar(255) DEFAULT NULL,
  date_created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_updated timestamp NOT NULL DEFAULT NULL,
  PRIMARY KEY (staff_image_mapPK),
  CONSTRAINT directory_user_id_UNIQUE UNIQUE  (directory_user_id)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table operator_message_history
--



CREATE TABLE operator_message_history (
  id SERIAL,
  text varchar(500) DEFAULT NULL,
  location VARCHAR(45) DEFAULT 'ALL',
  locationList varchar(500) DEFAULT NULL,
  locationType VARCHAR(45) DEFAULT 'ALL',
  duration int DEFAULT '-1',
  severity VARCHAR(45) DEFAULT 'EMERGENCY',
  displayType VARCHAR(45) DEFAULT 'FULLSCREEN',
  sentTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  operatorMessageId varchar(500) DEFAULT NULL,
  removed BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id)
) ;

-- SET foreign_key_checks = 1;


CREATE TABLE user_auth (
  user_authPK SERIAL,
  userId varchar(255) DEFAULT NULL,
  contextId varchar(255) DEFAULT NULL,
  contextValue varchar(2555) DEFAULT NULL,
  PRIMARY KEY (user_authPK),
  CONSTRAINT index3 UNIQUE  (userId,contextId)
) ;




CREATE TABLE phone_has_preferences (
 patient_patientPk int NOT NULL,
 audio_call_auto_answer BOOLEAN DEFAULT FALSE,
 video_call_auto_answer BOOLEAN DEFAULT FALSE,
 video_call_auto_answer_expiry_time int DEFAULT '10',
 last_updated timestamp NULL DEFAULT NULL,
 PRIMARY KEY (patient_patientPk),
 CONSTRAINT fk_phone_preferences_phone FOREIGN KEY (patient_patientPk) REFERENCES patient (patientPk) ON DELETE NO ACTION ON UPDATE NO ACTION
 ) ;
 
 
 -- content service: move when Videos are supported --
INSERT INTO metadata_registry (service,resourceName,resourceId,resourceContent,metadataId) VALUES 
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','VIDEOS','{"appId":"VIDEOS","appName":"Learn","appDescription":"Videos","privacyCheck":"NA"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#VIDEOS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','VIDEO_APPS','{"appId":"VIDEO_APPS","appName":"Video Apps","appDescription":"Video as App"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#VIDEO_APPS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','REQUIRED_VIDEOS','{"appId":"REQUIRED_VIDEOS","appName":"Required Videos","appDescription":"Required Videos assigned for Patient","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#REQUIRED_VIDEOS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','SUGGESTED_VIDEOS','{"appId":"SUGGESTED_VIDEOS","appName":"Suggested Videos","appDescription":"Suggested Videos assigned for Patient","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#SUGGESTED_VIDEOS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','MOVIES_ON_DEMAND','{"appId":"MOVIES_ON_DEMAND","appName":"Movies on Demand","appDescription":"Movies on Demand","privacyCheck":"NA"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#MOVIES_ON_DEMAND'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','CUSTOM_VIDEOS','{"appId":"CUSTOM_VIDEOS","appName":"Custom Videos","appDescription":"Video Provider is the Customer and/or Partner","privacyCheck":"NA"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#CUSTOM_VIDEOS')
;
INSERT INTO registry_configuration (metadataId,configId,configValue) VALUES 
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#VIDEOS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#VIDEOS','LOCATION_TYPE','GUEST_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#REQUIRED_VIDEOS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#SUGGESTED_VIDEOS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#VIDEO_APPS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#MOVIES_ON_DEMAND','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#CUSTOM_VIDEOS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#CUSTOM_VIDEOS','LOCATION_TYPE','GUEST_ROOM')
;

INSERT INTO resourcebundle VALUES 
('CONTENT#TOP_LEVEL_CATEGORY_NAME#EDUCATION#en_US','CONTENT','CONTENT,TOP_LEVEL_CATEGORY_NAME','en_US','EDUCATION','Education'),
('CONTENT#TOP_LEVEL_CATEGORY_NAME#RELAXATION#en_US','CONTENT','CONTENT,TOP_LEVEL_CATEGORY_NAME','en_US','RELAXATION','Relaxation'),
('CONTENT#TOP_LEVEL_CATEGORY_NAME#CUSTOM#en_US','CONTENT','CONTENT,TOP_LEVEL_CATEGORY_NAME','en_US','CUSTOM','Francisco'),
('CONTENT#TOP_LEVEL_CATEGORY_NAME#SWANK#en_US','CONTENT','CONTENT,TOP_LEVEL_CATEGORY_NAME','en_US','SWANK','Movies on Demand')
;

-- content service: end -- 


INSERT INTO metadata_registry (service,resourceName,resourceId,resourceContent,metadataId) VALUES 
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','AGE_BASED_THEMES','{"appId":"AGE_BASED_THEMES","appName":"Age Based Themes","appDescription":"Different Theme based on Age"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#AGE_BASED_THEMES'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','AUTO_ANSWER','{"appId":"AUTO_ANSWER","appName":"Phone Auto Answer","appDescription":"Ability to set Phone on Auto Answer","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#AUTO_ANSWER'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_MEDICATION','{"appId":"PATIENT_MEDICATION","appName":"Medication","appDescription":"View Prescribed Medicines","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_MEDICATION'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','GOALS','{"appId":"GOALS","appName":"Goal of the Day","appDescription":"Assign Goal(s) to Patient","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#GOALS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_FOOD_MENU','{"appId":"PATIENT_FOOD_MENU","appName":"Food Menu","appDescription":"View Meals"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FOOD_MENU'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_SCHEDULE','{"appId":"PATIENT_SCHEDULE","appName":"Schedule","appDescription":"Ability to view appointments","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_SCHEDULE'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_FEEDBACK','{"appId":"PATIENT_FEEDBACK","appName":"Feedback","appDescription":"Survey Feedback","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FEEDBACK'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_PROGRESS','{"appId":"PATIENT_PROGRESS","appName":"Progress","appDescription":"Progress on Assigned Activities","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PROGRESS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_REQUESTS','{"appId":"PATIENT_REQUESTS","appName":"Requests","appDescription":"Patient Requests","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_REQUESTS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_QUESTIONS','{"appId":"PATIENT_QUESTIONS","appName":"Questions","appDescription":"Patient Questions","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_QUESTIONS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_VISITORS','{"appId":"PATIENT_VISITORS","appName":"Patient Visitors","appDescription":"Visitor History","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_VISITORS'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_FAQ','{"appId":"PATIENT_FAQ","appName":"FAQ","appDescription":"Frequently Asked Questions"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FAQ'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_PRESENCE','{"appId":"PATIENT_PRESENCE","appName":"PATIENT_PRESENCE","appDescription":"Patient Presence","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PRESENCE'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','CARE_TEAM','{"appId":"CARE_TEAM","appName":"Care Team","appDescription":"Entire Care team","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#CARE_TEAM'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_FOOD_ORDER','{"appId":"PATIENT_FOOD_ORDER","appName":"Can Patient Order Food?","appDescription":"Whether parient can order food or not"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FOOD_ORDER'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','NURSE_ME_TAB','{"appId":"NURSE_ME_TAB","appName":"Nurse ME tab","appDescription":"Nurse ME tab"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#NURSE_ME_TAB'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','NURSE_WHITEBOARD','{"appId":"NURSE_WHITEBOARD","appName":"Nurse White Board","appDescription":"Nurse White Board"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#NURSE_WHITEBOARD'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','ESTIMATED_DISCHARGE_VIEW','{"appId":"ESTIMATED_DISCHARGE_VIEW","appName":"Estimated Discharge data Availability","appDescription":"This data is sent by EMR"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#ESTIMATED_DISCHARGE_VIEW'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','NURSE_CAN_REMOVE_PATIENT','{"appId":"NURSE_CAN_REMOVE_PATIENT","appName":"Can Nurse Remove Patient from Patient Connect?","appDescription":"Nurse can manually remove patient from Patient Connect."}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#NURSE_CAN_REMOVE_PATIENT'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','AUTO_ASSIGN_VIDEOS_BY_ICD','{"appId":"AUTO_ASSIGN_VIDEOS_BY_ICD","appName":"Auto Assign Videos by Diagnosis Codes","appDescription":"Auto Assign Videos by Diagnosis Codes"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#AUTO_ASSIGN_VIDEOS_BY_ICD'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_PRIVACY','{"appId":"PATIENT_PRIVACY","appName":"Patient Privacy Check","appDescription":"Patient Privacy check on some Screens","privacyCheck":"NA"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PRIVACY'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_NOTIFICATION_CENTER','{"appId":"PATIENT_NOTIFICATION_CENTER","appName":"Patient Notifications Center","appDescription":"Notification Center on Patient TV","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_NOTIFICATION_CENTER'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_PROFILE','{"appId":"PATIENT_PROFILE","appName":"Patient Profile","appDescription":"Patient Information and Assignment details","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PROFILE'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_TRACKING','{"appId":"PATIENT_TRACKING","appName":"Patient Tracking","appDescription":"Ability to track a patient","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_TRACKING'),
('APP_CUSTOMIZATION','APP_CUSTOMIZATION','PATIENT_TRACKING_STATISTICS','{"appId":"PATIENT_TRACKING_STATISTICS","appName":"Patient Tracking Statistics","appDescription":"Patient Tracking Statistics","privacyCheck":"ENABLED"}','APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_TRACKING_STATISTICS'),
('REQUESTS','REQUESTS','0-0','{"taskId":"0-0","taskDescription":"REQUESTS#taskDescription#0-0","iconURL":"REQUESTS#iconURL#0-0"}','REQUESTS#REQUESTS#0-0'),
('REQUESTS','REQUESTS','0-1','{"taskId":"0-1","taskDescription":"REQUESTS#taskDescription#0-1","iconURL":"REQUESTS#iconURL#0-1"}','REQUESTS#REQUESTS#0-1'),
('REQUESTS','REQUESTS','0-2','{"taskId":"0-2","taskDescription":"REQUESTS#taskDescription#0-2","iconURL":"REQUESTS#iconURL#0-2"}','REQUESTS#REQUESTS#0-2'),
('REQUESTS','REQUESTS','0-3','{"taskId":"0-3","taskDescription":"REQUESTS#taskDescription#0-3","iconURL":"REQUESTS#iconURL#0-3"}','REQUESTS#REQUESTS#0-3'),
('REQUESTS','REQUESTS','0-4','{"taskId":"0-4","taskDescription":"REQUESTS#taskDescription#0-4","iconURL":"REQUESTS#iconURL#0-4"}','REQUESTS#REQUESTS#0-4'),
('REQUESTS','REQUESTS','0-5','{"taskId":"0-5","taskDescription":"REQUESTS#taskDescription#0-5","iconURL":"REQUESTS#iconURL#0-5"}','REQUESTS#REQUESTS#0-5'),
('REQUESTS','REQUESTS','0-6','{"taskId":"0-6","taskDescription":"REQUESTS#taskDescription#0-6","iconURL":"REQUESTS#iconURL#0-6"}','REQUESTS#REQUESTS#0-6'),
('MESSAGES','MESSAGES','0-1','{"preDefinedMessageId":"0-1","preDefinedMessageText":"MESSAGES#preDefinedMessageText#0-1"}','MESSAGES#MESSAGES#0-1'),
('MESSAGES','MESSAGES','0-2','{"preDefinedMessageId":"0-2","preDefinedMessageText":"MESSAGES#preDefinedMessageText#0-2"}','MESSAGES#MESSAGES#0-2'),
('MESSAGES','MESSAGES','0-3','{"preDefinedMessageId":"0-3","preDefinedMessageText":"MESSAGES#preDefinedMessageText#0-3"}','MESSAGES#MESSAGES#0-3'),
('MESSAGES','MESSAGES','0-4','{"preDefinedMessageId":"0-4","preDefinedMessageText":"MESSAGES#preDefinedMessageText#0-4"}','MESSAGES#MESSAGES#0-4'),
('MESSAGES','MESSAGES','0-5','{"preDefinedMessageId":"0-5","preDefinedMessageText":"MESSAGES#preDefinedMessageText#0-5"}','MESSAGES#MESSAGES#0-5'),
('MESSAGES','MESSAGES','0-6','{"preDefinedMessageId":"0-6","preDefinedMessageText":"MESSAGES#preDefinedMessageText#0-6"}','MESSAGES#MESSAGES#0-6'),
('FAQS','FAQS','0-1','{"questionId":"0-1","question":"FAQS#question#0-1","answer":"FAQS#answer#0-1"}','FAQS#FAQS#0-1'),
('FAQS','FAQS','0-2','{"questionId":"0-2","question":"FAQS#question#0-2","answer":"FAQS#answer#0-2"}','FAQS#FAQS#0-2'),
('FAQS','FAQS','0-3','{"questionId":"0-3","question":"FAQS#question#0-3","answer":"FAQS#answer#0-3"}','FAQS#FAQS#0-3'),
('FAQS','FAQS','0-4','{"questionId":"0-4","question":"FAQS#question#0-4","answer":"FAQS#answer#0-4"}','FAQS#FAQS#0-4')
;

INSERT INTO metadata_registry(service,resourceName,resourceId,resourceContent,metadataId) VALUES 
('SCREENSHARE','CONFIGURATION','SCREEN-CONFIGURATION12929','{"resolution":{"width":"1920","height":"1080"},"frameRate":"30","selectConfig":true}','SCREENSHARE#CONFIGURATION#SCREEN-CONFIGURATION12929')
;
INSERT INTO registry_configuration(metadataId,configId,configValue)  VALUES ('SCREENSHARE#CONFIGURATION#SCREEN-CONFIGURATION12929','HEIGHT','1080');

INSERT INTO registry_configuration(metadataId,configId,configValue)  VALUES ('SCREENSHARE#CONFIGURATION#SCREEN-CONFIGURATION12929','WIDTH','1920');

INSERT INTO registry_configuration(metadataId,configId,configValue)  VALUES ('SCREENSHARE#CONFIGURATION#SCREEN-CONFIGURATION12929','FRAMERATE','30');


INSERT INTO registry_configuration (metadataId,configId,configValue) VALUES 
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_MEDICATION','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#AGE_BASED_THEMES','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#GOALS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FOOD_MENU','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_SCHEDULE','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FEEDBACK','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PROGRESS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_REQUESTS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_QUESTIONS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_VISITORS','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FAQ','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PRESENCE','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#CARE_TEAM','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_FOOD_ORDER','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#NURSE_ME_TAB','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#NURSE_WHITEBOARD','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#ESTIMATED_DISCHARGE_VIEW','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PRIVACY','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_NOTIFICATION_CENTER','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_PROFILE','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#AUTO_ANSWER','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_TRACKING','LOCATION_TYPE','PATIENT_ROOM'),
('APP_CUSTOMIZATION#APP_CUSTOMIZATION#PATIENT_TRACKING_STATISTICS','LOCATION_TYPE','PATIENT_ROOM'),
('REQUESTS#REQUESTS#0-0','AGE','G'),
('REQUESTS#REQUESTS#0-0','GENDER','all'),
('REQUESTS#REQUESTS#0-0','UNITID','0'),
('REQUESTS#REQUESTS#0-1','AGE','G'),
('REQUESTS#REQUESTS#0-1','GENDER','all'),
('REQUESTS#REQUESTS#0-1','UNITID','0'),
('REQUESTS#REQUESTS#0-2','AGE','G'),
('REQUESTS#REQUESTS#0-2','GENDER','all'),
('REQUESTS#REQUESTS#0-2','UNITID','0'),
('REQUESTS#REQUESTS#0-3','AGE','G'),
('REQUESTS#REQUESTS#0-3','GENDER','all'),
('REQUESTS#REQUESTS#0-3','UNITID','0'),
('REQUESTS#REQUESTS#0-4','AGE','G'),
('REQUESTS#REQUESTS#0-4','GENDER','all'),
('REQUESTS#REQUESTS#0-4','UNITID','0'),
('REQUESTS#REQUESTS#0-5','AGE','G'),
('REQUESTS#REQUESTS#0-5','GENDER','all'),
('REQUESTS#REQUESTS#0-5','UNITID','0'),
('REQUESTS#REQUESTS#0-6','AGE','G'),
('REQUESTS#REQUESTS#0-6','GENDER','all'),
('REQUESTS#REQUESTS#0-6','UNITID','0'),
('MESSAGES#MESSAGES#0-1','AGE','G'),
('MESSAGES#MESSAGES#0-1','GENDER','all'),
('MESSAGES#MESSAGES#0-1','UNITID','0'),
('MESSAGES#MESSAGES#0-2','AGE','G'),
('MESSAGES#MESSAGES#0-2','GENDER','all'),
('MESSAGES#MESSAGES#0-2','UNITID','0'),
('MESSAGES#MESSAGES#0-3','AGE','G'),
('MESSAGES#MESSAGES#0-3','GENDER','all'),
('MESSAGES#MESSAGES#0-3','UNITID','0'),
('MESSAGES#MESSAGES#0-4','AGE','G'),
('MESSAGES#MESSAGES#0-4','GENDER','all'),
('MESSAGES#MESSAGES#0-4','UNITID','0'),
('MESSAGES#MESSAGES#0-5','AGE','G'),
('MESSAGES#MESSAGES#0-5','GENDER','all'),
('MESSAGES#MESSAGES#0-5','UNITID','0'),
('MESSAGES#MESSAGES#0-6','AGE','G'),
('MESSAGES#MESSAGES#0-6','GENDER','all'),
('MESSAGES#MESSAGES#0-6','UNITID','0'),
('FAQS#FAQS#0-1','AGE','G'),
('FAQS#FAQS#0-1','GENDER','all'),
('FAQS#FAQS#0-1','UNITID','0'),
('FAQS#FAQS#0-2','AGE','G'),
('FAQS#FAQS#0-2','GENDER','all'),
('FAQS#FAQS#0-2','UNITID','0'),
('FAQS#FAQS#0-3','AGE','G'),
('FAQS#FAQS#0-3','GENDER','all'),
('FAQS#FAQS#0-3','UNITID','0'),
('FAQS#FAQS#0-4','AGE','G'),
('FAQS#FAQS#0-4','GENDER','all'),
('FAQS#FAQS#0-4','UNITID','0')
;

INSERT INTO resourcebundle VALUES 
('FAQS#answer#0-1#en_US','FAQS','answer','en_US','0-1','The gift shop will remain open between 9 AM to 6 PM on all days of the week'),
('FAQS#answer#0-1#es_US','FAQS','answer','es_US','0-1','"La tienda de regalos permanecerá abierta entre las 9 am a 6 pm los todos los días de la semana"'),
('FAQS#answer#0-2#en_US','FAQS','answer','en_US','0-2','Meals will be served based on the your medical condition and is selected by our nutritionist after consulting with your doctor. The are served during regular meal times of breakfast, lunch and dinner. '),
('FAQS#answer#0-2#es_US','FAQS','answer','es_US','0-2','"Las comidas se servirï¿½n en base a la que su condiciï¿½n mï¿½dica y se selecciona por nuestra nutricionista a instancias de su mï¿½dico. El durante las comidas regulares de desayuno, el almuerzo y la cena se sirven."'),
('FAQS#answer#0-3#en_US','FAQS','answer','en_US','0-3','You can order meals up to 2 hours prior to the time of the meal. Please use the food ordering section of your TV set to order extra meals for your guests.'),
('FAQS#answer#0-3#es_US','FAQS','answer','es_US','0-3','"Puede pedir comidas hasta 2 horas antes de la hora de la comida. Por favor, utilice la sección de pedidos de alimentos de su televisor para ordenar comidas extras para sus invitados."'),
('FAQS#answer#0-4#en_US','FAQS','answer','en_US','0-4','If you would like to see a chaplain, or attend a chapel service, please ask a member of ward staff help you contact them. Chaplaincy provides a 24 hour on-call service within the hospitals for urgent situations. Please ask staff to contact the duty chaplain via switchboard.'),
('FAQS#answer#0-4#es_US','FAQS','answer','es_US','0-4','"Si a usted le gustaría ver a un capellán, o asistir a un servicio de capilla, por favor pregunte a un miembro del personal de la sala ayuda se comunique con ellos. Capellanía ofrece un servicio de 24 horas de guardia en los hospitales para situaciones urgentes. Por favor, pregunte al personal para ponerse en contacto con el capellán deber a través de centralita."'),
('FAQS#question#0-1#en_US','FAQS','question','en_US','0-1','What are the hours for the gift shop?'),
('FAQS#question#0-1#es_US','FAQS','question','es_US','0-1','¿Cuáles son las horas para la tienda de regalos?'),
('FAQS#question#0-2#en_US','FAQS','question','en_US','0-2','When are meals served?'),
('FAQS#question#0-2#es_US','FAQS','question','es_US','0-2','Cuando las comidas se sirven?'),
('FAQS#question#0-3#en_US','FAQS','question','en_US','0-3','When can I order meals?'),
('FAQS#question#0-3#es_US','FAQS','question','es_US','0-3','¿Cuándo puedo pedir comidas?'),
('FAQS#question#0-4#en_US','FAQS','question','en_US','0-4','How do I ask for a Chaplain?'),
('FAQS#question#0-4#es_US','FAQS','question','es_US','0-4','¿Cómo puedo pedir un capellán?'),
('MESSAGES#preDefinedMessageText#0-1#es_US','MESSAGES','preDefinedMessageText','es_US','0-1','¿Cuánto tiempo estaré en el hospital?'),
('MESSAGES#preDefinedMessageText#0-1#en_US','MESSAGES','preDefinedMessageText','en_US','0-1','How long will I be in the hospital?'),
('MESSAGES#preDefinedMessageText#0-2#es_US','MESSAGES','preDefinedMessageText','es_US','0-2','Cuándo visitar a mi médico?'),
('MESSAGES#preDefinedMessageText#0-2#en_US','MESSAGES','preDefinedMessageText','en_US','0-2','When will my doctor visit me?'),
('MESSAGES#preDefinedMessageText#0-3#es_US','MESSAGES','preDefinedMessageText','es_US','0-3','¿Cuándo voy a tener pruebas?'),
('MESSAGES#preDefinedMessageText#0-3#en_US','MESSAGES','preDefinedMessageText','en_US','0-3','When will I have my tests performed?'),
('MESSAGES#preDefinedMessageText#0-4#es_US','MESSAGES','preDefinedMessageText','es_US','0-4','¿A qué hora se descarga?'),
('MESSAGES#preDefinedMessageText#0-4#en_US','MESSAGES','preDefinedMessageText','en_US','0-4','When will I be discharged?'),
('MESSAGES#preDefinedMessageText#0-5#es_US','MESSAGES','preDefinedMessageText','es_US','0-5','Cuando las comidas se sirven?'),
('MESSAGES#preDefinedMessageText#0-5#en_US','MESSAGES','preDefinedMessageText','en_US','0-5','When are meals served?'),
('MESSAGES#preDefinedMessageText#0-6#es_US','MESSAGES','preDefinedMessageText','es_US','0-6','Cuando se abra la tienda de regalos?'),
('MESSAGES#preDefinedMessageText#0-6#en_US','MESSAGES','preDefinedMessageText','en_US','0-6','When is the gift shop open?'),

('REQUESTS#iconURL#0-0#en_US','REQUESTS','iconURL','en_US','0-0','Default'),
('REQUESTS#iconURL#0-0#es_US','REQUESTS','iconURL','es_US','0-0','Default'),
('REQUESTS#iconURL#0-1#en_US','REQUESTS','iconURL','en_US','0-1','RoomTooHot'),
('REQUESTS#iconURL#0-1#es_US','REQUESTS','iconURL','es_US','0-1','RoomTooHot'),
('REQUESTS#iconURL#0-2#en_US','REQUESTS','iconURL','en_US','0-2','RoomTooCold'),
('REQUESTS#iconURL#0-2#es_US','REQUESTS','iconURL','es_US','0-2','RoomTooCold'),
('REQUESTS#iconURL#0-3#en_US','REQUESTS','iconURL','en_US','0-3','BringMeIcePack'),
('REQUESTS#iconURL#0-3#es_US','REQUESTS','iconURL','es_US','0-3','BringMeIcePack'),
('REQUESTS#iconURL#0-4#en_US','REQUESTS','iconURL','en_US','0-4','BedComfortLevel'),
('REQUESTS#iconURL#0-4#es_US','REQUESTS','iconURL','es_US','0-4','BedComfortLevel'),
('REQUESTS#iconURL#0-5#en_US','REQUESTS','iconURL','en_US','0-5','Hearing&Headset'),
('REQUESTS#iconURL#0-5#es_US','REQUESTS','iconURL','es_US','0-5','Hearing&Headset'),
('REQUESTS#iconURL#0-6#en_US','REQUESTS','iconURL','en_US','0-6','Default'),
('REQUESTS#iconURL#0-6#es_US','REQUESTS','iconURL','es_US','0-6','Default'),

('REQUESTS#taskDescription#0-0#en_US','REQUESTS','taskDescription','en_US','0-0','Generate Pin'),
('REQUESTS#taskDescription#0-0#es_US','REQUESTS','taskDescription','es_US','0-0','generar Pin'),
('REQUESTS#taskDescription#0-1#en_US','REQUESTS','taskDescription','en_US','0-1','My Room is too hot'),
('REQUESTS#taskDescription#0-1#es_US','REQUESTS','taskDescription','es_US','0-1','Mi habitación está demasiado caliente'),
('REQUESTS#taskDescription#0-2#en_US','REQUESTS','taskDescription','en_US','0-2','My Room is too cold'),
('REQUESTS#taskDescription#0-2#es_US','REQUESTS','taskDescription','es_US','0-2','MyHabitación es demasiado fría'),
('REQUESTS#taskDescription#0-3#en_US','REQUESTS','taskDescription','en_US','0-3','Bring me an ice pack'),
('REQUESTS#taskDescription#0-3#es_US','REQUESTS','taskDescription','es_US','0-3','Tráeme una bolsa de hielo'),
('REQUESTS#taskDescription#0-4#en_US','REQUESTS','taskDescription','en_US','0-4','Bed comfort level'),
('REQUESTS#taskDescription#0-4#es_US','REQUESTS','taskDescription','es_US','0-4','Cama nivel de comodidad'),
('REQUESTS#taskDescription#0-5#en_US','REQUESTS','taskDescription','en_US','0-5','Hearing and headset'),
('REQUESTS#taskDescription#0-5#es_US','REQUESTS','taskDescription','es_US','0-5','Audición y auriculares'),
('REQUESTS#taskDescription#0-6#en_US','REQUESTS','taskDescription','en_US','0-6','I would like to see a Chaplain'),
('REQUESTS#taskDescription#0-6#es_US','REQUESTS','taskDescription','es_US','0-6','Me gustaría ver a un capellán'),
('survey#option#option1#en_US','survey','option','en_US','option1','Strongly Disagree'),
('survey#option#option1#es_US','survey','option','es_US','option1','Totalmente en desacuerdo'),
('survey#option#option10#en_US','survey','option','en_US','option10','Hurts Little More'),
('survey#option#option10#es_US','survey','option','es_US','option10','duele poco mÃ¡s'),
('survey#option#option11#en_US','survey','option','en_US','option11','Hurts Even More'),
('survey#option#option11#es_US','survey','option','es_US','option11','duele aÃºn mÃ¡s'),
('survey#option#option12#en_US','survey','option','en_US','option12','Hurts Whole Lot'),
('survey#option#option12#es_US','survey','option','es_US','option12','duele mucho'),
('survey#option#option13#en_US','survey','option','en_US','option13','Hurts Most'),
('survey#option#option13#es_US','survey','option','es_US','option13','duele mÃ¡s'),
('survey#option#option14#en_US','survey','option','en_US','option14','Dont Know'),
('survey#option#option14#es_US','survey','option','es_US','option14','No lo sÃ©'),
('survey#option#option2#en_US','survey','option','en_US','option2','Disagree'),
('survey#option#option2#es_US','survey','option','es_US','option2','no estar de acuerdo'),
('survey#option#option3#en_US','survey','option','en_US','option3','Neutral'),
('survey#option#option3#es_US','survey','option','es_US','option3','Neutral'),
('survey#option#option4#en_US','survey','option','en_US','option4','Agree'),
('survey#option#option4#es_US','survey','option','es_US','option4','acordar'),
('survey#option#option5#en_US','survey','option','en_US','option5','Strongly Agree'),
('survey#option#option5#es_US','survey','option','es_US','option5','Muy de acuerdo'),
('survey#option#option6#en_US','survey','option','en_US','option6','Yes'),
('survey#option#option6#es_US','survey','option','es_US','option6','si'),
('survey#option#option7#en_US','survey','option','en_US','option7','No'),
('survey#option#option7#es_US','survey','option','es_US','option7','no'),
('survey#option#option8#en_US','survey','option','en_US','option8','No Hurt'),
('survey#option#option8#es_US','survey','option','es_US','option8','Sin dolor'),
('survey#option#option9#en_US','survey','option','en_US','option9','Hurts Little Bit'),
('survey#option#option9#es_US','survey','option','es_US','option9','Hurts poco'),
('survey#option#painscoreOption1#en_US','survey','option','en_US','painscoreOption1','No Pain'),
('survey#option#painscoreOption1#es_US','survey','option','es_US','painscoreOption1','No hay dolor'),
('survey#option#painscoreOption10#en_US','survey','option','en_US','painscoreOption10','Excruciating Pain'),
('survey#option#painscoreOption10#es_US','survey','option','es_US','painscoreOption10','insoportable dolor'),
('survey#option#painscoreOption11#en_US','survey','option','en_US','painscoreOption11','Unspeakable Pain'),
('survey#option#painscoreOption11#es_US','survey','option','es_US','painscoreOption11','dolor indecible'),
('survey#option#painscoreOption2#en_US','survey','option','en_US','painscoreOption2','Mild Pain'),
('survey#option#painscoreOption2#es_US','survey','option','es_US','painscoreOption2','dolor leve'),
('survey#option#painscoreOption3#en_US','survey','option','en_US','painscoreOption3','Minor Pain'),
('survey#option#painscoreOption3#es_US','survey','option','es_US','painscoreOption3','dolores menores'),
('survey#option#painscoreOption4#en_US','survey','option','en_US','painscoreOption4','Noticeable Pain'),
('survey#option#painscoreOption4#es_US','survey','option','es_US','painscoreOption4','notable dolor'),
('survey#option#painscoreOption5#en_US','survey','option','en_US','painscoreOption5','Moderate Pain'),
('survey#option#painscoreOption5#es_US','survey','option','es_US','painscoreOption5','El dolor moderado'),
('survey#option#painscoreOption6#en_US','survey','option','en_US','painscoreOption6','Moderately Strong Pain'),
('survey#option#painscoreOption6#es_US','survey','option','es_US','painscoreOption6','Dolor moderadamente fuerte'),
('survey#option#painscoreOption7#en_US','survey','option','en_US','painscoreOption7','Strong Pain'),
('survey#option#painscoreOption7#es_US','survey','option','es_US','painscoreOption7','dolor fuerte'),
('survey#option#painscoreOption8#en_US','survey','option','en_US','painscoreOption8','Severe Pain'),
('survey#option#painscoreOption8#es_US','survey','option','es_US','painscoreOption8','dolor severo'),
('survey#option#painscoreOption9#en_US','survey','option','en_US','painscoreOption9','Intense Pain'),
('survey#option#painscoreOption9#es_US','survey','option','es_US','painscoreOption9','dolor intenso'),
('survey#optiongroup#mainOptionGroup#en_US','survey','optiongroup','en_US','mainOptionGroup','mainOptionGroup'),
('survey#optiongroup#mainOptionGroup#es_US','survey','optiongroup','es_US','mainOptionGroup','mainOptionGroup'),
('survey#optiongroup#painscoreRating1#en_US','survey','optiongroup','en_US','painscoreRating1','painScoreRating'),
('survey#optiongroup#painscoreRating1#es_US','survey','optiongroup','es_US','painscoreRating1','painScoreRating'),
('survey#optiongroup#painscoreRating2#en_US','survey','optiongroup','en_US','painscoreRating2','painScoreRating'),
('survey#optiongroup#painscoreRating2#es_US','survey','optiongroup','es_US','painscoreRating2','painScoreRating'),
('survey#optiongroup#yesNo1#en_US','survey','optiongroup','en_US','yesNo1','yesNo'),
('survey#optiongroup#yesNo1#es_US','survey','optiongroup','es_US','yesNo1','yesNo'),
('survey#question#dischargeQuestId3#en_US','survey','question','en_US','dischargeQuestId3','Have you been oriented to this unit?'),
('survey#question#dischargeQuestId3#es_US','survey','question','es_US','dischargeQuestId3','Â¿Se le ha orientado a esta unidad?'),
('survey#question#dischargeQuestId4#en_US','survey','question','en_US','dischargeQuestId4','Do you understand why are you in the hospital?'),
('survey#question#dischargeQuestId4#es_US','survey','question','es_US','dischargeQuestId4','Â¿Entiendes por quÃ© estÃ¡s en el hospital?'),
('survey#question#dischargeQuestId5#en_US','survey','question','en_US','dischargeQuestId5','Have you watched the videos assigned to you?'),
('survey#question#dischargeQuestId5#es_US','survey','question','es_US','dischargeQuestId5','Â¿Has visto los videos asignadas a usted?'),
('survey#question#dischargeQuestId6#en_US','survey','question','en_US','dischargeQuestId6','Do you understand the content of these videos?'),
('survey#question#dischargeQuestId6#es_US','survey','question','es_US','dischargeQuestId6','Â¿Entiende el contenido de estos videos?'),
('survey#question#question1#en_US','survey','question','en_US','question1','My room and bathroom are clean'),
('survey#question#question1#es_US','survey','question','es_US','question1','Mi habitaciÃ³n y el baÃ±o estaban limpios'),
('survey#question#question2#en_US','survey','question','en_US','question2','Attention is paid to reduce noise so I can rest'),
('survey#question#question2#es_US','survey','question','es_US','question2','Se presta atenciÃ³n a reducir el ruido para que pueda descansar'),
('survey#question#question3#en_US','survey','question','en_US','question3','Staff are courteous and keep me informed'),('survey#question#question3#es_US','survey','question','es_US','question3','El personal es cortÃ©s y me mantienen informado'),('survey#question#question4#en_US','survey','question','en_US','question4','I feel prepared to go home'),('survey#question#question4#es_US','survey','question','es_US','question4','Me siento preparado para volver a casa'),('survey#question#question5#en_US','survey','question','en_US','question5','How are you feeling now ?'),
('survey#question#question5#es_US','survey','question','es_US','question5','Â¿CÃ³mo te sientes ahora?'),
('survey#question#question6#en_US','survey','question','en_US','question6','Are you feeling good ?'),
('survey#question#question6#es_US','survey','question','es_US','question6','Â¿Te sientes bien?'),
('survey#survey#cleanliness1#en_US','survey','survey','en_US','cleanliness1','Cleanliness'),
('survey#survey#cleanliness1#es_US','survey','survey','es_US','cleanliness1','limpieza'),
('survey#survey#discharge1#en_US','survey','survey','en_US','discharge1','Discharge'),
('survey#survey#discharge1#es_US','survey','survey','es_US','discharge1','descarga'),
('survey#survey#dischargeCheckList1#en_US','survey','survey','en_US','dischargeCheckList1','Discharge CheckList'),
('survey#survey#dischargeCheckList1#es_US','survey','survey','es_US','dischargeCheckList1','Lista de verificaciÃ³n de Descarga'),
('survey#survey#insurance1#en_US','survey','survey','en_US','insurance1','Insurance'),
('survey#survey#insurance1#es_US','survey','survey','es_US','insurance1','Prueba de Descarga'),
('survey#survey#painscore1#en_US','survey','survey','en_US','painscore1','Pain Score'),
('survey#survey#painscore1#es_US','survey','survey','es_US','painscore1','Dolor PuntuaciÃ³n'),
('survey#survey#safety1#en_US','survey','survey','en_US','safety1','Safety'),
('survey#survey#safety1#es_US','survey','survey','es_US','safety1','seguridad'),
('survey#survey#satisfaction1#en_US','survey','survey','en_US','satisfaction1','Satisfaction'),
('survey#survey#satisfaction1#es_US','survey','survey','es_US','satisfaction1','satisfacciÃ³n'),
('survey#survey#wellness1#en_US','survey','survey','en_US','wellness1','Wellness'),
('survey#survey#wellness1#es_US','survey','survey','es_US','wellness1','bienestar')
;




 
 
-- Properties inserts in config_registry for common services
--
-- amqp-- 
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.connectionfactory.host','rabbitmqhost');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.connectionfactory.port','5672');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.connectionfactory.username','cisco');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.connectionfactory.password','cisco');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.connectionfactory.heartbeat','10');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.hl7.exchange','hl7');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.hl7.routing-key','pme.hl7encoded');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.pme.exchange','pme');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.pme.patient.routingkey','pme.patient');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.pme.endpoint.notification.routingkey','pme.endpoint.notification');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.pme.patient.pathway.routingkey','pme.patient.pathway');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','amqp.pme.endpoint.notification.exchange', 'pme.endpoint.notification');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','email.notification.proxy.url','http://esbhost:8283/services/emailProxy');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','hl7.base.esb.url','http://esbhost:8283/');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','hl7.esb.url','services/hl7-outbound');
insert into config_registry(service,module,configKey,configValue) values('amqp','MES','hl7.esb.enabled','false');

--
-- directory-- 

insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.user.query.url.base','http://esbhost:8283/services/mgmt/Users');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.cache.validity.secs','18000');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.staff.role.pattern.doctor','staff physician,staff adult physician,poi-physician,poi-volunteer physican,poi-uced physican');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.staff.role.pattern.nurse','registered nurse,reg nurse,nurse home visitor,nurse anesthetist,nurse midwife,vocational nurse,nurse practitioner,continence nurse');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.staff.photo.source.external','false');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.staff.photo.base.url','http://franciscodev.july.mx');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','directory.staff.photo.default.url','http://franciscodev.july.mx/s/store/-1/cisco/pme/tv/images/theme/adult/normal/doc-default-img.png');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.titlePrefix.source','EMR');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.name.source','DIRECTORY,EMR');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.nameSuffix.source','EMR');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.credentialSuffix.source','EMR');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.jobTitle.source','DIRECTORY,PHOTO_MAP');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.teamName.source','STAFF_ASSIGN,PHOTO_MAP');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.photoUrl.source','DIRECTORY,PHOTO_MAP');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.roleDescription.source','EMR');
insert into config_registry(service,module,configKey,configValue) values('directory','MES','staff.profile.phoneNumber.source','DIRECTORY');
--
-- drugeducation-- 

insert into config_registry(service,module,configKey,configValue) values('drugeducation','MES','drug.education.locale.default','en_US');
insert into config_registry(service,module,configKey,configValue) values('drugeducation','MES','drug.education.default.list.sections','USED_FOR,TELL_DR_BEFORE_TAKING,BEST_WAY_TO_TAKE');
--

-- foodOrdering-- 

insert into config_registry(service,module,configKey,configValue) values('foodOrdering','MES','module.foodOrdering','FoodMenu');
insert into config_registry(service,module,configKey,configValue) values('foodOrdering','MES','defaultLocale.foodOrdering','en_US');
--
-- hl7Handler-- 

insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.port','5672');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.message_property','HL7_MESSAGE');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.HL7MessageTemplatesLocation','conf:/templates/HL7Templates');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.NOTIFICATION_CODES','ADT_A01,ADT_A02,ADT_A03,ADT_A08,ADT_A18,SIU_S12,SIU_S13,SIU_S14,SIU_S15,SIU_S26,RDE_O01,RDE_O11,PPR_PC1,PPR_PC2,PPR_PC3');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.rabbitmq.username','cisco');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.rabbitmq.host','rabbitmqhost');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.HL7_REQUEST_DATETIME_FORMAT','MMYYDD');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.rabbitmq.exchangeName','pme');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.rabbitmq.password','cisco');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.rabbitmq.routingKey','pme.patient');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.msg.size.limit','0');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.msgs.per.second.limit','0');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.patientClassExtract','//PV1/PV1.2');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.patientClass.admit.include','I,B,D');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.messageTypeExtract_1','//MSH/MSH.9/MSG.1');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.triggerEventExtract_1','//MSH/MSH.9/MSG.2');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.messageTypeExtract_2','//MSH/MSH.9/CM_MSG.1');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.handler.inbound.triggerEventExtract_2','//MSH/MSH.9/CM_MSG.2');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.patient.schedule.ts.format','yyyyMMddHHmm');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.timestamp.implicit.timezone','America/Chicago');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.patient.schedule.ts.past.margin.minutes','1');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.user.id.type.filter','NETID');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.patient.type.map.PRISONER','12');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.staff.assignment.max.age.days','7');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.staff.role.description.PRIMARY_NURSE','Registered Nurse');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.staff.role.pattern.doctor','consulting physician,attending');
insert into config_registry(service,module,configKey,configValue) values('hl7Handler','MES','hl7.staff.role.pattern.nurse','registered nurse,practitioner,1st call provider,2nd call provider');
--
--
-- ordering-- 

insert into config_registry(service,module,configKey,configValue) values('ordering','MES','nutrients_to_be_shown','Calories|Fat|Cholestrol|Sodium|Carbs|Protein|');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','host','207.8.55.20');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','port','8090');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','valid_meals','');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','number_of_meals','15');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','can_call','false');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','release_lock_wait_time','600000');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','release.lock.cron.expn','0 0/10 * * * ?');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','426','FoodOrdering.MrnNotFoundException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','427','FoodOrdering.RoomNotFoundForTheMrnException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','428','FoodOrdering.MealInfoNotFoundException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','429','FoodOrdering.MenuAssignmentNotFoundException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','430','FoodOrdering.MenuInfoCannotRetrieveNow,AnotherUserEditingPatronRecordException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','431','FoodOrdering.MenuInfoNotFoundForDate/MenuInputException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','defaultErrorKey','FoodOrdering.ResponseException');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','adapter.defn.id','3000');
insert into config_registry(service,module,configKey,configValue) values('ordering','MES','uncategorizedItem','(Unassigned)');
insert into config_registry(service,module,configKey,configValue) values('ordering', 'MES', 'computrition_host', 'localhost');
insert into config_registry(service,module,configKey,configValue) values('ordering', 'MES', 'computrition_port', '8080');

--
-- pathwayService-- 

insert into config_registry(service,module,configKey,configValue) values('pathwayService','MES','pathway.base.url','http://pathwayhost:8090/pme-pathway');
insert into config_registry(service,module,configKey,configValue) values('pathwayService','MES','pathway.taskApi.url','/api/tasks');
insert into config_registry(service,module,configKey,configValue) values('pathwayService','MES','nursecache.scheduler.crontab.expression','0 0/5 * * * ?');
insert into config_registry(service,module,configKey,configValue) values('pathwayService','MES','patient.batchSize','50');
--
-- patientservice-- 

insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.admitstatus.recent.time','4');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.admitstatus.soontobedischarged.time','4');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.usertasks.age.categories','G,R');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.dob.format','YYYYMMDD');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','device.image.url','/s/store/-1/cisco/pme/tv/images/theme/adult/normal/device_common_icon.png');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.emradmittedtime.format','yyyyMMddHHmm');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.emrestimateddischargetime.format','yyyyMMdd');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.name.default.display.format','FIRST_NAME_ONLY');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.name.display.allowed.format.choices','FIRST_NAME_ONLY,LAST_NAME_ONLY,FIRST_AND_LAST,FIRST_AND_LAST_INITIAL,FIRST_INITIAL_AND_LAST,DO_NOT_DISPLAY');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','patient.map.emrstaff.to.ad','true');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','emr.msg.drop.out.of.sequence','false');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','staff.name.display.format.choices.doctor','FIRST_AND_LAST,FIRST_AND_LAST_INITIAL,FIRST_INITIAL_AND_LAST,FIRST_LAST_DEGREE,FIRST_INITIAL_LAST_DEGREE,FIRST_LAST_INITIAL_DEGREE');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','staff.name.display.format.choices.nurse','FIRST_AND_LAST,FIRST_AND_LAST_INITIAL,FIRST_INITIAL_AND_LAST,FIRST_LAST_DEGREE,FIRST_INITIAL_LAST_DEGREE,FIRST_LAST_INITIAL_DEGREE');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','staff.name.display.format.choices.other','FIRST_AND_LAST,FIRST_AND_LAST_INITIAL,FIRST_INITIAL_AND_LAST,FIRST_LAST_DEGREE,FIRST_INITIAL_LAST_DEGREE,FIRST_LAST_INITIAL_DEGREE');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','staff.name.display.format.doctor','FIRST_AND_LAST_INITIAL');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','staff.name.display.format.nurse','FIRST_AND_LAST_INITIAL');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','staff.name.display.format.other','FIRST_AND_LAST_INITIAL');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','show.notifications','true');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','phone.ringer','false');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','video.call','true');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','presence.do.not.disturb','false');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','show.footer','true');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','video.call.auto.answer.expiry.duration','10');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.nurse.assignment.enabled','true');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.assign.nurseId','nurse1');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.careteam.nurses','nurse1,nurse2,nurse3');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','max.videos.assign','10');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','privacy.data.unlock.duration','60');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','privacy.data.lock','false');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','notification.events.toFilter','patientInit,patientLogout,patientUpdate,surveyProgress,SurveyResult,removeSurveyList,appointmentReminder,goalStatus,addPatientMessage,videoFeedbackStatus,addTask,taskFeedbackStatus,taskDeleted,showMessageFeedback,messageStatus,messageDeleted,addPatientMessage,Escalation,videoStatus,removeVideoList,rtlsStaffExit,locationUpdate,videoLibraryRefresh,playVideoRequested,taskCreate,taskComplete,screenCastStart,screenCastEnd,operatorMessage,removeOperatorMessage,presenceStatusChanged,taskUpdate,phonepreferencesupdate');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.nurse.roles','RegisteredNurse,MidWife,NursePractitioner');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.nurse.teamids',' ,2,3');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.nurse.teamnames',' , , NursePractitioners');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.nurse.jobtitles','Reg Nurse II,MidWife Prime, ');
insert into config_registry(service,module,configKey,configValue) values('patientservice','MES','demo.nurse.roledescriptions','Registered Nurse, 1st call Provider, Nurse Practitioners');

--
-- presence-application-- 

insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.host','sccdemo-cups.cisco.com');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.port','8083');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.protocol','https');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.username','cupsadmin');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.password','Cisco@123');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.server.domain','cisco.com');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.cert.location','/etc/pki/tls/certs/cups.keystore');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','cups.cert.password','Cisco_123');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','presence.enabled','true');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','user.expiration.time','3600');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','pme.staffcount.baseurl','http://meshost:8080');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','pme.staffcount.url','/mes/locations/%s/staffCount');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','presence.rtls.enabled','true');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','presence.sanitize.chars','true');
insert into config_registry(service,module,configKey,configValue) values('presence-application','MES','presence.sanitize.charset','{"","^","-"}');
--
-- presence-cache-- 
-- > > removed as those props were conflicting with iep-common-cache

--
-- presence-rabbitmq-- 

insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.server.hostname','rabbitmqhost');
insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.server.port','5672');
insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.server.username','cisco');
insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.server.password','cisco');
insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.server.queuename','pme.rtls.core');
insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.server.routingkey','pme.rtls');
insert into config_registry(service,module,configKey,configValue) values('presence-rabbitmq','MES','queue.exchange.name','pme');
--
-- raulandborg-- 

insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.ws.url','http://38.102.62.63:26001/SAIWebService');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.allStaff.soapaction','urn:GetOwnedStaffLocationLevelTeamAssignments');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.staffByLocation.soapaction','urn:GetThisLocationsAssignments');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.subscribe.soapaction','urn:Subscribe');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.unsubscribe.soapaction','urn:Unsubscribe');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.staffEventAck.soapaction','urn:AssignStaffLocationLevelTeamResponse');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','Source','PARKLAND');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','MsgTag','511');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','AppVersion','Rauland Assign Share 01.00');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','ProtocolVersion','1.20');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.subscription.port','http://173.36.245.229/services/SAIWebService');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','vendor_subscribe_source','vendor_cisco');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','SupportsPendingAssignments','false;');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.api.url','http://38.102.62.63:26001/SAIWebService');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','itemStart','1');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','itemMax','500');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','event.ack.api.url','http://38.102.62.63:26001/SAIWebService');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.service.available','false');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','cache.refresh.cron.expn','0 0/59 * * * ?');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.connection.timeout','10000');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.read.timeout','20000');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.import.data.location','c:\\opt\\cisco\\iep\\mes\\assets\\');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.location.data.filename','rblocations.csv');
insert into config_registry(service,module,configKey,configValue) values('raulandborg','MES','raulandborg.staff.data.filename','rbstaffs.csv');
--
-- rtls-- 

insert into config_registry(service,module,configKey,configValue) values('rtls','MES','rtls.map.staffid','false');
insert into config_registry(service,module,configKey,configValue) values('rtls','MES','rtls.duplicate.visit.duration.threshold.secs','600');
insert into config_registry(service,module,configKey,configValue) values('rtls','MES','rtls.patient.track.max.history.min', '0');
insert into config_registry(service,module,configKey,configValue) values('rtls', 'MES', 'tagnos.host', '172.21.133.224');
insert into config_registry(service,module,configKey,configValue) values('rtls', 'MES', 'tagnos.port', '8080');
insert into config_registry(service,module,configKey,configValue) values('rtls', 'MES', 'tagnos.username', 'CiscoIntegration');
insert into config_registry(service,module,configKey,configValue) values('rtls', 'MES', 'tagnos.password', '12345678');
insert into config_registry(service,module,configKey,configValue) values('rtls', 'MES', 'tagnos.connection.timeout', '1000');

--
-- schedule-- 

insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.toplevelgroup','morning,afternoon,evening,night');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.subgroups','early,mid,late');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.early.label','Early Morning');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.early.starttime','6:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.early.endtime','8:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.mid.label','Mid Morning');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.mid.starttime','8:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.mid.endtime','10:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.late.label','Late Morning');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.late.starttime','10:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.morning.late.endtime','12:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.subgroups','early,mid,late');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.early.label','Early afternoon');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.early.starttime','12:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.early.endtime','14:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.mid.label','Mid afternoon');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.mid.starttime','14:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.mid.endtime','16:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.late.label','Late afternoon');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.late.starttime','16:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.afternoon.late.endtime','18:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.subgroups','early,mid,late');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.early.label','Early evening');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.early.starttime','18:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.early.endtime','19:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.mid.label','Mid evening');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.mid.starttime','19:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.mid.endtime','20:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.late.label','Late evening');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.late.starttime','20:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.evening.late.endtime','21:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.subgroups','early,mid,late');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.early.label','Early night');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.early.starttime','21:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.early.endtime','00:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.mid.label','Mid night');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.mid.starttime','00:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.mid.endtime','03:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.late.label','Late night');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.late.starttime','03:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','schedule.night.late.endtime','06:00');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','survey.scheduler.crontab.expression','0 12 06 * * ?');
insert into config_registry(service,module,configKey,configValue) values('schedule','MES','survey.reminder.crontab.expression','0 12 06 * * ?');

--
-- screenshare-- 

insert into config_registry(service,module,configKey,configValue) values('screenshare','MES','otp.pool.maximumcapasity','100');
insert into config_registry(service,module,configKey,configValue) values('screenshare','MES','otp.pool.fillingfactor','0.5');
--

-- usertask-- 

insert into config_registry(service,module,configKey,configValue) values('usertask','MES','module.request','Request');
insert into config_registry(service,module,configKey,configValue) values('usertask','MES','defaultLocale.request','en_US');
--
-- version-- 

insert into config_registry(service,module,configKey,configValue) values('version','MES','pme.mes','464');
insert into config_registry(service,module,configKey,configValue) values('version','MES','pme.tvapp','71');



insert into config_registry_metadata(configKey,display,modify,description) values('computrition_host',true,true,'Computrition Ip');
insert into config_registry_metadata(configKey,display,modify,description) values('computrition_port',true,true,'computrition Port number');
insert into config_registry_metadata(configKey,display,modify,description) values('patient_can_order_food',true,true,'Patient can order food');
insert into config_registry_metadata(configKey,display,modify,description) values('raulandborg.api.url',true,true,'Raulandborg api url');
insert into config_registry_metadata(configKey,display,modify,description) values('raulandborg.ws.url',true,true,'Raulandborg ws url');
insert into config_registry_metadata(configKey,display,modify,description) values('raulandborg.service.available',true,true,'Raulandborg service availability');


-- content service --
insert into config_registry_metadata(configKey,display,modify,description) values('education.video.format',true,true,'Education video format');
insert into config_registry_metadata(configKey,display,modify,description) values('relaxation.video.format',true,true,'Relaxation video format');
insert into config_registry_metadata(configKey,display,modify,description) values('education.thumbnail.format',true,true,'Education thumbnail format');
insert into config_registry_metadata(configKey,display,modify,description) values('relaxation.thumbnail.format',true,true,'Relaxation thumbnail format');
insert into config_registry_metadata(configKey,display,modify,description) values('mediaserver.type',true,true,'Mediaserver type');
insert into config_registry_metadata(configKey,display,modify,description) values('mediaserver.dynamicurl',true,true,'Media server dynamic URL');
insert into config_registry_metadata(configKey,display,modify,description) values('mediaserver.dummydata',true,true,'Media server dummy data');
insert into config_registry_metadata(configKey,display,modify,description) values('apache.base.url',true,true,'Apache base URL');

insert into config_registry_metadata(configKey,display,modify,description) values('vod.update.poll.enable',true,true, 'Whether to poll VOD/Movies on demand titles & categories');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.provider.top.category',false,false, 'Category used for VOD/Movies on demand');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.provider.locale.default',true,true,'Locale for movie titles & descriptions');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.desired.encoding',true,true,'DRM encoding');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.poll.video.url',true,false,'URL for VOD adapter to poll movie metadata');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.poll.categories.url',true,false,'URL for VOD adapter to poll categories');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.category.resource.service', false, false, 'service for which resource bundle contains category names');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.category.resource.table', false, false, 'table for which resource bundle contains category names');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.server.host', true, true, 'URL for VOD/Movies on demand streaming server');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.server.customer.key', true, true, 'authentication key to access VOD/Movies on demand streaming server');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.rating.system1', true, true, 'age-based rating system to use- 1st choice');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.rating.system2', true, true, 'age-based rating system to use- 2nd choice');
insert into config_registry_metadata(configKey,display,modify,description) values('vod.language.filter',true,true, 'When true, languages supported by VOD provider will be filtered based on the langauges supported by the product');
-- --

-- speeddial service --
insert into config_registry_metadata(configKey,display,modify,description) values('whitelist.enable.phone.lookup.ad',true,true,'Look up staff phones in AD for whitelist?- true or false');
-- --

insert into config_registry_metadata(configKey,display,modify,description) values('drug.education.default.list.sections',true,true,'Drug education default list sections');
insert into config_registry_metadata(configKey,display,modify,description) values('drug.education.locale.default',true,true,'Drug education default locale');

insert into config_registry_metadata(configKey,display,modify,description) values('show.notifications',true,true,'Show notification');
insert into config_registry_metadata(configKey,display,modify,description) values('phone.ringer',true,true,'Phone ringer');

insert into config_registry_metadata(configKey,display,modify,description) values('video.call',true,true,'Video call');
insert into config_registry_metadata(configKey,display,modify,description) values('presence.do.not.disturb',true,true,'Presence Do not disturb');
insert into config_registry_metadata(configKey,display,modify,description) values('show.footer',true,true,'Show footer');
insert into config_registry_metadata(configKey,display,modify,description) values('video.call.auto.answer.expiry.duration',true,true,'Auto answer expiry duration for video call');
insert into config_registry_metadata(configKey,display,modify,description) values('privacy.data.unlock.duration',true,true,'Privacy data unlock duration');
insert into config_registry_metadata(configKey,display,modify,description) values('privacy.data.lock',true,true,'Privacy data lock');

insert into config_registry_metadata(configKey,display,modify,description) values('patient.name.default.display.format',true,true,'Patient name default display format');
insert into config_registry_metadata(configKey,display,modify,description) values('font.size',true,true,'Font size');
insert into config_registry_metadata(configKey,display,modify,description) values('language',true,true,'Language');


insert into config_registry_metadata(configKey,display,modify,description) values('hl7.timestamp.implicit.timezone',true,true,'hl7 timestamp implicit timezone');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.handler.inbound.NOTIFICATION_CODES',true,true,'which notifications should be processed');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.handler.inbound.patientClass.admit.include',true,true,'classes of patients (as CSV) to be admitted in CPC');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.handler.msg.size.limit',true,true,'hl7 handler msg size limit');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.handler.msgs.per.second.limit',true,true,'hl7 handler msgs per second limit');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.patient.schedule.ts.format',true,true,'hl7 patient schedule ts format');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.patient.schedule.ts.past.margin.minutes',true,true,'');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.patient.type.map.PRISONER',true,true,'patient type code used for prisoners');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.staff.assignment.max.age.days',true,true,'maximum age of staff assignments to store');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.staff.role.description.PRIMARY_NURSE',true,true,'role description to use for primary nurse');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.staff.role.pattern.doctor',true,true,'patterns (as CSV) in staff roles to be classified as doctor');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.staff.role.pattern.nurse',true,true,'patterns (as CSV) in staff roles to be classified as nurse');
insert into config_registry_metadata(configKey,display,modify,description) values('hl7.user.id.type.filter',true,true,'which user id to store for care team staff');

insert into config_registry_metadata(configKey,display,modify,description) values('patient.map.emrstaff.to.ad',true,true,'mapping of emrstaff to ad');
insert into config_registry_metadata(configKey,display,modify,description) values('directory.cache.validity.secs',true,true,'directory cache validity in secs');
insert into config_registry_metadata(configKey,display,modify,description) values('directory.staff.photo.source.external',true,true,'directory staff photo source');

insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.titlePrefix.source',true,true,'staff profile titlePrefix source');
insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.name.source',true,true,'staff profile name source');
insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.nameSuffix.source',true,true,'staff profile nameSuffix source');

insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.credentialSuffix.source',true,true,'staff profile credentialSuffix source');
insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.jobTitle.source',true,true,'staff profile jobTitle source');
insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.teamName.source',true,true,'staff profile teamName source');
insert into config_registry_metadata(configKey,display,modify,description) values('staff.profile.photoUrl.source',true,true,'staff profile photoUrl source');

insert into config_registry_metadata(configKey,display,modify,description) values('directory.staff.photo.base.url',true,true,'directory staff photo base url');
insert into config_registry_metadata(configKey,display,modify,description) values('directory.staff.photo.default.url',true,true,'directory staff photo default url');


insert into config_registry_metadata(configKey,display,modify,description) values('cups.host',true,true,'cups host');
insert into config_registry_metadata(configKey,display,modify,description) values('cups.port',true,true,'cups port');
insert into config_registry_metadata(configKey,display,modify,description) values('cups.protocol',true,true,'cups protocol');
insert into config_registry_metadata(configKey,display,modify,description) values('cups.username',true,true,'cups username');
insert into config_registry_metadata(configKey,display,modify,description) values('cups.password',true,true,'cups password');
insert into config_registry_metadata(configKey,display,modify,description) values('cups.cert.location',true,true,'cups cert location');
insert into config_registry_metadata(configKey,display,modify,description) values('cups.cert.password',true,true,'cups cert password');
insert into config_registry_metadata(configKey,display,modify,description) values('presence.enabled',true,true,'presence enabled');
insert into config_registry_metadata(configKey,display,modify,description) values('user.expiration.time',true,true,'user expiration time');
insert into config_registry_metadata(configKey,display,modify,description) values('presence.rtls.enabled',true,true,'presence rtls enabled');

insert into config_registry_metadata(configKey,display,modify,description) values('demo.nurse.assignment.enabled',true,true,'demo nurse assignment enabled');
insert into config_registry_metadata(configKey,display,modify,description) values('demo.assign.nurseId',true,true,'demo assign nurseId');
insert into config_registry_metadata(configKey,display,modify,description) values('max.videos.assign',true,true,'Max videos assigned');

insert into config_registry_metadata(configKey,display,modify,description) values('tagnos.username',true,true,'rtls tagnos username to access API');
insert into config_registry_metadata(configKey,display,modify,description) values('tagnos.password',true,true,'rtls tagnos password to access API');
insert into config_registry_metadata(configKey,display,modify,description) values('tagnos.host',true,true,'rtls tagnos host to access API');
insert into config_registry_metadata(configKey,display,modify,description) values('tagnos.port',true,true,'rtls tagnos port to access API');

-- insert into config_registry_metadata(configKey,display,modify,description) values('demo.careteam.nurses',true,true,'AD ids for demo nurses');
insert into config_registry_metadata(configKey,display,modify,description) values('demo.nurse.roles',true,true,'Roles for demo nurses');
insert into config_registry_metadata(configKey,display,modify,description) values('demo.nurse.teamids',true,true,'Team ids for demo nurses');
insert into config_registry_metadata(configKey,display,modify,description) values('demo.nurse.teamnames',true,true,'Team names for demo nurses');
insert into config_registry_metadata(configKey,display,modify,description) values('demo.nurse.jobtitles',true,true,'Job titles for demo nurses');
insert into config_registry_metadata(configKey,display,modify,description) values('demo.nurse.roledescriptions',true,true,'Role descriptions for demo nurses');

insert into config_registry_metadata(configKey,display,modify,description) values('rtls.map.staffid',true,true,'rtls staffid mapping enabled');
insert into config_registry_metadata(configKey,display,modify,description) values('rtls.patient.track.max.history.min',true,true,'minutes of past patient location history to report');



-- For the existing tables and sequences, but NOT applicable for 'future' objects
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO adapter;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO adapter;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO adaptercore;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO adaptercore;

-- to set default privileges for future objects
ALTER DEFAULT PRIVILEGES FOR ROLE adapter IN SCHEMA public GRANT ALL ON TABLES TO adapter;
ALTER DEFAULT PRIVILEGES FOR ROLE adapter IN SCHEMA public GRANT ALL ON SEQUENCES TO adapter;
-- ALTER DEFAULT PRIVILEGES FOR ROLE adaptercore IN SCHEMA public GRANT ALL ON TABLES TO adaptercore;
-- ALTER DEFAULT PRIVILEGES FOR ROLE adaptercore IN SCHEMA public GRANT ALL ON SEQUENCES TO adaptercore;
