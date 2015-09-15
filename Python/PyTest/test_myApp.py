import myApp
import pytest
import shutil
import os

class TestDoubleit(object):

	original_file = 'num.txt'
	copied_file_forTest = 'num_copied.txt'

	def setup_class(self):
		shutil.copy(TestDoubleit.original_file, TestDoubleit.copied_file_forTest)

	def teardown_class(self):
		os.remove(TestDoubleit.copied_file_forTest)

	def test_doublelines(self):
		myApp.doublelines(TestDoubleit.copied_file_forTest)

		origin_valus = [ int(line) for line in open(TestDoubleit.original_file)]
		copied_values = [ int(line) for line in open(TestDoubleit.copied_file_forTest)]


		for v1, v2 in zip(origin_valus, copied_values):
			assert int(v2) == int(v1) * 2

	def test_doubleit_value(self):
		assert myApp.doubleit(10) == 20

	def test_doubleit_tpye(self):
		with pytest.raises(TypeError):
			myApp.doubleit('hello')