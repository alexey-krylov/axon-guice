## API design ##

Main principles that has been used for _axon-guice_ API design is simplicity and flexibility.

Most of _AxonGuiceModule_ fields and methods has _protected_ modifier, so you can easily create your own variation of this module.

## CQRS learning curve ##
CQRS approach is not a silver bullet for every project. This approach requires good programmers qualification with good OOP skills (in my opinion - senior developers level with 5 years of experience is a minimum). Also deep DDD understanding for all team members is required.

There is also known rule of thumb for project selection when CQRS and DDD can be very useful and effective - duration of the project should be at least 5 man-years.

Second rule of thumb - CQRS is not needed for 95% of all projects.

Third rule of thumb - project should have **complex** business logic.

Useful links:
  * [When to avoid CQRS](http://www.udidahan.com/2011/04/22/when-to-avoid-cqrs/)
  * [When to use DDD](http://shishkin.wordpress.com/2008/10/10/when-to-use-domain-driven-design/)
  * [DDD in practice](http://www.infoq.com/articles/ddd-in-practice)
  * [When to use Axon Framework](http://www.axonframework.org/docs/2.0/single.html#d4e56)