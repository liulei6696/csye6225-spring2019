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
