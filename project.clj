(defproject xhr-redirect-demo "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.1.19"]
                 [cheshire "5.5.0"]]
  :aot [xhr-redirect-demo.main]
  :main xhr-redirect-demo.main)
