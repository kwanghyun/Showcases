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
              'src' : 'C:\\Softwares\\SampleCode\\Spark\\sbtApps\\target\\scala-2.10\\dsa_performance_analystic_2.10-1.0.jar',
              'dest' : '/home/scc-dev/software/spark-1.2.1-bin-hadoop2.4/myApps/dsa_performance_analystic_2.10-1.0.jar'
        })



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
	connect.sftp.close()
	connect.transport.close()
	logging.info("Bye")


def uploadFiles(host, file_list):
	try:
		connect(host);
		for fileMap in file_list:
			upload(fileMap)
		logging.info("File transfer completed!!")		

	except IOError, e:
		logging.error("######Oops! %s" % e)
	finally:
		close()


def main():
	

	host = "10.106.8.158"
	uploadFiles(host, file_list);



main()