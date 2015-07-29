podcastpedia
================

Project backing [Podcastpedia.org](http://www.podcastpedia.org)

## Project structure
Podcastpedia is a multi-module  [Maven](http://maven.apache.org/download.cgi) project structured in the following way:
```
+---podcastpedia
|   +---api
|   +---common
|   +---core
|   +---sql
|   +---web-ui
|   +---admin
```
* **podcastpedia** is the parent project
* **api** REST api supporting core functionalities for the application(in progress)
* **common** sub-project that contains domain objects and types used by the other subprojects (web-ui & admin)
* **core** module that contains database access and business layer; it support currently both the _api_ and _web-ui_ modules.
* **sql** placeholder for database setup scripts and useful sql statements
* **webapps** sub-project is the parent project for web applications; defines common dependencies and plugins used by the (Spring MVC)sub projects
  * **web-ui** - the web application that's actually behind the  [Podcastpedia.org](http://www.podcastpedia.org) website
  * **admin** - administration web application used to insert/update/remove podcasts from the directory; both web applications are implemented with [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)

Setup Guide (~15min)
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
3. __Optional__ - install [MySQL Workbench](http://www.mysql.com/products/workbench/) for easy DB development and administration
4. Setup MySQL configuration file
  1. For Windows place the configuration file where the MySQL server is installed - the [my.ini](_prepare_database_for_development/my.ini) file from above is an example used on a Windows 7 machine
  2. For linux you need to use .cnf files. You can see in this blog post -[Optimizing MySQL server settings](http://www.codingpedia.org/ama/optimizing-mysql-server-settings/) - how the MySQL database is configured in production for [Podcastpedia.org](http://www.podcastpedia.org)
**Note**
1. You will be using the admin usert setup at installation, to prepare the development database
2. MySql is installed by default on port 3306, and this is used throughout the Maven plugin configurations files
#### Prepare development database with the sql-maven-plugin
Change the username/password for your **root** user, configured at installation in the prepare-db maven profile:
```
<configuration>
  <driver>com.mysql.jdbc.Driver</driver>
  <url>jdbc:mysql://localhost:3306</url>
  <username>root</username><!-- use your installation admin user-->
  <password>root</password><!-- user your installation admin user's password-->
</configuration>
```
Use the MySql **root** user, configured at installation
```
shell> mysql --host=localhost --user=root --password=YOUR_ROOT_PASSWORD
```
####Execute db creation script
execute the [__prepare_database_for_import.sql__](sql/_prepare_database_for_development/prepare_database_for_import.sql) in the mysql shell command line by issuing the following command
`mysql> source "PATH_TO_FILE/prepare_database_for_import.sql"`

Example:

`mysql> source /home/ama/dev/repo/podcastpedia/sql/_prepare_db/prepare_db_and_user.sql`

#### Import database from file
Once the *pcmDB* and *pcm* user are set up, import the ["podcastpedia-2014-07-17-dev-db.sql"](sql/_prepare_database_for_development/podcastpedia-2014-07-17-dev-db.sql) file into the **pcmdb** database by executing the following command in the terminal:
```
mysql --user=pcm --password=pcm_pw pcmdb < "PATH_TO_FILE\podcastpedia-2014-06-17-dev-db.sql"
```
Example (windows):
```
mysql --user=pcm --password=pcm_pw pcmdb < "C:\projects\podcastpedia\sql\_prepare_db\podcastpedia-2014-06-17-dev-db.sql"
```
or (linux)
```
mysql --user=pcm --password=pcm_pw pcmdb < /home/ama/dev/repo/podcastpedia/sql/_prepare_db/podcastpedia-2014-07-17-dev-db.sql
```

That database setup should be ready now. A more detailed explanation about setting up the database is to find in the [sql README.md](sql/README.md)
***

### Build project
```
mvn clean package -DskipTests=true
```
***
### Run the website (_web-ui_ module)
#### Jetty [(Maven Jetty Plugin)](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html)
Execute the following command in the parent/root directory

```
mvn jetty:run -pl web-ui -Denv=dev
```
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

[MIT](https://github.com/podcastpedia/podcastpedia-web/blob/master/LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
