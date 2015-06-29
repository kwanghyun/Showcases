'''
Created on Apr 21, 2015

@author: kwjang
'''
import os
import shutil
import sys

# C:\Users\kwjang\Desktop\images\
# C:\Users\kwjang\workspace\KPI\kpi-toolbox\\analystics2\
target1 = {
              'src' : 'C:\Softwares\smartlocker\smartlocker-common\smart-locker\dbscripts\kpi2\KPI_Daily_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Daily_V1.0.hiveql'
        }
target2 = {
              'src' : 'C:\Softwares\smartlocker\smartlocker-common\smart-locker\dbscripts\kpi2\KPI_Weekly_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Weekly_V1.0.hiveql'
        }
target3 = {
              'src' : 'C:\Softwares\smartlocker\smartlocker-common\smart-locker\dbscripts\kpi2\KPI_Monthly_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Monthly_V1.0.hiveql'
        }
target4 = {
              'src' : 'C:\Softwares\smartlocker\smartlocker-common\smart-locker\dbscripts\kpi2\KPI_Yearly_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Yearly_V1.0.hiveql'
        }

targetList = []

def copyFile(src, dest):
    
    try:    
        shutil.copy(src, dest)
    # eg. src and dest are the same file
    except shutil.Error as e:
        print('Error: %s' % e)
    # eg. source or destination doesn't exist
    except IOError as e:
        print('Error: %s' % e.strerror)
    
    print "---------------------Done----------------------"


def copyFiles(list):
    for entry in list:
        print "source %s::::: file exist? %s" % (entry['src'],  os.path.exists(entry['src']))
        print "destination %s::::: file exist? %s" % (entry['dest'], os.path.exists(entry['dest']))
        copyFile(entry['src'], entry['dest'])



def main():
    isFirstArg = True
    for arg in sys.argv:
        print "Argument [%s]"  % arg
        if isFirstArg == True :
            print "Argument [%s]"  % arg
            isFirstArg = False
        elif arg == "all":
            targetList.append(target1)
            targetList.append(target2)
            targetList.append(target3)
            targetList.append(target4)
        elif arg == "d":
            targetList.append(target1)
        elif arg == "w":
            targetList.append(target2)
        elif arg == "m":
            targetList.append(target3)
        elif arg == "y":
            targetList.append(target4)
        else:
            print "Invalid argument [%s]"  % arg
            quit()
            
    copyFiles(targetList)



main()



