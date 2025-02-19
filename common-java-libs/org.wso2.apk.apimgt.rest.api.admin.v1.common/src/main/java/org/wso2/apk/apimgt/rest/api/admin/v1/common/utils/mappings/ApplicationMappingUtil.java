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

package org.wso2.apk.apimgt.rest.api.admin.v1.common.utils.mappings;

import org.apache.commons.lang3.StringUtils;
import org.wso2.apk.apimgt.api.model.Application;
import org.wso2.apk.apimgt.api.model.Scope;
import org.wso2.apk.apimgt.impl.APIConstants;
import org.wso2.apk.apimgt.rest.api.admin.v1.dto.*;
import org.wso2.apk.apimgt.rest.api.util.utils.RestApiCommonUtil;
import org.wso2.apk.apimgt.rest.api.util.RestApiConstants;

import java.util.*;

public class ApplicationMappingUtil {

    public static ApplicationListDTO fromApplicationsToDTO(Application[] applications) {
        ApplicationListDTO applicationListDTO = new ApplicationListDTO();
        List<ApplicationInfoDTO> applicationInfoDTOs = applicationListDTO.getList();
        if (applicationInfoDTOs == null) {
            applicationInfoDTOs = new ArrayList<>();
            applicationListDTO.setList(applicationInfoDTOs);
        }

        for (Application application : applications) {
            applicationInfoDTOs.add(fromApplicationToInfoDTO(application));
        }
        applicationListDTO.setCount(applicationInfoDTOs.size());
        return applicationListDTO;
    }

    /**
     * Sets pagination urls for a ApplicationListDTO object given pagination parameters and url parameters
     *
     * @param applicationListDTO a SubscriptionListDTO object
     * @param limit              max number of objects returned
     * @param offset             starting index
     * @param size               max offset
     */
    public static void setPaginationParams(ApplicationListDTO applicationListDTO, int limit, int offset,
            int size) {

        Map<String, Integer> paginatedParams = RestApiCommonUtil.getPaginationParams(offset, limit, size);

        String paginatedPrevious = "";
        String paginatedNext = "";

        if (paginatedParams.get(RestApiConstants.PAGINATION_PREVIOUS_OFFSET) != null) {
            paginatedPrevious = RestApiCommonUtil
                    .getApplicationPaginatedURL(paginatedParams.get(RestApiConstants.PAGINATION_PREVIOUS_OFFSET),
                    paginatedParams.get(RestApiConstants.PAGINATION_PREVIOUS_LIMIT), null);
        }

        if (paginatedParams.get(RestApiConstants.PAGINATION_NEXT_OFFSET) != null) {
            paginatedNext = RestApiCommonUtil
                    .getApplicationPaginatedURL(paginatedParams.get(RestApiConstants.PAGINATION_NEXT_OFFSET),
                        paginatedParams.get(RestApiConstants.PAGINATION_NEXT_LIMIT), null);
        }
        PaginationDTO paginationDTO = getPaginationDTO(limit, offset, size, paginatedNext, paginatedPrevious);
        applicationListDTO.setPagination(paginationDTO);
    }

    private static PaginationDTO getPaginationDTO(int limit, int offset, int total, String next, String previous) {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setLimit(limit);
        paginationDTO.setOffset(offset);
        paginationDTO.setTotal(total);
        paginationDTO.setNext(next);
        paginationDTO.setPrevious(previous);
        return paginationDTO;
    }

    /**
     * Maps the attribute from Application to ApplicationDto
     * @param application
     * @return
     */
    public static ApplicationInfoDTO fromApplicationToInfoDTO(Application application) {
        ApplicationInfoDTO applicationInfoDTO = new ApplicationInfoDTO();
        applicationInfoDTO.setApplicationId(application.getUUID());
        applicationInfoDTO.setStatus(application.getStatus());
        applicationInfoDTO.setName(application.getName());
        applicationInfoDTO.setGroupId(application.getGroupId());
        applicationInfoDTO.setOwner(application.getOwner());
        return applicationInfoDTO;
    }

    public static ApplicationDTO fromApplicationtoDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplicationId(application.getUUID());
        applicationDTO.setThrottlingPolicy(application.getTier());
        applicationDTO.setDescription(application.getDescription());
        Map<String,String> applicationAttributes = application.getApplicationAttributes();
        applicationDTO.setAttributes(applicationAttributes);
        applicationDTO.setName(application.getName());
        applicationDTO.setStatus(application.getStatus());
        applicationDTO.setOwner(application.getOwner());

        if (StringUtils.isNotEmpty(application.getGroupId())) {
            applicationDTO.setGroups(Arrays.asList(application.getGroupId().split(",")));
        }
        applicationDTO.setTokenType(ApplicationDTO.TokenTypeEnum.OAUTH);
        applicationDTO.setSubscriptionCount(application.getSubscriptionCount());
        if (StringUtils.isNotEmpty(application.getTokenType()) && !APIConstants.DEFAULT_TOKEN_TYPE
                .equals(application.getTokenType())) {
            applicationDTO.setTokenType(ApplicationDTO.TokenTypeEnum.valueOf(application.getTokenType()));
        }
        return applicationDTO;
    }

    public static List<ScopeInfoDTO> getScopeInfoDTO(Set<Scope> scopes) {
        List<ScopeInfoDTO> scopeDto = new ArrayList<ScopeInfoDTO>();
        for (Scope scope : scopes) {
            ScopeInfoDTO scopeInfoDTO = new ScopeInfoDTO();
            scopeInfoDTO.setKey(scope.getKey());
            scopeInfoDTO.setName(scope.getName());
            scopeInfoDTO.setDescription(scope.getDescription());
            if (StringUtils.isNotBlank(scope.getRoles())) {
                scopeInfoDTO.setRoles(Arrays.asList(scope.getRoles().trim().split(",")));
            }
            scopeDto.add(scopeInfoDTO);
        }
        return scopeDto;
    }
}
