from cassandra.cluster import Cluster
from common import KpiUtils
from datetime import datetime

cluster = Cluster(['10.106.9.157', '10.106.9.232','10.104.195.225'],protocol_version = 1,port=9042)
session = cluster.connect("EVENT_KS")


def start():

    deleteRows("2015-2-1 1:30:00", "2.0.0")
    
    cluster.shutdown()

def deleteRows(taret_date, version ):
    
    cql = """SELECT key, "Timestamp", "Version", "correlation_correlationId", payload_description, "payload_eventType"
        FROM com_cisco_bam_smartlocker_kpi
        WHERE "Timestamp" < %s AND "Version" = '%s' ALLOW FILTERING 
    """ % (KpiUtils.unix_time_millis(datetime.strptime(taret_date, '%Y-%m-%d %H:%M:%S')), version)
    
    rows = session.execute(cql)
   
    print "TOTAL ROWS : %d" % len(rows)
    print "--------------Selected keys--------------"
    
    for row in rows:
        print "%s, %s" %(row[0], 
                         datetime.fromtimestamp(int(row[1]/1000)).strftime('%Y-%m-%d %H:%M:%S'))
#         deleteRow(row[0])
    print "-------------------Done-------------------"


def deleteRow(key):
#     cql = """delete from com_cisco_bam_smartlocker_kpi 
#     where "Timestamp" < %s AND  "Version" = '%s'
#     """ % (KpiUtils.unix_time_millis(datetime.strptime("2015-3-28 1:30:00", '%Y-%m-%d %H:%M:%S')),"V2.0.0")
    cql = """delete from com_cisco_bam_smartlocker_kpi 
    where key = '%s' """ % (key)
    print cql
    
    session.execute(cql)
    
    print "delete completed...."

start()