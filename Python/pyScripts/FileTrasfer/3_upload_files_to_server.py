import sftp
import sys
import filecmp
import config

target_dsa_1 = {
  'src' : config.dsaDevPath + '\\DSAManager.js',
  # 'dest' : devHomePath + '/DSAManager.js'
  'dest' : config.serverFilePath + '/DSAManager.js'
}

target_dsa_2 = {
  'src' : config.dsaDevPath + '\\adopter\\dslink.js',
  'dest' : config.serverFilePath + '\\dslink.js'
}

target_dsa_3 = {
  'src' : config.dsaDevPath + '\\PanasonicLockerManager.js',
  'dest' : config.serverFilePath + '\\PanasonicLockerManager.js'
}

sl_webapp_1 = {
  'src' : config.slDevPath + '\\smart-locker.war',
  'dest' : config.slTomcatPath + '/smart-locker.war'
}

sl_webapp_2 = {
  'src' : config.slDevPath + '\\locker-service.war',
  'dest' : config.slTomcatPath + '/locker-service.war'
}

target_spring_demo_webapp = {
  'src' : config.demoWebAppJarPath + '\\logDemo.war',
  'dest' : config.testTomcatPath + 'logDemo.war'
}

spark_perf_app = {
  'src' : config.demoWebAppJarPath + '\\logDemo.war',
  'dest' : config.testTomcatPath + 'logDemo.war'
}


file_list = []

def fileTransfer(host, removeSrc, removeDest):
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
            
            sftpManager.upload(fileMap)

    except IOError, e:
        print("######Oops! %s" % e)
    finally:
        sftpManager.close()


def main():

    host = config.sl_default_server
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
        fileTransfer(host, False, False)

    elif len(sys.argv) == 1:
        # Default, DSAManager
        file_list.append(target_dsa_1)
        fileTransfer(host, False, False)

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


