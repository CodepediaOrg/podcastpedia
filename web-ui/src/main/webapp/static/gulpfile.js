//Gulp plugin
var gulp = require("gulp"),//http://gulpjs.com/
		util = require("gulp-util"),//https://github.com/gulpjs/gulp-util
		sass = require("gulp-sass"),//https://www.npmjs.org/package/gulp-sass
		autoprefixer = require('gulp-autoprefixer'),//https://www.npmjs.org/package/gulp-autoprefixer
		rename = require('gulp-rename'),//https://www.npmjs.org/package/gulp-rename,
    del=require('del'),//https://github.com/gulpjs/gulp/blob/master/docs/recipes/delete-files-folder.md
    uglify=require('gulp-uglify'),//https://www.npmjs.com/package/gulp-uglify
    concat=require('gulp-concat'),//https://github.com/wearefractal/gulp-concat
    jshint=require('gulp-jshint'),//https://www.npmjs.com/package/gulp-jshint
    wrapper = require('gulp-wrapper'), //https://www.npmjs.com/package/gulp-wrapper
		log = util.log,
    gulpif=require('gulp-if'),
    pump = require('pump'),
    runSequence = require('run-sequence'),
    sourcemaps = require('gulp-sourcemaps');

/* TODO - test image minification
    imagemin = require('gulp-imagemin'),
    pngquant = require('imagemin-pngquant'); // $ npm i -D imagemin-pngquant
*/

var config = {
  images: 'images/*'
};

var env = process.env.NODE_ENV || 'dev'; //environment variable that defaults to 'dev'


var sassFiles = "src/sass/**/*.scss";
gulp.task("sass", function(){
  log("Generate CSS files " + (new Date()).toString());

  var sassOptions = {
    errLogToConsole: true
  };

  if(env === 'dev'){
    sassOptions.outputStyle = 'expanded';
    //sassOptions.sourceComments = 'map';
  }

  if(env === 'prod'){
    sassOptions.outputStyle = 'compressed';
  }

  gulp.src(sassFiles)
      .pipe(gulpif(env ==='prod', sourcemaps.init()))
      .pipe(sass(sassOptions).on('error', sass.logError))
      .pipe(autoprefixer("last 3 version","safari 5", "ie 8", "ie 9"))
      .pipe(gulpif(env ==='prod', sourcemaps.write()))
      .pipe(gulp.dest("target/css"))
});


//copy fonts from source ("static/src/fonts")to the "css" directory because they are referenced by the css files
gulp.task('copy:fonts', function() {
  gulp.src('src/resources/fonts/**/*.{ttf,woff,eof,svg,eot}')
    .pipe(gulp.dest('target/css/fonts'));
});

//deletes content of the target directory, sort of a "maven clean" functionality
gulp.task('clean', function(cb){
    del('target/**/*', cb);
});

//delete content of "css" folder, before creating a new one
gulp.task('clean:css', function(cb){
  del.sync('target/css/**/*', cb);//https://github.com/sindresorhus/del#delsyncpatterns-options
});

var jsFiles = "src/js/podcastpedia/**/*.js";
gulp.task('js', function() {
    return gulp.src(jsFiles)
        .pipe(jshint())
        .pipe(concat('app.js'))
        .pipe(gulpif(env ==='prod', uglify()))
        .pipe(wrapper({
          header: '$( document ).ready(function() {' + '\n',
          footer: '\n' + '});'
        }))

        .pipe(gulp.dest('target/js'));
});

gulp.task('jshint', function() {
    gulp.src('target/js/*.js')
        .pipe(jshint())
        //.pipe(jshint.reporter('default'));
        .pipe(jshint.reporter('jshint-stylish'));
});

//minimize images
/*
gulp.task('min-images', () => {
  return gulp.src(config.images)
    .pipe(imagemin({
      progressive: true,
      svgoPlugins: [
        {removeViewBox: false},
        {cleanupIDs: false}
      ],
      use: [pngquant()]
    }))
    .pipe(gulp.dest('target/images'));
});
*/

gulp.task("build", ["clean", "copy:fonts", "sass", 'js']);

gulp.task("watch", function(){
  log("Watching scss files for modifications");
  gulp.watch(sassFiles, ["sass"]);

  log("Watching js files for modifications");
  gulp.watch(jsFiles, ["js"]);

});



//task is executed when running only the "gulp" command
//gulp.task('default', ['build']);
gulp.task('default', function() {
  runSequence('build');
});
