/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.apk.apimgt.api;

/**
 * This is the custom exception class to be thrown when a new resource which is attempted to add conflicts 
 * with an already existing resource.
 */
public class APIMgtResourceAlreadyExistsException extends APIManagementException {

    public APIMgtResourceAlreadyExistsException(String msg) {
        super(msg);
    }

    public APIMgtResourceAlreadyExistsException(String msg, Throwable e) {
        super(msg, e);
    }

    public APIMgtResourceAlreadyExistsException(String msg, ErrorHandler errorHandler) {
        super(msg, errorHandler);
    }

    public APIMgtResourceAlreadyExistsException(Throwable throwable) {
        super(throwable);
    }
}
