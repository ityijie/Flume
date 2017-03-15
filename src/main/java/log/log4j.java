package log;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
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

        thread mTh1 = new thread("A");
        try {
            mTh1.start();
        } catch (Exception e) {
            logger.info("程序出现错误,骚等片刻100S:" + e.toString());
            try {
                mTh1.sleep(100000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

    }
}

class thread extends Thread {

    String message = "----->testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
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
        Socket socket = null;
        try {
            socket = new Socket("datanode4",41414);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        String format = null;
        Long currtime = null;
        String test = "是否丢失 数据 log4j7测试数据";
        String mess = "thread send message  on -";
        String what = "- :  ---" + name + " for ";
        int i = 0;

        while (true) {
            try {
                if(socket.isConnected()){
                    currtime = System.currentTimeMillis();
                    format = time.format(currtime);
                    logger.info(test + mess + format + what + i++ + message);
                    System.out.println("41414端口开放");
                    sleep(2000);
                }else {
                    System.out.println("没有连接");
                    sleep(2000);
                }

            } catch (Exception e) {
                System.out.println("here");
                System.out.println(e);
            }

        }


    }
}
