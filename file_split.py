import numpy as np

def file_split(train_data, n):
    list1 =[]
    print "AAaaaaaaaa"
    list1=np.array_split(train_data,n)
    for i in range(4):
        print list1[i]
        print "\n"
    return list1