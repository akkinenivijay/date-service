(ns date-service.service.date 
  (:import (java.time format.DateTimeFormatter format.FormatStyle ZoneId Instant) (java.util Locale))
  )

(def formatter (.withZone (DateTimeFormatter/ofLocalizedDateTime FormatStyle/LONG) (ZoneId/systemDefault)))

(defn current-date-time [] {:time_of_day (.format formatter (Instant/now))})

(defn constructLocale [lstr]
  ;; Constructs a Locale from language and country.
  ;; Below are the conditions for different Locale constructors.
  (let [[:as params] (clojure.string/split lstr #"_")]
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
  (println "resolveLocale" lstr) 
  (if (clojure.string/blank? lstr) (java.util.Locale/getDefault) (constructLocale lstr))
  )

(defn processAcceptLang [acceptLang]
  ;; Processes Accept-lang header to set the date time format to Locale of the User. 
  ;; Time zone formatting is ignored as a country can have multiple time zones.
  (if (clojure.string/blank? acceptLang) 
    (Locale/getDefault)
    (let [[:as lang] 
          (nth (clojure.string/split acceptLang  #",") 0)]
      (println "lang" lang)
      (resolveLocale (clojure.string/replace lang  #"-" "_"))
      )
    )
  )


(resolveLocale "en_US")

(processAcceptLang "en-US ,en;q=0.8 ,te;q=0.6")

(current-date-time)
