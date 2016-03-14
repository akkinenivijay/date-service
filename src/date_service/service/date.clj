(ns date-service.service.date 
  (:import (java.time format.DateTimeFormatter format.FormatStyle ZoneId Instant) (java.util Locale))
  (:require [clojure.string :as str]
            [cheshire.core :refer :all :as chesh])
  )

(defn constructLocale [lstr]
  ;; Constructs a Locale from language and country.
  ;; Below are the conditions for different Locale constructors.
  (let [[:as params] (str/split lstr #"_")]
    (cond
      (= (count params) 1)
      (apply (fn [p1] (Locale. p1)) params)
      (= (count params) 2)
      (apply (fn [p1 p2] (Locale. p1 p2)) params)
      (= (count params) 3)
      (apply (fn [p1 p2 p3] (Locale. p1 p2 p3)) params)
      )))

(defn resolveLocale [lstr]
  ;; Return a default Locale if the accept-language string is blank
  (if (str/blank? lstr) (java.util.Locale/getDefault) (constructLocale lstr)))

(defn processAcceptLang [acceptLang]
  ;; Processes Accept-lang header to set the date time format to Locale of the User. 
  ;; Time zone formatting is ignored as a country can have multiple time zones.
  (if (str/blank? acceptLang) 
    (Locale/getDefault)
    (let [[:as lang] 
          (nth (str/split acceptLang  #",") 0)]
      (resolveLocale (str/replace lang  #"-" "_"))
      )
    )
  )

(defn now [acceptLang instantNow]
  (let [timeOfTheDay 
        (-> (DateTimeFormatter/ofLocalizedDateTime FormatStyle/LONG)
            (.withZone (ZoneId/systemDefault))
            (.withLocale (processAcceptLang acceptLang))
            (.format instantNow))]
    {:time_of_day timeOfTheDay}))

(constructLocale "en_US")

(resolveLocale "en_US")

(processAcceptLang "en-US,en;q=0.9,it;q=0.7,es;q=0.5")

(now "en-US,en;q=0.9,it;q=0.7,es;q=0.5" (Instant/now))

(chesh/generate-string {:foo "bar" :baz 5})
