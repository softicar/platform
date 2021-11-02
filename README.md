# SoftiCAR Platform

The _SoftiCAR Platform_ is a lightweight, Java-based library to create interactive business web applications.

## Main Features

- domain-oriented development of business applications
  - business objects and queries defined using _SoftiCAR Query and Modeling Language ([SQML](https://github.com/softicar/sqml))_
  - configurable and dynamically generated management pages ([CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete)) for business objects
  - custom workflows for business objects (work in progress)
- lightweight web applications based on a server-side _document object model (DOM)_
  - all business logic defined in Java and running on server
  - interactive web pages using asynchronous Javascript ([AJAX](https://en.wikipedia.org/wiki/Ajax_(programming)))

## Building and Development

To develop business applications based on the _SoftiCAR Platform_, the following is required:

- Java SDK 15+, e.g. [OpenJDK](https://adoptopenjdk.net/)
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/), e.g. 2020-09 (4.17) or higher
- [SQML Eclipse Plugin](https://github.com/softicar/sqml)

During development, an integrated [Jetty-Server](https://www.eclipse.org/jetty/) together with an integrated in-memory [H2-Database](https://www.h2database.com/html/main.html) can be used. To start such a server, create a Java class, similar to this:

```java
public class MyServlet extends HotDeploymentWebServiceServlet {

	public MyServlet() {

		database.applyFixture(() -> {
			TestFixtureRegistry registry = new TestFixtureRegistry(new CoreModuleTestFixture());
			registry.registerIfMissing(WorkflowModuleTestFixture::new);
			return registry;
		});
	}

	public static void main(String[] args) {

		new HotDeploymentWebServiceServer(MyServlet.class)//
				.setRequestString("service?id=" + EmfSourceCodeReferencePoints.getUuidOrThrow(PageService.class))
				.setPort(8000).startAndJoin();
	}
}
```

When executed, this will start a [Jetty-Server](https://www.eclipse.org/jetty/) and print something like this:

```
Server started at http://localhost:8000
Full URL:
http://localhost:8000/portal/service?id=95cf1a1b-c12e-4594-9d20-783988fe32b9
```

Open a web browser and enter the URL. To log in, use one of the default users: `admin.user`, `normal.user` or `view.user`. The password is `test`.

## Contributing

Please read the [contribution guidelines](CONTRIBUTING.md) for this repository and keep our [code of conduct](CODE_OF_CONDUCT.md) in mind.

## Operations

For operations, you will usually need the following:

- [Apache Tomcat 9+](http://tomcat.apache.org/)
- [MariaDB  10+](https://mariadb.org/) or [MySQL-Server 8+](https://dev.mysql.com/downloads/mysql/)
