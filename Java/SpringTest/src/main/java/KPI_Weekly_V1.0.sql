DROP TABLE SmartLockerTableWeekly;
CREATE EXTERNAL TABLE IF NOT EXISTS SmartLockerTableWeekly
	(rowID STRING, eventTime TIMESTAMP, appId STRING, eventType INT, transactionId STRING, sessionId STRING, version STRING, 
		eventTypeStr STRING, totalTime BIGINT, trackingNumber STRING, shippingLabel STRING, empId STRING, 
		site STRING, bank STRING, lockerId STRING, size STRING, successFlag INT, failureReason STRING, packageStatus STRING,
	 	surveyScore INT, numberOfPackages INT, pkgRetainedTime BIGINT, occupiedLockerCount INT, totalLockerCount INT) STORED BY 
	'org.apache.hadoop.hive.cassandra.CassandraStorageHandler' 
	WITH SERDEPROPERTIES (
 	"wso2.carbon.datasource.name" = "WSO2BAM_CASSANDRA_DATASOURCE",
 	"cassandra.ks.name" = "EVENT_KS" ,
	"cassandra.cf.name" = "com_cisco_bam_smartlocker_kpi" , 
	"cassandra.columns.mapping" = 
	":key, Timestamp, payload_appId, payload_eventType, payload_transactionId, payload_sessionId, Version, 
		eventType, totalTime, trackingNumber, shippingLabel, empId, 
		site, bank, lockerId, size, successFlag, failureReason, packageStatus,
		surveyScore, numberOfPackages, pkgRetainedTime, occupiedLockerCount, totalLockerCount" );

	
set hive.execution.engine=tez;
set hive.vectorized.execution.enabled = true;
set hive.vectorized.execution.reduce.enabled = true;

set TARGET_DAY = date_sub(to_date(from_unixtime(unix_timestamp())),1);
set TARGET_DAY_WEEKAGO = date_sub(to_date(from_unixtime(unix_timestamp())),7);


DROP TABLE CourierDropOffSiteBankVsAllWeekly;	
CREATE EXTERNAL TABLE IF NOT EXISTS CourierDropOffSiteBankVsAllWeekly(avgDepositTime STRING, depositCount INT, overallAvgDepositTime STRING, overallDepositCount INT, 
	transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_courier_dropoff_site_bank_vs_all_weekly(avgDepositTime DECIMAL(6,1), depositCount INT, OverallAvgDepositTime DECIMAL(6,1), OverallDepositCount INT, 
						transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CourierDropOffSiteBankVsAllWeekly 

						
SELECT s1.totalTime as avgDepositTime, s1.depositCount as depositCount, s2.totalTime as overallAvgDepositTime, s2.depositCount as overallDepositCount, 
	s1.targetDay as queryDate, s1.site as site , s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 32 AND successflag = 1 AND version = '1.0.0' 			
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY site, bank ) s1
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 32 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY 1 ) s2 
ON (s1.targetDay = s2.targetDay);


DROP TABLE CourierPickupSiteBankVsAllWeekly;	
CREATE EXTERNAL TABLE IF NOT EXISTS CourierPickupSiteBankVsAllWeekly(avgPickupTime STRING, pickupCount INT, overallAvgPickupTime STRING, overallPickupCount INT, 
				transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_courier_pickup_site_bank_vs_all_weekly(avgPickupTime DECIMAL(6,1), pickupCount INT, overallAvgPickupTime DECIMAL(6,1), overallPickupCount INT, 
				transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CourierPickupSiteBankVsAllWeekly


SELECT s1.totalTime as avgPickupTime, s1.retrieveCount as pickupCount, s2.totalTime as overallAvgPickupTime, s2.retrieveCount as overallPickupCount, 
		s1.targetDay as queryDate, s1.site as site, s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 33 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY site, bank) s1 
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 33 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY 1 ) s2
ON (s1.targetDay = s2.targetDay);




	
DROP TABLE CustomerDropOffSiteBankVsAllWeekly;	
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerDropOffSiteBankVsAllWeekly(avgDepositTime STRING, depositCount INT, overallAvgDepositTime STRING, overallDepositCount INT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_drop_off_site_bank_vs_all_weekly(avgDepositTime DECIMAL(6,1), depositCount INT, OverallAvgDepositTime DECIMAL(6,1), OverallDepositCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerDropOffSiteBankVsAllWeekly


SELECT s1.totalTime as avgDepositTime, s1.depositCount as depositCount, s2.totalTime as overallAvgDepositTime, s2.depositCount as overallDepositCount, 
	${hiveconf:TARGET_DAY} as queryDate, s1.site as site , s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 34 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY site, bank ) s1 
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as depositCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 34 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY 1 ) s2 
ON (s1.targetDay = s2.targetDay);



DROP TABLE CustomerPickupSiteBankVsAllWeekly;	
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerPickupSiteBankVsAllWeekly(avgPickupTime STRING, pickupCount INT, overallAvgPickupTime STRING, overallPickupCount INT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_pickup_site_bank_vs_all_weekly(avgPickupTime DECIMAL(6,1), pickupCount INT, overallAvgPickupTime DECIMAL(6,1), overallPickupCount INT, transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerPickupSiteBankVsAllWeekly  


SELECT s1.totalTime as avgPickupTime, s1.retrieveCount as pickupCount, s2.totalTime as overallAvgPickupTime, s2.retrieveCount as overallPickupCount, 
		${hiveconf:TARGET_DAY} as queryDate, s1.site as site, s1.bank as bank
FROM(
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 35 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY site, bank) s1 
JOIN (
	SELECT avg(totalTime) as totalTime, count(*) as retrieveCount, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableWeekly
	WHERE eventType = 35 AND successflag = 1 AND version = '1.0.0' 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	GROUP BY 1 ) s2
ON (s1.targetDay = s2.targetDay);	




DROP TABLE CustomerSatisfactionSurveySiteBankVsAllWeekly;
CREATE EXTERNAL TABLE IF NOT EXISTS CustomerSatisfactionSurveySiteBankVsAllWeekly(satisfactionRating FLOAT, OverallSatisfactionRating FLOAT, activityType STRING, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_customer_satisfaction_survey_site_bank_vs_all_weekly(satisfactionRating DECIMAL(6,3), overallSatisfactionRating DECIMAL(6,3), activityType VARCHAR(100), transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table CustomerSatisfactionSurveySiteBankVsAllWeekly    


SELECT s1.surveyRating  as surveyRating, s2.surveyRating as totalAvgSurveyRating, 
	s1.activityType as activityType, ${hiveconf:TARGET_DAY} as queryDate, s1.site as site, s1.bank as bank 
from (
	select avg(surveyScore) as surveyRating, eventtypestr as activityType, site as site, bank as bank 
	from SmartLockerTableWeekly 
	where  successflag = 1 AND version = '1.0.0' AND (eventType = 34 or eventType = 35) AND surveyScore > 0 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
	group by eventtypestr, site, bank) s1 
join (
	select avg(surveyScore) as surveyRating, eventtypestr as activityType
	from SmartLockerTableWeekly 
	where  successflag = 1 AND version = '1.0.0' AND (eventType = 34 or eventType = 35) AND surveyScore > 0 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}	
	group by eventtypestr) s2 
on (s1.activityType=s2.activityType);




DROP TABLE LockerUtilizationPCWeekly;
CREATE EXTERNAL TABLE IF NOT EXISTS LockerUtilizationPCWeekly(avgLockerUtilizationPC FLOAT, avgTotalLockers FLOAT, avgOccupiedLockers FLOAT, 
	overallAvgLockerUtilizationPC FLOAT, overallAvgTotalLockers FLOAT, overallAvgOccupiedLockers FLOAT, transactionDate TIMESTAMP, site STRING, bank STRING
) STORED BY 
'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
TBLPROPERTIES ( 
'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
'hive.jdbc.table.create.query' = 
'CREATE TABLE kpi_capcity_locker_utilization_site_bank_vs_all_weekly(avgLockerUtilizationPC DECIMAL(6,1), avgTotalLockers DECIMAL(6,1), avgOccupiedLockers DECIMAL(6,1), 
	overallAvgLockerUtilizationPC DECIMAL(6,1), overallAvgTotalLockers DECIMAL(6,1), overallAvgOccupiedLockers DECIMAL(6,1), transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );

insert overwrite table LockerUtilizationPCWeekly 


select s3.avgLockerUtilizationPC as avgLockerUtilizationPC, s3.avgTotalLockers as avgTotalLockers, s3.avgOccupiedLockers as avgOccupiedLockers, 
	s4.avgLockerUtilizationPC as overallAvgLockerUtilizationPC, s4.avgTotalLockers as overallAvgTotalLockers, s4.avgOccupiedLockers as overallAvgOccupiedLockers, 
	s3.queryDate as queryDate, s3.site as site, s3.bank as bank 
from (
	select (avg(s1.occupiedLockerCount)/avg(s1.totalLockerCount))*100 as avgLockerUtilizationPC, avg(s1.totalLockerCount) as avgTotalLockers, 
		avg(s1.occupiedLockerCount) as avgOccupiedLockers, ${hiveconf:TARGET_DAY} as queryDate, s1.site, s1.bank  
	from (
		select eventTime, site, bank, occupiedLockerCount, totalLockerCount 
		from SmartLockerTableWeekly 
		where successflag = 1 AND version = '1.0.0'
			AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
			AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
			AND (eventType = 32 OR eventType = 33 OR eventType = 34 OR eventType = 35 ) ) s1
	group by s1.site, s1.bank) s3 
join (
	select (avg(s2.occupiedLockerCount)/avg(s2.totalLockerCount))*100 as avgLockerUtilizationPC, avg(s2.totalLockerCount) as avgTotalLockers, 
		avg(s2.occupiedLockerCount) as avgOccupiedLockers, ${hiveconf:TARGET_DAY} as queryDate
	from (
		select eventTime,  totalLockerCount, occupiedLockerCount 
		from SmartLockerTableWeekly 
		where successflag = 1 AND version = '1.0.0'
			AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
			AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
			AND (eventType = 32 OR eventType = 33 OR eventType = 34 OR eventType = 35 ) ) s2
		group by 1 ) s4 
on (s3.queryDate = s4.queryDate);






DROP TABLE CapacityUtilizationRetainedTimeWeekly;	
CREATE EXTERNAL TABLE IF NOT EXISTS CapacityUtilizationRetainedTimeWeekly(avgRetainedTime STRING, overallAvgRetainedTime STRING, transactionDate TIMESTAMP, site STRING, bank STRING
	) STORED BY 
	'org.wso2.carbon.hadoop.hive.jdbc.storage.JDBCStorageHandler' 
	TBLPROPERTIES ( 
    'wso2.carbon.datasource.name'='MYSQL_DATA_SOURCE',
	'hive.jdbc.table.create.query' = 
	'CREATE TABLE kpi_capacity_utilization_retained_period_vs_all_weekly(avgRetainedTime DECIMAL(6,1), overallAvgRetainedTime DECIMAL(6,1), transactionDate TIMESTAMP, site VARCHAR(100), bank VARCHAR(100))' );
                                    
insert overwrite table CapacityUtilizationRetainedTimeWeekly

SELECT s1.pkgretainedtime as avgRetainedTime, s2.pkgretainedtime as overallAvgRetainedTime, s1.targetDay as queryDate, s1.site as site , s1.bank as bank
FROM(
	SELECT avg(cast(pkgretainedtime as BIGINT)/1000) as pkgretainedtime, site, bank, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE successflag = 1 AND version = '1.0.0' AND (eventType = 33 OR eventType = 35) 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
		AND pkgretainedtime IS NOT NULL 
	GROUP BY site, bank ) s1
JOIN (
	SELECT avg(cast(pkgretainedtime as BIGINT)/1000) as pkgretainedtime, ${hiveconf:TARGET_DAY} as targetDay
	FROM SmartLockerTableDaily
	WHERE successflag = 1 AND version = '1.0.0' AND (eventType = 33 OR eventType = 35) 
		AND to_date(eventTime) <= ${hiveconf:TARGET_DAY}
		AND to_date(eventTime) >= ${hiveconf:TARGET_DAY_WEEKAGO}
		AND pkgretainedtime IS NOT NULL 
GROUP BY 1 ) s2;

