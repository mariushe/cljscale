(ns cljscale.client
  (:require [quiescent.core :as q]
            [quiescent.dom :as d]
            [cljscale.generator :as g]))

(enable-console-print!)

(def fretboard (atom [[{:note "E"}{:note "F"}{:note "F#"}{:note "G"}{:note "G#"}]
                      [{:note "E"}{:note "F"}{:note "F#"}{:note "G"}{:note "G#"}]]))

(declare FretBoard)

(defn render []
  (q/render (FretBoard @fretboard)
            (.getElementById js/document "main")))

(q/defcomponent Fret [fret]
  (apply d/div {:className "fret"} (:note fret)))

(q/defcomponent String [frets]
  (apply d/div {:className "string"} (mapv Fret frets)))

(q/defcomponent FretBoard [fretboard]
  (apply d/div {:className "fretboard"} (mapv String fretboard)))

(defn start []
  (swap! fretboard (fn [_] (g/create-fretboard g/e-standard 12)))
  (render))

(start)
