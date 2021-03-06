(ns timesheet.monthly-calculation
  (:require [timesheet.monthly-data :as data]
            [timesheet.hours :as hours]
            [clojure.pprint :as pprint :refer [pprint print-table]]))

(def monthly-data (rest (data/get-monthly-data))) ;; no title row

(defn daily-total [mapped-row]
  (let [start (hours/time->time-in-minutes (:start mapped-row))
       end (hours/time->time-in-minutes (:end mapped-row))
       total-hours (hours/time-interval-in-hours start end)
        total-hours-without-lunch (with-precision 2 (/(bigdec (- total-hours 0.5)) 1))
        saldo (with-precision 2 (/(bigdec (- total-hours-without-lunch 7.5)) 1))]
    (merge {:hours total-hours-without-lunch :saldo saldo} mapped-row)))

(defn entry-row->map [row]
  (conj {} {:date (first row) :start (second row) :end (nth row 2)}))

(defn calculate-one-day [entry]
  (let [mapped-row (entry-row->map entry)]
    (daily-total mapped-row)))

(defn get-monthly-hours-per-day []
  (map calculate-one-day monthly-data))

(defn calculate-monthly-totals []
  (let [month (get-monthly-hours-per-day)
        monthly-total (reduce + (map :hours month))
        monthly-saldo (reduce + (map :saldo month))
        month-with-totals (merge {} {:total-saldo monthly-saldo :total-hours monthly-total})
        summary-line {:date "" :start "" :end "" :hours monthly-total :saldo monthly-saldo}
        table (conj (vec month) summary-line)]
    (println table)
    (println "Calculating...")
    (print-table table)
    month-with-totals))

(defn print-month []
  (let [monthly-total (calculate-monthly-totals)]
    (println "\n*********** Totals ***********")
    (println (str "Monthly total: " (:total-hours monthly-total)))
    (println (str "Saldo: " (:total-saldo monthly-total)))))
