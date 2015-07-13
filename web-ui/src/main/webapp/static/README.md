css-generator-sass-gulp
=======================

Project that generates the CSS used for Podcastpedia.org from Sass (.scss). The build tool used is Gulp. 

### Install and run the project
#### 1. Install [Node.js](http://nodejs.org/) on your machine
#### 2. Install gulp globally
`npm install -g gulp`
#### 3. Install gulp in your project devDependencies
`npm install --save-dev gulp`
#### 4. Install the required gulp plugins
`npm install --save-dev gulp-util gulp-sass gulp-minify-css gulp-rename gulp-autoprefixer`
#### 5. Run gulp tasks in your project's root to generate css or watch the files for modication (it automatically generates css by modification)
* `gulp sass` or just `gulp`
* `gulp watch`

## Associated posts on [Codingpedia.org](http://www.codingpedia.org)
* [CSS Preprocessors – Introducing Sass to Podcastpedia.org](http://www.codingpedia.org/ama/css-preprocessors-introducing-sass-to-podcastpedia-org/) 
* [How to use Gulp to generate CSS from Sass/scss](http://www.codingpedia.org/ama/how-to-use-gulp-to-generate-css-from-sass-scss/)
* [Simple trick to create a one line horizontal responsive menu with CSS only – Podcastpedia.org](http://www.codingpedia.org/ama/simple-trick-to-create-a-one-line-horizontal-responsive-menu-with-css-only-podcastpedia-org/)

##License
Copyright (c) 2014 Codingpedia Association. See the LICENSE file for license rights and limitations (MIT).
