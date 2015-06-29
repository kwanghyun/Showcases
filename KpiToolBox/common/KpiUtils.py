import time
from datetime import datetime
from datetime import date
from dateutil.relativedelta import relativedelta

def unix_time(dt):
    epoch = datetime.utcfromtimestamp(0)
    delta = dt - epoch
    return delta.total_seconds()

def unix_time_millis(dt):
    return long(unix_time(dt) * 1000)

def getTargetDayByDay(days_ago):    
    today = date.today()
    print "[TODAY]:::: %s" % today  
    
    #targetDay = date.today() + relativedelta(months=-2)
    targetDay = date.today() + relativedelta(days=-days_ago)
    print "[TARGET_DAY]:::: %s" % targetDay
    return targetDay

def strTimeProp(start, end, format, prop):
    """Get a time at a proportion of a range of two formatted times.

    start and end should be strings specifying times formated in the
    given format (strftime-style), giving an interval [start, end].
    prop specifies how a proportion of the interval to be taken after
    start.  The returned time will be in the specified format.
    """

    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))

    ptime = stime + prop * (etime - stime)

    return time.strftime(format, time.localtime(ptime))

def getRandomDate(start, end, prop):
    #return strTimeProp(start, end, '%m/%d/%Y %I:%M %p', prop)
    return strTimeProp(start, end, '%Y-%m-%d %H:%M:%S', prop)

# print unix_time_millis(datetime.datetime(2015, 3, 28, 0, 0))
# getTargetDayByDay(10)
