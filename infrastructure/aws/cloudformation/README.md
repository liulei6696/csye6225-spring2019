# CSYE 6225 - Spring 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
| Junyi Fang | 001495265 | fang.ju@husky.neu.edu  |
|  Ziyan Zhu | 001461543 | zhu.ziy@husky.neu.edu  |
|   Lei Liu  | 001443309 | liu.lei1@husky.neu.edu |

## Run Instructions: Create Network Stack
To create AWS infrastructure resources with AWS CloudFormation:
1. Download/Clone the repository and go to the directory /csye6225-spring2019/infrastructure/aws/cloudformation
2. Set up permission for the set-up script with command: `chmod +x ./csye6225-aws-cf-create-stack.sh`
3. Set up the infrastructure with command: `./csye6225-aws-cf-create-stack.sh`
4. Set up permission for the tear-down script with command: `chmod +x ./csye6225-aws-cf-terminate-stack.sh`
5. Clean up the infrastructure with command: `./csye6225-aws-cf-terminate-stack.sh`

## Run Instructions: Create Application Stack
To create the application resources with AWS CloudFormation:
1. Download/Clone the repository and go to the directory /csye6225-spring2019-ami/infrastructure/aws/cloudformation
2. Set up permission for the set-up script with command: `chmod +x ./csye6225-aws-cf-create-application-stack.sh`
3. Set up the infrastructure with command: `./csye6225-aws-cf-create-application-stack.sh`

To clean up the application resources with AWS CloudFormation:
1. Go to the directory /csye6225-spring2019-ami/infrastructure/aws/cloudformation
2. Set up permission for the tear-down script with command: `chmod +x ./csye6225-aws-cf-terminate-application-stack.sh`
3. Clean up the infrastructure with command: `./csye6225-aws-cf-terminate-application-stack.sh`
