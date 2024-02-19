For Ubuntu Linux

Welcome to SchedulerApp! 
This is a command line based minimal meeting scheduler application. 
You will be presented with the following options when running: 

Select an option:
1. Create person
2. Schedule a meeting
3. Show meetings for person
4. Suggest meeting times for a group
5. Help
6. Exit application


Running SchedulerApp.jar: 
  - clone this repo or download as zip
  - navigate to folder container SchedulerApp.jar
  - run following command in terminal "java -jar SchedulerApp.jar"

note: jar file may be blocked by your filesystem. you may need to give it permission to be executed:
  - Right click SchedulerApp.jar -> Properties -> Permissions -> "Execute: Allow executing file as program" should be ticked.


Implementation compromises:
  - Meetings can only be scheduled on todays date, between 9 and 17
  - It's assumed that emails are input in a valid email format and do not include "," as a character
  - There has not been placed a limit on the number of attendees for a given meeting
