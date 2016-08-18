(ns timesheet.monthly-calculation
  (:require [timesheet.monthly-data :as data]))

(def monthly-data (rest (data/get-monthly-data))) ;; no title row

(defn entry-row->map [row]
  (conj {} {:date (first row) :start (second row) :end (nth row 2)}))

(defn calculate-one-day [entry]
  (let [mapped-row (entry-row->map entry)]
    mapped-row))
