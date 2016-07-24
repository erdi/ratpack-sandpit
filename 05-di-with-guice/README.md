# Lab 05 - Google Guice lab

Ratpack provides integration with Google Guice for dependency injection.  Other DI integrations can be used with Ratpack
but the Guice one is of particular importance as additional Ratpack functionality is packaged up as Guice modules.

One key concept to understand about the Guice integration is that it provides a Guice backed `Registry`.  This means that
any objects bound with Guice are available in the `Context`.  As Handlers and other Ratpack infrastructure use the Context
to look up objects it means we are completely decoupled from any one DI implementation and can even use multiple implementations.

## What to do

1. Bind `DefaultBookService` as the implementation of `BookService` to Guice.
1. Bind `DefaultBookRepository` as the implementation of `BookRepository` (which is injected into DefaultBookService) to Guice.
1. Make sure `BookSpec` passes.

This time the hints are in the TODO within `Ratpack.groovy`

## This lab covers

* Binding objects with Google Guice

## Sign Posts

[`ratpack.groovy.Groovy.Ratpack#bindings(Closure)`](https://ratpack.io/manual/current/api/ratpack/groovy/Groovy.Ratpack.html#bindings-groovy.lang.Closure-)

[`ratpack.guice.BindingsSpec`](https://ratpack.io/manual/current/api/ratpack/guice/BindingsSpec.html)