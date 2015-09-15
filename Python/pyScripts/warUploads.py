import paramiko
import datetime
import time
import logging
from easygui import msgbox
import traceback

# logging.basicConfig(filename='log-file.log',level=logging.DEBUG)
logging.basicConfig(level=logging.INFO)
logging.getLogger("paramiko").setLevel(logging.ERROR)
# paramiko.util.log_to_file('logs/paramiko.log')

now = datetime.datetime.now()

file_list = []

file_list.append({
              'src' : 'C:\\Users\\kwjang\\Documents\\workspace-sts-3.6.4.RELEASE\\webapp.demo\\target\\logDemo.war',
              'dest' : '/home/scc-dev/software/apache-tomcat-7.0.64/webapps/logDemo.war'
        })

remove_file_list = []
remove_file_list.append("/home/scc-dev/software/apache-tomcat-7.0.64/webapps/logDemo.war")

remove_directory_list = []
remove_directory_list.append("/home/scc-dev/software/apache-tomcat-7.0.64/webapps/logDemo")

# file_list.append({
#               'src' : 'C:\\Softwares\\smartlocker\\dsa\\smart-locker-jar\\target\\smart-locker.war',
#               'dest' : '/opt/cisco/apache/tomcat/webapps/smart-locker.war'
#         })



# Open a transport
def connect(host):
	port = 22
	connect.transport = paramiko.Transport((host, port))
	logging.info('Connecting to ::::: %s' % host)
	
	# Auth
	username = "scc-dev"
	password = "Cisco_123"
	connect.transport.connect(username = username, password = password)

	# Go!
	connect.sftp = paramiko.SFTPClient.from_transport(connect.transport)

	logging.info("Wow!!! Conected!")


def download(fileMap):
	logging.info("Target file name ::: %s" % fileMap['src'])
	# file['dest'] = 'C:\\Users\\kwjang\\Desktop\\filelog-info_' + now.strftime('%Y%m%d_%H%M%S') + '.log' 
	connect.sftp.get(fileMap['src'], fileMap['dest'])


def remove(paths):
	logging.info("Deleting the File")
	
	for path in paths:
		try:
			logging.info("@@ path => %s" % path)
			connect.sftp.remove(path);
		except IOError, e:
			logging.error("######Oops! %s" % e)
			logging.error(traceback.format_exc())


def removeDir(paths):
	logging.info("Deleting the Directory")
	
	for path in paths:
		try:
			logging.info("@@ path => %s" % path)
			connect.sftp.rmdir(path);
		except IOError, e:
			logging.error("######Oops! %s" % e)
			logging.error(traceback.format_exc())
	

def upload(fileMap):
	connect.sftp.put(fileMap['src'], fileMap['dest'])


def close():
	logging.info("Closing connection.....")
	logging.info("   ")
	connect.sftp.close()
	connect.transport.close()


def uploadFiles(host):
	try:
		connect(host)
		remove(remove_file_list)
		# removeDir(remove_directory_list)
		for fileMap in file_list:
			logging.info(fileMap)
			upload(fileMap)

	except IOError, e:
		logging.error("######Oops! %s" % e)
	finally:
		close()


def main():
	# hosts = ["10.106.8.158", "10.106.8.160"]
	# for host in hosts:
	# 	downloadApplogs(host, file_list1)
	
	host = "10.106.8.16"
	uploadFiles(host)
	msgbox('DONE')

main()