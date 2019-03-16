#!/bin/bash
cd webapp/Notetaking/target
id=`ps -aux | grep Note`
kill -9 ${id:7:7}
java -jar -Dspring.profiles.active=prod NoteTaking-0.0.1-SNAPSHOT.jar

