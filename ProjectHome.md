# Overview #

Axon-Guice is an integration module between [Google Guice](http://code.google.com/p/google-guice/) and [Axon Framework](http://www.axonframework.org).

Axon Framework is a lightweight and powerful CQRS framework for Java. It has rich documentation and clean codebase. Integration module is built for 2.0 framework version.

## CQRS approach motivation ##
"_A single model cannot be appropriate for reporting, searching, and transactional behaviors._" – **Greg Young**

"_Every method should either be a command that performs an action, or a query that returns data to the caller, but not both._" - **Bertrand Meyer**

[![](http://axon-guice.googlecode.com/svn/wiki/img/schematics.png)](http://www.blogcoward.com/archive/2009/12/31/cqrs-for-dummies-ndash-0-of-N---Overview.aspx)


Theory can be found here:
  * http://www.amazon.com/Implementing-Domain-Driven-Design-Vaughn-Vernon/dp/0321834577
  * http://martinfowler.com/bliki/CQRS.html
  * http://en.wikipedia.org/wiki/Command–query_separation
  * http://www.slideshare.net/pjvdsande/rethink-your-architecture-with-cqrs
  * http://www.slideshare.net/pjvdsande/introduction-to-cqrs
  * http://www.udidahan.com/2009/12/09/clarified-cqrs/
  * http://codebetter.com/gregyoung/2010/02/13/cqrs-and-event-sourcing/
  * http://prezi.com/pvfwd4xscqxy/command-query-responsibility-segregation-evolution-of-the-layered-architecture/
  * http://abdullin.com/journal/2010/10/22/top-10-reasons-to-do-cqrs-in-a-pdf.html
  * [Exploring CQRS book from Microsoft ](http://www.microsoft.com/en-us/download/details.aspx?id=34774)

All reference documentation related to **Axon Framework** is
[here](http://www.axonframework.org/docs/2.0/single.html).


---


## Project features ##
  * Full support of [1-8](http://www.axonframework.org/docs/2.0/single.html#introduction) and [10](http://www.axonframework.org/docs/2.0/single.html#performance-tuning) of original Axon Framework documentation parts (all parts except Spring integration);
  * Highly customizable API;
  * Ability to inject all major Axon Framework components: CommandBus, EventBus, CommandGateway, UnitOfWork, Repository;
  * Option to perform components auto-discovery or specify all of them manually;
  * Significant [test coverage](http://code.google.com/p/axon-guice/source/browse/trunk/src/test/java/com/google/code/axonguice).


---


## Quickstart ##

1. Read Axon [manual](http://www.axonframework.org/download/) - all Axon features is available with this project

2. Install a Axon-Guice module:
```
// auto-discovery of all CQRS/Axon components
install(new AxonGuiceModule("com.mycomp.domain"));
```

3. Define your Aggregate Roots, Entities, Commands/Events, Command/Event Handlers, Domain Services, Query Services, Sagas and so on

4. [Override specific parts](ExtendingAxonGuiceModule.md) of AxonGuiceModule to meet your production requirements - for example you can use MongoEventStore instead of default FileSystemEventStore.

---


## Mavenize ##

Artifact is available in the Central Maven repository:
```
<dependency>
    <groupId>com.google.code.axon-guice</groupId>
    <artifactId>axon-guice</artifactId>
    <version>1.0.0</version>
</dependency>
```