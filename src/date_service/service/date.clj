(ns date-service.service.date 
  (:import (java.time format.DateTimeFormatter format.FormatStyle ZoneId Instant) (java.util Locale))
  )

(def mock-map {:key "mock"})

(def formatter (.withZone (DateTimeFormatter/ofLocalizedDateTime FormatStyle/LONG) (ZoneId/systemDefault)))

(defn current-date-time [] {:time_of_day (.format formatter (Instant/now))})

(current-date-time)

(defn constructLocale [lstr]
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
  (println lstr) 
  (if (clojure.string/blank? lstr) (java.util.Locale/getDefault) (constructLocale lstr))
  )

(resolveLocale "en_US")
