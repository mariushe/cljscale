(ns cljscale.client
  (:require [quiescent.core :as q]
            [quiescent.dom :as d]
            [cljscale.generator :as g]))

(enable-console-print!)

(def fretboard (atom []))

(def settings (atom {:root ""
                     :scale ""}))

(declare FretBoard)

(declare View)

(defn render []
  (q/render
   (View @fretboard)
   (.getElementById js/document "main")))

(defn load []
  (swap! fretboard (fn [_] (g/create-fretboard g/e-standard 24)))
  (when (not (= (:root @settings) ""))
    (swap! fretboard (fn [_] (g/add-root @fretboard (:root @settings)))))
  (when (not (= (:scale @settings) ""))
    (swap! fretboard (fn [_] (g/add-scale
                              @fretboard
                              (:scale @settings)
                              (:root @settings)))))
  (println @fretboard)
  (render))

(q/defcomponent RoundSpan []
  (apply d/span {:className "round-span"} ""))

(q/defcomponent NoteSpan [note]
  (apply d/span {:className "note"} (:note note)))

(q/defcomponent Fret [fret]
  (apply
   d/li
   {:className (str
                (when (:root fret) "root ")
                (if (:in-scale fret) "in-scale " "not-in-scale ")
                "fret")}
   ;;(when (:root fret) (RoundSpan))
   ;;(NoteSpan fret)
   (:note fret)
   ;;""
   ))

(q/defcomponent String [frets]
  (apply d/ul {:className "string"} (mapv Fret frets)))

(q/defcomponent FretBoard [fretboard]
  (d/div {:className "row"}
         (apply d/div {:className "fretboard col-sm-10 col-sm-offset-1"} (mapv String fretboard))))

(q/defcomponent Option [root]
  (d/option {:value root} root))

(q/defcomponent Root []
  (apply d/select
         {:className "testing"
          :onChange (fn [root]
                      (println "TEst")
                      (swap! settings assoc :root (.-value (.-target root)))
                      (load))}
         (map Option (conj g/notes ""))))

(q/defcomponent Scale []
  (apply d/select
         {:className "testing"
          :onChange (fn [root]
                      (println "TEst2")
                      (swap! settings assoc :scale (.-value (.-target root)))
                      (load))}
         (map Option (keys g/scales))))

(q/defcomponent View [fretboard] (d/div {}
                                        (FretBoard fretboard)
                                        (Root)
                                        (Scale)))

(load)
