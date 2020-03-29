# Nextbus API
An apache camel (https://camel.apache.org/) application that returns the time in minutes for a bus on "BUS ROUTE" leaving from 
"BUS STOP NAME" going in a "DIRECTION" using the API - https://svc.metrotransit.org/nextrip/. The application takes input from 
the application.yml file. If the last bus for the day is gone, the application returns nothing and exits.. 

# Getting Started
This application was developed using the following tech-stack and may require you to install them on your system. Note that for Gradle
the default Java version may not be Java 11 - in that case you have to appropriate set $JAVA_HOME in Gradle settings
1. Java 11.0.6
2. SpringBoot 2.2.5
2. Apache Camel 2.5.0
3. Junit (4.13), Camel-Spring test Suite
4. Built using Gradle 2.5.4
5. Intellij 2019.3.4 (Community Edition)
6. Create a folder (location specified in the .yml file) for sending the results

# E.g used for test and the results
## Running the tests
./gradlew clean test can run the test on a console (iTerm with zshell is used for this project). Otherwise, you can run the test using intelliJ.
BUS ROUTE: "METRO Blue Line"
BUS STOP NAME: "Target Field Station Platform 1"
DIRECTION: "south"

Results: The next bus leaves in 14 minutes

# Deployment
This code is written for use in dev env. For production env. certain changes, especially saving the results to a database or a kafka topic
instead of a file should be done depending on the use case. Also, tests should be mocked instead of hitting the real api.
Another addition could be to allow incomplete entry, to give options to the user to choose from.

# Versioning and Changes
0.0.1-SNAPSHOT - Initial Commit for the project

# Author(s)
Anwar Chengala
