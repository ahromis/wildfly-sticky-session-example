# Sticky Session Sample Application

A basic application that provides a cookie for testing with load balancing and persistence.

# About

In order to visualize the session id, you'll have to build the project, launch an app server and open you favorite browser at the URL of the deployed application.

Note that most modern browsers have a keep alive to keep connections alive when connected to a service. This is by design since it's more effecient to reuse connections. In order to showcase persistence with the `JSESSIONID`, connections are forcibly closed in the `index.jsp` using this block:

```
<%
response.setHeader("Connection", "close");
%>
```

Otherwise, the connection will close base upon on the `connectionTimeout` setting in your `server.xml` (for Tomcat). For the tomcat-ssl `Dockerfile` connections aren't forcibly closed to showcase re-using the connections for improved performance.

# Usage

1. ##Build the project with Maven

    * `mvn clean install`
    * Or using docker: `docker run -it --rm --name my-maven-project -v /var/run/docker.sock:/var/run/docker.sock -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3-jdk-7-alpine mvn clean install`

2. ##Build the Docker image

    Any of these will work depending on which application server you wish to use, or if you want to enable SSL.

    * `docker build --rm -f Dockerfile.tomcat -t ahromis/lbinfo:tomcat`
    * `docker build --rm -f Dockerfile.wildfly -t ahromis/lbinfo:wildfly`

    ####Building for TLS

    1. `cp src/main/webapp/index-ssl.jsp src/main/webapp/index.jsp`
    2. `docker build --rm -f Dockerfile.tomcat-ssl -t ahromis/lbinfo:tomcat-ssl`

3. ##Running the image

    ####Docker for Mac/Windows:

    * `docker run -d -p 8080:8080 ahromis/lbinfo:tomcat`

    ####As a Docker Swarm Mode service:

    1. `docker network create -d overlay lbinfo`
    2. `docker service create -p 8080 --network lbinfo --replicas 3 --name lbinfo ahromis/lbinfo:latest`

    ####Using TLS and Secrets

    1. `docker secret create cert.pem cert.pem`
    2. `docker secret create key.pem key.pem`
    3. `docker secret create chain.pem chain.pem`
    4. `docker service create --secret source=key.pem,target=key.pem --secret source=chain.pem,target=chain.pem --secret source=cert.pem,target=cert.pem -p 8443 --network lbinfo --replicas 3 --name ssl-test ahromis/lbinfo:tomcat-ssl`

    ####Using UCP HRM:

    * `docker service create -l com.docker.ucp.mesh.http=external_route=http://site.example.com,internal_port=8080 -p 8080 --network ucp-hrm --replicas 3 --name lbinfo ahromis/lbinfo:latest`

    ####Using UCP HRM with TLS:

    * `docker service create --secret source=key.pem,target=key.pem --secret source=chain.pem,target=chain.pem --secret source=cert.pem,target=cert.pem -l com.docker.ucp.mesh.http=external_route=sni://ssl.hromis.dckr.org,internal_port=8443 -p 8443 --network ucp-hrm --replicas 3 --name ssl-test ahromis/lbinfo:tomcat-ssl`
