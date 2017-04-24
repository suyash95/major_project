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
[reagent](https://reagent-project.github.io/index.html) and
[re-frame](https://github.com/Day8/re-frame) for components and state management.
For data visualization we have chosen the simple yet glorious
[dimple.js](http://dimplejs.org) (based on the mighty [d3](https://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0ahUKEwjZn_CK-LXTAhUGLVAKHfI1B5gQFgglMAA&url=https%3A%2F%2Fd3js.org%2F&usg=AFQjCNEngFpGFxW0ZaZmI3pS4-txJ6rydg&sig2=AruESeEWqSdZZeN4EAbXaA)).


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


