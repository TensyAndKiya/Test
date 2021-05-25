package com.clei.jmx.mbean;

import com.clei.utils.PrintUtil;

/**
 * 用户配置
 *
 * @author KIyA
 * @date 2021-05-25
 */
public class User implements UserMBean {

    private String name;
    private Integer age;
    private String address;

    @Override
    public String helloWorld() {
        return "你好，世界";
    }

    @Override
    public String getName() {
        PrintUtil.log("getName name : {}", name);
        return name;
    }

    @Override
    public void setName(String name) {
        PrintUtil.log("setName oldName : {}, newName : {}", this.name, name);
        this.name = name;
    }

    @Override
    public Integer getAge() {
        PrintUtil.log("getAge age : {}", age);
        return age;
    }

    @Override
    public void setAge(Integer age) {
        PrintUtil.log("setAge oldAge : {}, newAge : {}", this.age, age);
        this.age = age;
    }

    @Override
    public String getAddress() {
        PrintUtil.log("getAddress address : {}", address);
        return address;
    }

    @Override
    public void setAddress(String address) {
        PrintUtil.log("setAddress oldAddress : {}, newAddress : {}", this.address, address);
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
