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
Service level:
In signup function, if username or password is null, or username is not in email format, or username has been registered, function will return false. Otherwise, the function will return true.
In login function, if username or password is wrong, or username doesn't exist, it will return false, otherwise, the function will return true.
In nameValidation function, if username to be registered is not in email format, return false, otherwise, return true.
In isPasswordStrong function, if password to be registered is not strong, return false, otherwise, return true.
In passwordEncrypt function, password has been encrypted successfully because password is not same as what it was after the encrypt function is called.
In isPasswordCorrect function, if users type in the right password when logging in, it returns true, otherwise, returns false.
Dao level:
In getAllUsers function, it returns not null list of users when there are users in database.
In getUserByUsername function, it returns a user with the username it registers.
In updateUser function, it updates a user's information.
In insertUser function, it increase a record of a new user in database.
In deleteUserByUsername function, it decreases a user's record by the user's username in database.
## CI/CD


