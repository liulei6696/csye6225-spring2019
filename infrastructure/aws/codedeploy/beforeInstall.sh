#!/bin/bash 
# change to tomcat webapps directory. 
# this directory will be different for different tomcat versions. 

# sudo su
# whoami
while [ ! -f /home/centos/userdatacomplete.sh ]
do
  sleep 2
done
sudo touch bi
sudo rm -rf /opt/tomcat/latest/webapps/ROOT
