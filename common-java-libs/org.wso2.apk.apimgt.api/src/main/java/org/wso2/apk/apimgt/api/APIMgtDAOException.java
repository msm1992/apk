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
 * This is the Exception class for DAO related exceptions.
 */
public class APIMgtDAOException extends APIManagementException {

    /**
     * Calling super class constructure.
     *
     * @param msg  Error message
     * @param code Error code
     */
    public APIMgtDAOException(String msg, ExceptionCodes code) {
        super(msg, code);
    }

    public APIMgtDAOException(String msg, Throwable e, ExceptionCodes code) {
        super(msg, e, code);
    }

    public APIMgtDAOException(String msg, Throwable e, ErrorHandler errorHandler) {
        super(msg, e, errorHandler);
    }

    public APIMgtDAOException(String msg) {
        super(msg, ExceptionCodes.APIMGT_DAO_EXCEPTION);
    }

    public APIMgtDAOException(String msg, Throwable e) {
        super(msg, e, ExceptionCodes.APIMGT_DAO_EXCEPTION);
    }
}
