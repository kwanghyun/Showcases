--
-- Table structure for table content_video
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE content_video (
  content_video_pk SERIAL,
  video_id varchar(255) DEFAULT NULL,
  date_created timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  date_updated timestamp NULL,
  provider_unique_id varchar(255) DEFAULT NULL,
  common_key varchar(255) DEFAULT NULL,
  language VARCHAR(45) NOT NULL DEFAULT 'English',
  top_level_category varchar(255) DEFAULT NULL,
  media_server_attributes text DEFAULT NULL,
  PRIMARY KEY (content_video_pk)
) ;

CREATE INDEX VIDEO_idx ON content_video (provider_unique_id ASC, video_id ASC);

 
 
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE content_department (
  content_department_pk SERIAL,
  department_id varchar(255) DEFAULT NULL,
  video_id varchar(45) DEFAULT NULL,
  is_mandatory BOOLEAN DEFAULT NULL,
  content_video_fk int NOT NULL,
  PRIMARY KEY (content_department_pk),
  /*KEY fk_content_department_content_video1_idx (content_video_fk),*/
  CONSTRAINT fk_content_department_content_video1 FOREIGN KEY (content_video_fk) REFERENCES content_video (content_video_pk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_content_department_content_video1_idx ON content_department(content_video_fk);
/*!40101 SET character_set_client = @saved_cs_client */;




--
-- Table structure for table content_video_progress
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE content_video_progress (
  content_video_progress_pk SERIAL,
  user_id varchar(50) DEFAULT NULL,
  viewed_percentage int DEFAULT NULL,
  position varchar(50) DEFAULT '00:00:00',
  view_count int DEFAULT NULL,
  date_created timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  date_updated timestamp,
  is_mandatory BOOLEAN DEFAULT NULL,
  content_video_fk int NOT NULL,
  assigned_by VARCHAR(45) DEFAULT NULL,
  is_assigned BOOLEAN DEFAULT NULL,
  common_key VARCHAR(255) NOT NULL,
  language VARCHAR(45) NOT NULL,
  notify_when_complete BOOLEAN DEFAULT NULL,
  PRIMARY KEY (content_video_progress_pk),
  /*KEY fk_content_video_progress_content_video1_idx (content_video_fk),*/
  CONSTRAINT fk_content_video_progress_content_video1 FOREIGN KEY (content_video_fk) REFERENCES content_video (content_video_pk) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;
CREATE INDEX fk_content_video_progress_content_video1_idx ON content_video_progress(content_video_fk);
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE INDEX VIDEOPROGRESS_idx ON content_video_progress (user_id ASC);




/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE vod_list_last_update (
  vod_list_last_updatePk SERIAL,
  last_query_ts TIMESTAMP NOT NULL,
  locale_queried VARCHAR(16) NOT NULL,
  encoding_queried VARCHAR(32) NOT NULL,
  PRIMARY KEY (vod_list_last_updatePk));
  
-- content-- 

-- insert into config_registry(service,module,configKey,configValue) values('content','MES','sns-adapter.locid','1');
insert into config_registry(service,module,configKey,configValue) values('content','MES','solr.server.url','http://solrhost:8089/solr/');
insert into config_registry(service,module,configKey,configValue) values('content','MES','solr.solr.home','/opt/cisco/iep/mes/solr');
insert into config_registry(service,module,configKey,configValue) values('content','MES','solr.core.EDUCATION','edu');
insert into config_registry(service,module,configKey,configValue) values('content','MES','solr.core.RELAXATION','relax');
insert into config_registry(service,module,configKey,configValue) values('content','MES','solr.update.url.path','/update/csv');
insert into config_registry(service,module,configKey,configValue) values('content','MES','solr.delete.url.path','/update?commit=true&stream.body=<delete><query>{delete.query}</query></delete>&stream.contentType=text/xml;charset=utf-8');
insert into config_registry(service,module,configKey,configValue) values('content','MES','video.metadata.base.path','/opt/cisco/iep/mes/video-metadata/');
insert into config_registry(service,module,configKey,configValue) values('content','MES','education.gender.all','All');
insert into config_registry(service,module,configKey,configValue) values('content','MES','relaxation.gender.all','Any');
insert into config_registry(service,module,configKey,configValue) values('content','MES','education.video.format','mp4');
insert into config_registry(service,module,configKey,configValue) values('content','MES','relaxation.video.format','mp4');
insert into config_registry(service,module,configKey,configValue) values('content','MES','education.thumbnail.format','png');
insert into config_registry(service,module,configKey,configValue) values('content','MES','relaxation.thumbnail.format','png');
insert into config_registry(service,module,configKey,configValue) values('content','MES','mediaserver.type','APACHE');
insert into config_registry(service,module,configKey,configValue) values('content','MES','sns.base.category.path','/dms/core/contentcatalog/vportal/1/');
insert into config_registry(service,module,configKey,configValue) values('content','MES','mediaserver.dynamicurl','true');
insert into config_registry(service,module,configKey,configValue) values('content','MES','mediaserver.dummydata','false');
insert into config_registry(service,module,configKey,configValue) values('content','MES','apache.video.storage.path','/opt/cisco/iep/mes/content/video/');
insert into config_registry(service,module,configKey,configValue) values('content','MES','apache.thumbnail.storage.path','/opt/cisco/iep/mes/content/thumbnail/');
insert into config_registry(service,module,configKey,configValue) values('content','MES','apache.base.url','http://localhost:80');
insert into config_registry(service,module,configKey,configValue) values('content','MES','apache.video.url.path','/v/');
insert into config_registry(service,module,configKey,configValue) values('content','MES','apache.thumbnail.url.path','/tn/');

insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.update.poll.enable','false');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.provider.top.category','SWANK');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.provider.locale.default','en-US');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.desired.encoding','cenc');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.poll.video.url','http://esbhost:8283/services/swank-polling-proxy');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.poll.categories.url','http://esbhost:8283/services/swank-category-proxy');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.category.resource.service', 'CONTENT');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.category.resource.table', 'CATEGORY_NAME');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.server.host', 'ssdemo04.swankmp.com');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.server.customer.key', '3A7ED520-9CBE-4573-8252-E4E73912F06A');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.rating.system1', 'MPAA');
insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.rating.system2', 'US TV');

insert into config_registry(service,module,configKey,configValue) values('content','MES','vod.language.filter','false');

--
-- cms-content-- 

insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.enabled','true');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.api.url','http://cmshost:8080/nuxeo/site/automation');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.api.user','Administrator');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.api.password','Administrator');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.section.root','/default-domain/sections');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.media.root','/Media');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.url','http://cmshost:8080');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.content.url','/nuxeo/nxbigfile/default/{UID}/file:content/{NAME}');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.thumbnail.index','2');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.property.header.value','dublincore,file,video');
insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.append.query','AND ecm:currentLifeCycleState !="deleted" AND ecm:isCheckedInVersion=0');

insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.imagelibrary.root','/ImageLibrary');
insert into config_registry(service,module,configKey,configValue) values('content','MES','cms.image.library.modules', 'ChannelGuide,FoodMenu,Logos,Request,SpeedDial,Survey,AppIcons,PainScore,Preferences');
insert into config_registry(service,module,configKey,configValue) values('cms-content', 'MES', 'cms.image.library.theme.default', 'Default');
insert into config_registry(service,module,configKey,configValue) values('cms-content', 'MES', 'cms.image.library.theme.G', 'Textured');
insert into config_registry(service,module,configKey,configValue) values('cms-content', 'MES', 'cms.image.library.theme.PG', 'Kids');
insert into config_registry(service,module,configKey,configValue) values('cms-content', 'MES', 'cms.image.library.theme.PG-13', 'Kids');
insert into config_registry(service,module,configKey,configValue) values('cms-content', 'MES', 'cms.image.library.theme.NC-17', 'Textured');
insert into config_registry(service,module,configKey,configValue) values('cms-content', 'MES', 'cms.image.library.theme.R', 'Textured');

insert into config_registry(service,module,configKey,configValue) values('cms-content','MES','cms.swank.root','/SwankVOD');


-- insert data into config_registry_metadata

insert into config_registry_metadata(configKey,display,modify,description) values('cms.enabled',true,true,'CMS enabled');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.api.url',true,true,'CMS URL');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.api.user',true,true,'CMS User');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.api.password',true,true,'CMS Password');

insert into config_registry_metadata(configKey,display,modify,description) values('cms.thumbnail.index',true,true,'CMS thumbnail index');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.url',true,true,'CMS URL');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.image.library.theme.default', true, true, 'Default Theme assigned');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.image.library.theme.G', true, true, 'Theme for ageCategory = G');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.image.library.theme.PG', true, true, 'Theme for ageCategory = PG');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.image.library.theme.PG-13', true, true, 'Theme for ageCategory = PG-13');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.image.library.theme.NC-17', true, true, 'Theme for ageCategory = NC-17');
insert into config_registry_metadata(configKey,display,modify,description) values('cms.image.library.theme.R', true, true, 'Theme for ageCategory = R');

