import random
import sys
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


correlationId = random.randint(100000,999999)
tempDate = KpiUtils.getRandomDate("2015-1-1 1:30:00", "2015-3-28 1:30:00", random.random())
tradsactionDate = KpiUtils.unix_time_millis(datetime.strptime(tempDate, '%Y-%m-%d %H:%M:%S'))
event = random.choice(eventList)

print correlationId
print tradsactionDate
print event.event_description
print event.eventType