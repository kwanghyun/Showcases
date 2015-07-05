import paramiko
import datetime
import time
import logging

# logging.basicConfig(filename='log-file.log',level=logging.DEBUG)
logging.basicConfig(level=logging.INFO)
logging.getLogger("paramiko").setLevel(logging.ERROR)
# paramiko.util.log_to_file('logs/paramiko.log')

now = datetime.datetime.now()

file_list = []

file_list.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\\www\\dglux5\\Test\\panasonic\\SerialConnection.js',
              'dest' : '/opt/cisco/apache/tomcat/webapps/smart-locker/kiosk-ui/js/lib/dsa/panasonic/SerialConnection.js'
        })

# file_list.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\\www\\dglux5\\Test\\panasonic\\CommandModel.js',
#               'dest' : '/opt/cisco/apache/tomcat/webapps/smart-locker/kiosk-ui/js/lib/dsa/panasonic/CommandModel.js'
#         })

# file_list.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\\www\\dglux5\\Test\\PanasonicLockerManager.js',
#               'dest' : '/opt/cisco/apache/tomcat/webapps/smart-locker/kiosk-ui/js/lib/dsa/PanasonicLockerManager.js'
#         })

# file_list.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\\www\\dglux5\\Test\\LockerManager.js',
#               'dest' : '/opt/cisco/apache/tomcat/webapps/smart-locker/kiosk-ui/js/lib/dsa/LockerManager.js'
#         })

# file_list.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\dglux_server_0517\\www\\dglux5\\Test\\DSAManager.js',
#               'dest' : '/opt/cisco/apache/tomcat/webapps/smart-locker/kiosk-ui/js/lib/dsa/DSAManager.js'
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


def remove(path):
	logging.info("Deleting the srouce file")
	connect.sftp.remove(path);


def upload(fileMap):
	connect.sftp.put(fileMap['src'], fileMap['dest'])


def close():
	logging.info("Closing connection.....")
	logging.info("   ")
	connect.sftp.close()
	connect.transport.close()


def uploadFiles(host, file_list):
	try:
		connect(host);
		for fileMap in file_list:
			upload(fileMap)

	except IOError, e:
		logging.error("######Oops! %s" % e)
	finally:
		close()


def main():
	# hosts = ["10.106.8.158", "10.106.8.160"]
	# for host in hosts:
	# 	downloadApplogs(host, file_list1)
	
	host = "10.106.8.93"
	uploadFiles(host, file_list);


main()