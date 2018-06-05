rem @echo off
rename pom.xml pomeclipse.xml
rename pombuild.xml pom.xml
call mvn install -Dmaven.test.skip=true
rename pom.xml pombuild.xml
rename pomeclipse.xml pom.xml