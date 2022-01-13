# MyPosts
Is a timeline application, where each user has an account and can post a text and/or an image. Each post contacts the users name, username, and profile photo.

## **Introduction**

The application is developed to meet the requirments for Tuwaiq 1000 challenge 
* Implementing login and signup feature using Firebase authenticatio
* Using Firestore to store all posts to retireve and sort by post time
* Posts can have either text, photo, or both.

### Install

#### Command Line

Open terminal app and navigate to project folders

`$ cd /Users/user/project_folders`

Clone project repository

`$ git clone https://github.com/joud-almahdi/Project-01.git`

## **Prototype**

An example design to follow for the application

![](https://paper-attachments.dropbox.com/s_00D43E3A5A0D8FBD5D571BAF731F71582FFFE3B75BFD8D60794F2E448BDFD5F0_1641966412309_image.png)

![](https://paper-attachments.dropbox.com/s_00D43E3A5A0D8FBD5D571BAF731F71582FFFE3B75BFD8D60794F2E448BDFD5F0_1641966458297_image.png)

## **Flowchart**

The following section covers the flow of the application

### Slash

Using Lottie animation to make a good first impression to the users

<img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/Splash.jpg?raw=true" width="200" />

### Login and Sign up

A user can either login or sign up to the application. If the password is forgotten, then the user can reset it.

<img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/Login.jpg?raw=true" width="200" /> <img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/SignUp.jpg?raw=true" width="200" />                                                                                                                           
### View Posts

All post by all user are shown from the latest to the oldest. Users can like posts or share them with others.
                                                                                                                                    
<img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/Home_ViewPosts.jpg?raw=true" width="200" />

### Add Post

A user can add new post and include with a text, an image, or both, which then gets added in the View Posts interface in a timeline order.

<img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/Home_AddPosts.jpg?raw=true" width="200" /> <img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/Home_AddPosts_Image.jpg?raw=true" width="200" /> 

### User Profile

A user can view their own information and view the date they joined the application
                                                                                                                                                   
<img src="https://github.com/joud-almahdi/Project-01/blob/main/app/src/main/res/mipmap-mdpi/Home_Profile.jpg?raw=true" width="200" />

## Technology
* Firebase Authentication: Allow authentication to easily implemnted and track user login status.
* Firebase FireStore: To store posts, and additional user information. Any data retireved can be cached for offline access.
* Firebase FireStorage: To store photos online and link the download Uri to the prespective document

## Sources
* [Twuaiq](https://github.com/Twuaiq-1000-Kotlin-01/Project-01)
* [Firebase](https://firebase.google.com/?gclid=Cj0KCQiAuP-OBhDqARIsAD4XHpfRVOGWcZtcz6Cl6Jwz5WfPycv9G3G8S1yrn9g1n4fzBJ4Ae44Xh_oaAoeUEALw_wcB&gclsrc=aw.ds)

## Licence
Project has not been licenced 

