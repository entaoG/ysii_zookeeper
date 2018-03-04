package com;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author ysii
 * 连接Zookeeper
 */
public class ZookeeperConnection {

    //zookeeper instance
    private ZooKeeper zoo;

    final CountDownLatch connectionSignal = new CountDownLatch(1);

    //to connect zookeeper
    public ZooKeeper connect(String host) throws IOException,InterruptedException {
        zoo = new ZooKeeper(host, 5000, new Watcher() {
            public void process(WatchedEvent we) {
                if(we.getState() == Event.KeeperState.SyncConnected){
                    connectionSignal.countDown();
                }
            }
        });

        connectionSignal.await();
        return zoo;
    }

    //to disconnect from zookeeper server
    public void close() throws InterruptedException {
        zoo.close();
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String args[]){
        ZookeeperConnection coo = new ZookeeperConnection();
        try {
            ZooKeeper zoo = coo.connect("192.168.11.130");
            System.out.println(zoo.getState());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
