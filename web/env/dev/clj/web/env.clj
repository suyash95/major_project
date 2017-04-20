(ns web.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [web.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[web started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[web has shut down successfully]=-"))
   :middleware wrap-dev})
