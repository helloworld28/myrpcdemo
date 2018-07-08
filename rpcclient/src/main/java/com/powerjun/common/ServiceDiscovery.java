package com.powerjun.common;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.*;

/**
 * Created by Administrator on 2018/7/7.
 */
public class ServiceDiscovery {

    CuratorFramework curatorFramework;

    private final Map<String, List<String>> servicesMap = new HashMap<String, List<String>>();


    public ServiceDiscovery() {

        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZookeeperConfig.SERVER_ADDRESS)
                .namespace(ZookeeperConfig.NAME_SPACE)
                .sessionTimeoutMs(100000)
                .connectionTimeoutMs(1000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();
    }

    public void watchService(final Class serviceCls) throws Exception {
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/" + serviceCls.getName(), true);
        pathChildrenCache.getListenable().addListener(
                new PathChildrenCacheListener() {
                    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                        List<ChildData> currentData = pathChildrenCache.getCurrentData();
                        List<String> services = getServices();
                        for (ChildData childData : currentData) {
                            String path = childData.getPath();
                            services.add(path.substring(path.lastIndexOf("/") + 1));
                        }
                    }

                    private List<String> getServices() {
                        List<String> services = servicesMap.computeIfAbsent(serviceCls.getName(), k -> new ArrayList<>());
                        services.clear();
                        return services;
                    }
                }
        );
        pathChildrenCache.start();

        List<String> serviceList = curatorFramework.getChildren().forPath("/" + serviceCls.getName());
        servicesMap.put(serviceCls.getName(), serviceList);
    }

    public List<String> getServies(final Class serviceCls) {
        try {

            System.out.println(servicesMap);
            return servicesMap.get(serviceCls.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
