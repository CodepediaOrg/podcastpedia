podcastpedia-sql
================

DB setup and sql statements relevant for the MySql database backing [Podcastpedia.org](http://www.podcastpedia.org)

## Prepare the database for development

### Install MySql 5.5 and above
1. [Download MySQL Community Server](http://dev.mysql.com/downloads/mysql/) version 5.5 or 5.6 for the platform of your choice. 
2. [Install the MySQL Server](http://dev.mysql.com/doc/refman/5.6/en/installing.html)
  1. [Installing MySQL on Microsoft Windows](http://dev.mysql.com/doc/refman/5.6/en/windows-installation.html)
  2. [Installing MySQL on Linux](http://dev.mysql.com/doc/refman/5.6/en/linux-installation.html)
  * The development database for Podcastpedia runs on port **3307**, so install the database on that port, or change the port in [_jett9.xml_](../../web-ui/src/main/resources/config/jetty9.xml) or [_context.xml_](https://github.com/podcastpedia/podcastpedia-web/blob/master/src/main/webapp/META-INF/context.xml) file, depending whether you are using Jetty or Tomcat for testing the application 
3. __Optional__ - install [MySQL Workbench](http://www.mysql.com/products/workbench/) for easy DB development and administration
4. Setup MySQL configuration file
  1. For Windows place the configuration file where the MySQL server is installed - the [my.ini](_prepare_database_for_development/my.ini) file from above is an example used on a Windows 7 machine 
  2. For linux you need to use .cnf files. You can see in this blog post -[Optimizing MySQL server settings](http://www.codingpedia.org/ama/optimizing-mysql-server-settings/) - how the MySQL database is configured in production for [Podcastpedia.org](http://www.podcastpedia.org)

### Connect to the MySql console
Use the MySql "root" user, configured at installation
```
shell> mysql --host=localhost --user=root -p
```
Introduce your "root" password

### Create database and development DB user
Once you are connected to the MySQL command line execute the following commands:
```sql
-- delete the pcmDB database if existent
mysql> DROP DATABASE IF EXISTS pcmDB;

--create the pcmDB database
mysql> CREATE DATABASE pcmDB; 

-- drop 'pcm' user
mysql> DROP USER 'pcm'@'localhost';

-- create the development user 'pcm' identified by the password 'pcm_pw'
CREATE USER 'pcm'@'localhost' IDENTIFIED BY 'pcm_pw';

-- verify user creation
mysql> select host, user, password from mysql.user;
+-----------+-------------+-------------------------------------------+
| host      | user        | password                                  |
+-----------+-------------+-------------------------------------------+
| localhost | root        | *C2BD8E7A5247DF69A9A8CB29C8C6E8FC83D3681F |
| 127.0.0.1 | root        | *C2BD8E7A5247DF69A9A8CB29C8C6E8FC83D3681F |
| ::1       | root        | *C2BD8E7A5247DF69A9A8CB29C8C6E8FC83D3681F |
| %         | pcm_user    | *32D8ED777E1B90734ED5A6AFCD0E354230826743 |
| %         | rest_demo   | *3B8DD81985A42FD9B56133326F3B25A2985A3F75 |
*** | localhost | pcm         | *68DC5C435B9AAA7280CA4C89391C28EFEEC0E946 |***
| localhost | pdp_user    | *F776A21503EFA57908FEF30C914DFB9A9FC78EF3 |
+-----------+-------------+-------------------------------------------+

-- verify user privileges
mysql> SELECT host, user, select_priv, insert_priv, update_priv, delete_priv, create_priv, alter_priv, password FROM mysql.user WHERE user='pcm';

-- should have no(N) privileges
+-----------+------+-------------+-------------+-------------+-------------+-------------+------------+-
| host      | user | select_priv | insert_priv | update_priv | delete_priv | create_priv | alter_priv |
+-----------+------+-------------+-------------+-------------+-------------+-------------+------------+-
| localhost | pcm  | N           | N           | N           | N           | N           | N          |
+-----------+------+-------------+-------------+-------------+-------------+-------------+------------+-

-- grant full privileges to the user, for easy development
mysql> GRANT ALL PRIVILEGES ON *.* TO 'pcm'@'localhost';

-- verify privileges were set (Y)
mysql> SELECT host, user, select_priv, insert_priv, update_priv, delete_priv, create_priv, alter_priv, password FROM mysql.user WHERE user='pcm';

+-----------+------+-------------+-------------+-------------+-------------+-------------+------------+
| host      | user | select_priv | insert_priv | update_priv | delete_priv | create_priv | alter_priv |
+-----------+------+-------------+-------------+-------------+-------------+-------------+------------+
| localhost | pcm  | Y           | Y           | Y           | Y           | Y           | Y          |
+-----------+------+-------------+-------------+-------------+-------------+-------------+------------+

-- exit the mysql command line
exit;
```

>An alternative to introduce these commands manually, is to execute execute the [__prepare_database_for_import.sql__](_prepare_database_for_development/prepare_database_for_import.sql) in the command line by issuing the following command
>`mysql < "PATH_TO_FILE\prepare_database_for_import.sql"`

### Import database from file
Once the "pcmDB" and "pcm" user are set up, import the ["podcastpedia-2014-06-17-dev-db.sql"](_prepare_database_for_development/podcastpedia-2014-07-17-dev-db.sql) file into the pcmDB database:
```
mysql -p -u pcm pcmDB < "PATH_TO_FILE\podcastpedia-2014-06-17-dev-db.sql"
-- e.g. mysql -p -u pcm pcmDB < "C:\projects\podcastpedia\sql\_prepare_database_for_development\podcastpedia-2014-06-17-dev-db.sql"
```
Wait for a bit and then you can verify that everything was OK by connecting to the mysql command line and issuing "show tables" or "select from a table" commands:
```sql 
-- connect to the database with the development user
mysql --host=localhost --user=pcm --password=pcm_pw

-- use the podcastpedia database
mysql> USE pcmDB;

-- show tables imported
mysql> SHOW TABLES; 

-- select data from a table, e.g. "categories"
mysql> SELECT * from categories;
+-------------+-----------------------+--------------------+
| CATEGORY_ID | NAME                  | DESCRIPTION        |
+-------------+-----------------------+--------------------+
|          21 | science_technology    | science            |
|          22 | education             | education          |
|          24 | arts_culture          | Arts & culture     |
|          25 | health_medicine       | Health             |
|          27 | music                 | Music              |
|          28 | religion_spirituality | Religion           |
|          29 | tv_film               | science            |
|          31 | sport                 | Sport              |
|          33 | economy               | Economy            |
|          35 | hobby_freetime        | Hobby & free time  |
|          37 | family_children       | Family & children  |
|          38 | travel_transport      | Travel & Transport |
|          39 | people_society        | People             |
|          41 | internet_computer     | Internet           |
|          42 | news_politics         | News               |
|          43 | radio                 | Radio              |
|          44 | money_business        | Money              |
|          45 | entertainment         | Entertainment      |
|          46 | food_drink            | Food and drink     |
|          47 | nature_environment    | Nature             |
|          48 | general               | General            |
|          49 | history               | History            |
+-------------+-----------------------+--------------------+
```
### Backup the database (optional)
If you ever want to backup up the database you can use _mysqldump_, by issuing a command similar to the following on the command line:
```
shell> mysqldump pcmdb -u pcm -p -h 127.0.0.1 --single-transaction > c:/tmp/pcmdb-backup-2014.06.22.sql
```

### Next steps
This was the "most work" required in getting to run Podcastpedia.org locally. Now that the database is set up, [go on](../README.MD) and do the next steps. 


