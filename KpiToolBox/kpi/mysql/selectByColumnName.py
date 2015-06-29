import sys
import KpiMeta

# Open database connection
# db = MySQLdb.connect("localhost","root","root","test" ) 
db = KpiMeta.getDatabase()
cursor = db.cursor()
    
def findTables(list, column):
    
    print "Total : %d " % len(list) 
    inList = []
    notInList = []
    
    for table in list :
#         print "TABLE[%d]:::: %s" % (idx, table)  

        # Prepare SQL query to INSERT a record into the database.
        sql = "SELECT * FROM %s" % (table )
        
        try:
            # Execute the SQL command
            cursor.execute(sql)        
            field_names = [i[0] for i in cursor.description]
            
            found = "FALSE"
            for field in field_names:
                if field == column:
                    found = "TRUE"
                    break;
                
            if found == "TRUE":
                inList.append(table)
            else:
                notInList.append(table)
            
        except:
#             db.rollback()
            print "Error: unable to fecth data %s" % sys.exc_info()[0]

    print "-------------IN-------------"
    print printList(inList)
    print "------------NOT------------"
    print printList(notInList)

def printList(list):
    idx = 1
    for item in list:
        print "No.%d [%s]" % (idx, item)
        idx +=1

def start():
    searchColumn = "transactionDate"
    findTables(KpiMeta.targetTables, searchColumn)
    print "==================="
    findTables(KpiMeta.targetTablesWeekly, searchColumn)
    print "==================="
    findTables(KpiMeta.targetTablesMonthly, searchColumn)
    print "==================="
    findTables(KpiMeta.targetTablesYearly, searchColumn)
    print "==================="
    
    KpiMeta.closeConnection(db)
    

start()
    