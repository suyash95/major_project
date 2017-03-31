import numpy as np
import pandas as pd
import server
import cluster
import requests

def file_split(n):
	list1 = []
	df = pd.read_csv('./dataset_diabetes/data-set.csv')
	data = cluster.data_filtered(df)
	train_data,test_data = cluster.split_data(data)
	list1=np.array_split(train_data,n)
	return (list1,test_data)
    
    
if __name__ == '__main__':
	list1,test_data = file_split(4)
	print list1[0]
	r = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[0]})
	print r.text

	r1 = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[1]})
	print r1.text
	
	r2 = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[2]}) 
	print r2.text
	
	r3 = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[3]})
	print r3.text

