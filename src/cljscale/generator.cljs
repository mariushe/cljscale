(ns cljscale.generator)

(def note-map {"E" "F" "F" "F#" "F#" "G"
               "G" "G#" "G#" "A" "A" "Bb"
               "Bb" "B" "B" "C" "C" "C#"
               "C#" "D" "D" "D#" "D#" "E"})

(def e-standard ["E" "B" "G" "D" "A" "E"])

(defn create-note [note] {:note note})

(defn generate-frets [res note frets-left]
  (let [next (note-map note)]
    (if (= frets-left 0)
      res
      (recur (conj res (create-note next)) next (dec frets-left)))))

(defn create-generator [fret-count]
  (fn [note] (generate-frets [(create-note note)] note fret-count)))

(defn create-fretboard [tune fret-count]
  (mapv (create-generator fret-count) tune))

