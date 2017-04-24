(ns web.routes.home
  (:require [web.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [ajax.core :as ajax]
            [web.cluster.core :as cluster])
  (:import (java.util Vector)))

(defn home-page []
  (layout/render "home.html"))

(defn cluster-data []
  (response/ok (cluster/cluster-data)))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/cluster" []
       (cluster-data))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
        (response/header "Content-Type" "text/plain; charset=utf-8"))))
