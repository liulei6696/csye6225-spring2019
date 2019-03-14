#! /bin/bash
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

ami=`aws ec2 describe-images --owners self --filters "Name=root-device-type,Values=ebs" | grep -o '"ImageId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | tail -n 1`
read -p "Input name of the new stack: " stackName
read -p "Input name of the stack you want to refer to: " refStackName
read -p "Input name of the role stack you want to refer to: " roleStackName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-application.yaml --parameters "ParameterKey=refStackName,ParameterValue=$refStackName" "ParameterKey=amiId,ParameterValue=$ami" "ParameterKey=roleStackName,ParameterValue=$roleStackName"
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | head -n 1)
if [ $b ] && [ "$b"=="$stackName" ]; 
then 
echo "Stack "${stackName}" was successfully created"; 
else 
echo "Creation failure"; 
fi
