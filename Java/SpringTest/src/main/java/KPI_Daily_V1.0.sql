DROP TABLE SmartLockerTableDaily;
CREATE EXTERNAL TABLE IF NOT EXISTS SmartLockerTableDaily 
	(rowID STRING, eventTime TIMESTAMP, appId STRING, eventType INT, transactionId STRING, sessionId STRING, version STRING, 
		eventTypeStr STRING, totalTime BIGINT, trackingNumber STRING, shippingLabel STRING, empId STRING, 
		site STRING, bank STRING, lockerId STRING, size STRING, successFlag INT, failureReason STRING, packageStatus STRING,
	 	surveyScore INT, numberOfPackages INT, pkgRetainedTime STRING, occupiedLockerCount INT, totalLockerCount INT, occupancy STRING) STORED BY 
	'org.apache.hadoop.hive.cassandra.CassandraStorageHandler' 
	WITH SERDEPROPERTIES (
 	"wso2.carbon.datasource.name" = "WSO2BAM_CASSANDRA_DATASOURCE",
 	"cassandra.ks.name" = "EVENT_KS" ,
	"cassandra.cf.name" = "com_cisco_bam_smartlocker_kpi" , 
	"cassandra.columns.mapping" = 
	":key, Timestamp, payload_appId, payload_eventType, payload_transactionId, payload_sessionId, Version, 
		eventType, totalTime, trackingNumber, shippingLabel, empId, 
		site, bank, lockerId, size, successFlag, failureReason, packageStatus,
		surveyScore, numberOfPackages, pkgRetainedTime, occupiedLockerCount, totalLockerCount, occupancy" );

	
set hive.execution.engine=tez;
set hive.vectorized.execution.enabled = true;
set hive.vectorized.execution.reduce.enabled = true;

set TARGET_DAY = date_sub(to_date(from_unixtime(unix_timestamp())),1);
		

DROP TABLE CourierDropOffSiteBankVsAll;	
CREATE EXTERNAL TABLE IF NOT EXISTS CourierDropOffSiteBankVsAll(avgDepositTime STRING, depositCount INT, overallAvgDepositTime STRING, overallDepositCount INT, 
	transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_courier_dropoff_site_bank_vs_all(avgDepositTime DECIMAL(6,1), depositCount INT, OverallAvgDepositTime DECIMAL(6,1), OverallDepositCount INT, 
						transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CourierDropOffSiteBankVsAll 

						
SELECT s1.totalTime as avgDepositTime, s1.depositCount as depositCount, s2.totalTime as overallAvgDepositTime, s2.depositCount as overallDepositCount, 
	s1.targetDay as queryDate, s1.site as site , s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 32 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
	GROUP BY site, bank ) s1
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 32 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
	GROUP BY 1 ) s2 
ON (s1.targetDay = s2.targetDay);


DROP TABLE CourierPickupSiteBankVsAll;	
CREATE EXTERNAL TABLE IF NOT EXISTS CourierPickupSiteBankVsAll(avgPickupTime STRING, pickupCount INT, overallAvgPickupTime STRING, overallPickupCount INT, 
				transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_courier_pickup_site_bank_vs_all(avgPickupTime DECIMAL(6,1), pickupCount INT, overallAvgPickupTime DECIMAL(6,1), overallPickupCount INT, 
				transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CourierPickupSiteBankVsAll


SELECT s1.totalTime as avgPickupTime, s1.retrieveCount as pickupCount, s2.totalTime as overallAvgPickupTime, s2.retrieveCount as overallPickupCount, 
		s1.targetDay as queryDate, s1.site as site, s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 33 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
	GROUP BY site, bank) s1 
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 33 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
	GROUP BY 1 ) s2
ON (s1.targetDay = s2.targetDay);	



DROP TABLE CourierUnproductiveStopsSiteBankWise;
CREATE EXTERNAL TABLE IF NOT EXISTS CourierUnproductiveStopsSiteBankWise(failedCourierDropOffCount INT, transactionDate TIMESTAMP, site STRING, bank STRING, courierId STRING
	) STORED BY 
	'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
	TBLPROPERTIES ( 
    'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
	'hive.jdbc.table.create.query' = 	
	'CREATE TABLE kpi_courier_unproductive_dropoffs(failedCourierDropOffCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100), courierId VARCHAR(100))' );

insert overwrite table CourierUnproductiveStopsSiteBankWise


SELECT count(*) as failedCourierDropOff, ${hiveconf:TARGET_DAY} as eventDate, site, bank, empId
FROM SmartLockerTableDaily
WHERE eventType = 32  AND version = '1.0.0' AND successflag = 0 
	AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
	AND failureReason IS NOT NULL
GROUP BY site, bank, empId;

	
	
DROP TABLE CustomerDropOffSiteBankVsAll;	
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerDropOffSiteBankVsAll(avgDepositTime STRING, depositCount INT, overallAvgDepositTime STRING, overallDepositCount INT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_drop_off_site_bank_vs_all(avgDepositTime DECIMAL(6,1), depositCount INT, OverallAvgDepositTime DECIMAL(6,1), OverallDepositCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerDropOffSiteBankVsAll


SELECT s1.totalTime as avgDepositTime, s1.depositCount as depositCount, s2.totalTime as overallAvgDepositTime, s2.depositCount as overallDepositCount, 
	${hiveconf:TARGET_DAY} as queryDate, s1.site as site , s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 34 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
	GROUP BY site, bank ) s1 
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 34 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
	GROUP BY 1 ) s2 
ON (s1.targetDay = s2.targetDay);



DROP TABLE CustomerPickupSiteBankVsAll;	
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerPickupSiteBankVsAll(avgPickupTime STRING, pickupCount INT, overallAvgPickupTime STRING, overallPickupCount INT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_pickup_site_bank_vs_all(avgPickupTime DECIMAL(6,1), pickupCount INT, overallAvgPickupTime DECIMAL(6,1), overallPickupCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerPickupSiteBankVsAll  


SELECT s1.totalTime as avgPickupTime, s1.retrieveCount as pickupCount, s2.totalTime as overallAvgPickupTime, s2.retrieveCount as overallPickupCount, 
		${hiveconf:TARGET_DAY} as queryDate, s1.site as site, s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 35 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
	GROUP BY site, bank) s1 
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE eventType = 35 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
	GROUP BY 1 ) s2
ON (s1.targetDay = s2.targetDay);


DROP TABLE CustomerRetrieveTimeSiteWise;
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerRetrieveTimeSiteWise(acceptTime STRING, acceptCount INT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_retrieve_time_site_wise(acceptTime VARCHAR(2), acceptCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerRetrieveTimeSiteWise


SELECT  substr(eventTime,12,2) AS accepTime, count(*) AS acceptCount, to_date(eventTime) as eventDate, site, bank 
FROM SmartLockerTableDaily 
WHERE eventType = 35 AND successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
GROUP BY to_date(eventTime), substr(eventTime,12,2), site, bank;




DROP TABLE CustomerRedeliverySiteBankWise;
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerRedeliverySiteBankWise(redeliverCount INT, depositCount INT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_redelivery_site_bank_wise(redeliverCount INT, depositCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerRedeliverySiteBankWise  


SELECT sum(u.redeliverCount) as redeliverCount, sum(u.depositCount) as depositCount, ${hiveconf:TARGET_DAY} as queryDate, site, bank 
FROM (
	SELECT  count(*) AS redeliverCount, count(*) - count(*) AS depositCount, site, bank 
	FROM SmartLockerTableDaily 
	WHERE successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
		AND (eventType = 33 or eventType = 35) AND packageStatus = "REDELIVERED" 
	GROUP BY site, bank 
	union all
	SELECT  count(*) - count(*) as redeliverCount, count(*) as depositCount, site, bank 
	FROM SmartLockerTableDaily 
	WHERE successflag = 1 AND version = '1.0.0' AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
		AND (eventType = 33 or eventType = 35)
	GROUP BY site, bank 
) u
group by site, bank;



DROP TABLE CustomerSatisfactionSurveySiteBankVsAll;
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerSatisfactionSurveySiteBankVsAll(satisfactionRating FLOAT, OverallSatisfactionRating FLOAT, activityType STRING, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_satisfaction_survey_site_bank_vs_all(satisfactionRating DECIMAL(6,3), overallSatisfactionRating DECIMAL(6,3), activityType VARCHAR(100), transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerSatisfactionSurveySiteBankVsAll    


SELECT s1.surveyRating  as surveyRating, s2.surveyRating as totalAvgSurveyRating, 
	s1.activityType as activityType, ${hiveconf:TARGET_DAY} as queryDate, s1.site as site, s1.bank as bank 
from (
	select avg(surveyScore) as surveyRating, eventtypestr as activityType, site as site, bank as bank 
	from SmartLockerTableDaily 
	where  to_date(eventTime) = ${hiveconf:TARGET_DAY} AND successflag = 1 AND surveyScore > 0 
		AND version = '1.0.0' AND (eventType = 34 or eventType = 35)
	group by eventtypestr, site, bank) s1 
join (
	select avg(surveyScore) as surveyRating, eventtypestr as activityType
	from SmartLockerTableDaily 
	where  to_date(eventTime) = ${hiveconf:TARGET_DAY} AND successflag = 1 AND surveyScore > 0
		AND version = '1.0.0' AND (eventType = 34 or eventType = 35)
	group by eventtypestr) s2 
on (s1.activityType=s2.activityType);





DROP TABLE LockerPackageCount;
CREATE EXTERNAL TABLE IF NOT EXISTS LockerPackageCount(packageCount INT, lockerId STRING, site STRING, bank STRING, transactionDate TIMESTAMP
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_capcity_locker_package_count(packageCount INT, lockerId VARCHAR(100), site VARCHAR(100), bank VARCHAR(100), transactionDate TIMESTAMP)' );

insert overwrite table LockerPackageCount


select count(*) as packageCount, lockerId as lockerId, site, bank, ${hiveconf:TARGET_DAY} as queryDate 
from SmartLockerTableDaily 
where to_date(eventTime) = ${hiveconf:TARGET_DAY} and (eventType = 32 or eventType = 34)
	AND successflag = 1 AND version = '1.0.0'
group by site, bank, lockerId; 



DROP TABLE LockerUtilizationPC;
CREATE EXTERNAL TABLE IF NOT EXISTS LockerUtilizationPC(avgLockerUtilizationPC FLOAT, avgTotalLockers FLOAT, avgOccupiedLockers FLOAT, 
	overallAvgLockerUtilizationPC FLOAT, overallAvgTotalLockers FLOAT, overallAvgOccupiedLockers FLOAT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_capcity_locker_utilization_site_bank_vs_all(avgLockerUtilizationPC DECIMAL(6,1), avgTotalLockers DECIMAL(6,1), avgOccupiedLockers DECIMAL(6,1), 
	overallAvgLockerUtilizationPC DECIMAL(6,1), overallAvgTotalLockers DECIMAL(6,1), overallAvgOccupiedLockers DECIMAL(6,1), transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table LockerUtilizationPC 


select s3.avgLockerUtilizationPC as avgLockerUtilizationPC, s3.avgTotalLockers as avgTotalLockers, s3.avgOccupiedLockers as avgOccupiedLockers, 
	s4.avgLockerUtilizationPC as overallAvgLockerUtilizationPC, s4.avgTotalLockers as overallAvgTotalLockers, s4.avgOccupiedLockers as overallAvgOccupiedLockers, 
	s3.queryDate as queryDate, s3.site as site, s3.bank as bank 
from (
	select (avg(s1.occupiedLockerCount)/avg(s1.totalLockerCount))*100 as avgLockerUtilizationPC, avg(s1.totalLockerCount) as avgTotalLockers, 
		avg(s1.occupiedLockerCount) as avgOccupiedLockers, ${hiveconf:TARGET_DAY} as queryDate, s1.site, s1.bank  
	from (
		select eventTime, site, bank, occupiedLockerCount, totalLockerCount 
		from SmartLockerTableDaily 
		where to_date(eventTime) = ${hiveconf:TARGET_DAY} AND successflag = 1 AND version = '1.0.0' 
			AND (eventType = 32 OR eventType = 33 OR eventType = 34 OR eventType = 35 ) ) s1
	group by s1.site, s1.bank) s3 
join (
	select (avg(s2.occupiedLockerCount)/avg(s2.totalLockerCount))*100 as avgLockerUtilizationPC, avg(s2.totalLockerCount) as avgTotalLockers, 
		avg(s2.occupiedLockerCount) as avgOccupiedLockers, ${hiveconf:TARGET_DAY} as queryDate
	from (
		select eventTime,  totalLockerCount, occupiedLockerCount 
		from SmartLockerTableDaily 
		where to_date(eventTime) = ${hiveconf:TARGET_DAY} AND successflag = 1 AND version = '1.0.0' 
			AND (eventType = 32 OR eventType = 33 OR eventType = 34 OR eventType = 35 ) ) s2
		group by 1 ) s4 
on (s3.queryDate = s4.queryDate);




DROP TABLE IdealLockerConfigAll;
CREATE EXTERNAL TABLE IF NOT EXISTS IdealLockerConfigAll(count INT, site STRING, bank STRING, size STRING, transactionDate TIMESTAMP
	) STORED BY 
	'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
	TBLPROPERTIES ( 
    'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
	'hive.jdbc.table.create.query' = 
	'CREATE TABLE kpi_ideal_config_all(count INT, site VARCHAR(100), bank VARCHAR(100), size VARCHAR(10), transactionDate TIMESTAMP)' );
                                    
insert overwrite table IdealLockerConfigAll 


SELECT count(*) AS count, site, bank, size, ${hiveconf:TARGET_DAY} as queryDate 
FROM SmartLockerTableDaily 
WHERE  to_date(eventTime) = ${hiveconf:TARGET_DAY} and (eventType = 32 or eventType = 33)
	AND successflag = 1 AND version = '1.0.0'
GROUP BY site, bank, size;





DROP TABLE CapacityUtilizationRetainedTime;	
CREATE EXTERNAL TABLE IF NOT EXISTS CapacityUtilizationRetainedTime(avgRetainedTime STRING, overallAvgRetainedTime STRING, transactionDate TIMESTAMP, site STRING, bank STRING
	) STORED BY 
	'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
	TBLPROPERTIES ( 
    'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
	'hive.jdbc.table.create.query' = 
	'CREATE TABLE kpi_capacity_utilization_retained_period_vs_all(avgRetainedTime DECIMAL(6,1), overallAvgRetainedTime DECIMAL(6,1), transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );
                                    
insert overwrite table CapacityUtilizationRetainedTime


SELECT s1.pkgretainedtime as avgRetainedTime, s2.pkgretainedtime as overallAvgRetainedTime, s1.targetDay as queryDate, s1.site as site , s1.bank as bank
FROM(
	SELECT avg(cast(pkgretainedtime as BIGINT)/1000) as pkgretainedtime, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE successflag = 1 AND version = '1.0.0' AND (eventType = 33 OR eventType = 35) 
		AND pkgretainedtime IS NOT NULL AND to_date(eventTime) = ${hiveconf:TARGET_DAY} 
	GROUP BY site, bank ) s1
JOIN (
	SELECT avg(cast(pkgretainedtime as BIGINT)/1000) as pkgretainedtime, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE successflag = 1 AND version = '1.0.0' AND (eventType = 33 OR eventType = 35) 
		AND pkgretainedtime IS NOT NULL AND to_date(eventTime) = ${hiveconf:TARGET_DAY}
GROUP BY 1 ) s2;






DROP TABLE AbsentPackageCount;	
CREATE EXTERNAL TABLE IF NOT EXISTS AbsentPackageCount(packageCount INT, lockerId STRING, site STRING, bank STRING, transactionDate TIMESTAMP
	) STORED BY 
	'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
	TBLPROPERTIES ( 
	'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
	'hive.jdbc.table.create.query' = 
	'CREATE TABLE kpi_capcity_absent_package_count(packageCount INT, lockerId VARCHAR(100), site VARCHAR(100), bank VARCHAR(100), transactionDate TIMESTAMP)' );
                                    
insert overwrite table AbsentPackageCount


SELECT count(*) as packageCount, lockerId as lockerId, site, bank, ${hiveconf:TARGET_DAY} as queryDate 
from SmartLockerTableDaily 
where to_date(eventTime) = ${hiveconf:TARGET_DAY} and (eventType = 32 or eventType = 34)
	AND successflag = 1 AND version = '1.0.0' AND occupancy = 'VACANT'
group by site, bank, lockerId; 





DROP TABLE UntrackedPackageCount;	
CREATE EXTERNAL TABLE IF NOT EXISTS UntrackedPackageCount(packageCount INT, lockerId STRING, site STRING, bank STRING, transactionDate TIMESTAMP
	) STORED BY 
	'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
	TBLPROPERTIES ( 
	'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
	'hive.jdbc.table.create.query' = 
	'CREATE TABLE kpi_capcity_untracked_package_count(packageCount INT, lockerId VARCHAR(100), site VARCHAR(100), bank VARCHAR(100), transactionDate TIMESTAMP)' );
	                                    
insert overwrite table UntrackedPackageCount


select count(*) as packageCount, lockerId as lockerId, site, bank, ${hiveconf:TARGET_DAY} as queryDate 
from SmartLockerTableDaily 
where to_date(eventTime) = ${hiveconf:TARGET_DAY} and (eventType = 33 or eventType = 35)
	AND successflag = 1 AND version = '1.0.0' AND occupancy = 'OCCUPIED'
group by site, bank, lockerId; 




