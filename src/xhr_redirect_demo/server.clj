(ns xhr-redirect-demo.server
  (:require [org.httpkit.server :as s]))

(defn ui-app
  [req]
  {:status 200
   :headers {"Content-Type" "text/plain;charset=utf-8"}
   :body "hello HTTP!"})

(defn make-xhr-app
  [which]
  (fn xhr-app
    [req]
    {:status 200
     :headers {"Content-Type" "text/plain;charset=utf-8"}
     :body (name which)}))

(defn main
  [& args]
  (s/run-server (make-xhr-app :first) {:port 9201})
  (s/run-server (make-xhr-app :second) {:port 9202})
  (s/run-server ui-app {:port 9200}))
