#!/bin/bash

work_dir=$(cd `dirname $0`; pwd)
# echo ${work_dir}
cd ${work_dir}


source ./config

# echo $security_group_id

#aws ec2 delete-security-group --group-id $security_group_id
aws ec2 delete-subnet --subnet-id $subnet_1_id 
aws ec2 delete-subnet --subnet-id $subnet_2_id
aws ec2 delete-subnet --subnet-id $subnet_3_id
aws ec2 delete-route-table --route-table-id $route_table_id
aws ec2 detach-internet-gateway --internet-gateway-id $internet_gateway_id --vpc-id $vpc_id
aws ec2 delete-internet-gateway --internet-gateway-id $internet_gateway_id
aws ec2 delete-vpc --vpc-id $vpc_id
