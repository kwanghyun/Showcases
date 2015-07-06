#Binary Seach template file
from time import time

def contains(collection, target):
	"""Determine whether collection contain target."""
	return target in collection


def bs_contains(ordered, target):
	"""Use binary array search to determine if target is in collection"""

	low = 0
	high = len(ordered) - 1
	while low <= high:
		mid = (low + high) / 2
		if target == ordered[mid]:
			return True
		elif target < ordered[mid]:
			high = mid - 1
		else :
			low = mid + 1

	return -(low + 1)


def insertInPlace(ordered, target):
	"""Inserts target into it proper location"""
	for i in range(len(ordered)):
		if target < ordered[i]:
			ordered.insert(i, target)

	ordered.append(target)


def insertInPlaceBS(ordered, target):
	"""Inserts target into it proper location"""
	idx = bs_contains(ordered, target)
	if idx < 0:
		ordered.insert(-(idx + 1), target)

	ordered.insert(idx, target)


def performance(mode):
	"""Demonstrate execution performnace of contains """
	n = 1024
	while n < 50000000:
		sorted = range(n)
		now = time()

		#Code whose performance is to be evaludated
		if(mode == 1):
			bs_contains(sorted, -1)
		elif(mode == 2):
			contains(sorted, -1)
		elif(mode == 3):
			insertInPlace(sorted, n + 1)
		else:
			insertInPlaceBS(sorted, n + 1)
		done = time()

		print n, (done - now) * 1000
		n *= 2

# print("-------@Contains@-------")
# performance(1)
# print("-------@Binary Seach@-------")
# performance(2)
# print("-------@Insert@-------")
# performance(3)
# print("-------@Binary Seach Insert@-------")
# performance(4)