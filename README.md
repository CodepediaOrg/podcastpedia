podcastpedia
================

Project backing [Podcastpedia.org](http://www.podcastpedia.org)

## Project structure
Podcastpedia is a multi-module  [Maven](http://maven.apache.org/download.cgi) project structured in the following way:
```
+---podcastpedia
|   +---common
|   +---sql
|   +---webapps
|       +---web-ui
|       +---admin
```
* **podcastpedia** is the parent project 
* **common** sub-project that contains domain objects and types used by the other subprojects (web-ui & admin)
* **sql** placeholder for database setup scripts and useful sql statements 
* **webapps** sub-project is the parent project for web applications; defines common dependencies and plugins used by the (Spring MVC)sub projects 
  * **web-ui** - the web application that's actually behind the  [Podcastpedia.org](http://www.podcastpedia.org) website
  * **admin** - administration web application used to insert/update/remove podcasts from the directory; both web applications are implemented with [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)

Setup Guide
================
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
* Tomcat 7+ to be able to run the application on the Tomcat server
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
  * The development database for Podcastpedia runs on port **3307**, so install the database on that port, or change the port in [_jett9.xml_](../../web-ui/src/main/resources/config/jetty9.xml) or [_context.xml_](https://github.com/podcastpedia/podcastpedia-web/blob/master/src/main/webapp/META-INF/context.xml) file, depending whether you are using Jetty or Tomcat for testing the application 
3. __Optional__ - install [MySQL Workbench](http://www.mysql.com/products/workbench/) for easy DB development and administration
4. Setup MySQL configuration file
  1. For Windows place the configuration file where the MySQL server is installed - the [my.ini](_prepare_database_for_development/my.ini) file from above is an example used on a Windows 7 machine 
  2. For linux you need to use .cnf files. You can see in this blog post -[Optimizing MySQL server settings](http://www.codingpedia.org/ama/optimizing-mysql-server-settings/) - how the MySQL database is configured in production for [Podcastpedia.org](http://www.podcastpedia.org)

#### Connect to the MySql console
Use the MySql "root" user, configured at installation
```
shell> mysql --host=localhost --user=root -p
```
####Execute db creation script
execute the [__prepare_database_for_import.sql__](sql/_prepare_database_for_development/prepare_database_for_import.sql) in the command line by issuing the following command
>`mysql < "PATH_TO_FILE\prepare_database_for_import.sql"`

### Import database from file
Once the "pcmDB" and "pcm" user are set up, import the ["podcastpedia-2014-06-17-dev-db.sql"](sql/_prepare_database_for_development/podcastpedia-2014-07-17-dev-db.sql) file into the pcmDB database:
```
mysql -p -u pcm pcmDB < "PATH_TO_FILE\podcastpedia-2014-06-17-dev-db.sql"
-- e.g. mysql -p -u pcm pcmDB < "C:\projects\podcastpedia\sql\_prepare_database_for_development\podcastpedia-2014-06-17-dev-db.sql"
```
More details about setting up the database are to find in the [sql README.md](sql/README.md)
***

### Build project 
```
mvn clean package -DskipTests=true
```
***
### Run project with Maven Jetty Plugin
Change to the webapps/web-ui folder and execute the following command

```
mvn jetty:run
```
***

Well, that's it. Feel free to make a pull request  
## License

[MIT](https://github.com/podcastpedia/podcastpedia-web/blob/master/LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
