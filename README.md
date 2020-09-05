#Introduction
This project is a back-end server written in Java using Spring Boot framework. It provides Restful APIs to supports a study-oriented mobile app, which includes tutoring, resources sharing, posting, and social media features.

#file structure
+ src
  + main
    + java
      + com
        + wequan
          + bu
            + config //contains Spring Boot config Java classes
            + controller //contains Spring MVC controllers
            + repository
              + dao //contains the Data Access Objects
              + model //contains the POJOs
            + service //contains the service objects which work on business logic
            + security //contains the codes which do authentication using Spring Security
            + quartz //contains the quartz jobs
    + resources
      + mapper //contains the mybatis Mapper XML files
      + sql //contains the postgreSQL schema
