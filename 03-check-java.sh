docker exec spark bash -x -c "
   cd /home/iceberg/local && 
   java \
      --add-opens java.base/sun.nio.ch=ALL-UNNAMED \
      --add-opens java.base/java.nio=ALL-UNNAMED \
      -Dlog4j.configurationFile=log4j2.properties \
      -cp \"/opt/spark/jars/*:build/libs/*\" icb.CheckSpark
   "
