package com.clei.Y2019.M06.D13;

import com.clei.utils.PrintUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * 设计模式 代理
 *
 * @author KIyA
 */
public class DesignPatternProxy {

    public static void main(String[] args) {
        new StaticProxy().proxy();
        new CglibDynamicProxy().proxy();
        new JdkDynamicProxy().proxy();
    }
}

/**
 * 静态代理依赖于被代理的类,A修改之后,B可能也要跟着修改
 */
class StaticProxy {

    void proxy() {
        Cat c = new Cat();
        CatLogProxy clp = new CatLogProxy(c);
        CatTimeProxy ctp = new CatTimeProxy(clp);
        ctp.eat();
    }
}

/**
 * 通过继承
 */
class CglibDynamicProxy {

    void proxy() {
        // Test
        UserDao userDao = new UserDaoMysqlImpl();
        UserDao userDaoProxy = (UserDao) new LogHandler2().getInstance(userDao);
        userDaoProxy.save(1, "张三");
        userDaoProxy.delete(1);
        PrintUtil.log("目标对象类型： " + userDao.getClass());
        PrintUtil.log("代理目标对象类型： " + userDaoProxy.getClass());
    }
}

/**
 * 通过实现接口
 */
class JdkDynamicProxy {

    void proxy() {
        //ProxyFactory Test
        UserDao userDao = new UserDaoMysqlImpl();
        UserDao userDaoProxy = (UserDao) new ProxyFactory(userDao).getLogProxyInstance();
        userDaoProxy.save(1, "张三");
        userDaoProxy.update(1, "张三三");
        userDaoProxy.delete(1);
        PrintUtil.log("目标对象类型： " + userDao.getClass());
        PrintUtil.log("代理目标对象类型： " + userDaoProxy.getClass());
        //LogHandler Test
        UserDao userDao2 = new UserDaoOracleImpl();
        UserDao userDaoProxy2 = (UserDao) new LogHandler(userDao2).getInstance();
        userDaoProxy2.save(2, "李四");
        userDaoProxy.update(2, "李四四");
        userDaoProxy2.delete(2);
        PrintUtil.log("目标对象类型： " + userDao2.getClass());
        PrintUtil.log("代理目标对象类型： " + userDaoProxy2.getClass());
    }
}

interface Eatable {

    /**
     * 吃
     */
    void eat();
}

class Cat implements Eatable {

    @Override
    public void eat() {
        PrintUtil.log("猫吃老鼠！");
    }
}

class CatLogProxy implements Eatable {

    private final Eatable e;

    public CatLogProxy(Eatable e) {
        super();
        this.e = e;
    }

    @Override
    public void eat() {
        PrintUtil.log("Log Start...");
        e.eat();
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            PrintUtil.log("sleep 出错", e);
        }
        PrintUtil.log("Log End...");
    }
}

class CatTimeProxy implements Eatable {

    private final Eatable e;

    public CatTimeProxy(Eatable e) {
        super();
        this.e = e;
    }

    @Override
    public void eat() {
        long start = System.currentTimeMillis();
        e.eat();
        long end = System.currentTimeMillis();
        PrintUtil.log("运行时间：" + (end - start) + "毫秒！");
    }
}

class LogHandler2 implements MethodInterceptor {

    public Object getInstance(Object target) {
        //创建增强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        PrintUtil.log("开启日志。。。");
        Object result = methodProxy.invokeSuper(o, objects);
        PrintUtil.log("关闭日志。。。");
        return result;
    }
}

interface UserDao {

    /**
     * 新增
     *
     * @param id
     * @param name
     */
    void save(int id, String name);

    /**
     * 删除
     *
     * @param id
     */
    void delete(int id);

    /**
     * 更新
     *
     * @param id
     * @param name
     */
    void update(int id, String name);
}

class UserDaoMysqlImpl implements UserDao {

    @Override
    public void save(int id, String name) {
        PrintUtil.log("Mysql执行保存！");
    }

    @Override
    public void delete(int id) {
        PrintUtil.log("Mysql执行删除！");
    }

    @Override
    public void update(int id, String name) {
        PrintUtil.log("Mysql执行修改！");
    }
}

class UserDaoOracleImpl implements UserDao {

    @Override
    public void save(int id, String name) {
        PrintUtil.log("Oracle执行保存！");
    }

    @Override
    public void delete(int id) {
        PrintUtil.log("Oracle执行删除！");
    }

    @Override
    public void update(int id, String name) {
        PrintUtil.log("Oracle执行修改！");
    }
}

class ProxyFactory {

    private final Object obj;

    public ProxyFactory(Object obj) {
        super();
        this.obj = obj;
    }

    public Object getTransactionProxyInstance() {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), (tempProxy, method, args) -> {
            PrintUtil.log("开启事务！");
            method.invoke(obj, args);
            PrintUtil.log("关闭事务！");
            return tempProxy;
        });
    }

    public Object getLogProxyInstance() {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), (tempProxy, method, args) -> {
            String methodName = method.getName();
            //只代理save和delete类型的方法
            if (methodName.contains("save") || methodName.contains("delete")) {
                PrintUtil.log("开启日志！");
                method.invoke(obj, args);
                PrintUtil.log("关闭日志！");
            } else {
                method.invoke(obj, args);
            }
            return tempProxy;
        });
    }
}

class LogHandler implements InvocationHandler {

    Object target;

    public LogHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PrintUtil.log("开启日志。。。");
        method.invoke(target, args);
        PrintUtil.log("关闭日志。。。");
        return proxy;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
