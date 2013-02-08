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

import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

/**
 * RepositoryProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public abstract class RepositoryProvider implements Provider<Repository> {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    protected EventBus eventBus;

    @Inject
    protected EventStore eventStore;

    @Inject
    protected Map<Class<? extends AggregateRoot>, SnapshotterTrigger> snapshotterTriggers;

    protected Class<? extends AggregateRoot> aggregateRootClass;

	/*===========================================[ CONSTRUCTORS ]=================*/

    protected RepositoryProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        this.aggregateRootClass = aggregateRootClass;
    }
}