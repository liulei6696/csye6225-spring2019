#!/bin/bash
# sudo su
# sudo systemctl stop tomcat.service
sudo service awslogs stop
sudo chown tomcat:tomcat /opt/tomcat/latest/webapps/ROOT.war
sudo rm -rf /opt/tomcat/latest/webapps/*
# cleanup log files
sudo rm -rf /opt/tomcat/latest/logs/catalina*
sudo rm -rf /opt/tomcat/latest/logs/*.log
sudo rm -rf /opt/tomcat/latest/logs/*.txt
sudo systemctl restart tomcat
#configure cloudwatch
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/home/centos/amazon-cloudwatch-agent.json \
    -s
# sudo touch ai

