# Wildlife Tracker

## Author

**By Uwimana Rachel**

## Description

This is a simple java application that allows Rangers to track wildlife sightings in a given area and tracks wildlife categories such as all the animals and the endangered ones.

### Technologies used

* JAVA 
* HTML
* CSS (BOOTSTRAP)
* Spark (Java framework)
* Junit
* Postgres SQL (Database)


## Installation tips/ Setup instruction

* Clone this repository.
* Install  Postgres SQL.
* re-create my databases, columns, and tables by running the following commands in the terminal

  * psql (make sure that you have installed Postgres SQL)
  * CREATE DATABASE wildlife_tracker;
  * CREATE TABLE animals (id serial PRIMARY KEY, name varchar)
  * CREATE TABLE sightings (id serial PRIMARY KEY, location varchar, rangerName varchar)
  * CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker
  
  
## LICENSE

This website is under the MIT license. Copyright (c) 2019 Rachel