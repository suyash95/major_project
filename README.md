# clustering and stuff

## Introduction

Project is divided into client and server, each has to run separately. Server
performs the act of clustering and client is mostly responsible for
visualization but we don't know what the future holds.

### Server

We currently have a flask server running on port 5000. For now, we have
a GET endpoint which returns the representation of cluster.
The process of chunking of data and parallelizing it is not finalized yet.

### Client

Client is a clojurescript web application with Immutant server. It uses
reagent and re-frame for components and state management. For data visualization
we have chosen the simple yet glorious dimple.js (based on the mighty d3).


## Getting Started

```sh
git clone https://github.com/suyash95/major_project
```

### Starting Server

This project uses python 2.7, so make sure you have that.
To check version,
```sh
python --version
```

When everything looks right, go ahead

```sh
cd server
pip install -r requirements.txt
python server.py
```

### Starting client

First, Install [leiningen](https://leiningen.org/#install).

Then,
```sh
# change to web directory of this project
cd web

# run the server
lein run
```

Finally, in a separate terminal
```sh
# change to web directory of this project
cd web

# run figwheel
lein figwheel
```


Now, you should be able to go over to http://localhost:3000 to see the
visualization. If not, consider raising an issue.


