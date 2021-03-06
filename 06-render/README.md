# Lab 06 - Render lab

So far we have only used `response.send` to respond to requests.  Ratpack also has the concept of `Renderer` which we will
explore in this lab.

Ratpack provides many renderers out-of-the-box but you can also create your own renderers for custom objects.

## What to do

To complete this lab simply make all the feature methods in `RenderSpec` pass by using one of Ratpack's renderers or creating
one of your own.  Renderers are typically not used directly. Instead, they are used via by
`Context#render(Object)`.

## This lab covers

* Rendering String
* Rendering Groovy Markup Template
* Rendering a custom object with a different content type depending on what has been requested

## Sign Posts

[`ratpack.handling.Context#render(Object)`](https://ratpack.io/manual/current/api/ratpack/handling/Context.html#render-java.lang.Object-)

[`ratpack.render.Renderer`](https://ratpack.io/manual/current/api/ratpack/render/Renderer.html)