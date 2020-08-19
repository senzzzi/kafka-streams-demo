Kafka Streams Demo
=====

This repo allows to reproduce a (possible) issue with Kafka Streams 2.6.0.

On application.properties it is possible to configure the bootstrap servers or any other property you want. 
2 new topics will be created "demo-test" and "demo-test-2". A third one is created by the KStream aggregate "demo-agg-changelog". All of them have replication factor of 3 and min.insync.replicas of 2.

10000 records are inserted on the "demo-test" topic so there is some work for the KStream to do. 

By default, this runs with 2.6.0. Just run the project and you will see that the lag decrease very slowly.

On pom.xml there's a comment explaining what to do to go back to 2.5.1, just comment those 2 kafka dependencies. 

Another way to make this run faster is to increase commit interval. But the difference between 2.5.0 and 2.6.0 is huge.