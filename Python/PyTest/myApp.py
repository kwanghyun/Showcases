import sys

def doubleit(x):
	
	if not isinstance(x, (int, float)):
		raise TypeError
	returnValue = x * 2
	return returnValue

def doublelines(filename):
	with open(filename) as file:
		newList = [ str(doubleit(int(value))) for value in file]
	with open(filename, 'w') as file:
		file.write('\n'.join(newList))


if __name__ == '__main__':
	input_value = sys.argv[1]
	doubled_value = doubleit(input_value)

	print "the value of {0} is {1}".format(input_value, doubled_value)