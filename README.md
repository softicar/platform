# SoftiCAR Platform

The _SoftiCAR Platform_ is a lightweight, Java-based library to create interactive business web applications.

## 1 Main Features

- Domain-oriented development of business applications
  - Business objects and queries defined using _SoftiCAR Query and Modeling Language ([SQML](https://github.com/softicar/sqml))_
  - Configurable and dynamically generated management pages ([CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete)) for business objects
  - Custom workflows for business objects (work in progress)
- Lightweight web applications based on a server-side _document object model (DOM)_
  - All business logic defined in Java and running on server
  - Interactive web pages using asynchronous Javascript ([AJAX](https://en.wikipedia.org/wiki/Ajax_(programming)))

## 2 Releases and Versioning

Version numbers of _SoftiCAR Platform_ releases follow the [Semantic Versioning](https://semver.org/) principle.

```
     1.2.3
    /  |  \
major  |  patch
     minor
```

1. If there was an **API break** since the previous release, the **major version** is incremented: `1.2.3 -> 2.0.0` -- API breaks include:
   - Incompatible changes to existing Java code which is part of the API; most notably: changes to (or removal of) `public`/`protected` classes/fields/methods/signatures
   - Changes in the behavior of existing Java code (except fixes of defective behavior)
   - _Any_ change to a database table
   - Fundamental changes to the behavior or style of the UI
1. If there was **no API break** but a **new feature** was added, the **minor version** is incremented: `1.2.3 -> 1.3.0`
1. If there was **no API break** and **no new feature** was added, the **patch version** is incremented: `1.2.3 -> 1.2.4`
   - e.g. when _only_ defects were fixed

## 3 Building and Development

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

## 4 Operations

For operations, you will usually need the following:

- [Apache Tomcat 9+](http://tomcat.apache.org/)
- [MariaDB  10+](https://mariadb.org/) or [MySQL-Server 8+](https://dev.mysql.com/downloads/mysql/)

## 5 Contributing

Please read the [contribution guidelines](CONTRIBUTING.md) for this repository and keep our [code of conduct](CODE_OF_CONDUCT.md) in mind.
