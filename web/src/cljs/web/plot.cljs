(ns web.plot
  (:require [cljsjs.dimple]))

(defn add-measure-axis [chart axis label]
  (.addMeasureAxis chart axis label)
  chart)

(defn add-series [chart name chart-type]
  (.addSeries chart name chart-type)
  chart)

(defn draw-chart [chart]
  (.draw chart)
  chart)

(defn add-storyboard [chart field cb]
  (.setStoryboard chart field cb)
  chart)

(defn new-chart [data container-id]
  (js/dimple.chart.
    (.newSvg js/dimple (str "#" container-id) 800 600)
    (clj->js data)))

(defn draw-bubble-plot [chart]
  (-> chart
      (add-measure-axis "x" "x")
      (add-measure-axis "y" "y")
      (add-measure-axis "z" "z")
      (add-series "name" (.. js/dimple -plot -bubble))
      draw-chart))

(defn update-bubble-plot [data chart]
  (set! (.-data chart) (clj->js data))
  (draw-chart chart))

