#! /bin/bash
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

read -p "Input name of this stack: " stackName
read -p "Input name of the bucket for CodeDeploy files: " bucketName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-policies_roles-application.yaml --capabilities "CAPABILITY_NAMED_IAM" --parameters  "ParameterKey=bucketName,ParameterValue=$bucketName"
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
aws cloudformation describe-stacks
echo "stack created successfully"
