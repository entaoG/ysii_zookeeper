package com;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: ysii
 * @Description: 连接zookeeper，获取data
 * @Date: Created in 15:20 2018/3/2
 * @Modify by:
 */
public class ZKGetData {


    //static instance for zookeeper class.
    private static ZooKeeper zoo;

    //static instance for ZookeeperConnect class.
    private static ZookeeperConnection zookeeperConnection;


    private static Stat exists(String path, boolean watch) throws IOException, InterruptedException, KeeperException {

        return zoo.exists(path, watch);
    }


    public static void main(String[] args) {
        String path = "/MyFirstZnode";
        final CountDownLatch connectedSignal = new CountDownLatch(1);

        zookeeperConnection = new ZookeeperConnection();

        try {
            zoo = zookeeperConnection.connect("192.168.11.130");
            Stat stat = exists(path, true);

            if(stat != null){
                byte[] b = zoo.getData(path, new Watcher() {
                    @Override
                    public void process(WatchedEvent we) {
                        if(we.getType() == Event.EventType.None){
                            switch(we.getState()){
                                case Expired:
                                    connectedSignal.countDown();
                                    break;
                            }
                        }else {
                            String path = "/MyFirstZnode";

                            String data = null;
                            try {
                                byte[] bn = zoo.getData(path,false,null);
                                data = new String(bn,"UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }catch(KeeperException e){
                                e.printStackTrace();
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            System.out.println(data + "-------------");
                            connectedSignal.countDown();
                        }
                    }
                },null);

                String data = new String(b,"UTF-8");
                System.out.println(data);
                connectedSignal.await();
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
