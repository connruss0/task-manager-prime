# Prime Task Manager Coding Challenge Running and Testing Instuctions

# How to Run, Build, and Access Task Manager Application
1. Clone the repository
2. Ensure that docker desktop is installed and running
3. Run "docker-compose up --build" in a terminal to run tests, build, and start the application
4. The backend can now be accessed via postman and the ui connected to a backend database can be accessed via "localhost:5173"

# How to Test Application - Manual
1. Download Postman to test backend code or make sure the frontend code is running to test
2. Import Task Manager API postman file in the root of this repo to test manually.
3. The collection will allow you to test all the different end points of the Spring Boot Backend API

# How to Test Application - JUnit Tests
1. Open the application in your preffered IDE
2. IntelliJ allows for the tests to easily be run by clicking the run button in the different test files
3. When you build the application via Docker, the tests will also be run.

If you want to run tests via Maven command line follow the directions below:
1. Ensure Maven is installed on your system
2. Run "mvn test" in the task-manager directory that contains the Java Spring Boot code

# Reason for Adding a Postgresql Database
Initally, I had only a hashmap for storing data. This was done so that the application could quickly be spun up and tested. This could allow for a quick POC to get up and running. However, after this inital test was done it made sense to create an environment that would replicate production. I added a postgresql database that would allow for persistant storage in a docker container even when the application was stopped. I chose to add this complexity as it was not overly difficult, and it makes sense to develop locally in a more production like environment.
