package com.clei.Y2020.M06.D15;

import com.clei.utils.PrintUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring 配置文件加载
 */
public class SpringTest {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring.xml");

        Tiger tiger = context.getBean(Tiger.class);

        PrintUtil.log(tiger);
    }
}
