import json
import numpy as np
from flask import Flask
from flask import request

import cluster
import client

app = Flask(__name__)

@app.route("/cluster", methods=['POST', 'GET'])
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

        labels, centroids, train_data = cluster.start_clustering()

        res = {
            'labels': labels.tolist(),
            'centroids': centroids.tolist(),
            'data': train_data.tolist() }
        return json.dumps(res)

    else:
        labels, centroids, train_data = cluster.start_clustering()

        res = {
            'labels': labels.tolist(),
            'centroids': centroids.tolist(),
            'data': train_data.tolist() }

        return json.dumps(res)

if __name__ == "__main__":
    app.run()
