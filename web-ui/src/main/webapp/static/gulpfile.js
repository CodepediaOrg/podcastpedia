//Gulp plugin
var gulp = require("gulp"),//http://gulpjs.com/
		util = require("gulp-util"),//https://github.com/gulpjs/gulp-util
		sass = require("gulp-sass"),//https://www.npmjs.org/package/gulp-sass
		autoprefixer = require('gulp-autoprefixer'),//https://www.npmjs.org/package/gulp-autoprefixer
		minifycss = require('gulp-minify-css'),//https://www.npmjs.org/package/gulp-minify-css
		rename = require('gulp-rename'),//https://www.npmjs.org/package/gulp-rename,
    //clean  = require('gulp-clean'),//https://www.npmjs.com/package/gulp-clean --deprecated
    del=require('del'),//https://github.com/gulpjs/gulp/blob/master/docs/recipes/delete-files-folder.md
    uglify=require('gulp-uglify'),//https://www.npmjs.com/package/gulp-uglify,
    concat=require('gulp-concat'),//https://github.com/wearefractal/gulp-concat
    jshint=require('gulp-jshint'),//https://www.npmjs.com/package/gulp-jshint
    wrapper = require('gulp-wrapper'), //https://www.npmjs.com/package/gulp-wrapper
		log = util.log;

var sassFiles = "src/sass/**/*.scss";

gulp.task("sass", function(){
  log("Generate CSS files " + (new Date()).toString());
  gulp.src(sassFiles)
      .pipe(sass({ style: 'expanded' }))
      .pipe(autoprefixer("last 3 version","safari 5", "ie 8", "ie 9"))
      .pipe(gulp.dest("target/css"))
      .pipe(rename({suffix: '.min'}))
      .pipe(minifycss())
      .pipe(gulp.dest('target/css'));

  gulp.src('src/resources/fonts/**/*.{ttf,woff,eof,svg,eot}')
    .pipe(gulp.dest('target/css/fonts'));
});

gulp.task("watch", function(){
  log("Watching scss files for modifications");
  gulp.watch(sassFiles, ["sass"]);
});

//copy fonts from source ("static/src/fonts")to the "css" directory because they are referenced by the css files
gulp.task('copy:fonts', function() {
    gulp.src('src/resources/fonts/**/*.{ttf,woff,eof,svg,eot}')
        .pipe(gulp.dest('css/fonts'));
});

//deletes content of the target directory, sort of a "maven clean" functionality
gulp.task('clean', function(cb){
    del('target/**/*', cb);
});

//delete content of "css" folder, before creating a new one
gulp.task('clean:css', function(cb){
  del.sync('css/**/*', cb);//https://github.com/sindresorhus/del#delsyncpatterns-options
});

gulp.task('compress:js', function() {
    return gulp.src('src/js/pages/**/*.js')
        .pipe(concat('app.js'))
        //.pipe(uglify())
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

//when running just 'gulp' in the command line only the "sass" task will get executed
gulp.task("default", ["clean", "sass", 'compress:js']);
