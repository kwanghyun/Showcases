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
entire_zip = []

file_list.append({
              'src' : 'C:\\Softwares\\SampleCode\\dglogic\\sdk-dslink-javascript\\App.js',
              'dest' : '/home/scc-dev/software/sdk-dslink-javascript/App.js'
        })

# entire_zip.append({
#               'src' : 'C:\\Softwares\\SampleCode\\dglogic\\sdk-dslink-javascript.zip',
#               'dest' : '/home/scc-dev/software/sdk-dslink-javascript.zip'
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
	
	# APP File only
	hosts = ["10.106.8.158", "10.106.8.160", "10.106.8.80", "10.106.9.188", "10.106.9.157", "10.106.9.232", "10.106.8.162"]
	for host in hosts:
		uploadFiles(host, file_list);

	# Entire zip 
	# host = "10.106.8.158"
	# uploadFiles(host, entire_zip);



main()