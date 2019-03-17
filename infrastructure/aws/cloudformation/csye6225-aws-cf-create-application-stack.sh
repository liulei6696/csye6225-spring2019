#! /bin/bash
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

ami=`aws ec2 describe-images --owners self --filters 'Name=root-device-type,Values=ebs' --query 'sort_by(Images, &CreationDate)[-1].[ImageId]' --output 'text'`
read -p "Input name of the new stack: " stackName
read -p "Input name of the stack you want to refer to: " refStackName
read -p "Input name of the role stack you want to refer to: " roleStackName
read -p "Input name of the S3 bucket you want to use: " S3BucketName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-application.yaml --capabilities CAPABILITY_IAM --parameters "ParameterKey=refStackName,ParameterValue=$refStackName" "ParameterKey=amiId,ParameterValue=$ami" "ParameterKey=roleStackName,ParameterValue=$roleStackName" "ParameterKey=S3BucketName,ParameterValue=$S3BucketName"
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | head -n 1)
if [ $b ] && [ "$b"=="$stackName" ]; 
then 
echo "Stack "${stackName}" was successfully created"; 
else 
echo "Creation failure"; 
fi
