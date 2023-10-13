//
// Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
//
// WSO2 LLC. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
//

import ballerina/http;
import ballerina/log;

import wso2/apk_common_lib as commons;

configurable int BACKOFFICE_PORT = 9443;

listener http:Listener ep0 = new (BACKOFFICE_PORT, secureSocket = {
    'key: {
        certFile: <string>keyStores.tls.certFilePath,
        keyFile: <string>keyStores.tls.keyFilePath
    }
});

@http:ServiceConfig {
    cors: {
        allowOrigins: ["*"],
        allowCredentials: true,
        allowHeaders: ["*"],
        exposeHeaders: ["*"],
        maxAge: 84900
    }
}

service http:InterceptableService /api/backoffice on ep0 {

    public function createInterceptors() returns http:Interceptor|http:Interceptor[] {
        http:Interceptor[] interceptors = [jwtValidationInterceptor, requestErrorInterceptor, responseErrorInterceptor];
        return interceptors;
    }
    isolated resource function get apis(http:RequestContext requestContext, string? query, @http:Header string? 'if\-none\-match, int 'limit = 25, int offset = 0, "apiName"|"version"|"createdTime"|"status" sortBy = "createdTime", string sortOrder = "desc", @http:Header string? accept = "application/json") returns APIList|http:NotModified|commons:APKError {
        commons:UserContext authenticatedUserContext = check commons:getAuthenticatedUserContext(requestContext);
        anydata groups = authenticatedUserContext.claims["x-wso2-groups"];
        commons:Organization organization = authenticatedUserContext.organization;
        return getAPIList('limit, offset, query, organization.uuid, groups);
    }

    isolated resource function get apis/[string apiId](http:RequestContext requestContext, @http:Header string? 'if\-none\-match) returns API|http:NotModified|commons:APKError {
        return getAPI(apiId);
    }
    resource function put apis/[string apiId](http:RequestContext requestContext, @http:Header string? 'if\-none\-match, @http:Payload ModifiableAPI payload) returns API|commons:APKError {
        return updateAPI(apiId, payload);
    }

    isolated resource function get apis/[string apiId]/definition(http:RequestContext requestContext, @http:Header string? 'if\-none\-match) returns APIDefinition|http:NotModified|commons:APKError {
        APIDefinition|commons:APKError apiDefinition = getAPIDefinition(apiId);
        if apiDefinition is APIDefinition {
            log:printDebug(apiDefinition.toString());
        }
        return apiDefinition;
    }
    isolated resource function get apis/[string apiId]/thumbnail(@http:Header string? 'if\-none\-match, @http:Header string? accept = "application/json") returns http:Response|http:NotModified|NotFoundError|NotAcceptableError|commons:APKError {
        return getThumbnail(apiId);
    }
    isolated resource function put apis/[string apiId]/thumbnail(@http:Header string? 'if\-match, http:Request message) returns FileInfo|BadRequestError|NotFoundError|PreconditionFailedError|commons:APKError|error {
       return updateThumbnail(apiId, message);
    }
    resource function get apis/[string apiId]/documents(@http:Header string? 'if\-none\-match, int 'limit = 25, int offset = 0, @http:Header string? accept = "application/json") returns DocumentList|http:NotModified|NotFoundError|NotAcceptableError|commons:APKError {
        return getDocumentList(apiId, 'limit, offset);
    }
    isolated resource function post apis/[string apiId]/documents(@http:Payload Document payload) returns Document|BadRequestError|UnsupportedMediaTypeError|commons:APKError|error {
        Document documentBody = check payload.cloneWithType(Document);

        Document|commons:APKError createdDocument = createDocument(apiId, documentBody);
        return createdDocument;
    }
    resource function get apis/[string apiId]/documents/[string documentId](@http:Header string? 'if\-none\-match, @http:Header string? accept = "application/json") returns Document|http:NotModified|NotFoundError|NotAcceptableError|commons:APKError {
        return getDocumentMetaData(apiId, documentId);
    }
    resource function put apis/[string apiId]/documents/[string documentId](@http:Header string? 'if\-match, @http:Payload Document payload) returns Document|BadRequestError|NotFoundError|PreconditionFailedError|commons:APKError|error {
        Document documentBody = check payload.cloneWithType(Document);
        return UpdateDocumentMetaData(apiId, documentId, documentBody);
    }
    resource function delete apis/[string apiId]/documents/[string documentId](@http:Header string? 'if\-match) returns http:Ok|NotFoundError|PreconditionFailedError|commons:APKError {
        http:Ok|NotFoundError|commons:APKError deletedDocument =  deleteDocument(apiId, documentId);
        return deletedDocument;
    }
    resource function get apis/[string apiId]/documents/[string documentId]/content(@http:Header string? 'if\-none\-match, @http:Header string? accept = "application/json") returns http:Response|http:SeeOther|http:NotModified|NotFoundError|NotAcceptableError|commons:APKError {
        return getDocumentContent(apiId, documentId);
    }
    resource function post apis/[string apiId]/documents/[string documentId]/content(@http:Header string? 'if\-match, http:Request message) returns Document|BadRequestError|NotFoundError|PreconditionFailedError|commons:APKError|error {
        return addDocumentContent(apiId, documentId, message);
    }
    // resource function get apis/[string apiId]/comments(int 'limit = 25, int offset = 0, boolean includeCommenterInfo = false) returns CommentList|NotFoundError|InternalServerErrorError {
    // }
    // resource function post apis/[string apiId]/comments(string? replyTo, @http:Payload 'postRequestBody payload) returns CreatedComment|BadRequestError|UnauthorizedError|NotFoundError|UnsupportedMediaTypeError|InternalServerErrorError {
    // }
    // resource function get apis/[string apiId]/comments/[string commentId](@http:Header string? 'if\-none\-match, boolean includeCommenterInfo = false, int replyLimit = 25, int replyOffset = 0) returns Comment|UnauthorizedError|NotFoundError|NotAcceptableError|InternalServerErrorError {
    // }
    // resource function delete apis/[string apiId]/comments/[string commentId](@http:Header string? 'if\-match) returns http:Ok|UnauthorizedError|ForbiddenError|NotFoundError|http:MethodNotAllowed|InternalServerErrorError {
    // }
    // resource function patch apis/[string apiId]/comments/[string commentId](@http:Payload 'patchRequestBody payload) returns Comment|BadRequestError|UnauthorizedError|ForbiddenError|NotFoundError|UnsupportedMediaTypeError|InternalServerErrorError {
    // }
    // resource function get apis/[string apiId]/comments/[string commentId]/replies(@http:Header string? 'if\-none\-match, int 'limit = 25, int offset = 0, boolean includeCommenterInfo = false) returns CommentList|UnauthorizedError|NotFoundError|NotAcceptableError|InternalServerErrorError {
    // }
    isolated resource function get subscriptions(http:RequestContext requestContext, string? apiId, @http:Header string? 'if\-none\-match, string? query, int 'limit = 25, int offset = 0) returns SubscriptionList|http:NotModified|commons:APKError {
        return getSubscriptions(apiId);
    }
    // resource function get subscriptions/[string subscriptionId]/'subscriber\-info() returns SubscriberInfo|NotFoundError {
    // }
    isolated resource function post subscriptions/'block\-subscription(http:RequestContext requestContext, string subscriptionId, string blockState, @http:Header string? 'if\-match) returns http:Ok|commons:APKError {
        string|commons:APKError response = blockSubscription(subscriptionId, blockState);
        if response is commons:APKError {
            return response;
        } else {
            return http:OK;
        }
    }
    isolated resource function post subscriptions/'unblock\-subscription(http:RequestContext requestContext, string subscriptionId, @http:Header string? 'if\-match) returns http:Ok|commons:APKError {
        string|error response = unblockSubscription(subscriptionId);
        if response is commons:APKError {
            return response;
        } else {
            return http:OK;
        }
    }
    // resource function get 'usage\-plans(@http:Header string? 'if\-none\-match, int 'limit = 25, int offset = 0) returns UsagePlanList|http:NotModified|NotAcceptableError {
    // }
    // resource function get search(string? query, @http:Header string? 'if\-none\-match, int 'limit = 25, int offset = 0) returns SearchResultList|http:NotModified|NotAcceptableError {
    // }
    // resource function get settings() returns Settings|NotFoundError {
    // }

    isolated resource function get 'api\-categories(http:RequestContext requestContext) returns APICategoryList|commons:APKError {
        commons:UserContext authenticatedUserContext = check commons:getAuthenticatedUserContext(requestContext);
        commons:Organization organization = authenticatedUserContext.organization;
        return getAllCategoryList(organization.uuid);
    }

    isolated resource function post apis/'change\-lifecycle(http:RequestContext requestContext, string targetState, string apiId, @http:Header string? 'if\-match) returns LifecycleState|commons:APKError|error {
        commons:UserContext authenticatedUserContext = check commons:getAuthenticatedUserContext(requestContext);
        commons:Organization organization = authenticatedUserContext.organization;
        LifecycleState|error changeState = changeLifeCyleState(targetState, apiId, organization.uuid);
        if changeState is LifecycleState {
            return changeState;
        } else {
            return error("Error while updating LC state of API" + changeState.message());
        }
    }
    isolated resource function get apis/[string apiId]/'lifecycle\-history(http:RequestContext requestContext, @http:Header string? 'if\-none\-match) returns LifecycleHistory|commons:APKError {
        return getLcEventHistory(apiId);
    }
    isolated resource function get apis/[string apiId]/'lifecycle\-state(http:RequestContext requestContext, @http:Header string? 'if\-none\-match) returns LifecycleState|commons:APKError|error {
        LifecycleState|error currentState = getLifeCyleState(apiId);
        if currentState is LifecycleState {
            return currentState;
        } else {
            return error("Error while getting LC state of API" + currentState.message());
        }
    }
}
