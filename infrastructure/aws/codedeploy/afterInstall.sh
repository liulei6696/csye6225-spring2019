#!/bin/bash
sudo su
sudo systemctl stop tomcat.service
sudo rm -rf /opt/tomcat/latest/webapps/*
sudo chown tomcat:tomcat /opt/tomcat/webapps/NoteTaking.war
# cleanup log files
sudo rm -rf /opt/tomcat/logs/catalina*
sudo rm -rf /opt/tomcat/logs/*.log
sudo rm -rf /opt/tomcat/logs/*.txt
sudo touch ai