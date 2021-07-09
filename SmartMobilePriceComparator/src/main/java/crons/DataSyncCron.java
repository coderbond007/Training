//package crons;
//
//import org.apache.log4j.Logger;
//import utils.Util;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by vishal.p on 07/07/17.
// */
//public class DataSyncCron {
//    private static final Logger log = Logger.getLogger(DataSyncCron.class);
//    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//    public static void startScheduledTask() {
//        final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
//                new Runnable() {
//                    public void run() {
//                        try {
//                            DataSyncCron.updateAll();
//                        } catch (Exception e) {
//                            log.error(e.getMessage(), e);
//                        }
//                    }
//                }, 0, 15, TimeUnit.MINUTES);
//    }
//
//    public static void startScheduledTask2() {
//        final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
//                new Runnable() {
//                    public void run() {
//                        try {
//                            Util.setDatasourceHashMap();
//                        } catch (Exception e) {
//                            log.error(e.getMessage(), e);
//                        }
//                    }
//                }, 0, 5, TimeUnit.MINUTES);
//    }
//
//    private static void updateAll() throws Exception {
//        try {
////          Util.setDatasourceHashMap(); // Don't need to do this anymore as gayatri's process is hitting an update api on every update.
//            Util.getLoadShareForDatasources();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}