package log;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/11/2.
 */
public class log4j {

    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger(log4j.class);
        //通过配置文件，将信息，发送到flume source中
        String url = "测试数据";
        System.out.println("开始发送");
        logger.info("log4j7测试数据" + " thread send message  on -");

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);
        int i = 0;
        for(i = 0 ;i < 1 ;i++){
            String  a = i + "";
            //thread mTh = new thread(a);
            //mTh.start();
           // fixedThreadPool.execute(mTh);
        }
       thread mTh1 = new thread("A");
        //thread mTh2 = new thread("B");
        //thread mTh3 = new thread("C");
        //thread mTh4 = new thread("D");

       mTh1.start();
       // mTh2.start();

    }
}

class thread extends Thread {

    String message = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
            "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";

    Logger logger = Logger.getLogger(thread.class);
    Logger bpe1 = Logger.getLogger("vehicle_exam_event_his");
    Logger bpe2 = Logger.getLogger("vehicle_exam_score_his");
    Logger dse1 = Logger.getLogger("device_statushis");
    Logger dse2 = Logger.getLogger("device_statushis_invisible");
    Logger dse3 = Logger.getLogger("device_statushis_vehicle");
    Logger dse4 = Logger.getLogger("device_statushis_vehicle_invisible");
    Logger dse5 = Logger.getLogger("vehicle_behavior_point");
    Logger dse6 = Logger.getLogger("vehicle_behavior_process");

    private String name;

    public thread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        SimpleDateFormat time =new SimpleDateFormat("HH:mm:ss");
        int i = 0;
        while (true) {
            /*for (int i = 0; i < 101; i++) {
                String format = time.format(System.currentTimeMillis());
                logger.info("log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i + "--->" + message);
            }*/
            String format = time.format(System.currentTimeMillis());
            logger.info("log4j7测试数据" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            bpe1.info("bpe1 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            bpe2.info("bpe2 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            dse1.info("dse1 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            dse2.info("dse2 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            dse3.info("dse3 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            dse4.info("dse4 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            dse5.info("dse5 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            dse6.info("dse6 测试数据 log4j7" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
            logger.info("log4j7测试数据s" + " thread send message  on -" + format + "- :  ---"+name+" for " + i++ + "--->" + message);
          /*  try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            if(i>100 && i<110){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                   try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            }
        }


    }
}
