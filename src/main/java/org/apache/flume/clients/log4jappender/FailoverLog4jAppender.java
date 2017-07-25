/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
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
package org.apache.flume.clients.log4jappender;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.FlumeException;
import org.apache.flume.api.RpcClientConfigurationConstants;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.api.RpcClientFactory.ClientType;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Properties;

/**
 * author:edwardsbean
 */
public class FailoverLog4jAppender extends Log4jAppender {

    private String hosts;
    private String maxAttempts;
    private String maxIoWorkers;
    private boolean configured = false;

    public void setHosts(String hostNames) {
        this.hosts = hostNames;
    }

    public void setMaxAttempts(String maxAttempts) {
        this.maxAttempts = maxAttempts;
    }


    public void setMaxIoWorkers(String maxIoWorkers) {
        this.maxIoWorkers = maxIoWorkers;
    }


    Logger logger = Logger.getLogger(FailoverLog4jAppender.class);

    /**
     * 报错机制
     * @param event
     */
    @Override
    public synchronized void append(LoggingEvent event) {
        if (!configured) {
            String errorMsg = "Flume Log4jAppender not configured correctly! Cannot" +
                    " send events to Flume.";
            LogLog.error(errorMsg);
            if (getUnsafeMode()) {
                return;
            }
            //jie- 配置文件报错
            throw new FlumeException(errorMsg);
        }
        super.append(event);
    }


    /**
     * 重连机制
     * @throws FlumeException if the FailoverRpcClient cannot be instantiated.
     */
    @Override
    public void activateOptions() throws FlumeException {
        try {
            final Properties properties = getProperties(hosts, maxAttempts, maxIoWorkers,getTimeout());
            rpcClient = RpcClientFactory.getInstance(properties);
            if (layout != null) {
                layout.activateOptions();
            }
            configured = true;
        } catch (Exception e) {
            String errormsg = "RPC client creation failed! " + e.getMessage();
            LogLog.error(errormsg);
            logger.error("RPC client creation failed!" + "连接错误");
            if (getUnsafeMode()) {
                return;
            }
            throw new FlumeException(e);
        }

    }

    //配置必要的参数 说到底,就是配置文件
    /**
     * jie:ps自己重写获取配置文件信息,将多个网络端口配置起,是用FAILOVER(故障转移)
     * @param hosts        多个主机名
     * @param maxAttempts  主机数量
     * @param timeout      连接超时时间
     * @return
     * @throws FlumeException
     */
    private Properties getProperties(String hosts, String maxAttempts,String maxIoWorkers, long timeout) throws FlumeException {
        //获取多个主机名的hostsname
        if (StringUtils.isEmpty(hosts)) {
            throw new FlumeException("hosts must not be null");
        }


        Properties props = new Properties();
        //获取hostsname数组,按正则切分, \\s -> 空格   \\s+ ->  多个空格
        //datanode4:6501 datanode6:6501
        String[] hostsAndPorts = hosts.split("\\s+");
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < hostsAndPorts.length; i++) {
            String hostAndPort = hostsAndPorts[i];
            String name = "h" + i;
            props.setProperty(RpcClientConfigurationConstants.CONFIG_HOSTS_PREFIX + name,
                    hostAndPort);
            names.append(name).append(" ");
        }
        props.put(RpcClientConfigurationConstants.CONFIG_HOSTS, names.toString());
        //客户端连接方式: CONFIG_CLIENT_TYPE -> client.type
        //DEFAULT_FAILOVER : 故障转移   (NettyAvroRpcClient(普通连接),FAILOVER(故障转移),LOADBALANCE(负载均衡),THRIFT(节约模式?))
        props.put(RpcClientConfigurationConstants.CONFIG_CLIENT_TYPE,
                ClientType.DEFAULT_FAILOVER.toString());

        if (StringUtils.isEmpty(maxAttempts)) {
            throw new FlumeException("hosts must not be null");
        }

        //一样
        props.put(RpcClientConfigurationConstants.CONFIG_MAX_ATTEMPTS, maxAttempts);
        //一样
        props.setProperty(RpcClientConfigurationConstants.CONFIG_CONNECT_TIMEOUT,
                String.valueOf(timeout));
        //一样
        props.setProperty(RpcClientConfigurationConstants.CONFIG_REQUEST_TIMEOUT,
                String.valueOf(timeout));
      /*  props.setProperty(RpcClientConfigurationConstants.MAX_IO_WORKERS,
                String.valueOf(maxIoWorkers));   */
      //与slf4j框架bug 冲突
      //采用负载均衡方式配置打印日志时，出现循环打印出“Using default maxIOWorkers”导致栈溢出问题
      //在getProperties方法中增加对maxIOWorkers初始化
        props.setProperty(RpcClientConfigurationConstants.MAX_IO_WORKERS,
                (Runtime.getRuntime().availableProcessors() * 2)+"");


        return props;
    }
}
