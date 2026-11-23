![workflow](https://img.shields.io/github/actions/workflow/status/softicar/platform/continuous-integration.yml?branch=main)
![license](https://img.shields.io/github/license/softicar/platform)
![contributors](https://img.shields.io/github/contributors/softicar/platform)
![release](https://img.shields.io/github/v/release/softicar/platform)
![activity](https://img.shields.io/github/commit-activity/m/softicar/platform)

# SoftiCAR Platform

The SoftiCAR Platform is a lightweight, Java-based library to create interactive business web applications.

## 1 Main Features

- Domain-oriented development of business applications
  - Business objects and queries defined using SoftiCAR Query and Modeling Language ([SQML](https://github.com/softicar/sqml))
  - Configurable and dynamically generated management pages ([CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete)) for business objects
  - Custom workflows for business objects (work in progress)
- Lightweight web applications based on a server-side document object model (DOM)
  - All business logic defined in Java and running on server
  - Interactive web pages using asynchronous JavaScript ([AJAX](https://en.wikipedia.org/wiki/Ajax_(programming)))

## 2 Example Web Application

See the [SoftiCAR Platform Example Project](https://github.com/softicar/platform-example), for an exemplary web application that is based upon the SoftiCAR Platform.

## 3 Building and Development

To build and develop the code in this repository, an **Ubuntu 26.04 LTS (Resolute Raccoon)** based workstation is recommended, with the following software installed:

1. [Java Eclipse Temurin 21](https://adoptium.net/temurin/releases/?version=21), with `java` in the `PATH`
2. [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/), e.g. [2026-09 (4.41)](https://www.eclipse.org/downloads/packages/release/2026-09/r)
3. [SQML Eclipse Plugin](https://github.com/softicar/sqml)
4. [Docker Engine](https://docs.docker.com/engine/install/ubuntu/), for the tests that start containers (SMB, MariaDB migrations and Selenium)

In Eclipse, the code shall be imported via **File / Import / Gradle / Existing Gradle Project**.

For prototyping purposes, an integrated [Jetty Server](https://jetty.org/) and an integrated ephemeral [H2 Database](https://www.h2database.com/html/main.html) can be used.

To start such a server, create a Java class like this:

```lang=java
public class DevelopmentService extends WebServiceTestService {

    public DevelopmentService() {

        database.apply(() -> {
            TestFixtures.use(CoreModuleTestFixture.class);
            TestFixtures.use(WorkflowModuleTestFixture.class);
        });
    }

    public static void main(String[] args) {

        new HotWebServiceTestServer(DevelopmentService.class)//
            .setPort(8000)
            .startAndJoin();
    }
}
```

When executed, it will print something like this:

```
Server started at http://localhost:8000
Full URL:
http://localhost:8000/portal/service/95cf1a1b-c12e-4594-9d20-783988fe32b9
```

Open a web browser and enter the URL. To log in, use one of the default users: `admin.user`, `normal.user` or `view.user`. The password is `test` for each of those.

## 4 Releases and Versioning

Releases of this repository follow the [Semantic Versioning](https://semver.org/) principle.

```
     1.2.3
    /  |  \
major  |  patch
     minor
```

1. If there was an **API break** since the previous release, the **major version** is incremented: `1.2.3 -> 2.0.0` -- API breaks include:
   - Incompatible changes to existing Java code which is part of the API; most notably: changes to (or removal of) `public`/`protected` classes/fields/methods/signatures
   - Changes in the behavior of existing Java code (except fixes of defective behavior)
   - **Any** change to a database table
   - Fundamental changes to the behavior or style of the UI
2. If there was **no API break** but a **new feature** was added, the **minor version** is incremented: `1.2.3 -> 1.3.0`
3. If there was **no API break** and **no new feature** was added, the **patch version** is incremented: `1.2.3 -> 1.2.4`
   - e.g. when **only** defects were fixed

## 5 Contributing

Please read the [contribution guidelines](https://github.com/softicar/platform/blob/main/CONTRIBUTING.md) for this repository and keep our [code of conduct](https://github.com/softicar/platform/blob/main/CODE_OF_CONDUCT.md) in mind.

## 6 Notes to Contributors

### 6.1 Optimize All SVG Images

To convert all SVGs in a directory tree to an uncluttered and optimized format, use `scour` (`sudo apt install scour`):

```lang=bash
find . -type f -name "*.svg" | xargs -d $'\n' sh -c 'for file do\
  scour -i "$file" -o "$file"-opt --strip-xml-prolog --remove-metadata --enable-id-stripping --enable-comment-stripping;\
  mv -v -- "$file"-opt "$file";\
done' _
```
