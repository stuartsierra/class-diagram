(defproject com.stuartsierra/class-diagram "0.1.1-SNAPSHOT"
  :description "Generate and display class hierarchy diagrams for Java classes"
  :url "https://github.com/stuartsierra/class-diagram"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [rhizome "0.1.9"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}})
