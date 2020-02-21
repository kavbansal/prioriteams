# OO Design
A UML class diagram reflecting the "model" for that iteration only.
Use a software to draw this (e.g. draw.io) and save the diagram as an image. 
Upload the image and link it in here using this sintax

![](/docs/image.png)

# Wireframe
One (or a few) simple sketch of how the user interacts with the application. 
This could be a sketch of your user interface. 
You can draw it with hand and insert it here as an image.

<img src="/docs/WireframeDoc1.jpeg" />
<img src="/docs/WireframeDoc2.jpeg" />


# Iteration Backlog
List the User Stories that you will implement in this iteration.

As a professor, I want to create a poll so that I can figure out when everyone is available.


# Tasks
A tentative list of the "to do" in order to sucessfully complete this iteration. 
This list will change and it is good to keep it updated. 
It does not need to be exhustive.

For this iteration:

-Create a basic interface that professor can interact with to create the poll

-Create a database to store the availability from the CAs once it is collected (will probably simulate data for this iteration)


Long Term:

-Create a database containing tables storing the following information - names of people, position, priorities, times available.

-Create basic interface to enter availability. We should be able to prompt and read in the people's availability. Then, we want to parse availabilities and store them with, along with the person to which they correspond to, in a database.

-Implement algorithm to give a meeting time. Given the data we have in our database, we want to suggest a meeting time in which the person with the highest priority can be present and thereafter the number of people that can make it is maximized. This is a basic, rudimentary algorithm that we will likely only stick with for this iteration. 

-Create Professor, Event, and CA classes. Using instances of these classes, the goal is to extract the attributes and values of their fields and store them into a database. 
