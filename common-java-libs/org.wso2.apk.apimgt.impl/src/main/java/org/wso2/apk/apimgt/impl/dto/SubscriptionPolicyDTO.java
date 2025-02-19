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

package org.wso2.apk.apimgt.impl.dto;

import java.io.Serializable;

public class SubscriptionPolicyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    int spikeArrestLimit;
    String spikeArrestUnit;
    boolean stopOnQuotaReach;
    String tierQuotaType;
    int graphQLMaxDepth;
    int graphQLMaxComplexity;

    public int getSpikeArrestLimit() {
        return spikeArrestLimit;
    }

    public void setSpikeArrestLimit(int spikeArrestLimit) {
        this.spikeArrestLimit = spikeArrestLimit;
    }

    public String getSpikeArrestUnit() {
        return spikeArrestUnit;
    }

    public void setSpikeArrestUnit(String spikeArrestUnit) {
        this.spikeArrestUnit = spikeArrestUnit;
    }

    public boolean getStopOnQuotaReach() {
        return stopOnQuotaReach;
    }

    public void setStopOnQuotaReach(boolean stopOnQuotaReach) {
        this.stopOnQuotaReach = stopOnQuotaReach;
    }

    public String getTierQuotaType() {
        return tierQuotaType;
    }

    public void setTierQuotaType(String tierQuotaType) {
        this.tierQuotaType = tierQuotaType;
    }

    public int getGraphQLMaxDepth() {
        return graphQLMaxDepth;
    }

    public void setGraphQLMaxDepth(int graphQLMaxDepth) {
        this.graphQLMaxDepth = graphQLMaxDepth;
    }

    public int getGraphQLMaxComplexity() {
        return graphQLMaxComplexity;
    }

    public void setGraphQLMaxComplexity(int graphQLMaxComplexity) {
        this.graphQLMaxComplexity = graphQLMaxComplexity;
    }
}
