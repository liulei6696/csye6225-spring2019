sudo su
systemctl daemon-reload
service tomcat stop
# rm -rf /opt/tomcat/latest/webapps/*
# touch astop