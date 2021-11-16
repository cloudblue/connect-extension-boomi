/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.test.common;

import com.boomi.connector.api.*;

import java.util.Map;
import java.util.logging.Logger;

public abstract class BaseFilterTest {

    protected SimpleExpression createSimpleExp(
            String operator, String property, String... arguments) {
        return new SimpleExpression()
                .withOperator(operator)
                .withProperty(property)
                .withArguments(arguments);
    }

    protected GroupingExpression createGroupingExp(
            GroupingOperator operator, Expression... expressions) {
        return new GroupingExpression()
                .withOperator(operator)
                .withNestedExpressions(expressions);
    }

    protected FilterData getFilterData(QueryFilter queryFilter) {
        return new FilterData() {
            @Override
            public QueryFilter getFilter() {
                return queryFilter;
            }

            @Override
            public Object getUniqueId() {
                return null;
            }

            @Override
            public Object getTrackingId() {
                return null;
            }

            @Override
            public Map<String, String> getUserDefinedProperties() {
                return null;
            }

            @Override
            public Map<String, String> getDynamicProperties() {
                return null;
            }

            @Override
            public DynamicPropertyMap getDynamicOperationProperties() {
                return null;
            }

            @Override
            public Logger getLogger() {
                return null;
            }
        };
    }
}
