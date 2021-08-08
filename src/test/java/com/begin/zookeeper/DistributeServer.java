package com.begin.zookeeper;

import org.apache.zookeeper.*;

public class DistributeServer {

    public static void main(String[] args) throws Exception {

        DistributeServer server = new DistributeServer();

        server.doConnect();

        server.doRegist(args[0]);

        server.doBusiness();
    }

    private void doBusiness() throws Exception {
        System.out.println("server do business.");
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doRegist(String hostName) throws Exception {
        String path = zkClient.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(path);
    }

    private final static String connectString = "120.77.87.112:2181,120.77.87.112:2182,120.77.87.112:2183";
    private final static int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    public void doConnect() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, e -> {});
    }
}
