(ns web.handlers
  (:require [web.db :as db]
            [re-frame.core :refer [dispatch reg-event-db reg-event-fx]]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(reg-event-db
  :set-active-page
  (fn [db [_ page]]
    (assoc db :page page)))

(reg-event-fx
  :get-cluster-data
  (fn [_]
    {:http-xhrio {:method          :get
                  :uri             "http://localhost:5000/cluster"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:set-cluster]
                  :on-failure [:error-handler]}}))

(reg-event-db
  :set-cluster
  (fn [db [_ cluster]]
    (println "setting cluster")
    (assoc db :cluster cluster)))

(reg-event-db
  :error-handler
  (fn [_ [_ error]]
    (println error)))
