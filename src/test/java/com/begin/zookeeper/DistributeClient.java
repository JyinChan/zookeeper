package com.begin.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class DistributeClient {

    public static void main(String[] args) throws Exception {

        DistributeClient client = new DistributeClient();

        client.doConnect();

        client.getServers();

        client.doBusiness();
    }

    private void doBusiness() throws Exception {
        System.out.println("client do business...");
        Thread.sleep(Integer.MAX_VALUE);
    }

    private List<String> getServers() throws Exception {
        List<String> children = zkClient.getChildren("/servers", true);
        List<String> hosts = new ArrayList<>();

        for (String c: children) {
            byte[] data = zkClient.getData("/servers/" + c, false, null);
            hosts.add(new String(data));
        }

        System.out.println(hosts);

        return hosts;

    }

    private final static String connectString = "120.77.87.112:2181,120.77.87.112:2182,120.77.87.112:2183";
    private final static int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    public void doConnect() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, e -> {
            try {
                getServers();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
