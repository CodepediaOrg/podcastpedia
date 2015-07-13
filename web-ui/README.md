Podcastpedia web-ui
================

The web application backing the [Podcastpedia.org](http://www.podcastpedia.org) website
### Run project 
Once you have [set up the podcastpedia project](https://github.com/PodcastpediaOrg/podcastpedia) you can run the application directly via Maven in an embedded container: 
#### Jetty [(Maven Jetty Plugin)](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html)
Change to the *webapps/web-ui* folder and execute the following command on the terminal

```
mvn jetty:run
```
#### Tomcat [(Apache Tomcat Maven Plugin)](http://tomcat.apache.org/maven-plugin.html)
Change to the *webapps/web-ui* folder and execute the following command on the terminal

```
mvn tomcat7:run
```
### Or
you can copy the generated war file (_ROOT.war_) to your Tomcat _webapps_ folder and start the server. By default you can access the application under the same url _http://localhost:8080_

## License

[MIT](https://github.com/podcastpedia/podcastpedia-web/blob/master/LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
