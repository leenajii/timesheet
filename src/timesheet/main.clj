(ns timesheet.main
  (:gen-class)
  (:require [clojure.tools.trace :refer [trace]]
            [clojure.tools.logging :refer [info error]]
            [timesheet.employees :as employees])
  (:import [java.lang Runtime Thread]))

(defn run []
  (employees/calculate-monthly-salaries))

(defn -main []
  (try
    (run)
    (catch Exception e
      (let [error-message (str " Message: "(.getMessage e))]
        (error e error-message)))))


