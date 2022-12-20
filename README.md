![workflow](https://img.shields.io/github/actions/workflow/status/softicar/platform/continuous-integration.yml?branch=main)
![license](https://img.shields.io/github/license/softicar/platform)
![contributors](https://img.shields.io/github/contributors/softicar/platform)
![release](https://img.shields.io/github/v/release/softicar/platform)
![activity](https://img.shields.io/github/commit-activity/m/softicar/platform)

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

## 2 Example Web Application

See the [SoftiCAR Platform Example Project](https://github.com/softicar/platform-example), for an exemplary web application that is based upon the _SoftiCAR Platform_.

## 3 Building and Development

To build and develop the code in this repository, an **Ubuntu 20.04 (Focal)** based workstation is recommended, with the following software installed:

1. [AdoptOpenJDK 15 with HotSpot JVM](https://adoptopenjdk.net/archive.html?variant=openjdk15&jvmVariant=hotspot), with `java` in the `PATH`
2. [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/), e.g. [2020-09 (4.17)](https://www.eclipse.org/downloads/packages/release/2020-09/r)
3. [SQML Eclipse Plugin](https://github.com/softicar/sqml)

In _Eclipse_, the code shall be imported via _File / Import / Gradle / Existing Gradle Project_.

For prototyping purposes, an integrated [Jetty Server](https://www.eclipse.org/jetty/) and an integrated ephemeral [H2-Database](https://www.h2database.com/html/main.html) can be used:
- To start such a server, create a Java class like this:
  ```java
  public class DevelopmentServlet extends HotDeploymentWebServiceServlet {

      public DevelopmentServlet() {

          database.applyFixture(() -> {
              TestFixtureRegistry registry = new TestFixtureRegistry(new CoreModuleTestFixture());
              registry.registerIfMissing(WorkflowModuleTestFixture::new);
              return registry;
          });
      }

      public static void main(String[] args) {

          new HotDeploymentWebServiceServer(DevelopmentServlet.class)//
                  .setRequestString("service?id=" + EmfSourceCodeReferencePoints.getUuidOrThrow(PageService.class))
                  .setPort(8000).startAndJoin();
      }
  }
  ```
- When executed, it will print something like this:
  ```
  Server started at http://localhost:8000
  Full URL:
  http://localhost:8000/portal/service?id=95cf1a1b-c12e-4594-9d20-783988fe32b9
  ```
- Open a web browser and enter the URL.
- To log in, use one of the default users: `admin.user`, `normal.user` or `view.user`. The password is `test` for each of those.

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
   - _Any_ change to a database table
   - Fundamental changes to the behavior or style of the UI
2. If there was **no API break** but a **new feature** was added, the **minor version** is incremented: `1.2.3 -> 1.3.0`
3. If there was **no API break** and **no new feature** was added, the **patch version** is incremented: `1.2.3 -> 1.2.4`
   - e.g. when _only_ defects were fixed

## 5 Contributing

Please read the [contribution guidelines](CONTRIBUTING.md) for this repository and keep our [code of conduct](CODE_OF_CONDUCT.md) in mind.

## 6 Notes to Contributors

### 6.1 Optimize All SVG Images

To convert all SVGs in a directory tree to an uncluttered and optimized format, use `scour` (`sudo apt install scour`):

```
find . -type f -name "*.svg" | xargs -d $'\n' sh -c 'for file do\
  scour -i "$file" -o "$file"-opt --strip-xml-prolog --remove-metadata --enable-id-stripping --enable-comment-stripping;\
  mv -v -- "$file"-opt "$file";\
done' _
```
