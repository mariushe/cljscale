(ns cljscale.generator)

(def note-map {"E" "F" "F" "F#" "F#" "G"
               "G" "G#" "G#" "A" "A" "A#"
               "A#" "B" "B" "C" "C" "C#"
               "C#" "D" "D" "D#" "D#" "E"})

(def notes ["E" "F" "F#" "G" "G#"])

(def e-standard ["E" "B" "G" "D" "A" "E"])

(def scales {"" '(0 1 2 3 4 5 6 7 8 9 10 11)
             "pentatonic-minor" '(0 3 5 7 10)
             "phrygian" '(0 1 3 5 7 8 10)})


(defn find-note [note rest]
  (if (= rest 0)
    note
    (recur (note-map note) (dec rest))))

(defn create-scale-generator [root]
  (fn [steps] (find-note root steps)))

(defn find-scale [scale root]
  (map (create-scale-generator root) (scales scale)))

(defn add-scale-to-string [string scale]
  (mapv (fn [note]
          (println scale)
          (-> note
              (assoc :in-scale (not (not-any? #(= (note :note) %) scale)))))
        string))

(defn add-scale [fretboard scale root]
  (mapv (fn [string]
          (add-scale-to-string
           string
           (find-scale scale root)))
        fretboard))

(defn mark-if-root [note root]
  (assoc note :root  (= (note :note) root)))

(defn add-root [fretboard root]
  (mapv (fn [string]
          (mapv (fn [note] (mark-if-root note root)) string))
        fretboard))

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

