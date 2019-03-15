#!/bin/bash
cd webapp/Notetaking/target
java -jar -Dspring.profiles.active=prod NoteTaking-0.0.1-SNAPSHOT.jar

