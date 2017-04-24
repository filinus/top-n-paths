#Top-N-Paths

Project demonstrates capabilities of Java 8 Stream API and lambdas
Task to find top most N-pages path in log like

| user | page     |
|------|----------|
| U1   | /        |
| U2   | /        |
| U1   | /page    |
| U1   | /filter  |
| U1   | /catalog |
| U3   | /        |
| U4   | /        |
| U2   | /new     |
| U2   | /catalog |
| U1   | /pageZ   |

U* is a id of user session, second column is visit page from a log file

Examples of N-page-paths, where N=3:
*  / -> /new -> /catalog
*  / -> /page -> /filter
*  /page -> /filter -> /catalog
*  /filter -> /catalog ->pageZ  

## example of maven build

```
bash-3.2$ mvn clean install
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building topnpath 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ topnpath ---
[INFO] Deleting /work/top3paths/topnpath/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ topnpath ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.6.1:compile (default-compile) @ topnpath ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 1 source file to /work/top3paths/topnpath/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ topnpath ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.6.1:testCompile (default-testCompile) @ topnpath ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 1 source file to /work/top3paths/topnpath/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ topnpath ---
[INFO] Surefire report directory: /work/top3paths/topnpath/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running us.filin.topnpaths.TopMostPopularPathsTest
/ -> subscribers -> filter
subscribers -> filter -> export
filter -> export -> /
export -> / -> subscribers
/ -> catalog -> edit
catalog -> edit -> /
edit -> / -> catalog
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.119 sec

Results :

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- maven-jar-plugin:3.0.2:jar (default-jar) @ topnpath ---
[INFO] Building jar: /work/top3paths/topnpath/target/topnpath-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-install-plugin:2.4:install (default-install) @ topnpath ---
[INFO] Installing /work/top3paths/topnpath/target/topnpath-1.0-SNAPSHOT.jar to /Users/vfilin/.m2/repository/topnpath/topnpath/1.0-SNAPSHOT/topnpath-1.0-SNAPSHOT.jar
[INFO] Installing /work/top3paths/topnpath/pom.xml to /Users/vfilin/.m2/repository/topnpath/topnpath/1.0-SNAPSHOT/topnpath-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.148 s
[INFO] Finished at: 2017-04-24T11:55:32-07:00
[INFO] Final Memory: 20M/307M
[INFO] ------------------------------------------------------------------------
bash-3.2$ pwd
/work/top3paths/topnpath
bash-3.2$ java -jar target/topnpath-1.0-SNAPSHOT.jar  src/test/resources/files/test.txt 
/ -> subscribers -> filter
subscribers -> filter -> export
filter -> export -> /
export -> / -> subscribers
/ -> catalog -> edit
catalog -> edit -> /
edit -> / -> catalog
bash-3.2$ 
```
