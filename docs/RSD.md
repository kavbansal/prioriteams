# Requirement Specification Document

## Problem Statement 

> Write a few sentences that describes the problem you are trying to solve. In other words, justify why this software project is needed.

Existing scheduling services and platforms do not factor in priority. Many of these software solutions simply involve finding times in which there the highest number of people can attend. An example is the scheduling of CA staff meeting during the course of a semester. Naturally, it is most pertinent that the Head CA(s) and the Professor(s) are present at the meeting since they are the main stakeholders in directing the class. Ideally, the meeting time that will be chosen will be one at which all of the Head CA(s) and Professor(s) are present as well as the maximum number of other CAs. We will create a software solution that will find these optimal times in a sorted fashion. 


## Potential Clients
> Who are affected by this problem (and would benefit from the proposed solution)? I.e. the potential users of the software you are going to build.

In this specific case, CAs and Professors are affected by this problem and will benefit the most from the proposed solution. More generally, any group of people that needs to meet but whose presence requires different priority weights will benefit from this software.  

## Proposed Solution
> Write a few sentences that describes how a software solution will solve the problem described above.

A software solution can take in everyone's schedules and priorities. It will then use an algorithm that picks a time "hot-spot" based on maxiumum number of people available to meet and the priorities. 

## Functional Requirements
> List the (functional) requirements that software needs to have in order to solve the problem stated above. It is useful to write the requirements in form of **User Stories** and group them into those that are essential (must have), and those which are non-essential (but nice to have).


### Must have

As a professor, I want to be able to assign priority to different peoples’ schedules so my time can be valued more.

As a CA, I want to be able to enter my availability for staff meetings. 

As a Professor, I want to schedule meetings such that as many of the head CAs can meet with me, along with as many other CAs as possible after that. 

As a Professor, I need to specify how long the meeting needs to last. 

As a Professor, I want to be able to enter my availability. 




### Nice to have

As an professor, I want to be able to send out emails to confirm meeting time so that I can stay organized with my hires easily. 

As a professor, I want multiple meeting times suggested so that I have more than one option.




## Software Architecture
> Will this be a Web/desktop/mobile (all, or some other kind of) application? Would it conform to the Client-Server software architecture? 

This will be a Web application; yes, it would conform it to the Client-Server software architecture. The CAs and Professors would input their schedule/time availabilities and their priorities; the Server would store these availabilities and priorities, perform an algorithm, and then send back a few "optimal" staff meeting times. 
