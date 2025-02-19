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

package org.wso2.apk.apimgt.api.model.policy;

public class ApplicationPolicy extends Policy {
    private int applicationId;
    
    private byte[] customAttributes;

    public ApplicationPolicy(String name) {
        super(name);
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
    
    public byte[] getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(byte[] customAttributes) {
		this.customAttributes = customAttributes;
	}

    @Override
    public String toString() {
        return "ApplicationPolicy [policyName=" + getPolicyName()
                + ", applicationId =" + applicationId + ", description=" + getDescription() + ", defaultQuotaPolicy="
                + getDefaultQuotaPolicy() + "]";
    }
}
