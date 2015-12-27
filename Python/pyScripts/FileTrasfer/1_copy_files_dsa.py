'''
Created on Apr 21, 2015

@author: kwjang
'''
import sys
import fileCopy
import config

target1 = {
          'src' : config.dsaDevPath + '\\DSAManager.js',
          'dest' : config.eCmrBranchPath + '\\DSAManager.js'
        }

target2 = {
          'src' : config.dsaDevPath + '\\DSAManager.js',
          'dest' : config.eCmrSimulatorPath + '\\DSAManager.js'
        }

target3 = {
          'src' : config.dsaDevPath + '\\adopter\\dslink.js',
          'dest' : config.eCmrBranchPath + '\\dslink.js'
        }

target4 = {
          'src' : config.dsaDevPath + '\\adopter\\dslink.js',
          'dest' : config.eCmrSimulatorPath + '\\dslink.js'
        }


target5 = {
            'src' : config.dsaDevPath + '\\panasonic\\SerialConnection.js',
            'dest' : config.eCmrBranchPath + '\\panasonic\\SerialConnection.js'
        }

target6 = {
            'src' : config.dsaDevPath + '\\panasonic\\SerialConnection.js',
            'dest' : config.eCmrSimulatorPath + '\\panasonic\\SerialConnection.js'
        }

target7 = {
            'src' : config.dsaDevPath + '\\PanasonicLockerManager.js',
            'dest' : config.eCmrBranchPath + '\\PanasonicLockerManager.js'
        }

target8 = {
            'src' : config.dsaDevPath + '\\PanasonicLockerManager.js',
            'dest' : config.eCmrSimulatorPath + '\\PanasonicLockerManager.js'
        }

target9 = {
              'src' : config.dsaDevPath + '\\LockerManager.js',
              'dest' : config.eCmrBranchPath + '\\LockerManager.js'
        }

target10 = {
              'src' : config.dsaDevPath + '\\LockerManager.js',
              'dest' : config.eCmrSimulatorPath + '\\LockerManager.js'
        }

target11 = {
              'src' : config.dsaDevPath + '\\PrinterManager.js',
              'dest' : config.eCmrBranchPath + '\\PrinterManager.js'
        }

target12 = {
              'src' : config.dsaDevPath + '\\PrinterManager.js',
              'dest' : config.eCmrSimulatorPath + '\\PrinterManager.js'
        }


def main():
    
    fObj = fileCopy.FileCopy()

    if len(sys.argv) > 2:
        print "Invalid arguments"
        quit()
    elif len(sys.argv) == 1:
        fObj.setList(target1)
        fObj.setList(target2)
        fObj.copyFiles()

    elif sys.argv[1] == "1":
        fObj.setList(target1)
        fObj.setList(target2)
        fObj.copyFiles()
    elif sys.argv[1] == "2":
        fObj.setList(target1)
        fObj.setList(target2)
        fObj.setList(target3)
        fObj.setList(target4)
        fObj.copyFiles()
    elif sys.argv[1] == "3":
        fObj.setList(target1)
        fObj.setList(target2)
        fObj.setList(target3)
        fObj.setList(target4)
        fObj.copyFiles()
    else:
        print "Sorry! No Match Found"


    # print "---------DONE--------"
    # print fObj.target_list



if __name__ == "__main__":
    main()
