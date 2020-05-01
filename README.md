# 2020-spring-group-Lads
Group project
CA Staff Meeting Time

Currently, scheudling apps lilke When2Meet and Calendly find the best times for groups to meet based on the number of people that can meet at once. However, for events such as Staff Meetings for CAs, the optimal meeting time may not be the time frame in which the most people can meet if, for example, the Head CAs cannot be present. Thus, our software application will address the issue of finding the optimal meeting time given that certain individuals' presence at the meeting will take higher priority.

# Iteration 4:

FrontEnd - https://guarded-tor-51281.herokuapp.com/
BackEnd - https://cryptic-depths-43982.herokuapp.com/

Below is a table of the people we have hard-coded into the software and their associated priorities, id’s, usernames, and passwords. 
| Name         | Priority | ID | Username  | Password       |
|--------------|----------|----|-----------|----------------|
| Prof Madooei | 1        | 1  | amadooei  | ali            |
| Yash         | 2        | 2  | yash      | kumar          |
| Adeshola     | 2        | 3  | adeshola1 | adesholaJHU    |
| Shreya       | 2        | 4  | shreya123 | shreya_hopkins |
| Irfan        | 3        | 5  | ijamil1   | irfan          |
| Vishnu       | 3        | 6  | vjoshi6   | vishnu         |
| Ryan         | 3        | 7  | rhubley1  | ryan           |
| Dara         | 3        | 8  | dmoini1   | dara           |
| Kavan        | 3        | 9  | kbansal2  | kavan          |
| Justin       | 3        | 10 | jsong1    | justin         |


Below are steps that guides one through our application. Following these steps will allow one to view all the features of the product. (Steps 1-4 are for running locally, can skip if using Heroku link).

1. Open both the FrontEnd and BackEnd projects in different intelliJ windows.

2. In the src directory of the BackEnd, navigate to the ApiServer java file and run the main method to get the Api server up and running.

3. In the src directory of the FrontEnd, navigate to the WebServer java file and run the main method of that to host the web server.

4. After both servers are running, open a web browser and type the following url - http://localhost:4567/

5. You are now at our home page. 

6. Create an event. Enter availabilities for the event. Calculate the optimal time for the event. Below, we explain/walk-thru how this can be done. We also describe how the algorithm computes optimal times. 

# Algorithm Example & Explanation
First login as a Professor (Prof Madooei's username is "amadooei", password is "ali"
Create an event that is named and located whatever and wherever. However, specify that the duration is 1 hour. This is for the purpose of the example. Also make sure all users are invited to the event.

All availability registrations, which you will soon create, for the event that you created will be on the same day, Monday. It eases exposition of the algorithm, though the algorithm has also been tested and proven to work for more complex examples. 

Now, enter the following 10 availabilities. Entering an availability can be done on the “Register” page for an event. You will need to log in each as each person to submit their availability. 

| Person   | Event ID | Start Time | End Time | Day of Week |
|----------|----------|------------|----------|-------------|
| Ali      | 3        | 12 pm      | 7 pm     | Monday      |
| Yash     | 3        | 11 am      | 12 pm    | Monday      |
| Adeshola | 3        | 3 pm       | 6 pm     | Monday      |
| Shreya   | 3        | 2 pm       | 4 pm     | Monday      |
| Irfan    | 3        | 4 pm       | 8 pm     | Monday      |
| Vishnu   | 3        | 12 pm      | 7 pm     | Monday      |
| Ryan     | 3        | 1 pm       | 5 pm     | Monday      |
| Dara     | 3        | 3 pm       | 5 pm     | Monday      |
| Kavan    | 3        | 1 pm       | 3 pm     | Monday      |
| Justin   | 3        | 5 pm       | 8 pm     | Monday      |

After entering the above, navigate to the “View Events” page.  If you enter the “Event Id” for the event that you created which is simply the position of the event on the list of events that is displayed on the page you are on and click “Calculate Optimal Time for this Event”, the optimal time for your event will be calculated and displayed. For this example, the optimal time should be 15 (3 pm) on day 1. 

The algorithm proceeds in the following manner (Note, we convert times into military time format):

1. Loop through each of the Professor’s availabilities for the event at hand. If one of the availability submissions is less than the duration of the event, we skip over this availability and essentially consider it to be an invalid availability submission. 

2. For each of the Professor’s availabilities (assuming, now, that it is at least as long as the duration of the event), loop through each of the possible sub-intervals within the availability. For example, if the event is 1 hour long and the current availability of the Professor’s that we are looping over is 12-19, then we will loop through: (12-13, 13-14, 14-15, 15-16, 16-17, 17-18, and 18-19). The stride of the sub-intervals that we loop over is 1 hour, which is independent of the actual duration of the event. For example, if the event is 2 hours long and the current availability of the Professor’s that we are looping over is 12-19, then we will loop through: (12-14, 13-15, 14-16, 15-17, 16-18, 17-19). 

3. For each of these subintervals, we calculate the score of the subinterval. We do this as follows. We loop through all of the availabilities for this event besides the Professor’s. If an availability that we are looping through is on the same day of the week as the current availability of the Professor’s that we are looping through AND the availability we are looping through has a start time at least as early as the subinterval we are evaluating and an end time at least as late as the subinterval we are evaluating (ie: available for the ENTIRE duration of the subinterval which is equivalent to the length of the event), we add the term (100/person’s priority) to the score of the subinterval. The person’s priority corresponds to the priority of the person whose availability it is that actually corresponds to the availability we are evaluating in the for loop described in this step. 

4. The start time and day of the week of the sub-interval with the highest score is the optimal time. Note, the Professor is guaranteed to be available for the duration of the sub-interval. 


# Deploying with Heroku

The deployment with heroku follows similar guidelines to those mentioned on the jhu OOSE website.
1. Download (don't clone) this git repo with the most recent branch
2. Build the jar files for both front end and back end by using -  ./gradlew build jar
  a. Before building the FrontEnd jar, change the URLs in each of the DAO classes to the heroku url of the BackEnd.
3. In each of the project folders do the following - 
  a. git init
  b. heroku create
  c. ./gradlew build deployHeroku
