import sftp
import sys
import filecmp
import config

target_dsa_1 = {
  'src' : config.serverFilePath + '/DSAManager.js',
  # 'dest' : devHomePath + '/DSAManager.js'
  'dest' : config.temp_download_dir + '\\DSAManager.js'
}

target_dsa_2 = {
  'src' : config.serverFilePath + '/adopter/dslink.js',
  'dest' : config.temp_download_dir + '\\dslink.js'
}

target_dsa_3 = {
  'src' : config.serverFilePath + '/PanasonicLockerManager.js',
  'dest' : config.temp_download_dir + '\\PanasonicLockerManager.js'
}


file_list = []

def file_download(host, removeSrc, removeDest):
    # print("[fileTransfer() ...." + host)
    # print(file_list)

    sftpManager = sftp.SftpManager()

    if host is not None: 
        sftpManager.setHost(host);

    try:
        sftpManager.connect();
        for fileMap in file_list:
            
            if removeSrc == True:
                sftpManager.remove(fileMap['src'])
            
            if removeDest == True:
                sftpManager.remove(fileMap['dest'])
            
            sftpManager.download(fileMap)

    except IOError, e:
        print("######Oops! %s" % e)
    finally:
        sftpManager.close()


def printFileCmpResult(src, dest):

    print "[FILE CMP] :: src => " + src
    print "[FILE CMP] :: dest=> " + dest 

    if filecmp.cmp(src, dest) == True:
        print "[FILE CMP] :: Result => TRUE"
    else:
        print "[FILE CMP] :: Result => FALSE"


def main():

    host = sl_default_server
    fileMap = {}
    if len(sys.argv) > 5:
        print "Invalid arguments"
        quit()
    elif len(sys.argv) >= 3:
        # Host param
        host = sys.argv[2];

    elif len(sys.argv) >= 5:
        # Src param
        fileMap['src'] = sys.argv[3];
        # Dest param
        fileMap['dest'] = sys.argv[4];
        file_list.append(fileMap)
        file_download(host, False, False)

    elif len(sys.argv) == 1:
        # Default, DSAManager
        file_list.append(target_dsa_1)
        file_download(host, False, False)
        printFileCmpResult(config.dsaDevPath + '\\DSAManager.js' , config.temp_download_dir + '\\DSAManager.js')
        printFileCmpResult(config.dsaDevPath + '\\dslink.js' , config.temp_download_dir + '\\adopter\\dslink.js')
    elif sys.argv[1] == "1":
        # DSAManager, dslink 
        file_list.append(target_dsa_1)
        file_list.append(target_dsa_2)
        fileTransfer(host, False, False)
    elif sys.argv[1] == "2":
        file_list.append(target_dsa_1)
        file_list.append(target_dsa_2)
        file_list.append(target_dsa_3)
        fileTransfer(host, False, False)
    elif sys.argv[1] == "3":
        file_list.append(sl_webapp_1)
        fileTransfer(host, False, True)
    elif sys.argv[1] == "4":
        file_list.append(sl_webapp_2)
        fileTransfer(host, False, True)
    elif sys.argv[1] == "5":
        file_list.append(sl_webapp_1)
        file_list.append(sl_webapp_2)
        fileTransfer(host, False, True)
    elif sys.argv[1] == "6":
        host = tomcat_default_server
        file_list.append(target_spring_demo_webapp)
        fileTransfer(host, False, True)
    elif sys.argv[1] == "7":
        file_list.append(spark_perf_app)
        fileTransfer(host, False, True)

    else:
        print "Sorry! No Match Found"



if __name__ == "__main__":
    main()


