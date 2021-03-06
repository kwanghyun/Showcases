'''
Created on Apr 21, 2015

@author: kwjang
'''
import os
import shutil

sourceFile = 'C:\Softwares\SampleCode\dglogic\locker_demo\www\dglux5\test\adopter\Test.html'
destFile = 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\dsaSimulator\Test.html'

'''
os.path.isabs(path)
Return True if path is an absolute pathname. On Unix, that means it begins with a slash, 
on Windows that it begins with a (back)slash after chopping off a potential drive letter.

os.path.join(path, *paths)
On Windows, the drive letter is not reset when an absolute path component (e.g., r'\foo') is encountered. 
If a component contains a drive letter, all previous components are thrown away and the drive letter is reset. 
Note that since there is a current directory for each drive, os.path.join("c:", "foo") represents a path relative 
to the current directory on drive C: (c:foo), not c:\foo.
'''
def fileCopy():
    assert not os.path.isabs(sourceFile)
    dstdir =  os.path.join(destFile, os.path.dirname(sourceFile))
    
    os.makedirs(dstdir) # create all directories, raise an error if it already exists
    shutil.copy(sourceFile, dstdir)

targetList = []
targetList.append({
              'src' : 'C:\Softwares\SampleCode\dglogic\locker_demo\www\dglux5\Test\\adopter\IECAdopterSimluator.js',
              'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\dsaSimulator\\adopter\IECAdopterSimluator.js'
        })

targetList.append({
              'src' : 'C:\Softwares\SampleCode\dglogic\locker_demo\www\dglux5\Test\\adopter\IECAdopter.js',
              'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\dsaSimulator\\adopter\IECAdopter.js'
        })

targetList.append({
              'src' : 'C:\Softwares\SampleCode\dglogic\locker_demo\www\dglux5\Test\\adopter\AdopterCore.js',
              'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\dsaSimulator\\adopter\AdopterCore.js'
        })

targetList.append({
              'src' : 'C:\Softwares\SampleCode\dglogic\locker_demo\www\dglux5\Test\\adopter\dslink.js',
              'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\dsaSimulator\\adopter\dslink.js'
        })


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
        print "source %s::::: file exist? %s" % (entry['src'],  os.path.exists(entry['src']));
        print "destination %s::::: file exist? %s" % (entry['dest'], os.path.exists(entry['dest']));
#         copyFile(entry['src'], entry['dest'])



def main():
    copyFiles(targetList)



main()
# copyFile('C:\Users\kwjang\Documents\conns.json', 'C:\Users\kwjang\Desktop\conns.json');


