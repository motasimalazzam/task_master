# Taskmaster

## Overview

This repository includes an Android app that will be the main focus of the second half of the 401 course. over time this will grow to be a fully-featured application.

## Architecture

The programing language used to build this project is java and using the Android Studio to complete it.

**Author: Motasim Al-Azzam

## Lab: 26 - Beginning TaskMaster

This lab is an introduction to Android and how to use the Android Studio. In this lab I create three activities, The Main Activity, Add Task Activity and All Task Activity.

### Main Activity

It is the home page which contains an image and two buttons each one starts a new activity when click on it.

![Home page](screenshots/mainActivity-lab-26.jpg)

### Add Task Activity

When the user click on `ADD TASK` button from home page it will take The user to this page(*Add Task Page*). In this page the user can add a task title and a description and after that can click on th `ADD TASK` button.

![Add task page](screenshots/addTaskActivity-lab26.jpg)

When the user click on the `ADD TASK` button it will show a message that the task is submitted.

![Submitted task](screenshots/submittedTask-lab-26.jpg)

### All Task Activity

When the user click on `ALL TASK` button from home page it will take The user to this page(*All Task Page*). This page contains an image.

![All task page](screenshots/allTaskActivity-lab-26.jpg)

## Lab: 27 - Data in TaskMaster

In This lab added three buttons on the main page, each button for one task that takes the user to the task detail page when the user clicks on it. Also, added a setting button on the home page that takes the user to the setting page.

### Setting Activity

This page contains a fild which the user can put his/her name and click on save buttons to save the name.

![setting page](screenshots/settingActivity-lab-27.jpg)

### Main Activity

The home page contains new four buttons. The first three buttons for tasks, Each button takes the user to a different task detail page. and the fourth button for the setting page that I showed you above. Also, the name that the user wrote on the setting page appears at the top of the main page.

![main page](screenshots/mainActivity-lab-27.jpg)

### Task Detail Activity

Task detail page appears when the user click on each task on the home page.

![Task1 detail page](screenshots/taskDetailActivity-1-lab-27.jpg)

![Task2 detail page](screenshots/taskDetailActivity-2-lab-27.jpg)

![Task3 detail page](screenshots/taskDetailActivity-3-lab-27.jpg)

## Lab: 28 - RecyclerView

In this lab I used the `RecyclerView` to view all tasks in the home page as the list. 

### Main Activity

I refactored the homepage to show all the tasks as the list and the user can scroll down or up to view the tasks.

![RecyclerView list](screenshots/recyclerview-mainActivity-lab-28.jpg)

And also when the user clicks on any task, it will move him/her to the task details page.

![details page](screenshots/recyclerview-lab-28.jpg)

## Lab: 29 - Room

In this lab, I added a Room database to save the tasks and the details of tasks and get data from it, and let the recycler view take the data from the room database. 

### Add Task Activity

On this page, Added a new field in which can the user writes the state of the task.

![state of task](screenshots/addTaskActivity-lab-29.jpg)

Also added a spinner in which the user can select one of the choices for the image of the task.

![spinner](screenshots/spinner-lab-29.jpg)

### Main Activity

This is the home page and it contains tasks and each task has an image depends on the user's choice from the spinner. Also, the user can delete the task by click on the delete.

![home page1](screenshots/homepage-lab-29.jpg)

![home page2](screenshots/homepage2-lab-29.jpg)

### All Task Activity

Refactored this page to show all user tasks.

![all tasks](screenshots/allTaskActicity-lab-29.jpg) 

## Lab 31: Espresso and Polish

* In This lab I tets the code by using Espresso test.

* I created 4 test:

1. `testAddTask()`

To test the important UI elements are displayed on the Add Task page.

2. `assertTextChanged()`

To test if you edit the user’s username, and then assert that it says the correct thing on the homepage.

3. `testOpenTaskDetail()`

To test when you tap on a task, and then assert that the resulting activity displays the title, body and state of that task in task detail page.

4. `addTaskAndCheckItInTheList()`

To test if you can add a new task then when you tap on this task, and assert that the resulting activity displays the title, body and state of that task in task detail page. 

![EsspressoTest](screenshots/EspressoTest-lab-31.png)

## Lab 32: Amplify and DynamoDB

In this lab I implemented **AWS amplify** to access the data in **DynamoDB** insted of **Room**.

### Add Task Activity

Now when the user add new task in the add task page, The task will stor in the **DynamoDb** data base and also in the room to reach it if the user offline.

### Main Activity

I refactored the homepage's **RecyclerView** to display all Task entities in **DynamoDB**.

![tasks In Homepage](screenshots/mainActivity-lab-32.jpg)

![tasks In Homepage](screenshots/mainActivity2-lab-32.jpg)

* This is screenshot for the tasks in the **DynamoDb**

![tasks in DynamoDB](screenshots/TasksInDynamoDB-lab-32.png)

### All Task Activity

On the all task page the user can check the tasks when he/she is offline Because on this page I used the room to display the tasks.

![tasks in room](screenshots/allTaskActivity-lab-32.jpg)

## Lab 33: Related Data

### Add Task Activity

Modify the Add Task form to include either a Spinner for which team that task belongs to.

![Add Task Page](screenshots/addTaskActivity-lab-33.jpg)

### Setting Activity

In addition to a username, allow the user to choose their team on the Settings page. Use that Team to display only that team’s tasks on the homepage.

![settings page](screenshots/settingsActivity-lab-33.jpg)

### ### Main Activity

When the user chooses the team, the tasks will display on the home page, and at the top of the page, the team name will display.

![201 tasks](screenshots/mainActivity201-lab-33.jpg)

![301 tasks](screenshots/mainActivity301-lab-33.jpg)

![401 tasks](screenshots/mainActivity401-lab-33.jpg)

* Picturs below for **DynamoDB**:

![teams Tabl](screenshots/TeamsTable-lab-33.png)

![Tasks Table](screenshots/TasksTable-lab-33.png)


## Lab: 36 - Cognito

### Sign Up page 

we have this page to permit the user to make a new account.

![signup](https://github.com/motasimalazzam/task_master/blob/lab-36/screenshots/lab36(2))


### Verification Page

after the user signup he/she will receved a verification code on his/her Email and in this page they should write that code.

![ver](https://github.com/motasimalazzam/task_master/blob/lab-36/screenshots/lab36(4))

### Signin Page

On this page, after they have an account they can use this page to login into the application.

![login](https://github.com/motasimalazzam/task_master/blob/lab-36/screenshots/lab36(3))

### Main Page

Every user can see his/her username in the nav bar in the Main Page.

![main](https://github.com/motasimalazzam/task_master/blob/lab-36/screenshots/mainActivity-lab-36.jpg)

### Cognito

Here We can see every user thay have an account

![Cognito](https://github.com/motasimalazzam/task_master/blob/lab-36/screenshots/lab36(1))
