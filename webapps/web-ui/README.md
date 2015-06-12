Podcastpedia web-ui
================

The web application backing the [Podcastpedia.org](http://www.podcastpedia.org) website

### Run the project
* The easiest you can start the project with Jetty with the help of jetty-maven-plugin, by issuing the following command on the command line in the root directory:

```
mvn clean install jetty:run -Djetty.port=8080 -DskipTests=true
``` 
and then access the Podcastpedia website at _http://localhost:8080_

* Or copy the generated war file (_ROOT.war_) to your Tomcat _webapps_ folder and start the server. By default you can access the application under the same url _http://localhost:8080_

## License

[MIT](https://github.com/podcastpedia/podcastpedia-web/blob/master/LICENSE.txt) &copy; [Codingpedia Association](http://www.codingpedia.org/about-us/)
