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

package org.wso2.apk.apimgt.impl.internal;

import org.wso2.apk.apimgt.impl.APIManagerConfigurationService;
import org.wso2.apk.apimgt.impl.config.APIMConfigService;
import org.wso2.apk.apimgt.impl.config.APIMConfigServiceImpl;

public class ServiceReferenceHolder {

    private static final ServiceReferenceHolder instance = new ServiceReferenceHolder();
    private APIManagerConfigurationService amConfigurationService;
    private APIMConfigService apimConfigService;

    private ServiceReferenceHolder() {

    }

    public static ServiceReferenceHolder getInstance() {

        return instance;
    }

    public APIManagerConfigurationService getAPIManagerConfigurationService() {

        return amConfigurationService;
    }

    public void setAPIManagerConfigurationService(APIManagerConfigurationService amConfigurationService) {

        this.amConfigurationService = amConfigurationService;
    }

    public void setAPIMConfigService(APIMConfigService apimConfigService) {
        this.apimConfigService = apimConfigService;
    }

    public APIMConfigService getApimConfigService() {
        if (apimConfigService != null){
            return apimConfigService;
        }
        return new APIMConfigServiceImpl();
    }

}
