package log;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/12/14.
 */
public class log4jxml {
    private static Logger logger = Logger.getLogger(log4jxml.class);

    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        //加载配置文件
        DOMConfigurator.configure(log4jxml.class.getClassLoader().getResource("zz.xml"));
        long i = 0;
        while (true) {
            logger.info("test:" + (i++) + "");
            Thread.sleep(1000);
            System.out.println(i);
        }
    }
}
