#!/usr/bin/python
import random
import sys
from common import KpiUtils
from common import KpiMeta


# Open database connection
# db = MySQLdb.connect("localhost","root","root","test" ) 
db = KpiMeta.getDatabase()

# prepare a cursor object using cursor() method
cursor = db.cursor()


def insertData():   
    retainTime = random.randint(1000,9999)
    overallAvgRetainedTime = random.randint(1000,9999)
#     tradsactionDate = randomDate("1/1/2015 1:30 PM", "3/1/2015 4:50 AM", random.random())
    tradsactionDate = KpiUtils.getRandomDate("2015-1-1 1:30:00", "2015-3-28 1:30:00", random.random())
    site = 'site' + str(random.randint(1,5))
    bank = 'bank' + str(random.randint(1,2))
    
    print "retainTime::: %d" % retainTime
    print "overallAvgRetainedTime::: %d" % overallAvgRetainedTime
    print "tradsactionDate::: %s" % tradsactionDate
    
    # Prepare SQL query to INSERT a record into the database.
    sql = """INSERT INTO kpi_capacity_utilization_retained_period_vs_all(avgRetainedTime,
             overallAvgRetainedTime, transactionDate, site, bank)
             VALUES (%d, %d, '%s', '%s', '%s' )""" % (retainTime, overallAvgRetainedTime, tradsactionDate, site, bank)
    
    print sql
    
    try:
        # Execute the SQL command
#         cursor.execute(sql)
        # Commit your changes in the database
#         db.commit()
        print "Insert completed"
    except:
        # Rollback in case there is any error
        db.rollback()
        print "Insert failed, Reason : %s" % sys.exc_info()[0]
    

def start(iteration):
    for index in range(iteration):
        insertData()
    
    KpiMeta.closeConnection(db)
    
start(98)
    
    
    