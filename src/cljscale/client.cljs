(ns cljscale.client
  (:require [quiescent.core :as q]
            [quiescent.dom :as d]
            [cljscale.generator :as g]))

(enable-console-print!)

(def fretboard (atom []))

(declare FretBoard)

(defn render []
  (q/render (FretBoard @fretboard)
            (.getElementById js/document "main")))

(q/defcomponent Fret [fret]
  (apply
   d/div
   {:className (str
                (when (:root fret) "root ")
                (if (:in-scale fret) "in-scale " "not-in-scale ")
                "fret")}
   (:note fret)))

(q/defcomponent String [frets]
  (apply d/div {:className "string"} (mapv Fret frets)))

(q/defcomponent FretBoard [fretboard]
  (apply d/div {:className "fretboard"} (mapv String fretboard)))

(defn start []
  (swap! fretboard (fn [_] (g/create-fretboard g/e-standard 12)))
  (swap! fretboard (fn [_] (g/add-scale @fretboard "phrygian" "E")))
  (println fretboard)
  (render))

(start)
