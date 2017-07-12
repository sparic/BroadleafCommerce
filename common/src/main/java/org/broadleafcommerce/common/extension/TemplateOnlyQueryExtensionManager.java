/*
 * #%L
 * BroadleafCommerce Framework Web
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.common.extension;

import org.broadleafcommerce.common.extension.ExtensionHandler;
import org.broadleafcommerce.common.extension.ExtensionManager;
import org.broadleafcommerce.common.extension.ExtensionManagerOperation;
import org.broadleafcommerce.common.extension.ExtensionResultHolder;
import org.broadleafcommerce.common.extension.ExtensionResultStatusType;
import org.broadleafcommerce.common.extension.ItemStatus;
import org.broadleafcommerce.common.extension.ResultType;
import org.broadleafcommerce.common.extension.SparselyPopulatedQueryExtensionHandler;
import org.broadleafcommerce.common.extension.TemplateOnlyQueryExtensionHandler;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Provides specialized filter and restriction behavior for template-related (MT concept) queries. This is only meaningful
 * in a multi-tenant installation.
 *
 * @see TemplateOnlyQueryExtensionHandler
 * @author Jeff Fischer
 */
@Service("blTemplateOnlyQueryExtensionManager")
public class TemplateOnlyQueryExtensionManager extends ExtensionManager<TemplateOnlyQueryExtensionHandler> implements TemplateOnlyQueryExtensionHandler {

    public static final ExtensionManagerOperation refineRetrieve = new ExtensionManagerOperation() {
        @Override
        public ExtensionResultStatusType execute(ExtensionHandler handler, Object... params) {
            return ((TemplateOnlyQueryExtensionHandler) handler).refineRetrieve((Class<?>) params[0], params[1], (CriteriaBuilder) params[2], (CriteriaQuery) params[3], (Root) params[4], (List<Predicate>) params[5]);
        }
    };

    public static final ExtensionManagerOperation setup = new ExtensionManagerOperation() {
        @Override
        public ExtensionResultStatusType execute(ExtensionHandler handler, Object... params) {
            return ((TemplateOnlyQueryExtensionHandler) handler).setup((Class<?>) params[0]);
        }
    };

    public static final ExtensionManagerOperation breakdown = new ExtensionManagerOperation() {
        @Override
        public ExtensionResultStatusType execute(ExtensionHandler handler, Object... params) {
            return ((TemplateOnlyQueryExtensionHandler) handler).breakdown((Class<?>) params[0]);
        }
    };

    public static final ExtensionManagerOperation refineOrder = new ExtensionManagerOperation() {
        @Override
        public ExtensionResultStatusType execute(ExtensionHandler handler, Object... params) {
            return ((TemplateOnlyQueryExtensionHandler) handler).refineOrder((Class<?>) params[0], (CriteriaBuilder) params[1], (CriteriaQuery) params[2], (Root) params[3], (List<Order>) params[4]);
        }
    };

    public static final ExtensionManagerOperation isValidState = new ExtensionManagerOperation() {
        @Override
        public ExtensionResultStatusType execute(ExtensionHandler handler, Object... params) {
            return ((TemplateOnlyQueryExtensionHandler) handler).isValidState((ExtensionResultHolder<Boolean>) params[0]);
        }
    };

    public static final ExtensionManagerOperation buildStatus = new ExtensionManagerOperation() {
        @Override
        public ExtensionResultStatusType execute(ExtensionHandler handler, Object... params) {
            return ((TemplateOnlyQueryExtensionHandler) handler).buildStatus(params[0], (ExtensionResultHolder<ItemStatus>) params[1]);
        }
    };

    public TemplateOnlyQueryExtensionManager() {
        super(TemplateOnlyQueryExtensionHandler.class);
    }

    @Override
    public ExtensionResultStatusType refineRetrieve(Class<?> type, Object testObject, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Predicate> restrictions) {
        return execute(refineRetrieve, type, testObject, builder, criteria, root, restrictions);
    }

    @Override
    public ExtensionResultStatusType setup(Class<?> type) {
        return execute(setup, type);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public ExtensionResultStatusType breakdown(Class<?> type) {
        return execute(breakdown, type);
    }

    @Override
    public ExtensionResultStatusType refineOrder(Class<?> type, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Order> sorts) {
        return execute(refineOrder, type, builder, criteria, root, sorts);
    }

    @Override
    public ExtensionResultStatusType isValidState(ExtensionResultHolder<Boolean> response) {
        return execute(isValidState, response);
    }

    @Override
    public ExtensionResultStatusType buildStatus(Object entity, ExtensionResultHolder<ItemStatus> response) {
        return execute(buildStatus, entity, response);
    }
}