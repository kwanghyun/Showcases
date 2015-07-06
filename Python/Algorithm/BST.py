#Binary Seach tree implementation
from time import time
import random

class BinaryNode:
	def __init__(self, value = None):
		"""Create binary node"""
		self.value = value
		self.left = None
		self.right = None

	def add(self, val):
		"""Adds a new node"""
		if val <= self.value:
			if self.left:
				self.left.add(val)
			else:
				self.left = BinaryNode(val)
		else:
			if self.right:
				self.right.add(val)
			else:
				self.right = BinaryNode(val)

	def delete(self):
		if self.left == self.right == None:
			return None
		if self.left == None:
			return self.right
		if self.right == None:
			return self.left

		"""Find maximum value of left tree"""
		child = self.left
		grandChild = child.right
		if grandChild:
			while grandchild.right:
				child = grandchild
				grandchild = child.right
			self.value = grandchild.value
			child.right = grandchild.left
		else:
			self.left = child.left
			self.value = child.value

		return self


class BinaryTree:
	def __init__(self):
		"""Create empty binary tree"""
		self.root = None

	def add(self, value):
		"""Insert value"""
		if self.root is None:
			self.root = BinaryNode(value)
		else:
			self.root.add(value)

	def contains(self, target):
		"""Check value is in tree"""

		node = self.root
		while node:
			if target == node.value:
				return True

			if target < node.value:
				node = node.left
			else:
				node = node.right

		return False

	def remove(self, value):
		if self.root:
			self.root = self.removeFromParent(self.root, value)

	def removeFromParent(self, parent, value):
		if parent is None:
			return None

		if value == parent.value:
			return parent.delete()
		elif value < parent.value:
			parent.left = self.removeFromParent(parent.left, value)
		else:
			parent.right = self.removeFromParent(parent.right, value)

		return parent;

	def __iter__(self):
		"""In order traversal of elements in the tree"""
		if self.root:
			return self.root.inorder()



def test():
	bt = BinaryTree()
	bt.add(5)
	print bt.contains(5)
	print bt.contains(10)
	print bt.contains(1)
	bt.add(1)
	bt.add(10)
	print bt.contains(5)
	print bt.contains(10)
	print bt.contains(1)
	print("------@remove 10 @------")
	bt.remove(10)
	print bt.contains(10)

	print("------@remove 5 @------")
	bt.remove(5)
	print bt.contains(5)

def performance():
	"""Demonstrate execution performnace of contains """
	n = 1024
	while n < 65536:
		bt = BinaryTree()
		for i in range(n):
			bt.add(random.randint(1,n))

		now = time()
		result = bt.contains(random.randint(1,n))
		print n, result, (time() - now) * 1000
		n *= 2

# test()
# print("------------")
# performance()
# print("------------")

