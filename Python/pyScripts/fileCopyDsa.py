'''
Created on Apr 21, 2015

@author: kwjang
'''
import os
import shutil

sourceFile = 'C:\Softwares\SampleCode\dglogic\dglux_server_0517\www\dglux5\test\adopter\Test.html'
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

''' GENERAL '''
# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\UnitTestTemplate\\UnitTest.html',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\wwebapp\\dsaSimulator\\UnitTestTemplate\\UnitTest.html'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\UnitTestTemplate\\UnitTest.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\webapp\\dsaSimulator\\UnitTestTemplate\\UnitTest.js'
#         })

''' BROWSERFY FILES '''

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanaLockerSimulator.html',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\\smart-locker\\src\\main\\webapp\\dsaSimulator\\PanaLockerSimulator.html'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanasonicLockerSimulator.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\\smart-locker\\src\\main\\webapp\\dsaSimulator\\PanasonicLockerSimulator.js'
#         })


# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\panasonic\\CommandModel.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\panasonic\\CommandModel.js'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\panasonic\\SerialConnection.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\panasonic\\SerialConnection.js'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanasonicLockerManager.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\PanasonicLockerManager.js'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\DSAManager.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\DSAManager.js'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\LockerManager.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\LockerManager.js'
#         })


# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanaLockerManagerUnitTest.html',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\webapp\\dsaSimulator\\PanaLockerManagerUnitTest.html'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanaLockerManagerUnitTest.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\dsa\smart-locker\\src\\main\\webapp\\dsaSimulator\\PanaLockerManagerUnitTest.js'
#         })



''' E-commerce'''
targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanaLockerSimulator.html',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\dsaSimulator\\PanaLockerSimulator.html'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanasonicLockerSimulator.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\dsaSimulator\\PanasonicLockerSimulator.js'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\ServerSimulator.html',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\dsaSimulator\\ServerSimulator.html'
        })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\panasonic\\SerialConnection.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\panasonic\\SerialConnection.js'
#         })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PanasonicLockerManager.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\PanasonicLockerManager.js'
#         })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\DSAManager.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\dsaSimulator\\DSAManager.js'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\DSAManager.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\DSAManager.js'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PrinterManager.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\dsaSimulator\\PrinterManager.js'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\PrinterManager.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\PrinterManager.js'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\adopter\\dslink.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\dsaSimulator\\adopter\\dslink.js'
        })

targetList.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\adopter\\dslink.js',
              'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\dslink.js'
        })

# targetList.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\www\\dglux5\\Test\\LockerManager.js',
#               'dest' : 'C:\\Softwares\\smartlocker\\smartlocker-eCommerce\\smart-locker\\src\\main\\webapp\\kiosk-ui\\js\\lib\\dsa\\LockerManager.js'
#         })


''' NODE JS FILES '''
# targetList.append({
#               'src' : 'C:\Softwares\SampleCode\dglogic\sdk-dslink-javascript\\APP.js',
#               'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\\nodeJS-simulator\\APP.js'
#         })

# targetList.append({
#               'src' : 'C:\Softwares\SampleCode\dglogic\sdk-dslink-javascript\\Utils.js',
#               'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\\nodeJS-simulator\\Utils.js'
#         })

# targetList.append({
#               'src' : 'C:\Softwares\SampleCode\dglogic\sdk-dslink-javascript\\locker_list.json',
#               'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\\nodeJS-simulator\\locker_list.json'
#         })

# targetList.append({
#               'src' : 'C:\Softwares\SampleCode\dglogic\sdk-dslink-javascript\\simulator-config.json',
#               'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\\nodeJS-simulator\\simulator-config.json'
#         })

# targetList.append({
#               'src' : 'C:\Softwares\SampleCode\dglogic\sdk-dslink-javascript\\user-actions.json',
#               'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\\nodeJS-simulator\\user-actions.json'
#         })

# targetList.append({
#               'src' : 'C:\Softwares\SampleCode\dglogic\sdk-dslink-javascript\logger.js',
#               'dest' : 'C:\Softwares\smartlocker\dsa\smart-locker\src\main\webapp\\nodeJS-simulator\logger.js'
#         })

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
        print "src ..%s : exist? %s" % (entry['src'][-70:],  os.path.exists(entry['src']));
        print "dst ..%s : exist? %s" % (entry['dest'][-70:], os.path.exists(entry['dest']));
        copyFile(entry['src'], entry['dest'])



def main():
    copyFiles(targetList)



main()
# copyFile('C:\Users\kwjang\Documents\conns.json', 'C:\Users\kwjang\Desktop\conns.json');


