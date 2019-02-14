#!/bin/bash

set -e

# change working directory to script folder
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

# checking if jq installed
jqv=`jq --version`
if [ -z $jqv ]
then
	echo "jq version needed"
	echo "'apt-get install jq' or 'brew install jq'"
	exit
fi

# get vpc ids and let user choose
vpc_ids=`aws ec2 describe-vpcs | jq -r '.Vpcs[].VpcId'`
echo "available vpc: "
for line in $vpc_ids
do
	echo $line
done
read -p "please enter one of the following vpc id: " vpc_id


# delete subnets
echo "deleting subnets ..."
subnet_ids=`aws ec2 describe-subnets --filters "Name=vpc-id,Values=$vpc_id" | jq -r '.Subnets[].SubnetId'`
for line in $subnet_ids
do
	aws ec2 delete-subnet --subnet-id $line
	echo "deleted related subnet: $line"
done

# delete route table
echo "deleting route tables ..."
route_tables=`aws ec2 describe-route-tables --filters "Name=vpc-id,Values=$vpc_id" | jq '.RouteTables[]|select(.Associations==[])' | jq -r '.RouteTableId'`
for line in $route_tables
do
	aws ec2 delete-route-table --route-table-id $line
	echo "deleted route table: $line"
done
echo "finished deleting route table"

# delete internet gateway
echo "deleting internet gateway ..."
internet_gateways=`aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=$vpc_id" | jq -r '.InternetGateways[].InternetGatewayId'`
for line in $internet_gateways
do
	aws ec2 detach-internet-gateway --internet-gateway-id $line --vpc-id $vpc_id
	echo "detached internet gateway - $line from vpc - $vpc_id"
	aws ec2 delete-internet-gateway --internet-gateway-id $line
	echo "deleted internet gateway: $line"
done
echo "finished deleting internet gateways"

# delete vpc
echo "deleting vpc"
aws ec2 delete-vpc --vpc-id $vpc_id
echo "deleted vpc $vpc_id"

echo "success"


