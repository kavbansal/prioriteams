# OO Design
<img src="/docs/UMLIteration2.png" />

# Wireframe 
<img src="/docs/IMG_6879.jpeg" />
<img src="/docs/IMG_6874.jpeg" />
<img src="/docs/IMG_6875.jpeg" />
<img src="/docs/IMG_6877.jpeg" />
<img src="/docs/IMG_6878.jpeg" />
<img src="/docs/FullSizeRender.jpeg" />
<img src="/docs/IMG_6873.jpeg" />
<img src="/docs/IMG_6876.jpeg" />

# Iteration Backlog

As a Professor, I want the software to output an optimal meeting time based on the availability submissions and priorties. More specifically, the goal is to implement a more advanced algorithm than the current one that will factor in not only the availabilties associated with an event but also the priorities associated with each availability for that event. We want the algorithm to be far more sophisticated than something rudimentary/crude. 

As a Professor, I want to be able to insert into the website the full names, emails, priorities, and temporary username/password combination of the CAs and myself.

As a Professor, I want there to be a sign-in page so that only my CAs and myself can view and register for events. I don't want random users to corrupt the data being taken in by the platform; I want there to be a verification system that uses the information entered from the above User Story to ensure that only my CAs and I can actually view, manipulate, and enter data into the platform.

As a user of the software, I want the interface UX to be easy-to-use and easy on the eye. No one wants to use a software product that looks bad and outdated. 

# Tasks

Associate priorities with each of the CAs and the professor

Determine more precisely what it is we want the algorithm to do. That is, suppose meeting time 1 has far more people available but a number of high-priority, important individuals are not available and meeting time 2 has far less people available but  more high-priority, important individuals are available at this time, how do we handle this tradeoff?

Research optimal scheduling algorithms to gain better familiarity of existing algorithms and to see if we can adopt/modify certain aspects of them to fit our needs. 

Implement and test the optimizing algorithm

Modify the back-end database to be able to store name, email, username/password combinations of CAs and the Professor(s)

Implement a sign-in system that prevents anyone who is not signed in from accessing anything on the platform. 

Modify the existing software so that the Professor has access to all the data collected by the software but the CAs can only see the created events and the availabilities corresponding to these events that he or she has submitted ONLY. 

Work on improving UI/UX, especially with the register page.
