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

package com.google.code.axonguice.grouping;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import javax.annotation.Nullable;

/**
 * Basic predicates for {@link ClassesSearchGroupBuilder#withInclusionFilterPredicate(Predicate)}/{@link
 * ClassesSearchGroupBuilder#withExclusionFilterPredicate(Predicate)}.
 * Predicates can be grouped, see {@link Predicates}.
 *
 * @author Alexey Krylov
 * @see Predicates
 * @see ClassesSearchGroup
 * @since 07.02.13
 */
public interface ClassesSearchGroupFilterPredicates {
    Predicate<Class> AlowAll = new Predicate<Class>() {
        @Override
        public boolean apply(@Nullable Class input) {
            return true;
        }
    };

    Predicate<Class> DenyAll = new Predicate<Class>() {
        @Override
        public boolean apply(@Nullable Class input) {
            return false;
        }
    };
}