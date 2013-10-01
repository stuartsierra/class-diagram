# com.stuartsierra/class-diagram

Generate & display class hierarchy diagrams for Java classes.

Uses [Graphviz] via [Rhizome] to generate graphs.

[Graphviz]: http://www.graphviz.org/
[Rhizome]: https://github.com/ztellman/rhizome



## Releases and Dependency Information

Latest release version is **0.1.0**

[Leiningen] dependency information:

    [com.stuartsierra/class-diagram "0.1.0"]

[Maven] dependency information:

    <dependency>
      <groupId>com.stuartsierra</groupId>
      <artifactId>class-diagram</artifactId>
      <version>0.1.0</version>
    </dependency>

[Leiningen]: http://leiningen.org/
[Maven]: http://maven.apache.org/



## Usage and Examples

    (require '[com.stuartsierra.class-diagram :as diagram])

To open up a window showing the class hierarchy diagram of Clojure's
persistent list:

    (diagram/view clojure.lang.PersistentList)

![](https://raw.github.com/stuartsierra/class-diagram/master/examples/PersistentList.png)

Or save it to a PNG file:

    (diagram/png "PersistentList.png" clojure.lang.PersistentList)

Say you want to understand the relationships among Clojure's
persistent map types:

    (->> [(hash-map 1 2) (array-map) (sorted-map)]
         (map class)
         (apply diagram/view))

(You have to call `hash-map` with arguments because an empty map
defaults to an array-map.)

![](https://raw.github.com/stuartsierra/class-diagram/master/examples/maps.png)

Note: The diagrams only show **ancestors** of the input classes, not
subclasses.

You can get a legend for the symbols and colors used in the diagram:

    (diagram/view-legend)

![](https://raw.github.com/stuartsierra/class-diagram/master/examples/legend.png)

Or save that to a file:

    (diagram/png-legend "legend.png")



## Change Log

* Version 0.1.0 on 1-Oct-2013: Initial release



## Copyright and License

Copyright © 2013 Stuart Sierra. All rights reserved.

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0] which can be found in the file
epl-v10.html at the root of this distribution. By using this software
in any fashion, you are agreeing to be bound by the terms of this
license. You must not remove this notice, or any other, from this
software.

[Eclipse Public License 1.0]: http://opensource.org/licenses/eclipse-1.0.php

