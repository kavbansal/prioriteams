# 2020-spring-group-Lads
Group project
CA Staff Meeting Time

Currently, scheudling apps lilke When2Meet and Calendly find the best times for groups to meet based on the number of people that can meet at once. However, for events such as Staff Meetings for CAs, the optimal meeting time may not be the time frame in which the most people can meet if, for example, the Head CAs cannot be present. Thus, our software application will address the issue of finding the optimal meeting time given that certain individuals' presence at the meeting will take higher priority.

# Iteration 2:
Below are steps that guides one through our product. Following these steps will allow one to view all the features of the product. 

1. Open both the FrontEnd and BackEnd projects in different intelliJ windows.

2. In the src directory of the BackEnd, navigate to the ApiServer java file and run the main method to get the Api server up and running.

3. In the src directory of the FrontEnd, navigate to the WebServer java file and run the main method of that to host the web server.

4. After both servers are running, open a web browser and type the following url - http://localhost:4567/

5. You are now at our home page. You can select the "Create an Event" button to navigate to another page in which you'll be able to fill in the details of an event you are creating. 

6. Assuming you are at http://localhost:4567/create, you can enter the name of your event, the location, and the duration and then hit submit for your event to be created. Note, from this page, you can view all the events that have been created, you can go back to the home page from step 4, or you can register for an event.

7. Let's view all events. Assuming you are at http://localhost:4567/events, you can now see all the events that have been created. Each event has an event ID which is simply the position of the event in the list on the page (starting at 1). 

8. Now, let's click on the link that will let us register for an event. 

9. Assuming you are at http://localhost:4567/register, you can see all the created events, but you can also now register for an event. One of the features of this page is that you can see previously entered availabilities for any event. It is important to note that there are a few availabilities that have been hard-coded in on our end that you may not have entered! Now, you should see a form in which you can enter your own personId and the eventId of the event that you intend to register for. Beneath this, you can toggle the day of the week for which you are entering your availability and then a form in which you can select the times for when you are available. Once you hit submit, the details of your submission will appear in the form towards the top of the page that lists all availabilities for any event. 

10. Let's click on the link that takes us back to the View Events page. Enter the event Id for which you want an optimal meeting time and then hit calculate. Our algorithm looks at the availabilties that correspond to the event you specified and selects a random time from the times people deemed they were available to be optimal. Obviously, this is not optimal; it is just a crude/preliminary algorithm that outputs an answer.

11. That's our product thus far!
