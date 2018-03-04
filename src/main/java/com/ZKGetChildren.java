package com;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: ysii
 * @Description: 连接zookeeper,获取一个特定znode所有子节点
 * @Date: Created in 16:37 2018/3/2
 * @Modify by:
 */
public class ZKGetChildren {
    //static instance for zookeeper class.
    private static ZooKeeper zoo;

    //static instance for ZookeeperConnect class.
    private static ZookeeperConnection zookeeperConnection;


    private static Stat exists(String path, boolean watch) throws IOException, InterruptedException, KeeperException {

        return zoo.exists(path, watch);
    }


    public static void main(String[] args) {
        String path = "/MyFirstZnode";

        zookeeperConnection = new ZookeeperConnection();
        try {
            zoo = zookeeperConnection.connect("192.168.11.130");

            Stat stat = exists(path,true);

            if(stat != null){
                List<String> children = zoo.getChildren(path,false);
                for(int i = 0;i <children.size();i++){
                    System.out.println(children.get(i));
                }
            }else{
                System.out.println("Node does not exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e){
            e.printStackTrace();
        }

    }
}
