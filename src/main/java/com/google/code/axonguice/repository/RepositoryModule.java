/*
 * Copyright (C) 2013 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.axonguice.repository;

import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesGroup;
import com.google.code.axonguice.util.ReflectionsHelper;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.util.Types;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.repository.Repository;
import org.reflections.Reflections;

import java.util.Collection;

/**
 * RepositoryModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class RepositoryModule extends AbstractClassesGroupingModule {

    /*===========================================[ CONSTRUCTORS ]=================*/

    public RepositoryModule(String... aggregatesRepositoriesScanPackages) {
        super(aggregatesRepositoriesScanPackages);
    }

    public RepositoryModule(Collection<ClassesGroup> aggregatesRepositoriesClassesGroups) {
        super(aggregatesRepositoriesClassesGroups);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindEventStore();
        bindSnaphotEventStore();
        bindRepositories();
    }

    protected void bindEventStore() {
        // upcasting is here
        bind(EventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindSnaphotEventStore() {
        bind(SnapshotEventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindRepositories() {
        MapBinder<String, SnapshotterTrigger> mapBinder = MapBinder.newMapBinder(binder(), String.class, SnapshotterTrigger.class);

        for (ClassesGroup classesGroup : classesGroups) {
            Collection<String> packagesToScan = classesGroup.getPackages();
            logger.info(String.format("Searching %s for Aggregate Roots", packagesToScan));

            Reflections reflections = createReflections(packagesToScan);

            Iterable<Class<? extends AggregateRoot>> validAggregateRoots = filterClasses(classesGroup, ReflectionsHelper.findAggregateClasses(reflections));

            for (Class<? extends AggregateRoot> aggregateRootClass : validAggregateRoots) {
                logger.info(String.format("Found: [%s]", aggregateRootClass.getName()));
                bindSnapshotter(mapBinder, aggregateRootClass);
                bindRepository(aggregateRootClass);
            }
        }
    }

    protected void bindSnapshotter(MapBinder<String, SnapshotterTrigger> mapBinder, Class<? extends AggregateRoot> aggregateRootClass) {
        Provider snapshotterTriggerProvider = new SimpleEventCountSnapshotterTriggerProvider(aggregateRootClass);
        requestInjection(snapshotterTriggerProvider);
        String aggregateRootClassName = aggregateRootClass.getName();
        mapBinder.addBinding(aggregateRootClassName).toProvider(snapshotterTriggerProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\tSnapshotter set to: [%s]", snapshotterTriggerProvider.getClass().getName()));
    }

    protected void bindRepository(Class<? extends AggregateRoot> aggregateRootClass) {
        Provider repositoryProvider = new EventSourcingRepositoryProvider(aggregateRootClass);
        requestInjection(repositoryProvider);
        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(Repository.class, aggregateRootClass)))).toProvider(repositoryProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\tRepository set to: [%s]", repositoryProvider.getClass().getName()));
    }
}