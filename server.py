from flask import Flask
from flask import request

import cluster
import json

app = Flask(__name__)

@app.route("/cluster", methods=['POST'])
def cluster():
    if request.method == 'POST' :
        labels, centroids = cluster.start_clustering(
            './dataset_diabetes/data-set-dev.csv')

        res = { 'labels': labels.tolist(), 'centroids': centroids.tolist() }
        return json.dumps(res)

    return "Not handled"

if __name__ == "__main__":
    app.run()
