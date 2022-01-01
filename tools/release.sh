#!/usr/bin/env sh
# ./release.sh <path-to-src> <commit comment>

src=$1
git config user.email "tunachauict@gmail.com"
git config user.name "Tuan Chau"

mv $src/index.html index.html
mv $src/MonoSketch-web.js MonoSketch-web.js
mv $src/MonoSketch-web.js.map MonoSketch-web.js.map
mv $src/style.css style.css

git add -A
git commit -m "Update from $2"
git push