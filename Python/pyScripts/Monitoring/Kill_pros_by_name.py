import subprocess, os, signal

p = subprocess.Popen(['ps', '-A'], stdout=subprocess.PIPE)

out, err = p.communicate()

for line in out.splitlines():
    if 'consul' in line:
        pid = int(line.split(None, 1)[0])
        print "@@%s" % line
        os.kill(pid, signal.SIGKILL)

