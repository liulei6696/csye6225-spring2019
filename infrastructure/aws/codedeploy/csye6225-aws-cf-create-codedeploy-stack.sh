#! /bin/bash
set -e
read -p "Input name of the stack: " stackName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-codedeploy.yaml --parameters  "ParameterKey=bucket,ParameterValue=$bucketName"
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
aws cloudformation describe-stacks
b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | head -n 1)
if [ $b ] && [ "$b"=="$stackName" ]; 
then 
echo "Stack "${stackName}" was successfully created"; 
else 
echo "Creation failure"; 
fi