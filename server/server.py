import json
import numpy as np
from flask import Flask
from flask import request
from flask_cors import CORS, cross_origin

import cluster
import client
import timeit

app = Flask(__name__)
CORS(app)


@app.route("/cluster", methods=['POST', 'GET'])
def cluster_handler():
    if request.method == 'POST' :
    	data = request.get_json()
        print 'data is '
        print data

        labels, centroids,dataset = cluster.start_clustering(data['key'])

        res = {
            'labels': labels.tolist(),
            'centroids': centroids.tolist(),
            'data': dataset
        }
        return json.dumps(res)

    else:
        labels, centroids, train_data = cluster.default_clustering()

        print timeit.timeit(stmt = cluster.default_clustering, number = 1)

        res = {
            'labels': labels.tolist(),
            'centroids': centroids.tolist(),
            'data': train_data.tolist()

        }

        return json.dumps(res)

if __name__ == "__main__":
    app.run(threaded = True)
