# Sticky Session Sample for WildFly 8 #

The goal is to visualize the sessionId and check if it is suffixed by the route suffix.

Related to https://community.jboss.org/message/879684

# Manuel Test #

In order to visualize the session id, I'll have to build the project, launch WildFly and open you favorite browser at the root URL of the project.

* Build the project with Maven

 mvn clean package wildfly:run -DskipTests

* Open you browser at http://localhost:8080/sticky-example/. The page will just show the session id, which should end with _.xxx_.

# Automatic Test #

You can also run the verification automatically, with an Arquillian test.
 
* mvn clean test

If the build succeeds, it means that the session id has the _.xxx_  suffix.
