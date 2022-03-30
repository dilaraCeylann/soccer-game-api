# Project Name
> Soccer Online Managemenet API

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Prerequisite](#prerequisite)
* [Get Started](#prerequisite)
* [Usage](#usage)

## General Information
- Soccer Online Manager Game API 
- With this application, new users, players, teams can be defined. Various player transfers, purchases and sales can be made.


## Technologies Used 
- Java-8 
- Hibernate
- Spring Boot
- Spring Security
- JWT
- MapStruct
- Swagger
- Integration Test


## Features 
List the ready features here:
- Create , update and delete team information.
- Create , update and delete player information.
- Register and authenticate with admin and user role.
- You can put the player in your team on the transfer list.
- You can take the player on the transfer list to your team.


## Prerequisite

* Java-8
* Maven
* Mysql

## Get Started

* I added the database script to the necessary processes folder in the project. Before running the application, you can run the database script.

* You can change the fields below in Application.properties with your own database information.

    spring.datasource.url= jdbc:mysql://localhost:3307/soccer_game?useSSL=false&useUnicode=true&characterEncoding=UTF8&useLegacyDatetimeCode=false&serverTimezone=Turkey?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Turkey
    spring.datasource.username=root
    spring.datasource.password=
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
* Other in-app configuration values are below.

	    team.initial.price = 5000000
	    player.initial.value=1000000
	    player.initial.goalkeeperSize=3
	    player.initial.defenderSize=6
	    player.initial.midfielderSize=6
	    player.initial.attackerSize=5d
	
* If there is no record in the role table when the application is up, it will automatically create the roles ROLE_ADMIN and ROLE_USER. From here, you can sign-up and start.
   
## Usage

I added postman request examples to the necessary processes folder to test the application. You can try it here or you can test it from swagger with the url below. If you change the port, you should change the 8080 in the url.</br> 

	    http://localhost:8080/swagger-ui/index.html
