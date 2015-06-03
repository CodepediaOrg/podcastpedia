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
        concat=require('gulp-concat')
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
});

gulp.task("watch", function(){
    log("Watching scss files for modifications");
    gulp.watch(sassFiles, ["sass"]);
});

//copy fonts to the target/css directory because they are referenced by the css files
gulp.task('copyfonts', function() {
    gulp.src('src/fonts/**/*.{ttf,woff,eof,svg,eot}')
        .pipe(gulp.dest('target/css/fonts'));
});

//deletes content of the target directory, sort of a "maven clean" functionality
gulp.task('clean', function(cb){
    del('target/**/*', cb);
});

gulp.task('compress', function() {
    return gulp.src('js/**/*.js')
        .pipe(concat('all.js'))
        //.pipe(uglify())
        .pipe(gulp.dest('target/js'));
});

//when running just 'gulp' in the command line only the "sass" task will get executed
gulp.task("default", ["sass"]);