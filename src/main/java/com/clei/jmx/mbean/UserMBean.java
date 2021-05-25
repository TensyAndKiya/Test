package com.clei.jmx.mbean;

/**
 * 用户 MBean
 *
 * @author KIyA
 * @date 2021-05-25
 */
public interface UserMBean {

    /**
     * 获取name
     *
     * @return
     */
    String getName();

    /**
     * 设置name
     *
     * @param name
     */
    void setName(String name);

    /**
     * 获取age
     *
     * @return
     */
    Integer getAge();

    /**
     * 设置age
     *
     * @param age
     */
    void setAge(Integer age);

    /**
     * 获取address
     *
     * @return
     */
    String getAddress();

    /**
     * 设置address
     *
     * @param address
     */
    void setAddress(String address);

    /**
     * hello world
     *
     * @return
     */
    String helloWorld();
}
