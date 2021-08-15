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

## Lab: 29 - Room

In this lab, I added a Room database to save the tasks and the details of tasks and get data from it, and let the recycler view take the data from the room database. 

### Add Task Activity

On this page, Added a new field in which can the user writes the state of the task.

![state of task](screenshots/addTaskActivity-lab-29.jpg)

Also added a spinner in which the user can select one of the choices for the image of the task.

![spinner](screenshots/spinner-lab-29.jpg)

### Main Activity

 This is the home page and it conains tasks and each task has an image depends on the user choise from the spinner.

![home page1](screenshots/homepage-lab-29.jpg)

![home page2](screenshots/homepage2-lab-29.jpg)

### All Task Activity

Refactored this page to show all user tasks.

![all tasks](screenshots/allTaskActicity-lab-29.jpg) 