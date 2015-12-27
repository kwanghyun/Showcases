'''
Created on Apr 21, 2015

@author: kwjang
'''
import os
import shutil
import sys
import filecmp

class FileCopy:

    def __init__(self):
      self.target_list = []


    def copyFile(self, src, dest):        
        try:    
            shutil.copy(src, dest)
        # eg. src and dest are the same file
        except shutil.Error as e:
            print('Error: %s' % e)
        # eg. source or destination doesn't exist
        except IOError as e:
            print('Error: %s' % e.strerror)
        
        print "---------------------Done----------------------"


    def copyFiles(self):
        for entry in self.target_list:
            src = entry['src']
            dest = entry['dest']
            src_exist = os.path.exists(src)
            dest_exist = os.path.exists(dest)
            print "src :: file exist? %s ==> %s " % (src_exist, src)
            print "dest:: file exist? %s ==> %s " % (dest_exist, dest)

            if dest_exist and dest_exist:
                if filecmp.cmp(src, dest) == True:
                    print "[copyFiles()] :: Src and Dest are the same, Skip the file copy"
                    print ""
                else:
                    self.copyFile(entry['src'], entry['dest'])
            else:
                self.copyFile(entry['src'], entry['dest'])

    def setList(self, dict):
        self.target_list.append({
            'src' : dict['src'],
            'dest' : dict['dest']
        })



# obj = FileCopy()
# target1 = {
    
#               'src' : 'C:\Users\kwjang\workspace\KPI\sqls\KPI_Daily_V1.0.sql',
#               'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Daily_V1.0.hiveql'
#         }

# obj.setList(target1)
# print "---------------"
# # print obj.target_list

# print obj.copyFiles()


