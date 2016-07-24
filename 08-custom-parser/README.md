# Lab 08 - Custom parser lab

In the previous lab you learned about the `Context#parse(Object)` method.
This method is backed by classes implementing the `Parser` interface which are available in the context registry.
Ratpack ships with parsers for url encoded forms and json but it's very easy to add a custom parser.
In this lab you will learn how to.

## What to do

The `BookSpec` has been changed to send book to the server using XML instead of json.
Make it pass by adding a custom parser which extends `NoOptParserSupport` and uses `XmlMapper` to do the heavy lifting.
You can have a look at `JsonParser` for inspiration.
Don't forget to make your new parser available in the registry and ensure that `grooxy.util.XmlParser` is not shading your parser in your Ratpack script!

## This lab covers

* Custom parsers

## Sign Posts

[`ratpack.parse.Parser`](https://ratpack.io/manual/current/api/ratpack/parse/Parser.html)

[`ratpack.parse.NoOptParserSupport`](https://ratpack.io/manual/current/api/ratpack/parse/NoOptParserSupport.html)