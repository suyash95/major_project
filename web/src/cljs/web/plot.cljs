(ns web.plot
  (:require [cljsjs.dimple]))

(def test-data #js  [#js {:name "pt1"
                          :x 12
                          :y 17
                          :z 22}
                     #js {:name "pt2"
                          :x 21
                          :y 22
                          :z 28}
                     #js {:name "pt3"
                          :x 7
                          :y 54
                          :z 99}])

(defn add-measure-axis [chart axis label]
  (.addMeasureAxis chart axis label)
  chart)

(defn add-series [chart name chart-type]
  (.addSeries chart name chart-type)
  chart)

(defn draw-chart [chart]
  (.draw chart)
  chart)

(defn bubble-plot [container-id]
  (let [chart (js/dimple.chart. (.newSvg js/dimple (str "#" container-id 800 600) test-data))]
       (-> chart
           (add-measure-axis "x" "x")
           (add-measure-axis "y" "y")
           (add-measure-axis "z" "z")
           (add-series "name" (.. js/dimple -plot -bubble))
           draw-chart)))
