# CSYE 6225 - Spring 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
| Junyi Fang | 001495265 | fang.ju@husky.neu.edu  |
|  Ziyan Zhu | 001461543 | zhu.ziy@husky.neu.edu  |
|   Lei Liu  | 001443309 | liu.lei1@husky.neu.edu |

## Run Instructions
To create and clean up AWS infrastructure resources with AWS CLI:
1. Download jq with command: `sudo apt-get install jq`
2. Download/Clone the repository and go to the directory /csye6225-spring2019/infrastructure/aws/scripts
3. Set up permission for the set-up script with command: `chmod +x ./csye6225-aws-networking-setup.sh`
4. Set up the infrastructure with command: `./csye6225-aws-networking-setup.sh`
5. Set up permission for the tear-down script with command: `chmod +x ./csye6225-aws-networking-teardown.sh`
6. Clean up the infrastructure with command: `./csye6225-aws-networking-teardown.sh`
