# CSYE 6225 - Spring 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
| Junyi Fang | 001495265 | fang.ju@husky.neu.edu  |
|  Ziyan Zhu | 001461543 | zhu.ziy@husky.neu.edu  |
|   Lei Liu  | 001443309 | liu.lei1@husky.neu.edu |
| | | |

## Technology Stack
Spring boot + MyBatis + Maven + MySQL

## Build Instructions


## Deploy Instructions


## Running Tests
Junit needs to be imported for running Junit tests.

Service level:

1.testSignup function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), run the test. If four assertion functions satisfy, this test passes.

2.testLogin function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), run the test. If one assertion function satisfies, this test passes.

3.nameValidation function:
Run the test. If four assertion functions satisfy, this test passes.

4.isPasswordStrong function:
Run the test. If three assertion functions satisfy, this test passes.

5.passwordEncrypt function:
Run the test. If one assertion function satisfies, this test passes.

6.isUserRegistered function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), run the test. If two assertion functions satisfy, this test passes.

7.isPasswordCorrect function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), run the test. If two assertion functions satisfy, this test passes.

Dao level:

1.testGetAllUsers function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), insert no less then one record in the database, run the test. If one assertion function satisfies, this test passes.

2.testGetUserByUsername function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), insert one record with username is "aaa" in the database, run the test. If one assertion function satisfies, this test passes.

3.testUpdateUser function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), insert one record with username is "aaa" in the database, run the test. If two assertion functions satisfy, this test passes.

4.testInsertUser function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), make sure no user with username "123@qq.com" exists in the database, run the test. If one assertion function satisfies, this test passes.

5.testDeleteUserByUsername function:
Create database and table with attributes of username(VARCHAR) and password(VARCHAR), insert one record with username is "zzy" in the database, run the test. If two assertion functions satisfy, this test passes.

## CI/CD


