import paramiko
# import datetime
# import time
import logging

# logging.basicConfig(filename='log-file.log',level=logging.DEBUG)
logging.basicConfig(level=logging.INFO)
logging.getLogger("paramiko").setLevel(logging.ERROR)
# paramiko.util.log_to_file('logs/paramiko.log')

class SftpManager:
	def __init__(self):
		self.username = "scc-dev"
		self.password = "Cisco_123"
		self.port = 22
		self.host = ""
		self.transport = None
		self.sftp = None


	def setCredential(self, usr, pwd):
		self.username = usr
		self.password = pwd


	def setHost(self, host):
		self.host = host


	def setPort(self, port):
		self.port = port


	def connect(self):
		logging.info("connect()......" + self.host)
		self.transport = paramiko.Transport((self.host, self.port))
		# logging.info('host ::::: %s' % self.host)
		# logging.info('port ::::: %s' % self.port)
		
		# Auth
		self.transport.connect(username=self.username, password=self.password)

		# Go!
		self.sftp = paramiko.SFTPClient.from_transport(self.transport)

		logging.info("Wow!!! Conected!")


	def download(self, fileMap):
		logging.info("[Download] src ::: %s" % fileMap['src'])
		logging.info("[Download] dest::: %s" % fileMap['dest'])
		# file['dest'] = 'C:\\Users\\kwjang\\Desktop\\filelog-info_' + now.strftime('%Y%m%d_%H%M%S') + '.log' 
		self.sftp.get(fileMap['src'], fileMap['dest'])


	def downloads(self, file_list):
		for fileMap in file_list:
			self.download(fileMap)


	def remove(self, path):
		logging.info("Deleting a file => " + path)
		self.sftp.remove(path)


	def upload(self, fileMap):
		logging.info("[Upload] src ::: %s" % fileMap['src'])
		logging.info("[Upload] dest::: %s" % fileMap['dest'])

		self.sftp.put(fileMap['src'], fileMap['dest'])


	def uploads(self, file_list):
		for fileMap in file_list:
			self.upload(fileMap)


	def close(self):
		logging.info("Closing connection.....")
		logging.info("   ")
		self.sftp.close()
		self.transport.close()


if __name__ == "__main__":
    main()

	# def downloadBrokerPerfStat(host, file_list):
	# 	try:
	# 		connect(host);
	# 		for fileMap in file_list:
	# 			download(fileMap)
	# 			remove(fileMap['src']);

	# 	except IOError, e:
	# 		logging.error("######Oops! %s" % e)
	# 	finally:
	# 		close()


	# def downloadApplogs(host, file_list):
	# 	try:
	# 		connect(host);
	# 		for fileMap in file_list:
	# 			fileMap['dest'] = 'C:\\Users\\kwjang\\Desktop\\logs\\filelog-info_' + host + '.log' 
	# 			download(fileMap)
	# 			remove(fileMap['src']);

	# 	except IOError, e:
	# 		logging.error("######Oops! %s" % e)
	# 	finally:
	# 		close()



# hosts = ["10.106.8.158", "10.106.8.160"]
# hosts = ["10.106.8.158"]
# file_list = []
# file_list.append({
#       'src' : '/home/scc-dev/softwares/monitorTools/memory.log',
#       'dest' : 'C:\\Users\\kwjang\\Desktop\\logs\\memory.log'
# })
# 
# sm = SftpManager()
# 
# for host in hosts:
# 	try:
# 		sm.connect(hosts[0]);
# 		for fileMap in file_list:
# 			sm.download(fileMap)
# 			# sm.remove(fileMap['src']);
# 
# 	except IOError, e:
# 		logging.error("######Oops! %s" % e)
# 	finally:
# 		sm.close()



