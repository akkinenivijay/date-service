(defproject date-service "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [org.clojure/core.async "0.2.374"]
                 [io.reactivex/rxclojure "1.0.0"]
                 [co.paralleluniverse/pulsar "0.7.4"]
                 [cheshire "5.5.0"]

                 ;; Remove this line and uncomment one of the next lines to
                 ;; use Immutant or Tomcat instead of Jetty:
                 [io.pedestal/pedestal.jetty "0.4.1"]
                 ;; [io.pedestal/pedestal.immutant "0.4.1"]
                 ;; [io.pedestal/pedestal.tomcat "0.4.1"]

                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.12"]
                 [org.slf4j/jcl-over-slf4j "1.7.12"]
                 [org.slf4j/log4j-over-slf4j "1.7.12"]]
  :bootclasspath true
  :min-lein-version "2.0.0"
  :java-agents [[co.paralleluniverse/quasar-core "0.7.4"]]
  :resource-paths ["config", "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "date-service.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.4.1"]]}
             :uberjar {:aot [date-service.server]}}
  :main ^{:skip-aot true} date-service.server)

