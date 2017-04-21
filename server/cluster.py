import pandas as pd
import numpy as np
from time import time
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from sklearn import metrics
from sklearn import cross_validation as cv
import math

#from file_split import file_split


def data_filtered(df):
    filtered_data = df
    data = filtered_data.as_matrix(columns=[df.columns[14],df.columns[4],df.columns[9]])
    return data

def split_data(data):
    train_data, test_data = cv.train_test_split(data,test_size = 0.20)
    train_data = np.array(train_data)
    #print train_data
    test_data = np.array(test_data)
    return (train_data,test_data)


def getlabels(train_data):
    label = train_data[:,0]
    #print "labels are:"
    #print label
    return label

def kmeans(k, train_data):
    clf=KMeans(n_clusters=k,init='k-means++',precompute_distances='auto',n_jobs=1)
    t0=time()
    clf.fit(train_data)
    #print clf
    print "training time:" , round(time()-t0,3), "s"
    return clf

def prediction(test_data,clf):
	t1=time()
	pred=clf.predict(test_data)
	print pred
	print "Testing time :" , round(time()-t1,3) ,"s"
	labels = clf.labels_
	print labels
	centroids = clf.cluster_centers_
	print "centroids are :",centroids
	return (labels,centroids)

def metrices(label,clf):
	print("Homogeneity: %0.3f "% metrics.homogeneity_score(label,clf.labels_))
	print("Completeness: %0.3f" % metrics.completeness_score(label, clf.labels_))
	print("V-measure: %0.3f" % metrics.v_measure_score(label, clf.labels_))


def visualize(train_data,labels,centroids):
    for i in range(3):
        ds = train_data[np.where(labels==i)]
        plt.plot(ds[:,0],ds[:,1],'o')
        lines = plt.plot(centroids[i,0],centroids[i,1],'kx')
        plt.setp(lines,ms=15.0)
        plt.setp(lines,mew=2.0)
	plt.show()


def start_clustering():
    #print train_data
    df = pd.read_csv('../dataset_diabetes/data-set-dev.csv')
    data = data_filtered(df)

    # train_data,test_data = split_data(data)

    clf=kmeans(2, data)
    #clf= KMeans(4)
    #clf.fit(train_data)
    #kmeanss.k_means(train_data,4)
    #
    # label = getlabels(train_data)
    # labels , centroids = prediction(test_data,clf)
    #print labels
    #metrices(labels,clf)
    #visualize(train_data,labels,centroids)

    clusters = clf.fit(data)
    return (clusters.labels_, clusters.cluster_centers_, data)
