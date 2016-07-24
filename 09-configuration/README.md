# Lab 09 - Config

Most applications need some level of configuration. 
This can be used to specify the correct external resources to use (databases, other services, etc.), tune performance, or otherwise adjust to the requirements of a given environment. 
Ratpack provides an easy, flexible mechanism to access configuration information in your applications.

Configuration data is accessed via object binding which uses Jackson. 
Configuration data can be loaded from multiple sources, such as YAML files, JSON files, properties files, environment variables and system properties.
It's also possible to use custom configuration sources like a database or configuration management systems like consul by writing a class implementing `ConfigSource`.

In this lab we will learn how to use all of the built-in configuration source and how to write a custom one.

## What to do
In this lab simply make all the feature methods in `ConfigurationSpec` pass by adding the right config sources to `Ratpack.groovy`.

## This lab covers

* Using built-in configuration sources
* Implementing a custom configuration source
* Mapping configuration data to objects

## Sign Posts

[`ratpack.server.ServerConfigBuilder`](https://ratpack.io/manual/current/api/ratpack/server/ServerConfigBuilder.html)

[`ratpack.config.ConfigSource`](https://ratpack.io/manual/current/api/ratpack/config/ConfigSource.html)