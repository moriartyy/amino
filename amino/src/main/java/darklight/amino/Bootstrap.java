package darklight.amino;

import darklight.amino.common.Environment;
import darklight.amino.common.Settings;

import java.util.concurrent.CountDownLatch;

/**
 * Created by hongmiao.yu on 2016/3/9.
 */
public class Bootstrap {

    private static CountDownLatch keepAliveLatch = new CountDownLatch(1);

    public static void main(String[] args) {
//        FileSystemXmlApplicationContext context1 = new FileSystemXmlApplicationContext("classpath:spring/job1.xml");
//        JobController controller1 = (JobController)context1.getBean("jobController");
//        System.out.println(controller1.getName());
//
//        FileSystemXmlApplicationContext context2 = new FileSystemXmlApplicationContext("classpath:spring/job2.xml");
//        JobController controller2 = (JobController)context2.getBean("jobController");
//        System.out.println(controller2.getName());

        Environment environment = new Environment(Settings.SYSTEM_SETTINGS);
        Settings settings = Settings.builder()
                .put(environment.getSettings())
                .loadFromUrl(environment.resolveConfig("amino.properties")).build();

        Engine engine = new Engine(settings, environment);
        engine.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> { keepAliveLatch.countDown(); }));

        Thread keepAliveThead = new Thread(() -> {
            try {
                keepAliveLatch.await();
                engine.stop();
            } catch (InterruptedException e) {
            }
        });

        keepAliveThead.start();

    }
}
