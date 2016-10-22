Build development environments

System required
A. JDK8
B. Eclipse4 or later version
C. Apache Tomcat7

1. Download archives file
   click "Download ZIP" button

2. Unzip the archive file bamboobsc-master.zip
   and Unzip to C:\home\git\

3. Open eclipse
   workspace dir input C:\home\git\bamboobsc-master

4. Configure eclipse

5. Import project
   a. core-persistence
   b. core-lib
   c. core-base
   d. core-web
   e. gsbsc-standard
   f. gsbsc-web
   g. qcharts-standard
   h. qcharts-web
   i. gsbsc-mobile-web
   
   config tomcat7 server 
   
   config core-lib project "ThirdPartyLib" user libraries
   add External JARs: all jar file on C:\home\git\bamboobsc-master\core-web\WEB-INF\lib\ 
   
   config core-base project "CORE-LIB" user libraries
   add External JARs: "core-persistence.jar" and "core-lib.jar" on C:\home\git\bamboobsc-master\core-export-lib\

   config gsbsc-standard project "CORE-BASE" user libraries
   add External JARs: "core-base.jar" on C:\home\git\bamboobsc-master\core-export-lib\

5. Clean Java Problems ( if found Java Problem on Markers )
   1. Clean all project 
   2. Restart

   no found Java Problem, Environment Setup Complete

