#!/bin/bash
sudo su
systemctl daemon-reload
sudo service tomcat start
touch astar