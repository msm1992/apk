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

import java.io.Serializable;

public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    private int policyId;
    private String uuid;
    private String policyName;
    private String displayName;
    private String description;
    private QuotaPolicy defaultQuotaPolicy;
    private String tenantDomain;
    private boolean isDeployed;

    public Policy(String name){
        this.policyName = name;
        this.policyId = -1;
        this.isDeployed = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setDefaultQuotaPolicy(QuotaPolicy defaultQuotaPolicy) {
        this.defaultQuotaPolicy = defaultQuotaPolicy;
    }

    public QuotaPolicy getDefaultQuotaPolicy() {
        return defaultQuotaPolicy;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }


    @Override
    public String toString() {
        return "Policy{" +
                "defaultQuotaPolicy=" + defaultQuotaPolicy +
                ", policyName='" + policyName + '\'' +
                ", description='" + description +
                '}';
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }
}
