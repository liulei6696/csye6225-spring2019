#!/bin/bash 
# change to tomcat webapps directory. 
# this directory will be different for different tomcat versions. 

sudo su
# whoami
rm -rf /opt/tomcat/latest/webapps/*
# touch bi