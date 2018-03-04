package com;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @Author: ysii
 * @Description: 1、连接zookeeper 2、检查znode是否存在
 * @Date: Created in 15:08 2018/3/2
 * @Modify by:
 */
public class ZKexists {
    //static instance for zookeeper class.
    private static ZooKeeper zoo;

    //static instance for ZookeeperConnect class.
    private static ZookeeperConnection zookeeperConnection;


    private static Stat exists(String path, boolean watch) throws IOException, InterruptedException, KeeperException {

       return zoo.exists(path, watch);
    }


    public static void main(String[] args) {
        try {
            //znode path
            String path = "/MyFirstZnode";

            Stat stat = new Stat();
            zookeeperConnection = new ZookeeperConnection();
            zoo = zookeeperConnection.connect("192.168.11.130");
           stat =  exists(path, true);//exists
            zookeeperConnection.close();

            if(stat != null){
                System.out.println("Node exists and the node version is "+ stat.getVersion());
            }else{
                System.out.println("Node does not exists");
            }
            System.out.println("Success!"+" \n"+stat);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
