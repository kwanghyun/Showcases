import socket

my_hostname = socket.gethostname()
my_ip_address = socket.gethostbyname(socket.gethostname())
sl_server = '10.106.8.212'
demo_tomacat_server = '10.106.8.53'
spark_master = '10.106.8.158'


print('========================================================================')
print('Welcome to File Trasfer Menu ('+ my_hostname + '::' + my_ip_address +')') 
print('========================================================================')
print('1. [Copy file] : Copy DSA files to Kiosk UI for SL git repo')
print(' 		@filename => 1_copy_files_dsa.py <int>')
print(' 			- _ : DSAManager.js')
print(' 			- 1 : DSAManager.js')
print(' 			- 2 : DSAManager.js, dslink.js')
print(' 			- 3 : All')
print('2. [Copy file] : Copy KPI files to SL git repo ')
print(' 		@filename => 2_copy_files_kpi.py <int>')
print(' 			- _  : All')
print(' 			- 1 : Daily')
print(' 			- 2 : Weekly')
print(' 			- 3 : Monthly')
print(' 			- 4 : Yearly')
print('3. [Upload] : upload files to target Server')
print(' 		@filename => 3_upload_files_to_server.py <int> [host] [src] [dest]')
print(' 			- _ : Smartlocker UI : DSManager' + sl_server)
print(' 			- 1 : Smartlocker UI : DSManager, dslink to ' + sl_server)
print(' 			- 2 : Smartlocker UI : ALL' + sl_server)
print(' 			- 3 : Smartlocker.war to ' + sl_server)
print(' 			- 4 : locker-service.war to ' + sl_server)
print(' 			- 5 : #1,#2 both to ' + sl_server)
print(' 			- 6 : Spring Demo Webapp ' + demo_tomacat_server)
print(' 			- 7 : Scala performance app upload to ' + spark_master)
print('4. [Download] : download files from target Server')
print(' 		@filename => 4_download_files_from_server.py <int>')
print(' 			- 1 : DSAManager.js')
print(' 			- 2 : DSAManager.js, dslink.js')
print(' 			- 3 : All')





# Describe the all the python scripts here
# But you need to call python stript again to call actual script.
# For example, 
# 1_fileCopyDSA(1,2)