#!/bin/bash 
# change to tomcat webapps directory. 
# this directory will be different for different tomcat versions. 

# sudo su
# whoami
sudo rm -rf /opt/tomcat/apache-tomcat-9.0.16/webapps/*
sudo touch bi