# Lab 04 - Advanced registries lab

The context object mechanism also supports partitioning application logic by providing different objects to different partitions.
It is particularly useful when registering custom `ClientErrorHandler`s and `ServerErrorHandler`s which are contextual objects that handle
responding to requests resulting in errors.

## What to do

1. Follow the hints in `ErrorsSpec` to make it green. When editing the application script try to add handlers that modify the registry and not ones that throw additional exceptions.

## This lab covers

* Registering objects for different application partitions
* Registering objects to be available outside of the current partition
* Server and client error handlers

## Sign Posts

[`ratpack.error.ClientErrorHandler`](https://ratpack.io/manual/current/api/ratpack/error/ClientErrorHandler.html)

[`ratpack.error.ServerErrorHandler`](https://ratpack.io/manual/current/api/ratpack/error/ServerErrorHandler.html)

[Manual section on application partitioning](https://ratpack.io/manual/current/context.html#partitioning)