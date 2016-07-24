# Ratpack Sandpit

A test driven [Ratpack](http://ratpack.io/) workshop.

## Prerequisites

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Git
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

## Setup

* Clone this repo
```
git clone https://github.com/erdi/ratpack-sandpit.git
```

* Run a Gradle build, using the Gradle Wrapper, to setup an IntelliJ IDEA project
```
cd ratpack-sandpit
./gradlew idea
```
  
* Open the generated `ratpack-sandpit.ipr` file in IntelliJ IDEA 

## Instructions

The workshop is comprised of a number of "labs" and each lab is in its own subproject.

In the root of each lab subproject is a `README.md` file. This contains the details and instructions for the lab.

There is also a `solutions` branch in which lab subprojects are modified to contain _possible_ solutions with all tests passing. 
There are many ways to solve the labs, the `solutions` branch is just one of them.

## Running the tests

Tests can be run individually from the IDE or using Gradle. To run tests for the first lab you can use the following:
```
./gradlew :01-handlers:test
```

You can also take advantage of Gradle's [continuous mode](https://docs.gradle.org/current/userguide/continuous_build.html) when modifying your code. 
This will continually run tests on each modification that Gradle detects to your source files:
```
./gradlew :01-handlers:test --continuous
```
