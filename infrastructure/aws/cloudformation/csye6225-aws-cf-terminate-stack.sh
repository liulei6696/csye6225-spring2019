#!/bin/bash
set -e
echo "Input name of the stack that you want to terminate:"
read stackName
aws cloudformation delete-stack --stack-name $stackName
aws cloudformation wait stack-delete-complete --stack-name $stackName
#aws cloudformation describe-stacks
#echo "Stack deleted successfully“
#b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | #sed 's/\"//g')
#if [ !"$b" ] || [ "$b"!="$stackName" ]; 
#then 
#echo "Stack was successfully deleted"; 
#else 
#echo "Deletion failure"; 
#fi
