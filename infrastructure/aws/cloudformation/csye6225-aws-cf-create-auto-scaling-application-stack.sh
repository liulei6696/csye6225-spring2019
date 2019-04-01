#! /bin/bash
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}


read -p "Input name of the this stack: " stackName
read -p "Input name of the network stack you want to refer to: " refStackName
read -p "Input name of the role stack you want to refer to: " roleStackName
read -p "Input name of the S3 bucket you want to use to store files: " S3BucketName
read -p "Input name of your domain: " domainName
ami=`aws ec2 describe-images --owners self --filters 'Name=root-device-type,Values=ebs' --query 'sort_by(Images, &CreationDate)[-1].[ImageId]' --output 'text'`
DBServerSecurityGroupID=`aws ec2 describe-security-groups --filters "Name=tag:aws:cloudformation:logical-id, Values=DBServerSecurityGroup" --query "SecurityGroups[*].GroupId" --output text`
WebServerSecurityGroupID=`aws ec2 describe-security-groups --filters "Name=tag:aws:cloudformation:logical-id, Values=WebServerSecurityGroup" --query "SecurityGroups[*].GroupId" --output text`
LoadBalancerSecurityGroupID=`aws ec2 describe-security-groups --filters "Name=tag:aws:cloudformation:logical-id, Values=LoadBalancerSecurityGroup" --query "SecurityGroups[*].GroupId" --output text`
LoadBalancerName="csyeLoadBalancer"
NAME=$(aws route53 list-hosted-zones --query "HostedZones[0].Name" --output text)
VpcId=`aws ec2 describe-vpcs --filter "Name=tag:Name,Values=${refStackName}-csye6225-vpc" --query 'Vpcs[*].{id:VpcId}' --output text`
echo "Vpc found: "$VpcId
ParamVpcId=$VpcId
z_id=$(aws route53 list-hosted-zones --query 'HostedZones[0].Id' --output text)
TagKey=domainName
TagValue=csye6225
echo "DBServerSecurityGroupID found: "$DBServerSecurityGroupID
echo "WebServerSecurityGroupID found: "$WebServerSecurityGroupID
echo "LoadBalancerSecurityGroupID found: "$LoadBalancerSecurityGroupID
echo "hostedzone id found: "$z_id
echo "domain found: "$domainName
# export CERTIFICATE_ARN=$(aws acm list-certificates --query "CertificateSummaryList[0].CertificateArn" --output text)
# echo "certificate arn:"$CERTIFICATE_ARN
# echo "Name:"$NAME
# export TOPIC_NAME=password_reset
aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-auto-scaling-application.json --capabilities CAPABILITY_IAM --parameters "ParameterKey=refStackName,ParameterValue=$refStackName" "ParameterKey=amiId,ParameterValue=$ami" "ParameterKey=roleStackName,ParameterValue=$roleStackName" "ParameterKey=S3BucketName,ParameterValue=$S3BucketName" "ParameterKey=DomainName,ParameterValue=$domainName" "ParameterKey=WebServerSecurityGroupID,ParameterValue=$WebServerSecurityGroupID" "ParameterKey=DBServerSecurityGroupID,ParameterValue=$DBServerSecurityGroupID" "ParameterKey=ParamVpcId,ParameterValue=$VpcId" "ParameterKey=LoadBalancerName,ParameterValue=$LoadBalancerName" "ParameterKey=LoadBalancerSecurityGroupID,ParameterValue=$LoadBalancerSecurityGroupID" "ParameterKey=HostedZoneId,ParameterValue=$z_id" "ParameterKey=TagKey,ParameterValue=$TagKey" "ParameterKey=TagValue,ParameterValue=$TagValue"
echo "creating"
aws cloudformation wait stack-create-complete --stack-name $stackName
b=$(aws cloudformation describe-stacks | grep -o '"StackName": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | head -n 1)
if [ $b ] && [ "$b"=="$stackName" ]; 
then 
echo "Stack "${stackName}" was successfully created"; 
else 
echo "Creation failure"; 
fi
