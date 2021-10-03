# User Event Scheduler Web App
## Overview
This is a web app that enables users to create personal events and meetings with other users. All users can schedule personal events and some partner meetings with another user. Users who have manager authentication can also schedule team meetings involving multiple users. When events/meetings get schduled, notification emails will be automatically sent to users who got invited. All users can also view upcoming events and update events information for events which are created by themselves. For account management, only users with admininstration authemtication can view all user accounts' information and make changes to fields of these acounts. 
## Frameworks
For this project, I made use of the spring framework and hibernate. JSP and Spring MVC are used to build the front end and the back end controller of the app. Hibernate is used to create the DAO that communicate the databases (set up with MySQL) and the app interface. To implement the user account system, including registration, login, logout, and reset password functionalities, we used the Spring Security framework. 
## Database
<img src="database_structure.jpg" width="500" height="300"/> 
The tables are build like the graph above. Meeting & user and user & role relationships are all many to many relationships. Here the roles indicate the level of authetication that each type of account can have when using the app. 
