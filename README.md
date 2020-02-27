# 2020-spring-group-Lads
Group project
CA Staff Meeting Time

Currently, scheudling apps lilke When2Meet and Calendly find the best times for groups to meet based on the number of people that can meet at once. However, for events such as Staff Meetings for CAs, the optimal meeting time may not be the time frame in which the most people can meet if, for example, the Head CAs cannot be present. Thus, our software application will address the issue of finding the optimal meeting time given that certain individuals' presence at the meeting will take higher priority.


# Iteration 1:
We separate the front end and back end of our web application into two projects names as such. Do as follows to get the web app up and running:

1. Open both the FrontEnd and BackEnd projects in different intelliJ windows.
2. In the src directory of the BackEnd, navigate to the ApiServer class and run the main method to get the Api server up and running.
3. In the src directory of the FrontEnd, navigate to the WebServer class and run the main method of that to host the web server.
4. After both servers are running, open a web browser and type the following url - http://localhost:4567/

In this iteration, we created a basic user interface with a functioning back end that allows for creating, storing, and accessing events for the professor. We did not accomplish storing the availability of the people involved in the meeting. What we have accomplished though, has laid a nice foundation for us to build on and create a successful app in the end. To interact with our web app you can enter all the fields necessary to create an event, and then follow the link to view the created events. The database was prepopulated with some sample events to start.
