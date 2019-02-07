#! /bin/bash
set -e
echo "Input name of the new stack:"
read stackName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-networking.yaml
aws cloudformation wait stack-create-complete --stack-name $stackName
a=$(aws cloudformation describe-stacks)
b=$(jq '.Stacks[0].StackName'<<<"$a")
if [ $b ] && [ "$b"=="$stackName" ]; 
then 
echo "Stack was successfully created"; 
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

