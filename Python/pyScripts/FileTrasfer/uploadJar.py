import paramiko
import logging

# logging.basicConfig(filename='log-file.log',level=logging.DEBUG)
logging.basicConfig(level=logging.INFO)
logging.getLogger("paramiko").setLevel(logging.ERROR)


class SftpManager:
    username = "scc-dev"
    password = "Cisco_123"
    port = 22
    host = ""
    transport = None
    sftp = None

    def __init__(self, host):
        self.host = host

    def setCredential(self, usr, pwd):
        self.username = usr
        self.password = pwd

    def setHost(self, host):
        self.host = host

    def setPort(self, port):
        self.port = port

    def connect(self):
        logging.info("connect()......%s " % self.host)
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
        self.sftp.get(fileMap['src'], fileMap['dest'])

    def downloads(self, file_list):
        for fileMap in file_list:
            self.download(fileMap)

    def remove(self, path):
        logging.info("[Delete]Deleting a file => " + path)
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
        self.sftp.close()
        self.transport.close()
        logging.info("DONE - BYE")


if __name__ == "__main__":
    # execute only if run as a script
    logging.info("Running as script")
    sftp = SftpManager("10.106.8.80")
    fileMap = {
        'src': '/Users/jangkwanghyun/Cisco/pme/spark-analytics/loadSimulatorApp/target/loadSimulator-1.0.0-SNAPSHOT.jar',
        'dest': '/home/scc-dev/softwares/loadSimulator/loadSimulator-1.0.0-SNAPSHOT.jar'
    }
    sftp.connect()
    sftp.upload(fileMap)
    sftp.close()

