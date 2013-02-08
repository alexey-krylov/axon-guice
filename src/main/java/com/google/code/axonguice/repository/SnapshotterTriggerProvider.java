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

import com.google.inject.Provider;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventstore.SnapshotEventStore;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * SnapshotterProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public abstract class SnapshotterTriggerProvider implements Provider<SnapshotterTrigger> {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected SnapshotEventStore eventStore;
    protected Class<? extends AggregateRoot> aggregateRootClass;
    protected Snapshotter snapshotter;

    /*===========================================[ CONSTRUCTORS ]=================*/

    protected SnapshotterTriggerProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        this.aggregateRootClass = aggregateRootClass;
    }

    @Inject
    protected void init(SnapshotEventStore eventStore) {
        List<AggregateFactory<?>> genericAggregateFactories = new ArrayList<AggregateFactory<?>>();
        genericAggregateFactories.add(new GenericAggregateFactory(aggregateRootClass));
        AggregateSnapshotter aggregateSnapshotter = new AggregateSnapshotter();
        aggregateSnapshotter.setEventStore(eventStore);
        aggregateSnapshotter.setAggregateFactories(genericAggregateFactories);

        snapshotter = aggregateSnapshotter;
    }
}
