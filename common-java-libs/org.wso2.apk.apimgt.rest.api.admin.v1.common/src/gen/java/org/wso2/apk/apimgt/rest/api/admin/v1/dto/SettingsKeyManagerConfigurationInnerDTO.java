package org.wso2.apk.apimgt.rest.api.admin.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.apk.apimgt.rest.api.admin.v1.dto.KeyManagerConfigurationDTO;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;



public class SettingsKeyManagerConfigurationInnerDTO   {
  
  private String type;

  private String displayName;

  private String defaultConsumerKeyClaim;

  private String defaultScopesClaim;

  private List<KeyManagerConfigurationDTO> configurations = null;

  private List<KeyManagerConfigurationDTO> endpointConfigurations = null;


  /**
   **/
  public SettingsKeyManagerConfigurationInnerDTO type(String type) {
    this.type = type;
    return this;
  }

  
  @ApiModelProperty(example = "default", value = "")
  @JsonProperty("type")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  /**
   **/
  public SettingsKeyManagerConfigurationInnerDTO displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  
  @ApiModelProperty(example = "default", value = "")
  @JsonProperty("displayName")
  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  /**
   **/
  public SettingsKeyManagerConfigurationInnerDTO defaultConsumerKeyClaim(String defaultConsumerKeyClaim) {
    this.defaultConsumerKeyClaim = defaultConsumerKeyClaim;
    return this;
  }

  
  @ApiModelProperty(example = "azp", value = "")
  @JsonProperty("defaultConsumerKeyClaim")
  public String getDefaultConsumerKeyClaim() {
    return defaultConsumerKeyClaim;
  }
  public void setDefaultConsumerKeyClaim(String defaultConsumerKeyClaim) {
    this.defaultConsumerKeyClaim = defaultConsumerKeyClaim;
  }


  /**
   **/
  public SettingsKeyManagerConfigurationInnerDTO defaultScopesClaim(String defaultScopesClaim) {
    this.defaultScopesClaim = defaultScopesClaim;
    return this;
  }

  
  @ApiModelProperty(example = "scope", value = "")
  @JsonProperty("defaultScopesClaim")
  public String getDefaultScopesClaim() {
    return defaultScopesClaim;
  }
  public void setDefaultScopesClaim(String defaultScopesClaim) {
    this.defaultScopesClaim = defaultScopesClaim;
  }


  /**
   **/
  public SettingsKeyManagerConfigurationInnerDTO configurations(List<KeyManagerConfigurationDTO> configurations) {
    this.configurations = configurations;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("configurations")
  public List<KeyManagerConfigurationDTO> getConfigurations() {
    return configurations;
  }
  public void setConfigurations(List<KeyManagerConfigurationDTO> configurations) {
    this.configurations = configurations;
  }

  public SettingsKeyManagerConfigurationInnerDTO addConfigurationsItem(KeyManagerConfigurationDTO configurationsItem) {
    if (this.configurations == null) {
      this.configurations = new ArrayList<>();
    }
    this.configurations.add(configurationsItem);
    return this;
  }


  /**
   **/
  public SettingsKeyManagerConfigurationInnerDTO endpointConfigurations(List<KeyManagerConfigurationDTO> endpointConfigurations) {
    this.endpointConfigurations = endpointConfigurations;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("endpointConfigurations")
  public List<KeyManagerConfigurationDTO> getEndpointConfigurations() {
    return endpointConfigurations;
  }
  public void setEndpointConfigurations(List<KeyManagerConfigurationDTO> endpointConfigurations) {
    this.endpointConfigurations = endpointConfigurations;
  }

  public SettingsKeyManagerConfigurationInnerDTO addEndpointConfigurationsItem(KeyManagerConfigurationDTO endpointConfigurationsItem) {
    if (this.endpointConfigurations == null) {
      this.endpointConfigurations = new ArrayList<>();
    }
    this.endpointConfigurations.add(endpointConfigurationsItem);
    return this;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SettingsKeyManagerConfigurationInnerDTO settingsKeyManagerConfigurationInner = (SettingsKeyManagerConfigurationInnerDTO) o;
    return Objects.equals(type, settingsKeyManagerConfigurationInner.type) &&
        Objects.equals(displayName, settingsKeyManagerConfigurationInner.displayName) &&
        Objects.equals(defaultConsumerKeyClaim, settingsKeyManagerConfigurationInner.defaultConsumerKeyClaim) &&
        Objects.equals(defaultScopesClaim, settingsKeyManagerConfigurationInner.defaultScopesClaim) &&
        Objects.equals(configurations, settingsKeyManagerConfigurationInner.configurations) &&
        Objects.equals(endpointConfigurations, settingsKeyManagerConfigurationInner.endpointConfigurations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, displayName, defaultConsumerKeyClaim, defaultScopesClaim, configurations, endpointConfigurations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SettingsKeyManagerConfigurationInnerDTO {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    defaultConsumerKeyClaim: ").append(toIndentedString(defaultConsumerKeyClaim)).append("\n");
    sb.append("    defaultScopesClaim: ").append(toIndentedString(defaultScopesClaim)).append("\n");
    sb.append("    configurations: ").append(toIndentedString(configurations)).append("\n");
    sb.append("    endpointConfigurations: ").append(toIndentedString(endpointConfigurations)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

