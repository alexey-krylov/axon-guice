## Developer Guide ##
Welcome to the developer documentation of _axon-guice_ project - integration module between Google Guice and Axon Framework.

Current project version is: **1.0.0**

## Axon Framework - what is it? ##
Here is description from Axon Framework authors:

"_Axon Framework helps build scalable, extensible and maintainable applications by supporting developers apply the Command Query Responsibility Segregation (CQRS) architectural pattern. It does so by providing implementations of the most important building blocks, such as aggregates, repositories and event buses (the dispatching mechanism for events). Furthermore, Axon provides annotation support, which allows you to build aggregates and event listeners withouth tying your code to Axon specific logic. This allows you to focus on your business logic, instead of the plumbing, and helps you to make your code easier to test in isolation._"

## What are axon-guice? ##
Axon-Guice is an integration module between [Google Guice](http://code.google.com/p/google-guice/) and [Axon Framework](http://www.axonframework.org).

Module provides:
  * Full support of [1-8](http://www.axonframework.org/docs/2.0/single.html#introduction) and [10](http://www.axonframework.org/docs/2.0/single.html#performance-tuning) of original Axon Framework documentation parts (all parts except Spring integration);
  * Highly customizable API;
  * Ability to inject all major Axon Framework components: CommandBus, EventBus, CommandGateway, UnitOfWork, Repository;
  * Option to perform components auto-discovery or specify all of them manually - see [ExtendingAxonGuiceModule](ExtendingAxonGuiceModule.md);
  * Significant [test coverage](http://code.google.com/p/axon-guice/source/browse/trunk/src/test/java/com/google/code/axonguice).

## How did this work? ##

There is a four main steps:

1. Carefully read Axon [manual](http://www.axonframework.org/download/) - all Axon features is available with this project

2. Install a Axon-Guice module:
```
// auto-discovery of all CQRS/Axon components
install(new AxonGuiceModule("com.mycomp.domain"));
```

3. Define your Aggregate Roots, Entities, Commands/Events, Command/Event Handlers, Domain Services, Query Services, Sagas and so on

4. [Override specific parts of AxonGuiceModule](ExtendingAxonGuiceModule.md) to meet your production requirements - for example you can use MongoEventStore instead of default FileSystemEventStore.

## Why not just use pure Axon Framework? ##

Of course you can... but with Spring Framework only or with manual components instantiation and wiring mode.

Module allows you to use Axon with all Guice pleasure - injection, modularity, absence of XML-based configurations, AOP, explicit bindings and so on.

## Where can i get it? ##

Project is mavenized and published in the Central Maven repository.

```
<dependency>
    <groupId>com.google.code.axon-guice</groupId>
    <artifactId>axon-guice</artifactId>
    <version>${version.axon-guice}</version>
</dependency>
```

This project is depends on several 3rd party libraries which is required to work. So, _without_ Maven you should manually add actual versions of this 3rd party libraries. Actual list of them can be viewed in _dependencies_ section of [pom.xml](http://code.google.com/p/axon-guice/source/browse/trunk/pom.xml).

Actual project version will be always on top of this page.