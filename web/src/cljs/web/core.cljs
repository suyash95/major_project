(ns web.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [ajax.core :as ajax]
            [web.ajax :refer [load-interceptors!]]
            [web.handlers]
            [web.subscriptions]
            [web.plot :as pl])
  (:import goog.History))

(defn navbar []
  [:nav.navbar.navbar-default
   [:div.container
    [:div.navbar-header
     [:a.navbar-brand.page-header.title [:strong "Cluster"]]]]])

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:img {:src (str js/context "/img/warning_clojure.png")}]]]])

(defn home-render []
  [:div
   [:div.row
    [:div.col-md-4.col-md-offset-8.col-sm-offset-8
     [:button.btn.btn-primary
      {:on-click #(rf/dispatch [:get-cluster-data])}
      "Plot Charts"]]]
   [:div.row.marketing
    [:div.col-md-6 {:id "charts-container"}]]])

(defn dimple-component []
  (let [chart (atom nil)
        plot-chart (fn [data]
                     (-> data
                         (pl/new-chart "charts-container")
                         pl/draw-bubble-plot))]
      (r/create-class {:reagent-render (fn []
                                         [:div.col-md-6 {:id "charts-container"}])
                       :component-did-mount (fn [comp]
                                              (reset! chart (-> (r/props comp)
                                                                :data
                                                                plot-chart)))
                       :component-did-update (fn [comp]
                                                (-> (r/props comp)
                                                    :data
                                                    (pl/update-bubble-plot @chart)))})))


(defn dimple-container []
  (let [cluster-data (rf/subscribe [:cluster-data])]
    (fn []
      [dimple-component @cluster-data])))

(defn home-page []
  [:div
   [:div.row
    [:div.col-md-4.col-md-offset-8.col-sm-offset-8
     [:button.btn.btn-primary
      {:on-click #(rf/dispatch [:get-cluster-data])}
      "Plot Charts"]]]
   [:div.row.marketing
    [dimple-container]]])

(def pages
  {:home #'home-page})

(defn page []
  [:div
   [navbar]
   [(pages @(rf/subscribe [:page]))]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (rf/dispatch [:set-active-page :home]))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components)
  (rf/dispatch [:get-cluster-data]))
