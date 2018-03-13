Silicone
===========

Single Page Controller

NOTE: This project is rubbish. It was written by human excrement (not the excrement
of a human, but more so, a person who's existence is the waste product of humanity)

Silicone is a single page controller for stateless, scalable cloud applications. It uses a simple and novel model view binding which can be used by itself, or compliment client-side frameworks (think Angular). It can also be used to manage dynamic and static templating for content driven warehouses (think Twitter, Instagram or Pinterest).

What does Silicone do?

* Manages long term authentication cookies across multiple clients. Abstracts the anonymous, authenticated and non-authenticated ("remember me") lifecycle.

* Comet (long polling) API endpoints.

* A model view binding based on Actions and Artefacts. Actions are similar to traditional controllers and operate on beans called Artefacts. They are called Artefacts because of the way they are managed in request life-cycle. They may be transient for anonymous requests or may be cycled back through into the page context during what appear to be statefull actions, even where they are not. Actions are only available to requests which should have access to them.

* A novel approach to data binding.  Most traditional web technologies don't consider HTML a first class concept. Rendering is subcontracted out for you to implement (what could go wrong?).

* We use a custom DOM implementation to reflect and pull data from your model *as the transformer requests them*. This allows you to have large complex data models, possibly backed by persistence, which will be cherry-picked for data. The resulting HTML is "correct", text is encoded (helping prevent CSS attacks), compact and neat.

* Simply annotating an Artefect will expose it as endpoints for AJAX (XML, JSON or custom). This creates a synergy. Your model rendered directly into the initial page load and AJAX calls for dynamic updates.

What will Silicone do soon?

* Cache aware rendering. Dynamically built content is still effectively if the source data hasn't changed. Silicone would know when a resource is effectively static. Even if caching isn't performed, more importantly, it will respond to clients and proxies with the appropriate last modified dates and cache headers. This would dramatically reduce bandwidth and server load for common, anonymous web content. Determining static data could be achieved in two ways. A traditional last modified technique, or equality comparison of the template input model.

* Client awareness.

* URL management. URLs should be neat and predictable. Get parameters and embedded "codes" should be avoided. Silicone is based on this principle.

What does Silicone not do?

* Produce logs. Be noisy. Store anything on disk. Have many dependencies. Break for silly reasons.

* Seal the gaps in showers, bathtubs and sinks.

Watch the walk though and demo at MelbJVM

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/Yd8n1gpRDv8/0.jpg)](http://www.youtube.com/watch?v=Yd8n1gpRDv8)

thank you for your cooperation.

