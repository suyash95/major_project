from flask import Flask
from flask import request
import numpy as np

import cluster
import json
from ast import literal_eval	

app = Flask(__name__)

@app.route("/cluster", methods=['POST'])
def cluster_handler():	
    if request.method == 'POST' :
    	#print request.form
    	train_data = request.form.getlist('key')
    	#print train_data
    	list1 = []
    	i=0
    	l=[]
    	for element in train_data:
            if i<3:
                l.append(element)
            else:
                data = np.array(l)
                list1.append(data)
                i=0
                l=[]
                l.append(element)
    		
            i+=1
        data = np.array(l)    
        list1.append(data)
        
        data1 = np.array(list1)
        print data1

    	#body = request.get_json()
    	#print 'Body', body
    	#train_data = request.form
    	#print "train_data is:",train_data
    	#test_data = request.data1
    	#train_data = [item for value in train_data for item in literal_eval(value)]
    	
    	#print train_data

        labels, centroids = cluster.start_clustering(data1)

        res = { 'labels': labels.tolist(), 'centroids': centroids.tolist() }
        return json.dumps(res)

    return "Not handled"

if __name__ == "__main__":
    app.run()