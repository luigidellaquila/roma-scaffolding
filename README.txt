This is an Eclipse project, you can start using it just importing it clicking on
File -> import -> General -> Existing project into workspace

To run the project you have to

1. run the ANT taks named "persistence-compile"
 	- you can do it on the command line with "ant persistence-compile"
 	- you can run it in Eclipse, if you have Ant plugin instaled
 	- IMPORTANT: do it every time you start the application
 	
 	
2. create a run configuration:
	- Run -> Run Configuratoins... -> Java Application (right click) -> New
	- in Main tab 
		- choose current project
		- write "org.mortbay.start.Main" as Main Class
	- in Arguments tab
		- Program arguments: src/jetty.xml
		- VM arguments: -Djetty-webapp=WebContent
		
3. execute your run configuration


You can also create a WAR file just using the "dist" ANT target


If you want to start experimenting with Roma, you can start from the MainPage class. 

Enjoy!
