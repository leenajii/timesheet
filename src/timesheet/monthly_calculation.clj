(ns timesheet.monthly-calculation
  (:require [timesheet.monthly-data :as data]
            [timesheet.hours :as hours]))

(def monthly-data (rest (data/get-monthly-data))) ;; no title row

(defn daily-total [mapped-row]
  (let [start (hours/time->time-in-minutes (:start mapped-row))
       end (hours/time->time-in-minutes (:end mapped-row))
       total-hours (hours/time-interval-in-hours start end)
        total-hours-without-lunch (- total-hours 0.5)]
    (merge {:hours total-hours-without-lunch} mapped-row)))

(defn entry-row->map [row]
  (conj {} {:date (first row) :start (second row) :end (nth row 2)}))

(defn calculate-one-day [entry]
  (let [mapped-row (entry-row->map entry)]
    (daily-total mapped-row)))

(defn print-month []
  (map calculate-one-day monthly-data))
