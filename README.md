# spray-actor-per-request

The set-up of this template has been greatly inspired from the
[NET-A-PORTER](https://github.com/NET-A-PORTER/spray-actor-per-request) version. I've made the following changes:

* Use a router for incoming requests this improves response-times when route matching is slow
* Made the `PerRequest` trait more generic