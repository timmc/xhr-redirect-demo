(ns xhr-redirect-demo.server
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]
            [org.httpkit.server :as s]))

(def host
  "Domain name to use. We use lvh.me (points to 127.0.0.1) instead of
localhost because browsers may treat local hosts differently for
CORS."
  "lvh.me")

(def main-page
  (.replace
   (slurp (io/resource "public/index.html") :encoding "UTF-8")
   "${hostname}"
   (String. (.encode (java.util.Base64/getEncoder) (.getBytes host)))))

(def main-js
  (slurp (io/resource "public/main.js") :encoding "UTF-8"))

(defn ui-app
  "An app for UI responses."
  [req]
  (case (:uri req)
    "/" {:status 200
         :headers {"Content-Type" "text/html;charset=utf-8"}
         :body main-page}
    "/main.js" {:status 200
                :headers {"Content-Type" "text/javascript;charset=utf-8"}
                :body main-js}
    {:status 404
     :headers {"Content-Type" "text/plain;charset=utf-8"}
     :body "Page not found"}))

(def calls
  (atom {}))

(defn record-call
  [which req]
  (let [id (:uri req)
        header (first
                (filter #(= (get-in req [:headers %]) "payload")
                        ["accept" "xhr-demo-request"]))
        entry {:which which
               :method (:request-method req)
               :header header}]
    (swap! calls update-in [id] (fnil conj []) entry)
    (println "Called" id "with" entry)))

(defn xhr-app
  [req which]
  (record-call which req)
  (let [resp-json (json/generate-string [which (get @calls (:uri req))])
        hdr-base {"Content-Type" "application/json;charset=utf-8"
                  "Access-Control-Allow-Origin" "*"
                  "Access-Control-Allow-Methods" "GET"
                  "Access-Control-Allow-Headers" "Xhr-Demo-Request"
                  "Access-Control-Expose-Headers" "Xhr-Demo-Response"}]
    (case (:request-method req)
      :options
      {:status 200
       :headers hdr-base
       :body resp-json}

      :get
      (case which
        :first
        {:status 303
         :headers (merge hdr-base
                         {"Location" (str "http://" host ":9202"
                                          (:uri req))})
         :body resp-json}

        :second
        {:status 200
         :headers hdr-base
         :body resp-json}))))

(defn make-xhr-app
  "Given :first or :second, yield an app for the appropriate XHR responder."
  [which]
  (fn closed-xhr-app
    [req]
    ;; Allow reloading with var indirection
    (#'xhr-app req which)))

(defn main
  "Start servers."
  [& args]
  (s/run-server (make-xhr-app :first) {:port 9201})
  (s/run-server (make-xhr-app :second) {:port 9202})
  ;; Allow reloading with var indirection
  (s/run-server #'ui-app {:port 9200})
  (println (str "Visit http://" host ":9200/")))
