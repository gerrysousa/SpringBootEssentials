# SpringBootEssentials
Esse código é referente ao curso de java Spring Boot Essentials do Dev Dojo

Spring Boot Essentials
https://www.youtube.com/watch?v=R-F-UcDo_5I&list=PL62G310vn6nF3gssjqfCKLpTK2sZJ_a_1&index=1

To run the application go to the root folder, and run the command below:

```$docker-compose up```

To create an executable from this project, run the follow commands:

```$mvn clean```

And then:

```$mvn package```

The "mvn package" command, run the unit tests and create a .jar file on the "/target" folder.

If you want skip unit tests, run the follow command with parameter "-DskipTests=true".

```$mvn package -DskipTests=true```

Creating a service with the .jar file on linux, init.d:

```$sudo ln -s /home/c/workspace/SpringBootEssentials/target/spring-essentials.jar /etc/init.d/spring-essentials```

Auto initializing with the operational system:

```$sudo update-rc.d spring-essentials defaults```

Initializing the service:

```$sudo service spring-essentials start```

See the service status:

```$sudo service spring-essentials status```


Update the service version, firstly stop the service:

```sudo service spring-essentials stop```

Then update the source code with commands needed. (git pull, etc), run "mvn package" again:

```$mvn package```

Run systemctl command:

```$systemctl daemon-reload```

And start the service again:

```sudo service spring-essentials start```
