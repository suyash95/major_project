import pandas as pd
import numpy as np 
from time import time
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from sklearn import metrics
from sklearn import cross_validation as cv
import math

df = pd.read_csv('./dataset_diabetes/data-set-dev.csv')
#data_dict = df.to_dict(orient='dict')
#data_dict = list(df.T.itertuples())
#print data_dict

'''
for i in range(len(df.race)):
    if math.isdigit(df.race[i]):
        df.drop(df.index[i])


'''

filtered_data = df[df.race != '?']

data = filtered_data.as_matrix(
    columns=[df.columns[2],df.columns[4],df.columns[9]])


print "first data",data


train_data, test_data = cv.train_test_split(data,test_size = 0.20)
train_data = np.array(train_data)
#print train_data
test_data = np.array(test_data)

label = train_data[:,1]
print label

clf=KMeans(n_clusters=5,init='k-means++',precompute_distances='auto',n_jobs=1)
t0=time()
clf.fit(train_data)
#print clf
print "training time:" , round(time()-t0,3), "s"
t1=time()
pred=clf.predict(test_data)
print pred
print "Testing time :" , round(time()-t1,3) ,"s"
labels = clf.labels_
print labels
centroids = clf.cluster_centers_
print "centroids are :",centroids
print("Homogeneity: %0.3f "% metrics.homogeneity_score(label,clf.labels_))
#print("Completeness: %0.3f" % metrics.completeness_score(train_data, clf.labels_))

#print("V-measure: %0.3f" % metrics.v_measure_score(train_data, clf.labels_))

for i in range(3):
    ds = train_data[np.where(labels==i)]
    plt.plot(ds[:,0],ds[:,1],'o')
    lines = plt.plot(centroids[i,0],centroids[i,1],'kx')
    plt.setp(lines,ms=15.0)
    plt.setp(lines,mew=2.0)
plt.show()
