package com.clei.Y2020.M06.D15;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * spring 配置文件加载
 */
public class SpringTest {
    public static void main(String[] args) {

        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("spring/spring.xml"));

        Tiger tiger = xmlBeanFactory.getBean(Tiger.class);

        System.out.println(tiger);
    }
}
