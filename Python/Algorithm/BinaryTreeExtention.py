from BST import BinaryTree

def balancedTree(ordered):
	"""Create balanced binary tree from ordered collection"""
	bt = BinaryTree()
	addRange(bt, ordered, 0, len(ordered) - 1)

	return bt

def addRange(bt, ordered, low, high):
	"""Add range to the bt in way that Bt remains balanced"""
	if low <= high:
		mid = (low + high) / 2

		bt.add(ordered[mid])
		addRange(bt, ordered, low, mid - 1)
		addRange(bt, ordered, mid + 1, high)

def test():
	x = range(10)
	bt = balancedTree(x)
	print bt.root.value
	print bt.root.left.value
	print bt.root.right.value

# test()