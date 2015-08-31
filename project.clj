(defproject cljscale "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.48"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [quiescent "0.2.0-alpha1"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler cljscale.handler/app}
  :profiles
  {:dev {:plugins [[lein-cljsbuild "1.1.0"]
                   [lein-figwheel "0.3.7"]]
         :dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]
         :cljsbuild {:builds [{:source-paths ["src"]
                               :figwheel true
                               :compiler {:output-to "target/classes/public/app.js"
                                          :output-dir "target/classes/public/out"
                                          :optimizations :none
                                          :source-map true}}]}}})
