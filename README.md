# School Management System JavaFX
Previews

![](screenshots/bandicam%202020-06-21%2022-50-26-573.jpg) 

![](screenshots/bandicam%202020-06-21%2022-52-09-029.jpg)

![](screenshots/bandicam%202020-06-21%2022-53-33-500.jpg)

![](screenshots/bandicam%202020-06-21%2022-53-55-710.jpg)

![](screenshots/bandicam%202020-06-21%2022-55-12-582.jpg)

![](screenshots/bandicam%202020-06-21%2022-55-25-004.jpg)

![](screenshots/bandicam%202020-06-21%2022-56-42-952.jpg)


 <b>This project was written in kotlin and java</b>
 
 
### Libraries

This project uses a number of libraries:
=> javafx <p/>
=> Jfoenix <p/>
=> Sping framework <p/>
=> Apache Common Codecs <p/>
=> Mysql connector 8<p/>
=> Kotlin lang <p/>


This project is written in java 8, however it can be run also in the future release of java with no syntax error or problem (9, 11, 12, 13, 14 #current release)
to run this project in java 9-14 you need to set up javafx sdk and use VMOptions to set it to path. The <b>schoolmanagement.Main</b> class is the main class to run.


//Database. 
Concerning database, you are going to setup your database connection url in the following spring config.xml file : <b>schoolmanagement.resource.config.config.xml</b>

there is a folder in the projects directory called <b>db database</b> that is the my database that i backup. so enter your mysql/phpmyadmin/mysql workshop and import the backup file. so it wll load all the tables and database for you,

The passwprd for the admin is :
<strong><b>usernme: admin</b></strong>
<strong><b>password: admin</b></strong>

or go straight ahead and create your own user by sighning up, then going to mysql and <b> alter table users set role = 'admin' where username = 'your user name' <b/>

Note: *Dont forget to edit the schoolmanagement.resource.config.xml file , make sure you change the connection string to match your local host / db connection url, and need to be mysql*

uing any db different than mysql will result to changing all the mysql syntax in the application to the current db syntax that you are using