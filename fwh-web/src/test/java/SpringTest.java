import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: fwh-parent
 * @description: test
 * @author: fwh
 * @date: 2021-07-14 10:20
 **/
public class SpringTest {
    public static void main(String[] args) {
        //配置文件形式
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("xml");
        classPathXmlApplicationContext.getBean("test");
        //注解扫描形式
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("club.fuwenhao");
        annotationConfigApplicationContext.getBean("test");
    }
}

