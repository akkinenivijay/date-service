(ns date-service.service.date 
  (:import (java.time format.DateTimeFormatter format.FormatStyle ZoneId Instant) (java.util Locale))
  (:require [clojure.string :as str])
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
  (if (str/blank? lstr) (java.util.Locale/getDefault) (constructLocale lstr))
  )

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

(defn now [acceptLang]
  ;; Returns the current time in a format compatible with the user's Locale.
  (let [
        locale (processAcceptLang acceptLang)
        formattr (DateTimeFormatter/ofLocalizedDateTime FormatStyle/LONG)
        formattrWithZone (.withZone formattr (ZoneId/systemDefault))
        formattrWithLocale (.withLocale formattrWithZone locale)
        ]
    {:time_of_day (.format formattrWithLocale (Instant/now))}
       )
)

(defn now2 [acceptLang]
  (let [formatterWithLocale 
        (-> (DateTimeFormatter/ofLocalizedDateTime FormatStyle/LONG)
            (.withZone (ZoneId/systemDefault))
            (.withLocale (processAcceptLang acceptLang))
            )]
    (println formatterWithLocale)
    {:time_of_day (.format formatterWithLocale (Instant/now))}
    )
  )

(constructLocale "en_US")

(resolveLocale "en_US")

(processAcceptLang "en-US,en;q=0.9,it;q=0.7,es;q=0.5")

(now2 "en-US,en;q=0.9,it;q=0.7,es;q=0.5")
