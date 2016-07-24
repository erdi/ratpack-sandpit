# Lab 02 - Reusable handlers lab

There are many good reasons to refactor parts of your handler chain into their own classes.  For example:

* Improve readability
* Allow handlers to be easily unit tested
* Share common handlers across applications
* Extend the handler DSL with your own shortcut methods

In this lab you will see how easy this is and still take advantage of the Groovy handler DSL.

## What to do

Simply make all the feature methods in `UserHandlersSpec` and `SoapActionHandlerSpec` pass. There are more hints available
within the specs. When you're done, change the application script to use `UserHandlers` and the dsl handler shortcut for SOAP actions.

Don't forget that `HandlerSpec` should also still completely pass.

## This lab covers

* Refactor a Chain into it's own class
* Refactor a repeating handler pattern into a handler shortcut

## Sign Posts

[`ratpack.groovy.handling.GroovyChainAction`](https://ratpack.io/manual/current/api/ratpack/groovy/handling/GroovyChainAction.html)

[Groovy extension modules](http://groovy-lang.org/metaprogramming.html#_extending_existing_classes)

