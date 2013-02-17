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

package com.google.code.axonguice.domain.api;

import com.google.code.axonguice.domain.model.OrderId;

import java.io.Serializable;

/**
 * @author Alexey Krylov
 * @since 17.02.13
 */
public class AbstractOrderEvent implements Serializable {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = 1169187425619138782L;

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    protected OrderId orderId;

	/*===========================================[ CONSTRUCTORS ]=================*/

    protected AbstractOrderEvent(OrderId orderId) {
        this.orderId = orderId;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public OrderId getOrderId() {
        return orderId;
    }
}
