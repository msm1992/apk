// Copyright (c) 2021, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

package resource

// Resource types in xDS v3.
const (
	apiTypePrefix                 = "type.googleapis.com/"
	ConfigType                    = apiTypePrefix + "wso2.discovery.config.enforcer.Config"
	APIType                       = apiTypePrefix + "wso2.discovery.api.Api"
	SubscriptionListType          = apiTypePrefix + "wso2.discovery.subscription.SubscriptionList"
	ApplicationListType           = apiTypePrefix + "wso2.discovery.subscription.ApplicationList"
	APIListType                   = apiTypePrefix + "wso2.discovery.subscription.APIList"
	ApplicationPolicyListType     = apiTypePrefix + "wso2.discovery.subscription.ApplicationPolicyList"
	SubscriptionPolicyListType    = apiTypePrefix + "wso2.discovery.subscription.SubscriptionPolicyList"
	ApplicationKeyMappingListType = apiTypePrefix + "wso2.discovery.subscription.ApplicationKeyMappingList"
	KeyManagerType                = apiTypePrefix + "wso2.discovery.keymgt.KeyManagerConfig"
	RevokedTokensType             = apiTypePrefix + "wso2.discovery.keymgt.RevokedToken"
	ThrottleDataType              = apiTypePrefix + "wso2.discovery.throttle.ThrottleData"
	GAAPIType                     = apiTypePrefix + "wso2.discovery.ga.Api"
	APKMgtApplicationType         = apiTypePrefix + "wso2.discovery.apkmgt.ApplicationDetails"

	// AnyType is used only by ADS
	AnyType = ""
)

// Type is an alias to string which we expose to users of the snapshot API which accepts `resource.Type` resource URLs.
type Type = string
