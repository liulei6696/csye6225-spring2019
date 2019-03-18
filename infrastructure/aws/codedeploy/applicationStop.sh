sudo su
systemctl daemon-reload
sudo service tomcat stop
rm -rf /opt/tomcat/latest/webapps/*
# touch astop