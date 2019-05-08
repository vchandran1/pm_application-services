Steps to run Services application
Prerequisites:

1.	MongoDB needs to be present
2.	Default config looks up to the below 3 props
	spring.data.mongodb.host=localhost
	spring.data.mongodb.port=27017
	spring.data.mongodb.database=cdeproject
3. Gradle Version > 4.x
4. Node Version > 10.X

so if we have Mongo running on a different port/server, please change the application.properties.
It also needs database by name “cdeproject”. If not, change the application.properties to match the DB name

Setup :

1.	Clone or download both the code from repo - https://github.com/vchandran1/pm_application-services
2.	This is a gradle project and we need gradle 4.x version to be available
3.	Run a gradle build
	a.	It will generate spring boot jar
	b.	It will run junits and produce test reports (using Jacoco)
	c.	All the mock data for junits are available under src/test/resources folder
4.	Start the boot jar 
	a.	It will try to use 9001 port. If needed we can change the port number in the application.properties 


Generate Coverage or Test Report:

1. Go under the the service project folder.
2. Run the below gradle command "gradle clean build"
3. The result will be generated under below path

		
			Test Coverage : build/reports/tests/test/index.html
			Jacoco Report : build/reports/tests/jacoco/html/index.html
