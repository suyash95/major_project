import numpy as np
import pandas as pd
import server
import cluster
import grequests

def file_split(n):
	list1 = []
	df = pd.read_csv('./dataset_diabetes/data-set.csv')
	data = cluster.data_filtered(df)
	train_data,test_data = cluster.split_data(data)
	list1=np.array_split(train_data,n)
	return (list1,test_data)
    
    
if __name__ == '__main__':
	list1,test_data = file_split(4)
	
	def exception_handler(request, exception):
		print "Request failed"

	reqs = [
	grequests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[0]}),
	grequests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[1]}),
	grequests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[2]}),
	grequests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[3]})
	
	]
	r=grequests.map(reqs, exception_handler=exception_handler)
	for i in r:
		print i.text


	#r = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[0]})
	
	#print r.text

	
	#r1 = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[1]})
	#print r1.text
	
	
	#r2 = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[2]}) 
	#print r2.text
	
	#r3 = requests.post('http://127.0.0.1:5000/cluster',data = {'key': list1[3]})
	#print r3.text

