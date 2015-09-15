# Binary Search Tree Implementation

import pickle
import json

# ############################### Pickle

# myList = range(1,1000)

# with open('data.txt', 'w') as file:
#     pickle.dump(myList, file)

# with open('data.txt') as file:
#     unpickledList = pickle.load(file)

# for token in unpickledList:
#     print token

# ############################### JSON

# jdata={}
# jdata['key1'] = 'val1'
# jdata['key2'] = 'val2'
# jdata['key3'] = 'val3'

# with open('jdata.json', 'w') as file:
#     json.dump(jdata, file, indent=4, separators=(',', ':'))

# with open('jdata.json') as file:
#     loadedJson = json.load(file)


# print loadedJson


# ############################### Yaml

import yaml 

mydic = {'a' : 1, 'b': 2, 'c': 3}
mylist = [1,2,3,4,5]
mytuple = ('x', 'y', 'z')

# loaded_yaml = yaml.dump(mydic, default_flow_style=False)
# print loaded_yaml

# print yaml.dump(mylist, default_flow_style=False)

# print yaml.dump(mytuple, default_flow_style=False)

with open('test.yaml', 'w') as file:
    yaml.dump(mydic, file)

with open('test.yaml') as file:
    struct = yaml.load(file)

print json.dumps(struct, indent=4, separators=(',',': '))
