(ns timesheet.monthly-calculation
  (:require [timesheet.monthly-data :as data]))

(def monthly-data (rest (data/get-monthly-data))) ;; no title row
