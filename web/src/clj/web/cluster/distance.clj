(ns web.cluster.distance
  (:require [clojure.math.numeric-tower :as math]
            [clojure.core.async :refer [go <!]]))

(defn- sqr
  "Uses the numeric tower expt to square a number"
  [x]
  (math/expt x 2))

(defn euclidean-squared-distance
  "Computes the Euclidean squared distance between two sequences"
  [a b]
  (reduce + (map (comp sqr -) a b)))

(defn calculate
  "Computes the Euclidean distance between two sequences"
  [a b]
  (math/sqrt (euclidean-squared-distance a b)))
