(ns com.stuartsierra.class-diagram
  "Generate class hierarchy diagrams for Java classes."
  (:require [rhizome.dot :as dot]
            [rhizome.viz :as r]))

(defn- interface? [class]
  (.isInterface class))

(defn- abstract? [class]
  (java.lang.reflect.Modifier/isAbstract (.getModifiers class)))

(defn- in-package [package-name class]
  (.startsWith (.getName class) package-name))

(def ^:private node-descriptor-base
  {:fontname "Helvetica"})

(def ^:private java-descriptor
  {:fillcolor "lightgrey" :style "filled"})

(def ^:private interface-descriptor
  {:shape "ellipse" :color "black"})

(def ^:private abstract-descriptor
  {:shape "trapezium" :color "blue"})

(def ^:private concrete-descriptor
  {:shape "box" :color "blue"})

(defn- node-descriptor [class]
  (merge node-descriptor-base
         {:label (.getSimpleName class)}
         (when (in-package "java." class)
           java-descriptor)
         (cond (interface? class) interface-descriptor
               (abstract? class) abstract-descriptor
               :else concrete-descriptor)))

(def ^:private class-extends-descriptor
  {:color "blue" :arrowhead "empty" :weight 10})

(def ^:private interface-extends-descriptor
  {:color "black" :arrowhead "empty"})

(def ^:private implements-descriptor
  {:color "black" :arrowhead "open"})

(defn- edge-descriptor [from to]
  (if (interface? to)
    (if (interface? from)
      interface-extends-descriptor
      implements-descriptor)
    class-extends-descriptor))

(def ^:private graph-options
  {:rankdir "BT"
   :size "10,10"})

(defn dot
  "Returns string of GraphViz dot instructions to draw a class
  hierarchy diagram of the classes."
  [& classes]
  (dot/graph->dot (distinct (concat classes (mapcat ancestors classes)))
                  parents
                  :node->descriptor node-descriptor
                  :edge->descriptor edge-descriptor
                  :options graph-options))

(defn view
  "Opens a window displaying a class hierarchy diagram of the classes."
  [& classes]
  (-> (apply dot classes) r/dot->image r/view-image))

(defn png
  "Writes a PNG image file to filename of the class hierarchy diagram
  of the classes."
  [filename & classes]
  (-> (apply dot classes) r/dot->image (r/save-image filename)))

(def ^:private legend-graph
  {:concrete [:abstract :interface]
   :abstract [:object]
   :interface [:superinterface]
   :superinterface []
   :object []})

(def ^:private legend-node-descriptor
  {:concrete (assoc concrete-descriptor
               :label "Class")
   :abstract (assoc abstract-descriptor
               :label "Abstract Class")
   :interface (assoc interface-descriptor
                :label "Interface")
   :superinterface (merge interface-descriptor
                          java-descriptor
                          {:label "java.* Interface"})
   :object (merge concrete-descriptor
                  java-descriptor
                  {:label "java.* Class"})})

(defn- legend-edge-descriptor [a b]
  (condp = [a b]
    [:concrete :abstract] (assoc class-extends-descriptor
                            :label "extends")
    [:concrete :interface] (assoc implements-descriptor
                             :label "implements")
    [:abstract :object] (assoc class-extends-descriptor
                          :label "extends")
    [:interface :superinterface] (assoc interface-extends-descriptor
                                   :label "extends")))

(defn dot-legend
  "Returns a string of GraphViz dot instructions to draw a legend for
  the class hierarchy diagrams produced by this namespace."
  []
  (dot/graph->dot (keys legend-graph)
                  legend-graph
                  :node->descriptor legend-node-descriptor
                  :edge->descriptor legend-edge-descriptor
                  :options (assoc graph-options :size "3,3")))

(defn view-legend
  "Opens a window displaying a legend for the class hierarchy diagrams
  produced by this namespace."  
  []
  (-> (dot-legend) r/dot->image r/view-image))

(defn png-legend
  "Writes a PNG image file to filename of a legend for the class
  hierarchy diagrams produced by this namespace."
  [filename]
  (-> (dot-legend) r/dot->image (r/save-image filename)))
