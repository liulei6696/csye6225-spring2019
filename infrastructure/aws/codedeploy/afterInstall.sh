#!/bin/bash
sudo su
systemctl stop tomcat.service
# sudo rm -rf /opt/tomcat/latest/webapps/*
chown tomcat:tomcat /opt/tomcat/webapps/ROOT.war
# cleanup log files
rm -rf /opt/tomcat/logs/catalina*
rm -rf /opt/tomcat/logs/*.log
rm -rf /opt/tomcat/logs/*.txt
# sudo touch ai