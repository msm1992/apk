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

package org.wso2.apk.apimgt.impl.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.apk.apimgt.api.APIManagementException;
import org.wso2.apk.apimgt.api.APIManagerDatabaseException;
import org.wso2.apk.apimgt.api.ExceptionCodes;
import org.wso2.apk.apimgt.impl.ConfigurationHolder;
import org.wso2.apk.apimgt.impl.dto.DatasourceProperties;
import org.wso2.apk.apimgt.impl.internal.ServiceReferenceHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public final class APIMgtDBUtil {

    private APIMgtDBUtil() {
    }

    private static final Log log = LogFactory.getLog(APIMgtDBUtil.class);
    private static volatile HikariDataSource dataSource = null;

    /**
     * Initializes the data source
     *
     * @throws APIManagerDatabaseException if an error occurs while loading DB configuration
     */
    public static void initialize() {
        if (dataSource != null) {
            return;
        }

        synchronized (APIMgtDBUtil.class) {

            if (dataSource == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Initializing data source");
                }
                ConfigurationHolder config = ServiceReferenceHolder.getInstance().getAPIManagerConfigurationService()
                        .getAPIManagerConfiguration();
                DatasourceProperties datasourceProperties = config.getDatasourceProperties();
                if (dataSource == null) {
                    HikariConfig hikariConfig = new HikariConfig();
                    hikariConfig.setJdbcUrl(datasourceProperties.getUrl());
                    hikariConfig.setUsername(datasourceProperties.getUsername());
                    hikariConfig.setPassword(datasourceProperties.getPassword());
                    hikariConfig.setMaximumPoolSize(datasourceProperties.getMaxPoolSize());
                    hikariConfig.setMinimumIdle(datasourceProperties.getMinIdleTime());
                    hikariConfig.setMaxLifetime(datasourceProperties.getMaxLifeTime());
                    hikariConfig.setAutoCommit(datasourceProperties.isSetAutocommit());
                    hikariConfig.setConnectionTestQuery(datasourceProperties.getTestQuery());
                    hikariConfig.setValidationTimeout(datasourceProperties.getValidationTimeout());
                    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
                    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
                    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                    dataSource = new HikariDataSource(hikariConfig);
                    log.debug("Hikari datasource created successfully");
                }
            }
        }
    }

    /**
     * Utility method to get a new database connection
     *
     * @return Connection
     * @throws SQLException if failed to get Connection
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        throw new SQLException("Data source is not configured properly.");
    }

    /**
     * Utility method to close the connection streams.
     *
     * @param preparedStatement PreparedStatement
     * @param connection        Connection
     * @param resultSet         ResultSet
     */
    public static void closeAllConnections(PreparedStatement preparedStatement, Connection connection,
                                           ResultSet resultSet) {
        closeConnection(connection);
        closeResultSet(resultSet);
        closeStatement(preparedStatement);
    }

    /**
     * Close Connection
     *
     * @param dbConnection Connection
     */
    private static void closeConnection(Connection dbConnection) {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                log.warn("Database error. Could not close database connection. Continuing with " +
                        "others. - " + e.getMessage(), e);
            }
        }
    }

    /**
     * Close ResultSet
     *
     * @param resultSet ResultSet
     */
    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.warn("Database error. Could not close ResultSet  - " + e.getMessage(), e);
            }
        }

    }

    /**
     * Close PreparedStatement
     *
     * @param preparedStatement PreparedStatement
     */
    public static void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.warn("Database error. Could not close PreparedStatement. Continuing with" +
                        " others. - " + e.getMessage(), e);
            }
        }

    }

    /**
     * Function converts IS to String
     * Used for handling blobs
     *
     * @param is - The Input Stream
     * @return - The inputStream as a String
     */
    public static String getStringFromInputStream(InputStream is) {
        String str = null;
        try {
            str = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error occurred while converting input stream to string.", e);
        }
        return str;
    }

    /**
     * Function converts IS to byte[]
     * Used for handling input streams
     *
     * @param is - The Input Stream
     * @return - The inputStream as a byte array
     */
    public static byte[] getBytesFromInputStream(InputStream is) {
        byte[] byteArray = null;
        try {
            byteArray = IOUtils.toByteArray(is);
        } catch (IOException e) {
            log.error("Error occurred while converting input stream to byte array.", e);
        }
        return byteArray;
    }

    /**
     * Set autocommit state of the connection
     *
     * @param dbConnection Connection
     * @param autoCommit   autoCommitState
     */
    public static void setAutoCommit(Connection dbConnection, boolean autoCommit) {
        if (dbConnection != null) {
            try {
                dbConnection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                log.error("Could not set auto commit back to initial state", e);
            }
        }
    }

    /**
     * Handle connection rollback logic. Rethrow original exception so that it can be handled centrally.
     *
     * @param connection Connection
     * @param error      Error message to be logged
     * @param e          Original SQLException
     * @throws SQLException When an SQL error occurs
     */
    public static void rollbackConnection(Connection connection, String error, SQLException e) throws SQLException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                // rollback failed
                log.error(error, rollbackException);
            }
            // Rethrow original exception so that it can be handled in the common catch clause of the calling method
            throw e;
        }
    }

    /**
     * Handle connection rollback logic. Rethrow original exception so that it can be handled centrally.
     *
     * @param connection Connection
     * @param error      Error message to be logged
     */
    public static void rollbackConnection(Connection connection, String error) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                // rollback failed
                log.error(error, rollbackException);
            }
        }
    }

    /**
     * Converts a JSON Object String to a String Map
     *
     * @param jsonString JSON String
     * @return String Map
     * @throws APIManagementException if errors occur during parsing the json string
     */
    public static Map<String, Object> convertJSONStringToMap(String jsonString) throws APIManagementException {
        Map<String, Object> map = null;
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                map = objectMapper.readValue(jsonString, Map.class);
            } catch (IOException e) {
                String msg = "Error while parsing JSON string";
                log.error(msg, e);
                throw new APIManagementException(msg, e, ExceptionCodes.INTERNAL_ERROR);
            }
        }
        return map;
    }
}
