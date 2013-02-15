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

package com.google.code.axonguice.saga;

import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesSearchGroup;
import com.google.inject.Scopes;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.SagaFactory;
import org.axonframework.saga.SagaManager;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collection;

/**
 * SagaModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class SagaModule extends AbstractClassesGroupingModule<AbstractAnnotatedSaga> {

	/*===========================================[ CONSTRUCTORS ]=================*/

    @SafeVarargs
    public SagaModule(Class<? extends AbstractAnnotatedSaga>... classes) {
        super(classes);
    }

    public SagaModule(String... sagasScanPackages) {
        super(sagasScanPackages);
    }

    public SagaModule(Collection<ClassesSearchGroup> sagasClassesSearchGroups) {
        super(sagasClassesSearchGroups);
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindResourceInjector();
        bindSagaFactory();
        bindSagaRepository();
        bindSagaManager();
    }

    protected void bindResourceInjector() {
        bind(ResourceInjector.class).to(GuiceResourceInjector.class).in(Scopes.SINGLETON);
    }

    protected void bindSagaFactory() {
        bind(SagaFactory.class).toProvider(GenericSagaFactoryProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindSagaRepository() {
        bind(SagaRepository.class).toProvider(InMemorySagaRepositoryProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindSagaManager() {
        Collection<Class<? extends AbstractAnnotatedSaga>> sagaTypes = new ArrayList<>();

        if (classesGroup.isEmpty()) {
            // find all saga subclasses
            for (ClassesSearchGroup classesSearchGroup : classesSearchGroups) {
                Collection<String> packagesToScan = classesSearchGroup.getPackages();
                logger.info(String.format("Searching %s for Sagas", packagesToScan));

                Reflections reflections = createReflections(packagesToScan);

                // Extraction of all AbstractAnnotatedSaga subclasses
                Collection<Class<? extends AbstractAnnotatedSaga>> validSagaClasses =
                        filterSearchResult(reflections.getSubTypesOf(AbstractAnnotatedSaga.class), classesSearchGroup);
                for (Class<?> sagaClass : validSagaClasses) {
                    logger.info(String.format("\tFound: [%s]", sagaClass.getName()));
                }

                sagaTypes.addAll(validSagaClasses);
            }
        } else {
            sagaTypes.addAll(classesGroup);
        }

        if (!sagaTypes.isEmpty()) {
            AnnotatedSagaManagerProvider annotatedSagaManagerProvider = new AnnotatedSagaManagerProvider(sagaTypes);
            requestInjection(annotatedSagaManagerProvider);
            bind(SagaManager.class).toProvider(annotatedSagaManagerProvider).in(Scopes.SINGLETON);
        } else {
            logger.info("No Sagas found");
        }
    }
}