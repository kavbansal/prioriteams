# Summary
Prioriteams involved writing a large amount of code for both our front end and back end systems to interact properly. However, one of the largest lessons learned throughout this project was how important proper software practices are when designing and building out a scalable solution. The group initially considered a wide variety of designs for the look of our web application and put a great deal of effort into creating a functional relationship between our models, tables, and views. While there was a lot of change throughout the 5 iterations which is to be expected when working as an agile team, we also quickly realized how important it was that we laid a solid foundation on which we could begin expanding our code base. Another large lesson that was learned in creating Prioriteams was the importance of front end presentation. While creating the algorithm was a pivotal part of our minimum viable product, our advisors made it clear that it was critical to also have a view and “feel” about our application that would be inviting and welcoming to new users. Though we had our struggles in this regard, we learned the importance of it and did our best to improve upon our existing skills as much as possible. 

# Project Strengths
One of our strengths throughout the project was setting and accomplishing goals each iteration by dividing larger features into a series of smaller tasks. This enabled us to collaborate efficiently and effectively while progressively building upon our existing application at every step of the journey. Successful and frequent communication among all group members helped us ensure that everyone was on the same page at all stages. Over the course of the project as we gained momentum we all developed greater motivation and enthusiasm for the project, which helped us make especially great progress in our later iterations.

# Challenges Faced

During this semester of OOSE, we started a bit slowly off the block and also had some disagreements with our previous advisor. At the start of the semester, we were a bit noncommittal over what we wanted to work on during the course. This hesitance impacted our enthusiasm and our motivation initially. Also, getting off on the wrong foot with our advisor compounded our troubles at the beginning of the course. However, we were able to bounce back and hone in on what we wanted to accomplish. 

In terms of code-related challenges, we had some challenges handling the various edge cases related to the scheduling algorithm. Even by constraining the problem at hand, it was tough to see the possible pit-falls in our code. Additionally, it was a challenge to keep the code organized, the classes simple, the flow smooth, and to abide by software design principles. We feel that we managed to make the flow of our application be quite smooth and intuitive. 

Another challenge we faced during this semester was caused by the sudden transition of the semester to be remote. Working together remotely instead of being able to work together in person was quite a challenge just because of the unfamiliarity of the situation and the various technological hinderances. 

# User Stories Delivered
* “As a CA, I want to view all the events that the Professor created so that I can keep track of my commitments.”
  - We were able to deliver this user story by creating a page for a CA such that the CA can view which events they have been invited to in the form of cards that state the event’s name, location, and duration. When the user wants to see what time the event is most likely to meet, they can proceed to another page to calculate the optimal time for any of the events. 

* “As a CA, I want to register for an event so that my availability may be taken into consideration.”
  - As mentioned above, users are able to see cards for the events in which the professor invited them. When they click on the card, they have the option to enter their availability for the event such that they can log it for any given week, for any number of hours within that week. They are given a small graph of checkboxes that specify different hours in the day for all 7 days of the week, which they can select to indicate that they are available for a given event at that time. A person’s availability for a given event is also not reflected in their availability for other events, so users can commit to numerous different meetings at their own convenience. 

* “As a CA, once I register for an event, I want to easily add my availability for the event so that finding a meeting time is as simple as possible. My availability can differ by the day of the week.”
  - See above, as we allow for users to enter their availability as they wish for different days of the week, and different hours of the day. 

* “As a CA, once I have submitted my availabilities for events, I want to view my availability submissions for these events so that I can confirm they are correct.”
  - Once a user submits their availability, they are able to see their availability for each day of the week listed below the event description for each day of the week. 

*“As a Professor, I want to be able to insert into the website the full names, emails, priorities, and temporary username/password combination of the CAs and myself so that collecting availabilities is as simple as possible.”
  - This user story was slightly modified as we progressed through our fourth iteration. A professor is able to invite a user via email now, as opposed to having to list their full names and priorities. We made this design choice because we later on allow a professor to modify a CA’s priority when they are viewing an event before calculating optimal meeting time - therefore, this would be an unnecessary step for the professor to take. We also felt that listing names and temporary usernames / passwords was unnecessary. This is because in one of our tables, we ensured that an email was a unique identifier for a person who has a Prioriteams account. Therefore, the less information the professor had to provide, the better. 

* “As a Professor, I want there to be a sign-in page so that only my CAs and myself can view and register for events. I don't want random users to corrupt the data being taken in by the platform; I want there to be a verification system using the information entered in the User Story above to ensure that only my CAs and I can actually view, manipulate, and enter data into the platform.”
  - The sign in page was an easy implementation where we essentially query a username and password combination when a potential user attempts to log in. This prevents users without credentials from getting authentication and having access to the views of a professor or CA. 

* “As a Professor, I want the software to output an optimal meeting time based on the availability submissions and priorities so that as many people as possible can attend the event, particularly those of higher priorities.”
  - Our algorithm takes in the availabilities of an event and determine the optimal time based on the priorities. We have certain assumptions and constraints on this problem that we will discuss in our presentation and that our advisor, Yash, knows about.

# User Stories Not Delivered

* “As a CA or a Professor, once I am signed-in, I want there to be the option to alter my username/password combination.”
  - This is a user story that we did not get to, as we felt that this was not an important part of our web application’s MVP. 

* “As a professor, I want multiple meeting times suggested so that I have more than one option.”
  - We wanted to just add more flexibility to the application. We didn’t want to constraint or restrict the Professor’s options. This user story is something we would have worked on if there was more time. 

* “As a professor, I want to create a poll to gauge which of the multiple schedules that was produced is ideal if there are ties in priority so that I can make a subjective judgement call above and beyond the algorithm.”
  - The implementation of a poll would require implementation of the above user story, which was not conducive to the way our algorithm was originally constructed. 

# Reflection and Looking Ahead

Looking back, we would definitely want to start the semester off in a better fashion. More specifically, we would spend more time and be more rigorous in the brainstorming/planning and design aspects of the project early on. Looking back, it is easy to see how having better thought out plans for code structure and better defined tasks for the application would have made it easier to transition into coding and allowed us to see challenges early on. Additionally, as we touched upon above, we would have liked to better abide by software design principles and keep our code better organized. In a similar theme, we would also have done more work in the first half of the course. Though we are happy with what we have done, we had to put in quite a bit of additional work in the second half of the semester to make up for a poor start. 

If we had more time, we would want to implement a way for our algorithm to handle location conflicts when deciding optimal times for various events. Additionally, we would have attempted to implement some feature where entering and viewing availabilities was easier and resembled something like that of When2Meet. We might also like to enable a feature such that an event could be a one-off instead of a recurring meeting. It would also be interesting to build upon our existing invitation system to allow users to have different priorities for different events.

Another thing that could have been considered if we were given more time is making our login protocol more secure. Currently, if a user is able to correctly decipher a username and password, they can view the same page as the actual user with the correct credentials could see. On the flipside, when a user is logged in with the correct credentials, some of his information is compromised when he logs in and is navigating through the webpage. In order to make our web application more secure with more time, we would have incorporated some sort of hashing function that allows users to uniquely hash their username and password combination and store it safely as they navigate Prioriteams such that only our servers can perform a symmetric operation and verify a person’s identity. 
