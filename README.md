# CSYE 6225 - Spring 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
| Junyi Fang | 001495265 | fang.ju@husky.neu.edu  |
|  Ziyan Zhu | 001461543 | zhu.ziy@husky.neu.edu  |
|   Lei Liu  | 001443309 | liu.lei1@husky.neu.edu |


## Technology Stack
Spring boot + MyBatis + Maven + MySQL

## Build Instructions
To build the project, you should have:
+ Java SE 1.8.0
+ MySQL
+ IDE that support Maven and Apache Tomcat (e.g.Intellij IDEA)
installed and set up on your local system.

## Deploy Instructions
1. Download or clone the project from the [repository](https://github.com/muffinffff/csye6225-spring2019)
2. Open the NodeTaking project (csye6225-spring2019/webapp/NoteTaking) with your IDE and import the dependencies following the instructions (if any) given by the IDE
3. Make sure to set your Java SDK
4. Set up the username and password of your MySQL in **src/main/java/resourse/application.properties**
5. Create a database named _csye6225_ and build a table named _user_ with structure:
| ColumnName | Datatype | PK | Null | Binary |
| --- | --- | --- |
| username | varchar(255) | yes | no | yes |
| password | varchar(255) | no | no | yes |

## Running Tests


## CI/CD


