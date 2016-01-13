# Introduction #

By default _AxonGuiceModule_ configured to be usable out of the box but with one  qualification - this usability acceptable only for testing: it uses FileSystemEventStore to store events, Sagas is not persistent, Command/Event buses will be functional only in one JVM.

All power of Axon can be achieved only by extending _AxonGuiceModule_.

# Details #

_AxonGuiceModule_ contains several mandatory modules - you can change implementation each of them by overriding createXXXModule methods:
```
public class MyAxonGuiceModule extends AxonGuiceModule {

    @Override
    protected DomainModule createDomainModule() {
        return new EventSourcedDomainModule(Order.class);
    }

    @Override
    protected RepositoryModule createRepositoryModule() {
        return new EventSourcedRepositoryModule(Order.class);
    }

    @Override
    protected CommandHandlingModule createCommandHandlingModule() {
        return new CommandHandlingModule(SimpleCommandHandler.class, OrderCommandHandler.class);
    }

    @Override
    protected AggregateRootCommandHandlingModule createAggregateRootCommandHandlingModule() {
        return new AggregateRootCommandHandlingModule(Order.class);
    }

    @Override
    protected EventHandlingModule createEventHandlingModule() {
        return new EventHandlingModule(SimpleEventHandler.class);
    }

    @Override
    protected SagaModule createSagaModule() {
        return new SagaModule(TestOrderSaga.class);
    }
}
```

Some of this modules accepts constructor parameters, some of them not.
Modules with parameters has _AbstractClassesGroupingModule_ superclass.
This kind of modules intended to configure Axon subsystems which requires detailed knowledge of your domain - aggregate roots, event/command handlers, repositories and so on.
_AbstractClassesGroupingModule_ provides three options of retrieving this information:
  * Direct indication of required domain parts in module constructor (array of aggregate roots classes & etc);
  * Auto-search in provided packages;
  * Auto-search in provided collection of ClassesSearchGroup - this is most powerful way of AxonGuiceModule configuration. It allows combining search inclusion/exclusion filters/predicates and packages.

All auto-searches performs with [Reflections](http://code.google.com/p/reflections/) library.

# MongoDB, Redis, Cassandra, RabbitMQ, JMS & etc #

Axon-Guice does not provides all kind of implementation modules for the sake of simplicity. Instead of large amount of distinct (and usually very complex to configure) modules Axon-Guice provides elastic framework with clear extension points.

# Auto-search with Classes Search Groups #
Most powerful way of Axon components auto resolution is using ClassesSearchGroupBuilder.

Here is simple example:
```
ClassesSearchGroup classesSearchGroup = ClassesSearchGroupBuilder.forPackage("com.google.code.axonguice").
                withInclusionFilterPredicate(new Predicate<Class>() {
                    @Override
                    public boolean apply(@Nullable Class input) {
                        return SimpleCommandHandler.class.isAssignableFrom(input);
                    }
                }
                ).build();
```

Please refer original documentation related to [Guava predicates support](http://code.google.com/p/guava-libraries/wiki/FunctionalExplained#Predicates).