# Lab 01 - Handlers

Handlers are the fundamental component of any Ratpack application.  Unlike the routing table model you’re probably more
used to, all request processing in Ratpack apps is done by composing a chain of handlers.

When a request is received, a [`Context`](https://ratpack.io/manual/current/context.html#context) is created which represents the current state.
The context gives access to the request, the response and some other things we’ll see in future labs.
The context is passed to the handler chain and each handler in the chain is asked to respond until one does.
A handler can respond, do some logic and pass control to the next handler or insert new handlers and delegate to them.

## What to do
In this lab simply make all the feature methods in `HandlersSpec` pass by adding the right handlers to `Ratpack.groovy`.
Try to make each feature method pass in order and refactor as you go.

After making a feature method pass, don't forget to make sure the others still pass.

## This lab covers

* Simple routing
* Routing by HTTP method
* Routing by HTTP header
* Grouping handlers with the same prefix
* Routing by regular expression
* Using path tokens
* Static assets

## Sign Posts
[Manual section on handlers](http://www.ratpack.io/manual/current/handlers.html#handlers)

[`ratpack.handling.Chain`](https://ratpack.io/manual/current/api/ratpack/handling/Chain.html) and [`ratpack.handling.GroovyChain`](https://ratpack.io/manual/current/api/ratpack/groovy/handling/GroovyChain.html)

[`ratpack.handling.Context`](https://ratpack.io/manual/current/api/ratpack/handling/Context.html)