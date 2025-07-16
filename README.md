# Java Spring Boot Backend Code for Prime Task Manager Coding Challenge

# How to Run, Build, and Access Java Spring REST API
1. Clone repo through the many ways of cloning a repo to local enviornment
2. Navigate to the the directory where you have cloned the repo via command line/terminal
3. Ensure that you have Docker Desktop installed
4. Run the following command to build the frontend image -> "docker build -t task-manager ."
5. After building the image, run the following command to run the image -> "docker run -p 8080:8080 task-manager"

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

