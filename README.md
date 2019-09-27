# Wildlife Tracker

## Author

*Uwimana Rachel*

## Description

This is a simple java application that allows Rangers to track wildlife sightings in a given area and tracks wildlife categories such as all the animals and the endangered ones.

### Technologies used

* JAVA 
* HTML
* CSS and (BOOTSTRAP)
* Spark (Java framework)
* Junit
* Postgres SQL (Database)

## contacts

**urakhel7@gmail.com**

## Installation tips/ Setup instruction

* Clone this repository.
* Install  Postgres SQL.
* re-create my databases, columns, and tables by running the following commands in the terminal

  * psql (make sure that you have installed Postgres SQL)
  * CREATE DATABASE wildlife_tracker;
  * CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker
  * CREATE TABLE animals (id serial PRIMARY KEY, name varchar, health varchar, age varchar, endangered boolean)
  * CREATE TABLE sightings (id serial PRIMARY KEY, location varchar, rangerName varchar)
 
 
## A Little bit about how my project looks like

![Homepage](src/main/resources/public/img1.png)
![Add animals](src/main/resources/public/img2.png)
  
## LICENSE 

MIT License

Copyright (c) [2019] [Uwimana Rachel]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

