(ns web.subscriptions
  (:require [re-frame.core :refer [reg-sub subscribe]]))

(reg-sub
  :page
  (fn [db _]
    (:page db)))

(reg-sub
  :cluster-info
  (fn [db _]
    (:cluster db)))


(defn transform-cluster-data-py [cluster]
  (mapv
    (fn [[x y z]] {:name (str "name" x y z)
                   :x (int x)
                   :y y
                   :z z})
    (:data cluster)))

(defn transform-cluster-data [cluster]
  (mapv
    (fn [{label :label [x y z] :data}] {:name (str "name" x y z)
                                        :x (int x)
                                        :y y
                                        :z z})
    (:data cluster)))

(reg-sub
  :cluster-data
  (fn [_ _] (subscribe [:cluster-info]))
  (fn [cluster _] {:data (transform-cluster-data cluster)}))
