(ns xhr-redirect-demo.main
  "AOT compilation barrier, see redirect-demo.server for real main ns."
  (:gen-class))

(defn -main
  "Chain to xhr-redirect-demo.server/main"
  [& args]
  (require 'xhr-redirect-demo.server)
  (apply (resolve 'xhr-redirect-demo.server/main) args))
