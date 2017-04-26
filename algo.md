# K means clustering

## Algorithm


### Initialization

* Parse the dataset and represent the dataset in a matrix to make further computations easier  
* Define mutable variables to represent the state of algorithm. State consist of the iteration count, the labels   
of dataset and the cluster centres at the completion of the given iteration.
* Initialize the cluster centres.


### Clustering

* At the start of each iteration, check for convergence. Convergence is acheived if either the lables of the    
  nodes is unchanged with respect to the previous iteration or a maximum number of iterations has been reached.
* Next step is __Labelling data__ . Here we have to find out the cluster label for each of the node. For this,   
  we have to calculate the distances of the point from all the n centers and label the node with the cluster nearest   
  to it. In our implementation, this is acheived by performing this operation in a `pmap` , which allows us to   
  parallely calculate the distance of nodes from the centres.
* After labelling the data with the nearest cluster we have to calulate the new centres. In our implementation     
  we ```reduce``` over the data generating a data structure we gives us absolute aggregate sum of the distances for all    
  the dimensions over the dataset. This is done in the ```get-centers``` function.
* After the aggregation, the reduced data is passed on to calculate the position of the new centers.
* At the end, we update the mutable variables to reflect the new labelled representation and centres.
* The iteration then starts again.

### Visualization
* In case the convergence is reached, we pass the labelled represantion of the cluster points to the browser   
  in JSON format.
* In the browser, the clustering data is received in a ```reframe handler``` which updates the app db.
* As the app db changes all the components that have subscribed to that data is refreshed.
* The visualization chart is a react component which contains ```dimple.js``` visualization wrapped in    
  clojurescript.
* During the initialization of the chart component, it creates a chart and does all the initialization and     
  adds the chart element to the DOM. Once the data is received the data is added to the chart and we are able    
  see the visualization in a reactive way.
  
  
  
The clustering algorithm is mostly composed of pure functions (apart from the cluster state management),    
this allows us to be able to design composable architecture. In case of pure functions, we have the guarantee that    
the same input will always produce the same output. This allows us to build highly composable and modular programs.    
One of the major advantage of this is that the computation heavy functions such as ```find-label```, can be 
easily swapped out for a GPU based implementation.
