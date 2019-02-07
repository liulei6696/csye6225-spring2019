#!/bin/bash
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

read -p "Input name of the stack that you want to terminate: " stackName
aws cloudformation delete-stack --stack-name $stackName
echo "deleting"
aws cloudformation wait stack-delete-complete --stack-name $stackName

echo "delete stack "${stackName}" success"
#aws cloudformation describe-stacks
#echo "Stack deleted successfully“
#b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | #sed 's/\"//g')
#if [ !"$b" ] || [ "$b"!="$stackName" ]; 
#then 
#echo "Stack was successfully deleted"; 
#else 
#echo "Deletion failure"; 
#fi
