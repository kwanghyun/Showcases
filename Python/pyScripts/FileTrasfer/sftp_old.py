import paramiko
import datetime
import time
import logging

# logging.basicConfig(filename='log-file.log',level=logging.DEBUG)
logging.basicConfig(level=logging.INFO)
logging.getLogger("paramiko").setLevel(logging.ERROR)
# paramiko.util.log_to_file('logs/paramiko.log')

now = datetime.datetime.now()

file_list1 = []
file_list2 = []
broker_stat_file_list = []
upload_list = []

file_list1.append({
              'src' : '/home/scc-dev/software/sdk-dslink-javascript/filelog-info.log',
              'dest' : ''
        })

file_list2.append({
              'src' : '/home/scc-dev/software/sdk-dslink-javascript/filelog-info.log',
              'dest' : ''
        })


broker_stat_file_list.append({
              'src' : '/home/scc-dev/softwares/monitorTools/cpu.log',
              'dest' : 'C:\\Users\\kwjang\\Desktop\\logs\\cpu.log'
        })

broker_stat_file_list.append({
              'src' : '/home/scc-dev/softwares/monitorTools/memory.log',
              'dest' : 'C:\\Users\\kwjang\\Desktop\\logs\\memory.log'
        })

broker_stat_file_list.append({
              'src' : '/home/scc-dev/softwares/monitorTools/disk.log',
              'dest' : 'C:\\Users\\kwjang\\Desktop\\logs\\disk.log'
        })

upload_list.append({
	    'src' : '/home/scc-dev/software/sdk-dslink-javascript/filelog-info.log',
        'dest' : ''
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


def downloadBrokerPerfStat(host, file_list):
	try:
		connect(host);
		for fileMap in file_list:
			download(fileMap)
			remove(fileMap['src']);

	except IOError, e:
		logging.error("######Oops! %s" % e)
	finally:
		close()


def downloadApplogs(host, file_list):
	try:
		connect(host);
		for fileMap in file_list:
			fileMap['dest'] = 'C:\\Users\\kwjang\\Desktop\\logs\\filelog-info_' + host + '.log' 
			download(fileMap)
			remove(fileMap['src']);

	except IOError, e:
		logging.error("######Oops! %s" % e)
	finally:
		close()


def upload(file_list):
	for file in file_list:	
		file['src'] = 'C:\\Users\\kwjang\\Desktop\\logs\\filelog-info_' + host +'.log'
		file['dest'] = '/home/scc-dev/software/sdk-dslink-javascript/filelog-info.log'
		
		connect.sftp.put(file['src'], file['dest'])



def close():
	logging.info("Closing connection.....")
	logging.info("   ")
	connect.sftp.close()
	connect.transport.close()


def main():
	hosts = ["10.106.8.158", "10.106.8.160"]
	for host in hosts:
		downloadApplogs(host, file_list1)
	brokerHost = "10.106.8.159"

	downloadBrokerPerfStat(brokerHost, broker_stat_file_list);


main()