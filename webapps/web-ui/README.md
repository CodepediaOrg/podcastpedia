web-ui
================

The web application backing the [Podcastpedia.org](http://www.podcastpedia.org) website

Setup Guide
================

## Install and run the Podcastpedia.org website on your local machine

### Prerequisites
#### 
* MySQL 5.5 or 5.6 
  * [Download MySQL Community Server](http://dev.mysql.com/downloads/mysql/)
  * [Prepare the MySQL database for Podcastpedia development](https://github.com/podcastpedia/podcastpedia-sql)
* IDE ( preffered Eclipse 4.3+) 
  * [The Eclipse Project Downloads] (http://download.eclipse.org/eclipse/downloads/)
* JDK 1.7 (if you want to use Jetty 9 with the jetty-maven-plugin from project)
  * [Java SE Development Kit 7 Downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
  * set `JAVA_HOME =  jdk-install-dir` in your environment variables
* Maven 3.*
  * [Download Apache Maven](http://maven.apache.org/download.cgi) - Maven installation instructions are also available on this website
* Tomcat 7+ to be able to run the application on the Tomcat server
  *  [Tomcat 7 Downloads](http://tomcat.apache.org/download-70.cgi)
  *  [Tomcat 7 Documentation](http://tomcat.apache.org/tomcat-7.0-doc/index.html)

#### Podcastpedia artefacts
Download [podcastpedia-parent](https://github.com/podcastpedia/podcastpedia-parent) and [podcastpedia-common](https://github.com/podcastpedia/podcastpedia-common) and this project - **podcastpedia-web** - into the same folder on your local machine:
```
+---podcastpedia
|   +---podcastpedia-admin
|   +---podcastpedia-common
|   +---podcastpedia-parent
|   +---podcastpedia-sql
|   +---podcastpedia-web
```
* install [podcastpedia-parent](https://github.com/podcastpedia/podcastpedia-parent) in your local Maven repository
  * change to the _podcastpedia-parent_ folder 
  * execute `mvn clean install -DskipTests=true` on the command line
* install [podcastpedia-common](https://github.com/podcastpedia/podcastpedia-common) in your local Maven repository
  * change to the _podcastpedia-common_ folder 
  * execute `mvn clean install -DskipTests=true` on the command line

> Note: The application can be easily run from [Jetty](http://wiki.eclipse.org/Jetty), with the help of [jetty-maven-plugin](http://wiki.eclipse.org/Jetty/Feature/Jetty_Maven_Plugin) which is configured in the [pom.xml](https://github.com/podcastpedia/podcastpedia-web/blob/master/pom.xml)

### Install the _podcastpedia-web_ project
1. download/clone the project 
2. as mentioned in the **Prerequisites** section you need to prepare the database as described in the [README.md](https://github.com/podcastpedia/podcastpedia-sql) of the podcastpedia-sql project
3. change to the _podcastpedia-web_ folder and excute the following maven command 

```
mvn clean install -DskipTests=true
```

### Run the project
* The easiest you can start the project with Jetty with the help of jetty-maven-plugin, by issuing the following command on the command line in the root directory:

```
mvn jetty:run -Djetty.port=8080
``` 
and then access the Podcastpedia website at _http://localhost:8080_

* Or copy the generated war file (_ROOT.war_) to your Tomcat _webapps_ folder and start the server. By default you can access the application under the same url _http://localhost:8080_

## License

[MIT](https://github.com/podcastpedia/podcastpedia-web/blob/master/LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
