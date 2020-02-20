# OO Design
A UML class diagram reflecting the "model" for that iteration only.
Use a software to draw this (e.g. draw.io) and save the diagram as an image. 
Upload the image and link it in here using this sintax

![](/docs/image.png)

# Wireframe
One (or a few) simple sketch of how the user interacts with the application. 
This could be a sketch of your user interface. 
You can draw it with hand and insert it here as an image.

<img src="/docs/wireframe.png" />


# Iteration Backlog
List the User Stories that you will implement in this iteration.

As a CA, I want to be able to enter my availability for staff meetings.

As a Professor, I want to schedule meetings such that as many of the head CAs can meet with me, along with as many other CAs as possible after that.

As a Professor, I want to be able to enter my availability.

As a Professor, I want to specify how long my meeting will last and that the Head TA must be present. I want the software to suggest a meeting time which satisfies these parameters.

# Tasks
A tentative list of the "to do" in order to sucessfully complete this iteration. 
This list will change and it is good to keep it updated. 
It does not need to be exhustive.

Create a database containing tables storing the following information - names of people, position, priorities, times available.

Create basic interface to enter availability. We should be able to prompt and read in the people's availability. Then, we want to parse availabilities and store them with, along with the person to which they correspond to, in a database.

Implement algorithm to give a meeting time. Given the data we have in our database, we want to suggest a meeting time in which the person with the highest priority can be present and thereafter the number of people that can make it is maximized. This is a basic, rudimentary algorithm that we will likely only stick with for this iteration. 

