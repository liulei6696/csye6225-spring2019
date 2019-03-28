#! /bin/bash
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

read -p "Input name of this new stack: " stackName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-networking.yaml
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | head -n 1)
if [ $b ] && [ "$b"=="$stackName" ]; 
then 
echo "Stack "${stackName}" was successfully created"; 
else 
echo "Creation failure"; 
fi
#aws cloudformation describe-stacks
#echo "success"
# description=`aws cloudformation describe-stacks | grep Stacks | grep -Po 'Stacks[" :]+\K[^"]+'`
# response=  
# if [ ! $description ]; then  
#   echo "Stack is empty, creation fail!"  
# else  
#   echo "Stack created successfully!"
# fi  

