#!/usr/bin/python

import sys
import KpiMeta

# Open database connection
# db = MySQLdb.connect("localhost","root","root","test" ) 
db = KpiMeta.getDatabase()

# prepare a cursor object using cursor() method
cursor = db.cursor()

def truncateTables(targetTables):
    idx = 1

    for table in targetTables :
        print "TABLE[%d]:::: %s" % (idx, table)  
        idx += 1
        # Prepare SQL query to INSERT a record into the database.
        sql = "TRUNCATE TABLE %s" % (table)
             
        try:
            # Execute the SQL command
            cursor.execute(sql)
            # Fetch all the rows in a list of lists.
        except:
            db.rollback()
            print "Error: unable to fecth data %s" % sys.exc_info()[0]
    

def checkKpiTables(targetTables):
    idx = 1
    for table in targetTables :
        
        # Prepare SQL query to INSERT a record into the database.
        sql = "SELECT * FROM %s" % (table)
        
        try:
            # Execute the SQL command
            cursor.execute(sql)
            # Fetch all the rows in a list of lists.a
            results = cursor.fetchall()
            
            field_names = [i[0] for i in cursor.description]
            print field_names
            for row in results:
                print row
            print "No.%d::::TOTAL:::::[%d]ROWS:::TABLE[%s]" %(idx, cursor.rowcount, table)
            print "----------------------------------------------------"
            idx +=1 
        except:
            db.rollback()
            print "Error: unable to fecth data %s" % sys.exc_info()[0]





def start():
    
    print "=============DAILY===============" 
#     truncateTables(KpiMeta.targetTables)
    checkKpiTables(KpiMeta.targetTables)
    print "=============WEEKLY===============" 
#     truncateTables(KpiMeta.targetTablesWeekly)
#     checkKpiTables(KpiMeta.targetTablesWeekly)
    print "=============MONTHLY===============" 
#     truncateTables(KpiMeta.targetTablesMonthly)
#     checkKpiTables(KpiMeta.targetTablesMonthly)
    print "=============YEARLY===============" 
#     truncateTables(KpiMeta.targetTablesYearly)
#     checkKpiTables(KpiMeta.targetTablesYearly)

    KpiMeta.closeConnection(db)

start()