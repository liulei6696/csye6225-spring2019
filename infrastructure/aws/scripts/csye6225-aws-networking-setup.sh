#!/bin/bash

# quit if any commands return an error code
set -e

# cd to script directory
work_dir=$(cd `dirname $0`; pwd)
cd ${work_dir}

# regular expression for CIDR address
RE="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\/([0-9]|[1-2][0-9]|3[0-2]))$"


# ====================================================
# input for CIDR address
# TODO: validate subnet according to VPC CIDR
read -p "enter CIDR block of new VPC(default 10.0.0.0/20): " vpc_cidr_block
if [ -z ${vpc_cidr_block} ]
then 
	echo "using default 10.0.0.0/20"
	vpc_cidr_block="10.0.0.0/20"
fi
until [[ ${vpc_cidr_block} =~ $RE ]]
do
	echo "value "${vpc_cidr_block}" not valid, please enter again!"
	read -p "enter CIDR block of new VPC(default 10.0.0.0/20): " vpc_cidr_block
	if [ -z ${vpc_cidr_block} ]
	then 
		echo "using default 10.0.0.0/20"
		vpc_cidr_block="10.0.0.0/20"
	fi
done
echo "success"

# subnet_1_cidr_block
read -p "enter CIDR block for subnet 1(default 10.0.1.0/24): " subnet_1_cidr_block
if [ -z ${subnet_1_cidr_block} ]
then 
	echo "using default 10.0.1.0/24"
	subnet_1_cidr_block="10.0.1.0/24"
fi
until [[ ${subnet_1_cidr_block} =~ $RE ]]
do
	echo "value "${subnet_1_id}" not valid, please enter again!"
	read -p "enter CIDR block for subnet 1(default 10.0.1.0/24): " subnet_1_cidr_block
	if [ -z ${subnet_1_cidr_block} ]
	then 
		echo "using default 10.0.1.0/24"
		subnet_1_cidr_block="10.0.1.0/24"
	fi
done
echo "success"

# subnet_2_cidr_block
read -p "enter CIDR block for subnet 2(default 10.0.2.0/24): " subnet_2_cidr_block
if [ -z ${subnet_2_cidr_block} ]
then 
	echo "using default 10.0.2.0/24"
	subnet_2_cidr_block="10.0.2.0/24"
fi
until [[ ${subnet_2_cidr_block} =~ $RE ]]
do
	echo "value "${subnet_2_id}" not valid, please enter again!"
	read -p "enter CIDR block for subnet 2(default 10.0.2.0/24): " subnet_2_cidr_block
	if [ -z ${subnet_2_cidr_block} ]
	then 
		echo "using default 10.0.2.0/24"
		subnet_2_cidr_block="10.0.2.0/24"
	fi
done
echo "success"

# subnet_3_cidr_id
read -p "enter CIDR block for subnet 3(default 10.0.3.0/24): " subnet_3_cidr_block
if [ -z ${subnet_3_cidr_block} ]
then 
	echo "using default 10.0.3.0/24"
	subnet_3_cidr_block="10.0.3.0/24"
fi
until [[ ${subnet_3_cidr_block} =~ $RE ]]
do
	echo "value "${subnet_3_id}" not valid, please enter again!"
	read -p "enter CIDR block for subnet 3(default 10.0.3.0/24): " subnet_3_cidr_block
	if [ -z ${subnet_3_cidr_block} ]
	then 
		echo "using default 10.0.3.0/24"
		subnet_3_cidr_block="10.0.3.0/24"
	fi
done
echo "success"


# ====================================================
# start building
echo "Start setting up networking"

vpc_id=`aws ec2 create-vpc --cidr-block ${vpc_cidr_block} | grep -o '"VpcId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g'`
echo "created VPC: "${vpc_id}" on "${vpc_cidr_block}

subnet_1_id=`aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${subnet_1_cidr_block} | grep -o '"SubnetId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g'`
subnet_2_id=`aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${subnet_2_cidr_block} | grep -o '"SubnetId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g'`
subnet_3_id=`aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${subnet_3_cidr_block} | grep -o '"SubnetId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g'`
echo "created subnet "${subnet_1_id}", "${subnet_2_id}", "${subnet_3_id}

internet_gateway_id=`aws ec2 create-internet-gateway | grep -o '"InternetGatewayId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g'`
aws ec2 attach-internet-gateway --vpc-id ${vpc_id} --internet-gateway-id ${internet_gateway_id}
echo "created and attached internet gateway: "${internet_gateway_id}

route_table_id=`aws ec2 create-route-table --vpc-id ${vpc_id} | grep -o '"RouteTableId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g'`
echo "creatd route table "${route_table_id}

aws ec2 associate-route-table  --subnet-id ${subnet_1_id} --route-table-id ${route_table_id}
aws ec2 associate-route-table  --subnet-id ${subnet_2_id} --route-table-id ${route_table_id}
aws ec2 associate-route-table  --subnet-id ${subnet_3_id} --route-table-id ${route_table_id}
echo "attached subnets to route table"

aws ec2 create-route --route-table-id ${route_table_id} --destination-cidr-block 0.0.0.0/0 --gateway-id ${internet_gateway_id}
echo "created a public route in the public route table above with destination CIDR block '0.0.0.0/0' and internet gateway created above as the target"

security_group_id=`aws ec2 describe-security-groups --filters "Name=vpc-id, Values="${vpc_id} | grep -o '"GroupId": *"[^"]*"' | grep -o '"[^"]*"$' | sed 's/\"//g' | head -n 1`
echo "editing group "${security_group_id}

json=`aws ec2 describe-security-groups --group-id ${security_group_id} --query "SecurityGroups[0].IpPermissions"`
aws ec2 revoke-security-group-ingress --cli-input-json "{\"GroupId\": \"${security_group_id}\", \"IpPermissions\": $json}"
aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 22 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 80 --cidr 0.0.0.0/0
echo "added rules that allow TCP traffic from anywhere"

# write to config file
echo "vpc_cidr_block=${vpc_cidr_block}" > config
echo "subnet_1_cidr_block=${subnet_1_cidr_block}" >> config
echo "subnet_2_cidr_block=${subnet_2_cidr_block}" >> config
echo "subnet_3_cidr_block=${subnet_3_cidr_block}" >> config
echo "vpc_id=${vpc_id}" >> config
echo "subnet_1_id=${subnet_1_id}" >> config
echo "subnet_2_id=${subnet_2_id}" >> config
echo "subnet_3_id=${subnet_3_id}" >> config
echo "internet_gateway_id=${internet_gateway_id}" >> config
echo "route_table_id=${route_table_id}" >> config
echo "security_group_id=${security_group_id}" >> config
