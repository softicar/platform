# How to Contribute to the SoftiCAR Gradle Plugins

First of all, thank you for taking the time to contribute!

## Bug Report

Before reporting a bug, please check that the bug was **not** reported before, by searching under [GitHub Issues](../../issues).

When filing a bug report, try to describe the easiest way to reproduce the problem, e.g. screenshots are often helpful. The easier to understand and reproduce the bug, the higher the chances that someone will take a look at it and fix it.

Also, describe the expected behavior in contrast to the observed behavior. You can skip that if this contrast is obvious.

Of course, you can always try to fix the bug yourself if you want to.

## Feature Request

Before asking for new features, please check that the feature was **not** already requested before, by searching under [GitHub Issues](../../issues).

To request new features or changes to existing features, create an issue in the form of a user story. That is, specify **who** wants to do **what** and **why**?

## Pull Request

### 1. Title

We currently use [JIRA](https://www.atlassian.com/software/jira) for internal issue management. The prefix `PLAT-` plus the respective issue number shall be given as a prefix to the title, e.g. `PLAT-123`, `PLAT-345`. 

After the prefix a short title shall be given in [Title Case](https://en.wikipedia.org/wiki/Title_case), e.g. `PLAT-123 Cool New Feature`. We aim for a clean *Git* history that speaks for itself and the commit titles shall be worded with that in mind. Meaningless titles like `Updates` or `Changes` shall not be used.

If you're working on a [GitHub Issue](../../issues) no prefix is necessary.

### 2. Description

If the pull request resolves or addresses a [GitHub Issue](../../issues), the issue shall be referenced in the description using a '#', e.g. `#123`.

Furthermore, a pull request shall describe how the changes were validated, e.g. a test plan shall be given.
- A test plan is not required for purely mechanical refactorings (e.g. renaming, moving, etc. by the IDE), as well as changes to documentation.
- For changes to code that is sufficiently covered by unit tests (or similar automated tests), it suffices to refer to those tests in the description, e.g. `validation: see new unit tests`.
- A list of test classes shall be given, if the relevant test classes are not obviously deducible:
  - For example, if class `Foo` was added together with test class `FooTest`, it need not be listed explicitly.
  - If class `Foo` was refactored and test class `FooTest` exists, it need not be listed explicitly.
  - If the behavior of class `Foo` was changed, test class `FooTest` must be modified too, it need not be listed explicitly.

## Documentation

Please consult the [README](README.md) file, check out our [Wiki pages](../../wiki) or start a [Discussion](../../discussions).
