#!/usr/bin/python

from datetime import date
import sys
from common import KpiMeta
from common import KpiUtils

# Open database connection
# db = MySQLdb.connect("localhost","root","root","test" ) 
db = KpiMeta.getDatabase()

# prepare a cursor object using cursor() method
cursor = db.cursor()

def cleanUpTables(targetTables, DELETE_MODE, targetDay):
    idx = 1

    
    for table in targetTables :
        print "TABLE[%d]:::: %s" % (idx, table)  
        idx += 1
        # Prepare SQL query to INSERT a record into the database.
        sql = "SELECT * FROM %s WHERE transactionDate < '%s'" % (table, targetDay )
        
        deleteSql =  """DELETE FROM %s WHERE transactionDate < '%s'""" % (table, targetDay)
     
        try:
            # Execute the SQL command
            cursor.execute(sql)
            # Fetch all the rows in a list of lists.
    
            if DELETE_MODE == "ON":
                print ":::::Total %d row(s) will be deleted..." % cursor.rowcount
            else :
                print ":::::Total %d row(s) is(are) selected..." % cursor.rowcount
            
            if cursor.rowcount > 0:
                results = cursor.fetchall()
                num_fields = len(cursor.description)
                field_names = [i[0] for i in cursor.description]
                
                print "-------------------HEADER-------------------"
                print field_names;
                
                rowList = []
                print "-------------------TARGET ROWS START------------------"
                for row in results:
                    for rowIndex in range(num_fields):
                        if isinstance(row[rowIndex], date):
                            rowList.append(row[rowIndex].strftime('%Y-%m-%d %H:%M:%S'))
                        else:
                            rowList.append(row[rowIndex])
                        
                    # Now print fetched result
                    print rowList
                    rowList = []
                
                print "-------------------TARGET ROWS END------------------"
                
                #Delete the rows
                if DELETE_MODE == "ON":
                    cursor.execute(deleteSql)
                    db.commit()
                    print "Delete completed............"
                
            print "============================"
        except:
            db.rollback()
            print "Error: unable to fecth data %s" % sys.exc_info()[0]
    



def start():
    DELETE_MODE = "OFF"
    TARGET_DAYS = 70
    
    targetDay = KpiUtils.getTargetDayByDay(TARGET_DAYS)
    
    print "[DELETE_MODE]::::%s" % DELETE_MODE
    print "============================" 
    cleanUpTables(KpiMeta.targetTables, DELETE_MODE , targetDay)
#     cleanUpTables(KpiMeta.targetTablesWeekly, DELETE_MODE , targetDay)
#     cleanUpTables(KpiMeta.targetTablesMonthly, DELETE_MODE , targetDay)
#     cleanUpTables(KpiMeta.targetTablesYearly, DELETE_MODE , targetDay)
    
    KpiMeta.closeConnection(db)

start()