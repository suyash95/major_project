(ns user
  (:require [mount.core :as mount]
            [web.figwheel :refer [start-fw stop-fw cljs]]
            web.core))

(defn start []
  (mount/start-without #'web.core/repl-server))

(defn stop []
  (mount/stop-except #'web.core/repl-server))

(defn restart []
  (stop)
  (start))


