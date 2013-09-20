#!/bin/bash


cp -a tutorial/html/* ~/Internetauftritt/projects/dbdoclet

for htmlFile in `find ~/Internetauftritt/projects/dbdoclet -name "*.html"` ; do
   sed -e "/<div class=\"navheader\">i $htmlCode/"
done

