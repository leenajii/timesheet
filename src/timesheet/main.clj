(ns timesheet.main
  (:gen-class)
  (:require [clojure.tools.trace :refer [trace]]
            [clojure.tools.logging :refer [info error]]
            [timesheet.monthly-calculation :as calc])
  (:import [java.lang Runtime Thread]))

(defn run []
  (calc/print-month))

(defn -main []
  (try
    (run)
    (catch Exception e
      (let [error-message (str " Message: "(.getMessage e))]
        (error e error-message)))))


