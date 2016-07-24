# 10 - Deploying to heroku lab

In this lab you will learn how to deploy a Ratpack Hello World app from scratch (actually from a git repo that contains a Gradle Wrapper) to heroku.

## What to do

1. Clone the repo at https://github.com/erdi/ratpack-heroku-primer

1. Run `./gradlew idea` and open the project in the IDE

1. Add the [Groovy Ratpack plugin](https://plugins.gradle.org/plugin/io.ratpack.ratpack-groovy) to the build

1. Add [Maven Central as the repository](https://docs.gradle.org/current/userguide/artifact_dependencies_tutorial.html#N10660) to the build so that Ratpack's dependencies can be resolved

1. Run `./gradlew idea` to import the dependencies to the IDE

1. Add a `src/ratpack/Ratpack.groovy` and implement a Ratpack application that responds with "Hello World!" to every single request

1. Run the app using `./gradlew run` and verify that it responds as expected

1. Add a `stage` task to your build which will be executed by heroku upon deployment. It should be a [`Sync`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Sync.html) task and should sync from contents of the main distribution (`distributions.main.contents`) into `deploy` dir of the project

1. Execute the `stage` task and verify that the contents of the distribution end up in the `deploy` dir 

1. Execute `cleanStage` task to get rid of the `deploy` dir

1. Add a `Procfile` file in the root of the project which tells heroku the location of your application with the following line: `web: deploy/bin/ratpack-heroku-primer`

1. Commit your changes

1. [Create a heroku account](https://signup.heroku.com/login) if you don't have one and [install heroku CLI](https://toolbelt.heroku.com/) if you don't have it on your machine

1. Login to heroku by executing `heroku login` on your command line

1. Execute `heroku create` in the root of your project to add a heroku app for it

1. Note the url of the created application - your application will be deployed at that address

1. Push master to the `heroku` remote added to your repository by the previous command and see the app being built and deployed

1. Assign a dyno to your application by executing `heroku ps:scale web=1`

1. Go to the url of your application and see it respond with "Hello world!"

1. Customise the response text in your application script, commit, push to heroku and verify that the new response text is used