(ns dash.util
  (:require [clojure.string :as string]
            [cljs.reader :as reader]))

(defn name-as-id [some-name]
  "Cleanse a named thing into a usable string id."
  (string/replace some-name #"[^a-zA-Z\d*+!\-;_?]" "_" ))

(defn name-as-kw [some-name]
  "Cleanse and keywordify some-name with the same rules as dash.util/name-as-id."
  (keyword (name-as-id some-name)))

(defn ensure-has-id [some-object]
  "Return an object with an :id of its keywordized :name."
  (let [kw-name (name-as-kw (:name some-object))]
    (if-not (contains? some-object :name) some-object
      (if-not (= (:id some-object kw-name))
        (assoc some-object :id kw-name)
        some-object))))

(defn abbreviate [some-name]
  "Takes the first letter of each word and makes a new word out of it"
  (let [words (string/split (string/trim (string/upper-case some-name)) #"\s+")]
    (str
      (ffirst words)
      (if (= (alength (to-array words)) 1)
        nil
        (first (last words))))))

(defn pluralize [word number]
  "Takes a word and makes it plural, if appropriate"
  (if (> number 1)
    (str word "s")
    word))

(defn uri [root path]
  "Construct a URI given a root and path"
  (str root path))
