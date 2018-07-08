package com.powerjun.rpcserver;

import com.powerjun.RpcService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Created by Administrator on 2018/7/6.
 */
public class RPCServiceRegister {
    CuratorFramework client;


    public RPCServiceRegister() {
        client = CuratorFrameworkFactory
                .builder()
                .namespace(ZookeeperConfig.NAME_SPACE)
                .connectString(ZookeeperConfig.SERVER_ADDRESS)
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .connectionTimeoutMs(4000)
                .sessionTimeoutMs(4000)
                .build();
        client.start();
    }


    public void publishService(Object object, String address) {
        try {
            Class clsPath = object.getClass().getAnnotation(RpcService.class).value();
            String serviceName = clsPath.getName();


            String parentPath = "/" + serviceName;
            Stat stat = client.checkExists().forPath(parentPath);
            if (stat == null) {
                client.create().forPath(parentPath);
            }
            client.create().withMode(CreateMode.EPHEMERAL).forPath(parentPath + "/" + address);
            System.out.println("注册服务成功-》" + parentPath + "/" + address);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
