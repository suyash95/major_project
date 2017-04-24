(ns web.cluster.core
  (:require [ajax.core :as ajax]
            [clojure.core.async :as async :refer [go >! <! <!! >!! chan close!]]
            [clojure-csv.core :as csv]
            [web.cluster.distance :as distance]))

;(require '[ajax.core :as ajax])
;(require '[clojure.core.async :as async :refer [go >! <! <!! >!! chan close!]])
;(require '[clojure-csv.core :as csv])
;(require '[web.cluster.distance :as distance])


(def cwd "/home/akash/workspace/major_project/")


(defn cluster [data ch]
  (ajax/POST
    "http://localhost:5000/cluster"
    {:params {:key data}
     :format (ajax/json-request-format)
     :response-format (ajax/json-response-format {:keywords? true})
     :handler (fn [data] (go (>! ch data)))}))

(def cluster-count 3)

(def iteration-limit 10)

(defn parse-int [s]
  (Integer. (re-find  #"\d+" s)))


(defn- process-row [row cols]
  (mapv
    (fn [col-pos] (try (parse-int (nth row col-pos))
                       (catch Exception e 0)))
    cols))

;(process-row ["1" "0" "3" "8" "9"] [0 1 2]) => [1 0 3]

(defn prepare-data []
  (let [rows (->> "dataset_diabetes/data-set-dev.csv"
                  (str cwd)
                  slurp
                  csv/parse-csv
                  rest)]
    (mapv
      (fn [row] (process-row row [4 3 9]))
      rows)))

; (prepare-data) => [[1 5 1] [1 15 3] [1 25 2] [0 35 2] [0 45 1] [0 55 3]
;                    [0 65 4] [0 75 5] [1 85 13] [1 95 12] [1 45 9] [0 65 7]]

; An approach to compute things via async channels => brilliant failure
;(time (let [data (prepare-data)
;            data-size (count data)
;            ini-index (repeatedly cluster-count #(rand-int (- data-size 1)))
;            ini-centers (mapv #(nth data %) ini-index)
;            node-chan (repeatedly data-size chan)]
;        (doseq [row data
;                ch node-chan]
;          (go (>! ch (map #(distance/calculate % row)
;                           ini-centers))))
;        (dotimes [i data-size]
;              (let [[v c] (async/alts!! node-chan)]
;                (count v)))
;        "ok"))


(defn find-min-idx [v]
  (second (rest (reduce (fn [[index min-value min-index] x]
                          (if
                            (or (nil? min-value)
                                (< x min-value))
                            [(inc index) x index]
                            [(inc index) min-value min-index]))
                        [0 nil nil]
                        v))))



;(find-min-idx '(50.049975024968795 40.792156108742276 90.6697303403953)) => 1

; label the data according to the nearest distance from center
(defn find-label [data centers]
  (->> data
       (pmap (fn [row] (map #(distance/calculate % row)
                            centers)))
       (map find-min-idx)))

; From the given aggregated data points find out the centroids
(defn- calculate-mean [aggregated-data]
  (mapv
    (fn [{x :x y :y z :z count :count}]
      [(float (/ x count))
       (float (/ y count))
       (float (/ z count))])
    aggregated-data))

; Given an existing labelled data find the new mean
(defn get-centers [labelled-data]
  (calculate-mean (reduce (fn [acc {cluster-pos :label [x y z] :data}]
                            (let [{x-sum :x
                                   y-sum :y
                                   z-sum :z
                                   count :count} (nth acc cluster-pos)]
                              (assoc acc cluster-pos {:x (+ x-sum x)
                                                      :y (+ y-sum y)
                                                      :z (+ z-sum z)
                                                      :count (inc count)})))
                          (vec (repeat 3 {:x 0 :y 0 :z 0 :count 0}))
                          labelled-data)))

; label the data according to given clusters
(defn label-data [centers data]
  (mapv
    (fn [row label] {:label label
                     :data row})
    data
    (find-label data centers)))

; check if algo has converged => that occurs when either the nodes don't
; change their labels or if no of iterations has reached the limit
(defn check-convergence [centres current-labels data iteration-num]
  (let [new-labels (label-data centres data)]
    (if (or (= new-labels current-labels) (< iteration-num iteration-limit))
      new-labels
      false)))

; cluster data all the state management is done here
; an impure function
(defn cluster-data []
  (let [data (prepare-data)
        data-size (count data)
        ini-index (repeatedly cluster-count #(rand-int (- data-size 1)))
        centers (atom [])
        labelled-data (atom [])
        iteration-count (atom 0)]
       (reset! labelled-data (mapv
                               (fn [row] {:data row :label 0})
                               data))
       (reset! centers (mapv #(nth data %) ini-index))
       (when-let [new-labels (check-convergence @centers @labelled-data data @iteration-count)]
         (reset! labelled-data new-labels)
         (reset! centers (get-centers @labelled-data)))
       {:data @labelled-data
        :centers @centers}))



;(ajax/GET "http://localhost:5000/cluster"
;          {:response-format (ajax/json-response-format {:keywords? true})
;           :handler format-data})

