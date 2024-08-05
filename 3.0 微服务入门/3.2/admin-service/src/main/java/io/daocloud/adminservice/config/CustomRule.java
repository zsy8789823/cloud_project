package io.daocloud.adminservice.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import io.daocloud.adminservice.service.UserService;import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;

/**
 * Author: Garroshh
 * date: 2020/7/16 5:32 下午
 */
@Configuration
public class CustomRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }
    int lastServer = 0;
    @Override
    public Server choose(Object key) {

        ILoadBalancer loadBalancer = getLoadBalancer();

        //获取所有可达服务器列表
        List<Server> servers = loadBalancer.getReachableServers();
        if (servers.isEmpty()) {
            return null;
        }

        int nextIndex = (lastServer+1)%servers.size();
        // 永远选择最后一台可达服务器
        Server targetServer = servers.get(nextIndex);
        lastServer = nextIndex;
        System.out.println("choose" + targetServer.getHost());
        return targetServer;
    }

}
