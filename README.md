# SoftiCAR Platform

The _SoftiCAR Platform_ is a lightweight, Java-based library to create interactive business web applications.

## Main Features

- domain-oriented development of business applications
  - business objects and queries defined using _SoftiCAR Query and Modeling Language (SQML)_
  - configurable and dynamically generated management pages (CRUD) for business objects
  - custom workflows for business objects (work in progress)
- lightweight web applications based on a server-side _document object model (DOM)_
  - all business logic defined in Java and running on server
  - interactive web pages using asynchronous Javascript (AJAX)

## Requirements

To create business applications based on the _SoftiCAR Platform_, the following is required:

- Java SDK 15+, e.g. [OpenJDK](https://adoptopenjdk.net/)
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/), e.g. 2020-09 (4.17) or higher
- [SQML Eclipse Plugin](https://github.com/Prevent-DEV/com.softicar.sqml)

During development, an integrated [Jetty-Server](https://www.eclipse.org/jetty/) together with an integrated in-memory [H2-Database](https://www.h2database.com/html/main.html) can be used. For operations, you will usually need the following:

- [Apache Tomcat 9+](http://tomcat.apache.org/)
- [MariaDB  10+](https://mariadb.org/) or [MySQL-Server 8+](https://dev.mysql.com/downloads/mysql/)
