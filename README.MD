# Coding Exercise
The live paired-programming exercise will examine how you work in a development environment. You will contribute code to create automated tests for the specified API endpoints. The testing framework will be provided for you in this repository. The following libraries (of main use) in this Java test framework are: [TestNG](https://testng.org/doc/index.html), [Gradle](https://docs.gradle.org/current/userguide/userguide.html), and [REST-Assured](https://rest-assured.io/).

First and foremost, clone the repository to your local machine. Next, review the [Swaggerhub Petstore API](https://petstore3.swagger.io/). 

During live session, you will automate the following endpoints:
* POST /pet – Create a pet
* GET /pet/{petId} – Get the pet by id
* POST /pet/{petId} – Update the pet by id
* DELETE /pet/{petId} – Delete the pet

The assertions required:
* POST /pet
  * status code is 200
  * id from JSON response is same as payload
  * name from JSON response is same as payload
* GET /pet/{petId} – Get the pet by id
  * status code is 200
  * id from JSON response is same as request
  * name from JSON response is expected from create request
* POST /pet/{petId} – Update the pet by id
  * status code is 200
  * id from JSON response is same as request
  * name from JSON response is expected from update payload
* DELETE /pet/{petId} – Delete the pet
  * status code is 200
  * pet no longer exists when calling GET /pet/{petId}

### NOTES:
* Test framework will be set up
* Use REST-Assured library to automate these tests
* Add these test files to the regression.xml file to run via Gradle task


# SOA Functional Test Automation Starter Project (PLEASE UPDATE THIS README FOR YOUR PROJECT)
@author: chan.suom@americanwell.com

## Updates (26-jan-2021)
This boot-strapped test automation code is now specific for java services using Rest-Assured to test the service endpoints. Web UI and Mobile UI will no longer be included in this package.

## Introduction
This project is boot-strapped to include source code classes and test classes to automate for API/services, web UI, mobile web view, and mobile native app testing. This framework exists to start your team with the tools and libraries needed to automate in a consistent manner across our organization. With the example code and example tests, you should be able to run these tests on your machine without issue as long as you have configured your system for automation.

This is a [Gradle](https://gradle.org/) project (v6.6 is currently supported) and our dependencies and tasks (build, clean, test, etc.) are managed in the `build.gradle` file. Our code is compatible with [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html). [TestNG](https://testng.org/doc/) (v7.3.0 is currently supported) is the testing framework. Test results are consumed and managed through [Report Portal](https://reportportal.io/) on our local server at http://bos-ts-rp-01:8080. [REST-Assured](http://rest-assured.io/) (v3.3.0 is currently supported) is the requests library used for API testing. [Selenium Webdriver](https://www.seleniumhq.org/projects/webdriver/) (v3.141 is currently supported) is the library used for web UI testing. [Appium](http://appium.io/) (v1.18.0 is currently supported) is the library we use for mobile automation. 

## Installation

Clone the repository for testing: `ssh://git@bitbucket.americanwell.com:7999/qaaut/<test_project>.git`
[IntelliJ](https://www.jetbrains.com/idea/) is highly recommended for development and testing. Version 2019.2 of the Community Edition is the stable release for our tests. If you decide to use another IDE, do so at your own peril ;).

Open IntelliJ and open the project by the `build.gradle` file so that the dependencies will be installed and indexed for the project.

We will need to install a couple of plug-ins to get us started:
* [Lombok](https://projectlombok.org/setup/intellij) - while we are not using this plug-in to streamline getters/setters, we are using this to streamline logging with the class annotation of `@Slf4j`. Annotation processing needs to be configured in IntelliJ. Go to the Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors:
    * Enable annotation processing check box is checked
    * Obtain processors from project classpath option is selected
* [SonarLint](https://www.sonarlint.org/intellij/) - we will be using this linter to ensure we meet modern Java coding practices. You can use this tool to scan your file via the SonarLint tool in the IntelliJ editor (bottom of UI)

In addition, make sure IntelliJ's Java compiler is pointing to JDK 11. Go to the Preferences -> Build, Execution, Deployment -> Compiler -> Java Compiler:
* Set project bytecode version to 11
* Set target bytecode version to 11 for both the main and test modules of the project

Lastly, set the IntelliJ Gradle JVM to JDK 11 also. Go to the Preferences -> Build, Execution, Deployment -> Build Tools -> Gradle:
* set Gradle JVM to 11

#### Setup config.yaml
For testing, ensure your `config.yaml` file is set up at `src/test/resources/config.yaml`. Copy the included `config.sample` file and rename the new file's extension to `.yaml`. Once you are confident in moving over to your team's application, remove all example-related configs and adjust the values of other settings to match your project's needs. The `config.yaml` is for local testing and will should be ignored in your commit (please update your project's .gitignore file to exclude this). The `config.sample` should be included in your commit as this will serve as a template for the CI/CD pipeline to create a respective file for e2e testing.

Point your tests to the desired service url, web app url, and mobile testing url by filling these values in with the URLs provided by the team to get started. 

#### Running the smoke test 
To test that everything is set up for development, run any of the Gradle tasks related to smoke tests such as `./gradlew testWebSmoke`. All the web smoke tests should run and you should see a launch under your space in Report Portal along with the results - you should have set up your RP account and your reportportal.properties file by now ;)

Do the same for the services: `./gradlew testServiceSmoke`. The tests should run against the designated service URL under the `serviceURL` config key.

Lastly, run the mobile smoke tests for both web view and native app testing with `./gradlew testMobileWebSmoke` and `./gradlew testMobileAppSmoke` relative to which type of testing you wish to conduct. I shouldn't have to say it, but run one of these at a time :|

The included APK and IPA are the Consumer Mobile SDK Sample apps. Make sure to place your desired APK and IPA file in the `apps` directory when you are ready to start testing your project's mobile apps. It is good practice to not commit these files with your work. The .gitignore file should be updated to reflect this.

## Development
### Feature Branches
All work should have an associated JIRA ticket when possible. If it is a minor fix, use the label `NOJIRA` when creating your feature branch. The format of the feature branch should be as follows:

`review/QAAUT-1234-short-description-of-work`

### GIT Flow (General)
Make sure you branch off the `platinum` branch. 
```
git checkout platinum
git checkout -b review/QAAUT-1234-my-feature-branch
```
When you are satisfied with the changes, add the modified or new files to staging (ready to commit status).

First, check the status of your changes first (do this often!) You will be presented with a list of untracked and tracked changes:
`git status`

You can add one or a few files at a time (separate by space)
`git add path/to/changed/file path/to/new/file`

Or you can add all your changes at once:
`git add -A` or `git add .`

Do a `git status` again to ensure you have all the files you intend for your commit. When you are satisfied use `git commit` which will bring up the interactive VI(M) editor. Follow this format for your commit message. Be sure to add the JIRA ticket(s) for any work included into the commit message; this will sync with the Jira ticket to provide a link to the BitBucket commit and pull request:
```
QAAUT-1234 Short Jira Title
- ExampleClass was updated due to dependencies on refactor of method Foo.bar()
- added new method doThis() in ExampleTwoClass to do a new thing
- added test cases: QAAUT-1000, QAAUT-1001, QAAUT-1002
```

After your commit message is completed. It is time to push your changes for a code review (or just to push up for safe-keeping!). Make sure to rebase your branch with `platinum` to ensure you are up to date with the latest changes from `platinum`:
```
git checkout platinum //make sure your changes are either stashed or committed before you can checkout a new branch
git pull //this gets all the latest changes from origin to your local platinum
git checkout review/QAAUT-1234-my-feature-branch
git rebase platinum //this places all the latest changes from platinum behind your changes
```
Now you are ready to send your branch upstream. You will only need to use this specific command the first time you are pushing up a new branch to origin:
`git push -u origin review/QAAUT-1234-my-feature-branch`

All new commits from this branch (after staging your changes) will use the command: `git push`
The new commit's message does not need the header as shown in the commit message example. Use the following format:
```
- fixed method Foo.boo() that was always returning true
```

If you have a typo or some insignificant change, you can just amend your previous commit after adding your changes to staging: `git commit --amend` 
This will bring up the interactive editor. You can modify your message to include the change or keep the same message. The next command is to send this amended commit upstream since you are sending up the same commit hash: `git push -f`

Outside of this, if you're havin' GIT problems, I feel bad for you, son, I got 99 problems but a commit ain't one. (Yes, that was bad) In any case, please reach out to anyone in the TestOps or Integration/Regression squad for help.

## Pull Requests
**Before you create a pull request, ensure your changes do not break any tests! 

Be sure to include the ticket number in your PR's title so that the relation between it and the Jira ticket is obvious. This also links the branch/commit/PR from BitBucket to Jira.

Please be proactive with your PR. If you get a comment, respond as soon as you can. If you are a reviewer, get used to marking the PR as "Needs Work" if you have comments/concerns on the functionality and the coding standards are not met.

When making changes to your PR, make sure they are **new** commits (refrain from `git commit --amend` unless, as stated above, it's for minor changes) so we can keep track of the changes. When you get the required number of approvals, then you must squash your commits and push the branch back up for a final review before merging.

You can use the interactive editor to squash your commits with the command `git rebase -i HEAD~n` where `n` is the number of commits from your branch that you wish to squash. 
