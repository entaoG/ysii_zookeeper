package com;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author ysii
 * 1、连接Zookeeper
 * 2、创建znode
 */
public class ZKCreate {

    //static instance for zookeeper class.
    private static ZooKeeper zoo;

    //static instance for ZookeeperConnect class.
    private static ZookeeperConnection zookeeperConnection;


    private static void create(String path, byte[] data) throws IOException, InterruptedException, KeeperException {

        zoo.create(path, data , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    public static void main(String[] args) {
        try {
            //znode path
            String path = "/MyFirstZnode";

            //data in n=byte array
            byte[] data = "My Frist Znode date".getBytes();

            zookeeperConnection = new ZookeeperConnection();
            zoo = zookeeperConnection.connect("192.168.11.130");
            create(path, data);//create
            zookeeperConnection.close();

            System.out.println("Success!");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }




}
