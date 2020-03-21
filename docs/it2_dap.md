

# OO Design

<img src="/docs/UMLIteration2.png" />


# Wireframe 

<img src="/docs/FullSizeRender 5.jpeg" />
<img src="/docs/FullSizeRender.jpeg" />
<img src="/docs/FullSizeRender 2.jpeg" />
<img src="/docs/FullSizeRender 3.jpeg" />
<img src="/docs/FullSizeRender 4.jpeg" />




# Iteration Backlog

- As a CA, I want to view all the events that the Professor created.

- As a CA, I want to register for an event.

- As a CA, once I register for an event, I want to add my availability for the event. My availability can differ by the day of the week. 

- As a CA, once I have submitted my availabilities for events, I want to view what my availability submissions for these events.

- As a Professor, I want the software to output an optimal meeting time based on the availability submissions.

# Tasks

- Take in the availability submissions and store them in a Availability database based on the person submitting and the event that they are submitting availabilties for. 

- Create instances of the Person class and store them into the a Persons database as they are signing in.

- From our homepage (index.hbs), have buttons/links to different webpages to either create an event, register for an event (within which, there will be the capability to submit availabilities), and view the availabilities that the actual user has submitted for events.

- Create the handlebars templates/html docs for the varying webpages. These include an updated index.hbs template, a template to register for events, a template to view events, and a template to create events. The index.hbs should include a sign-in component.

- Implement algorithm to determine the optimal meeting time. 

# Retrospective

This iteration, we were able to pick up the pace and build upon what we had started in Iteration 1 to implement many of the core user stories in our RSD. We were able to meet together when necessary and divide the work where possible to accomplish this. Our application is now able to take in and store availabilities, display relevant information about created events, and use a rudimentary algorithm to generate some suggested time for the event. We also created our handlebars files and used css and bootstrap to improve the UI. At the moment, our application is still only able to take in one continuous availability period at a time, and that is certainly something we hope to fix in iteration 3 to make the user experience less complicated. In addition, we haven't incorporated priorities yet, and that is something that we will focus on in the future. Finally, our current algorithm for determining the optimal time is very basic and we plan to work on developing a more robust algorithm in iteration 3.
