package com;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @Author: ysii
 * @Description: 连接zookeeper,删除znode
 * @Date: Created in 16:45 2018/3/2
 * @Modify by:
 */
public class ZKDelete {

    //connection
    private static ZookeeperConnection zookeeperConnection;
    //Zookeeper
    private static ZooKeeper zoo;

    //自定一删除方法
    private static void delete(String path) throws KeeperException ,InterruptedException{
            zoo.delete(path , zoo.exists(path, true).getVersion());
    }

    public static void main(String[] args) {
        String path = "/MyFirstZnode"; //Assign path to znode

        zookeeperConnection = new ZookeeperConnection();
        try {
            zoo = zookeeperConnection.connect("192.168.11.130");
            delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
    }
}
