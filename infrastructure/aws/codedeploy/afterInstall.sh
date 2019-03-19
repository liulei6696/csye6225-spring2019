#!/bin/bash
# sudo su
# sudo systemctl stop tomcat.service
# sudo rm -rf /opt/tomcat/latest/webapps/*
sudo chown tomcat:tomcat /opt/tomcat/webapps/ROOT.war
# cleanup log files
sudo rm -rf /opt/tomcat/logs/catalina*
sudo rm -rf /opt/tomcat/logs/*.log
sudo rm -rf /opt/tomcat/logs/*.txt
sudo systemctl restart tomcat
# sudo touch ai
#configure cloudwatch
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/cloudwatch-config.json \
    -s