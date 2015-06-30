from cassandra.cluster import Cluster
import random
from common import KpiUtils
from datetime import datetime

class Event:
    def __init__(self, event_description, eventType):
        self.event_description = event_description
        self.eventType = eventType
        
eventList = []
eventList.append(Event("EMPLOYEE_LOGIN", 30))
eventList.append(Event("EMPLOYEE_LOGOUT", 31))
eventList.append(Event("EMPLOYEE_DEPOSIT", 32))
eventList.append(Event("EMPLOYEE_RETRIEVAL", 33))
eventList.append(Event("CUSTOMER_DEPOSIT", 34))
eventList.append(Event("CUSTOMER_RETRIEVAL", 35))



cluster = Cluster(['10.106.9.157', '10.106.9.232','10.104.195.225'],protocol_version = 1,port=9042)
session = cluster.connect("EVENT_KS")

def start():


    insertBulkData(100)
#     deleteRow()
#     selectRows()
#     printAll()
    
    cluster.shutdown()
    
    
def insertBulkData(iteration):
    for i in range(iteration):
        insertData()


def insertData():   
    correlationId = random.randint(100000,999999)
    tempDate = KpiUtils.getRandomDate("2015-1-1 1:30:00", "2015-3-28 1:30:00", random.random())
    tradsactionDate = KpiUtils.unix_time_millis(datetime.strptime(tempDate, '%Y-%m-%d %H:%M:%S'))
    event = random.choice(eventList)
    key ="%d:::%d" %(tradsactionDate, random.randint(0,999999)) 

    cql = """
    insert into com_cisco_bam_smartlocker_kpi 
    (key, "Timestamp", "Version", "correlation_correlationId", payload_description, "payload_eventType") 
    values 
    ('%s', %s, '%s', '%s', '%s', %s) 
    """ % (key, tradsactionDate, "2.0.0",correlationId, event.event_description, event.eventType)
    print cql
    
    session.execute(cql)
    
    print "insert completed..."
    

def printAll():
    rows = session.execute("SELECT * FROM com_cisco_bam_smartlocker_kpi")   
    print "TOTAL ROWS : %d" % len(rows)
    
    for row in rows:
        print(row)
        
        
start()
