///**
// * @program: fwh-parent
// * @description:
// * @author: fwh
// * @date: 2021-07-16 14:14
// **/
//public class FormatTest {
//
//}
//
//public class DoubleCheckLock {
//    private static DoubleCheckLock instance;
//
//    private DoubleCheckLock() {
//    }
//
//    public static DoubleCheckLock getInstance() { //第一次检测
//        if (instance == null) {
//            //同步
//            synchronized (DoubleCheckLock.class) {
//                if (instance == null) {
//                    //多线程环境下可能会出现问题的地方 instance = new DoubleCheckLock(); }
//                }
//            }
//            return instance;
//        }
//    }
//}