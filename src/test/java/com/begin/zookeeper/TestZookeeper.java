package com.begin.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestZookeeper {

    private final static String connectString = "120.77.87.112:2181,120.77.87.112:2182,120.77.87.112:2183";
    private final static int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("---- start watch ---");
                System.out.println(watchedEvent);
                List<String> children;
                try {
                    children = zkClient.getChildren("/", true);
                    System.out.println("Watcher -- getChildren /: ");
                    children.forEach(System.out::println);
                    System.out.println("---- end watch -----");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testCreateNode() throws Exception {
        String path = zkClient.create("/Test1", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(path);
        path = zkClient.create("/Test1/C1", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(path);
    }

    @Test
    public void testGetDataAndWatch() throws Exception {
        List<String> data = zkClient.getChildren("/", true);
        System.out.println("Test -- getChildren /: ");
        data.forEach(System.out::println);

        List<String> data2 = zkClient.getChildren("/Test1", false);
        System.out.println("Test -- getChildren /Test1: ");
        data2.forEach(System.out::println);

        Thread.sleep(100000);

    }

    public void exist() throws Exception {
        System.out.println(zkClient.exists("/Test1", false));
    }

}
