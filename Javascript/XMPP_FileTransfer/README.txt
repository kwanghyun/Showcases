This sample code is work fine but not throughly test as I can say it product level code, we've martured code with our project devlopment but the code is quite coupled with our project, so sharing this simple code may be easier to understand and integrate with your apps.

Here are remaining Issues in this sample code. (But we've fixed these in our solution.)
1. You must chuck a part of the file and then send it respectively for sending bigger than 4MBfile (Sample code are read whole file and then chunking, it works fine with smaller than 4MB file)
2. Only tested with Firefox, Chrome.

Please refer CAXL_FileTransfer.xslx for Test usage and Source code work flow.
