// packages
const gulp = require("gulp");
const postCSS = require("gulp-postcss");
const sass = require("gulp-sass")(require("sass"));
const minify = require("gulp-csso");
sass.compiler = require("node-sass");

// file path
const path = {
  style: {
    scss: "assets/scss/*.scss",
    css: "static/css",
  },
  html: {
    index: "./index.html",
    templates: "./templates/*.html",
  },
};

// scss => css
gulp.task("css", function () {
  return gulp
    .src(path.style.scss)
    .pipe(sass().on("error", sass.logError))
    .pipe(postCSS([require("tailwindcss"), require("autoprefixer")]))
    .pipe(minify())
    .pipe(gulp.dest(path.style.css));
});

// keep 'watch'
gulp.task("watch", function () {
  gulp.watch(path.html.index, gulp.series["css"]);
  gulp.watch(path.html.templates, gulp.series["css"]);
});
