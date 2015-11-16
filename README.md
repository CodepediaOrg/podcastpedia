podcastpedia
================

[Podcastpedia.org](http://www.podcastpedia.org) - the open source podcast directory

## Project structure
Podcastpedia is a multi-module  [Maven](http://maven.apache.org/download.cgi) project structured in the following way:
```
+---podcastpedia
|   +---common
|   +---core
|   +---web-ui
|   +---api
|   +---sql
|   +---admin
```
* **podcastpedia** - is the parent project
* **common** - contains domain objects and types used by the other modules (core, web-ui, api & admin)
* **core** - code for database access and business layer; it support currently both the _api_ and _web-ui_ modules.
* **web-ui** - the web application that's actually behind the  [Podcastpedia.org](http://www.podcastpedia.org) website
* **api** - REST api supporting core functionalities for the application(in progress)
* **sql** - contains database setup scripts and useful sql statements
* **admin** - administration web application used to insert/update/remove podcasts from the directory; implemnted with [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)

Setup Guide
================
_(~15min of which 13 min MySql installation)_
## Install and run the Podcastpedia.org website on your local machine

### Prerequisites
####
* MySQL 5.5 or 5.6
  * [Download MySQL Community Server](http://dev.mysql.com/downloads/mysql/)
  * [Prepare the MySQL database for Podcastpedia development](sql/README.md)

* JDK 1.7 (if you want to use Jetty 9 with the jetty-maven-plugin from project)
  * [Java SE Development Kit 7 Downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
  * set `JAVA_HOME =  jdk-install-dir` in your environment variables
* Maven 3.*
  * [Download Apache Maven](http://maven.apache.org/download.cgi)
    * [Maven installation instructions](https://maven.apache.org/download.cgi#Installation) - basically extracting the archives and settings some environment variables
* (optional) Tomcat 7+ to be able to run the application on a standalone Tomcat server
  *  [Tomcat 7 Downloads](http://tomcat.apache.org/download-70.cgi)
  *  [Tomcat 7 Documentation](http://tomcat.apache.org/tomcat-7.0-doc/index.html)
* (optional) IDE ( preferred Eclipse 4.3+ or IntelliJ)
  * [The Eclipse Project Downloads] (http://download.eclipse.org/eclipse/downloads/)
  * [IntelliJ IDEA] (https://www.jetbrains.com/idea/download/)

***
### [Download project](https://github.com/PodcastpediaOrg/podcastpedia)
```
git clone https://github.com/PodcastpediaOrg/podcastpedia.git
```
***

### Prepare MySql Database
#### Install MySql 5.5 and above
1. [Download MySQL Community Server](http://dev.mysql.com/downloads/mysql/) version 5.5 or 5.6 for the platform of your choice.
2. [Install the MySQL Server](http://dev.mysql.com/doc/refman/5.6/en/installing.html)
  1. [Installing MySQL on Microsoft Windows](http://dev.mysql.com/doc/refman/5.6/en/windows-installation.html)
  2. [Installing MySQL on Linux](http://dev.mysql.com/doc/refman/5.6/en/linux-installation.html)
3. __Optional__ - install [MySQL Workbench](http://www.mysql.com/products/workbench/) for easy DB development and administration
4. Setup MySQL configuration file
  1. For Windows place the configuration file where the MySQL server is installed - the [my.ini](_prepare_database_for_development/my.ini) file from above is an example used on a Windows 7 machine
  2. For linux you need to use .cnf files. You can see in this blog post -[Optimizing MySQL server settings](http://www.codingpedia.org/ama/optimizing-mysql-server-settings/) - how the MySQL database is configured in production for [Podcastpedia.org](http://www.podcastpedia.org)

**Note**

1. You will be using the admin user setup at installation, to prepare the development database
2. MySql is installed by default on port 3306; should you use another port number please adjust that throughout the Maven plugin configuration files

#### Prepare development database with the sql-maven-plugin
Change the username/password in the [pom.xml](sql/pom.xml) corresponding to your **root** user, configured at installation time:
```
<configuration>
  <driver>com.mysql.jdbc.Driver</driver>
  <url>jdbc:mysql://localhost:3306</url>
  <username>root</username><!-- use your installation admin user-->
  <password>root</password><!-- user your installation admin user's password-->
</configuration>
```
First step is to prepare the "podcast" user and the podcast_db with the following maven command
```
mvn install -Pprepare-db -pl sql
```
and the second step is to import the data into the database
```
mvn install -Pimport-db -pl sql
```

That database setup should be ready now. You can choose to setup the database via the MySql console - more details is to find in the [sql README.md](sql/README.md)
***

### Build project
```
mvn clean install -DskipTests=true
```
***
### Run the website (_web-ui_ module)
#### Jetty [(Maven Jetty Plugin)](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html)
Execute the following command in the parent/root directory

```
mvn jetty:run -pl web-ui -Denv=dev
```
Access homepage at [http://localhost:8080](http://localhost:8080) - 
_user/password_ for login _test-dev@podcastpedia.org/test_

#### Tomcat [(Apache Tomcat Maven Plugin)](http://tomcat.apache.org/maven-plugin.html)
Execute the following command in the parent/root directory

```
mvn tomcat7:run -pl web-ui -Denv=dev
```

***

## Contributing

If you wish to contribute to this website, please [fork it on GitHub](https://github.com/PodcastpediaOrg/podcastpedia-web.git), push your
change to a named branch, then send a pull request. If it is a big feature,
you might want to start an Issue first to make sure it's something that will
be accepted.  If it involves code, please also write tests for it.

## License

[MIT](LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
