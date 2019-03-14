#! /bin/bash
set -e
read -p "Input name of the stack: " stackName
read -p "Input name of the bucket: " bucketName
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-policies_roles-application.yaml --capabilities "CAPABILITY_NAMED_IAM" --parameters  "ParameterKey=bucketName,ParameterValue=$bucketName"
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
aws cloudformation describe-stacks
echo "stack created successfully"
