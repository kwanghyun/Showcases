import sys
import fileCopy

target1 = {
    
              'src' : 'C:\Users\kwjang\workspace\KPI\sqls\KPI_Daily_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Daily_V1.0.hiveql'
        }
target2 = {
              'src' : 'C:\Users\kwjang\workspace\KPI\sqls\KPI_Weekly_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Weekly_V1.0.hiveql'
        }
target3 = {
              'src' : 'C:\Users\kwjang\workspace\KPI\sqls\KPI_Monthly_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Monthly_V1.0.hiveql'
        }
target4 = {
              'src' : 'C:\Users\kwjang\workspace\KPI\sqls\KPI_Yearly_V1.0.sql',
              'dest' : 'C:\Softwares\smartlocker\smartlocker-common\kpi\kpi-toolbox\\analytics\KPI_Yearly_V1.0.hiveql'
        }


def main():
    
    fObj = fileCopy.FileCopy()

    if len(sys.argv) > 2:
        print "Invalid arguments"
        quit()
    elif len(sys.argv) == 1:
        fObj.setList(target1)
        fObj.setList(target2)
        fObj.setList(target3)
        fObj.setList(target4)
        fObj.copyFiles()

    elif sys.argv[1] == "1":
        fObj.setList(target1)
        fObj.copyFiles()
    elif sys.argv[1] == "2":
        fObj.setList(target2)
        fObj.copyFiles()
    elif sys.argv[1] == "3":
        fObj.setList(target3)
        fObj.copyFiles()
    elif sys.argv[1] == "4":
        fObj.setList(target4)
        fObj.copyFiles()
    else:
        print "Sorry! No Match Found"


    # print "---------DONE--------"
    # print fObj.target_list



if __name__ == "__main__":
    main()




