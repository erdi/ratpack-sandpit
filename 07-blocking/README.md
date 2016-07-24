# Lab 07 - Blocking lab

So far we have been working with CPU bound code. We have not had to perform any kind of synchronous or blocking actions.
Ratpack is asynchronous and non-blocking from the ground up. Ratpack's APIs are marked as `ratpack.api.NonBlocking` where
appropriate to denote the API's non-blocking and asynchronous nature.

In the real world our existing libraries tend to be blocking and synchronous like the JDBC library.

Ratpack uses a much smaller set of Threads to manage HTTP Request handling, a single thread could be responsible for handling
hundreds of active connections. If you perform a blocking action one of Ratpack's main compute threads you will impede
throughput and prevent other requests from being processed until the blocking code completes.

Ratpack provides a great utility for integrating synchronous/blocking code with Ratpack's Execution Model via
`ratpack.exec.Blocking`. This `Blocking` utility allows users to specify blocking code that will inform Ratpack to
 execute the blocking code on Ratpack's blocking scheduler.

Ratpack provides two constructs for representing an asynchronous units of work: `ratpack.exec.Promise` and `ratpack.exec.Operation`.

The `Blocking` utility creates `Promise` and `Operation` from user supplied code which can then be further mapped or consumed.

## What to do

Make the tests in `BookSpec` pass. 

To be able to do that will need to implement the `DefaultBookRepository` to perform blocking database calls.
This lab provides generated jOOQ library that specifies a typed representation of the `book` table.

There are also some TODOs within `Ratpack.groovy`.

## This lab covers

* Working with synchronous/blocking libraries
* Working with Promises and Operations
* Working with Blocking api
* Working with Ratpack's integration with H2 and Hikari
* Parsing incoming JSON

## Sign Posts

[`ratpack.exec.Promise`](https://ratpack.io/manual/current/api/ratpack/exec/Promise.html)

[`ratpack.exec.Operation`](https://ratpack.io/manual/current/api/ratpack/exec/Operation.html)

[`ratpack.exec.Blocking`](https://ratpack.io/manual/current/api/ratpack/exec/Blocking.html)

[`ratpack.handling.Context#parse(Class)`](https://ratpack.io/manual/current/api/ratpack/handling/Context.html#parse-java.lang.Class-)