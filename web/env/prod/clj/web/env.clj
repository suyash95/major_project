(ns web.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[web started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[web has shut down successfully]=-"))
   :middleware identity})
