# Requirement Specification Document

## Problem Statement 

> Write a few sentences that describes the problem you are trying to solve. In other words, justify why this software project is needed.

Existing scheduling services and platforms do not factor in priority. Many of these software solutions simply involve finding times in which there the highest number of people can attend. An example is the scheduling of CA staff meetings during the course of a semester. Naturally, it is most pertinent that the Head CA(s) and the Professor(s) are present at the meeting since they are the main stakeholders in directing the class. Ideally, the meeting time that will be chosen will be one at which all of the Head CA(s) and Professor(s) are present as well as the maximum number of other CAs. Perhaps, the Professor also wants to assign different CAs varying priorities. 


## Potential Clients
> Who are affected by this problem (and would benefit from the proposed solution)? I.e. the potential users of the software you are going to build.

In this specific case, CAs and Professors are affected by this problem and will benefit the most from the proposed solution. More generally, any group of people that needs to meet but whose presence requires different priority weights will benefit from this software.  

## Proposed Solution
> Write a few sentences that describes how a software solution will solve the problem described above.

A software solution can take in everyone's schedules and priorities and then use an algorithm that picks a time "hot-spot" based on maxiumum number of people available to meet and the priorities. The goal is to determine if there is a tradeoff and  minimize the tradeoff between having as many people present at the meeting and also ensuring that the most prioritized people will be present at the suggested meeting time. 

## Functional Requirements
> List the (functional) requirements that software needs to have in order to solve the problem stated above. It is useful to write the requirements in form of **User Stories** and group them into those that are essential (must have), and those which are non-essential (but nice to have).


### Must have

As a CA, I want to view all the events that the Professor created.

As a CA, I want to register for an event.

As a CA, once I register for an event, I want to add my availability for the event. My availabilitiy can differ by the day of the week. 

As a CA, once I have submitted my availabilities for events, I want to view what my availability submissions for these events.

As a Professor, I want there to be a sign-in page so that only my CAs and myself can view and register for events. I don't want random users to corrupt the data being taken in by the platform; I want there to be a verification system to ensure that only my CAs and I can actually view, manipulate, and enter data into the platform.

As a Professor, I want the software to output an optimal meeting time based on the availability submissions. 

### Nice to have

As a professor, I want multiple meeting times suggested so that I have more than one option.

As a professor, I want to create a poll to gauge which of the multiple schedules that was produced is ideal if their are ties in priority so that I can make a subjective judgement call above and beyond the algorithm. 




## Software Architecture
> Will this be a Web/desktop/mobile (all, or some other kind of) application? Would it conform to the Client-Server software architecture?   

This will be a Web application; yes, it would conform it to the Client-Server software architecture. The CAs and Professors would input their schedule/time availabilities and their priorities; the Server would store these availabilities and priorities, perform an algorithm, and then send back a few "optimal" staff meeting times. 
