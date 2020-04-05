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

As a CA, when I register for an event, I want the process for entering my availability to be both simple and flexible so that my availability may be taken into consideration when determining the meeting time.

# Tasks

Associate priorities with each of the CAs and the professor

Determine more precisely what it is we want the algorithm to do. That is, suppose meeting time 1 has far more people available but a number of high-priority, important individuals are not available and meeting time 2 has far less people available but  more high-priority, important individuals are available at this time, how do we handle this tradeoff?

Research optimal scheduling algorithms to gain better familiarity of existing algorithms and to see if we can adopt/modify certain aspects of them to fit our needs. 

Implement and test the optimizing algorithm

Modify the existing software so that the Professor has access to all the data collected by the software but the CAs can only see the created events and the availabilities corresponding to these events that he or she has submitted ONLY. 

Work on improving UI/UX, especially with the register page.

# Retrospective

This iteration, we were able to successfully accomplish what we had planned to deliver. Notably, we were able to incorporate priorities with the CAs and Professor, as well as develop and implement a new algorithm to take both these priorities and all availabilities for an event into consideration. Further explanation of this algorithm is detailed in the document linked in our README. Additionally, we were able to modify our register page to simplify the availability-entering process. We do plan on further improving the UI/UX all around to create a better experience for users. We also plan to continue testing and refining our algorithm to provide the most helpful and accurate optimal time suggestions for all cases. Some challenges we faced this iteration were trying to figure out the best way to allow users to submit multiple availability time slots with one click of the "Submit" button, as well as trying to switch our time intervals to a half hour basis rather than an hourly one. Both of these proved to be too much for us to dwell on for the brief time period of the iteration, however we will certainly work on both of these in the next iteration in order to make our Alpha release as successful as possible.
