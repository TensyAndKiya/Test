package com.clei.jvm;

import com.clei.utils.PrintUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * invokeDynamic指令
 *
 * @author KIyA
 */
public class InvokeDynamicTest {

    public static void main(String[] args) {
        new Son().say();
    }

    private static class GrandFather {

        void say() {
            PrintUtil.log("I'm grandfather.");
        }
    }

    private static class Father extends GrandFather {

        @Override
        void say() {
            PrintUtil.log("I'm father.");
        }
    }

    private static class Son extends Father {

        /**
         * 让孙子调用爷爷的方法 没管用
         */
        @Override
        void say() {
            MethodType methodType = MethodType.methodType(void.class);
            try {
                Field lookupImpl = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                lookupImpl.setAccessible(true);
                MethodHandles.Lookup lookup = (MethodHandles.Lookup) lookupImpl.get(null);
                MethodHandle methodHandle = lookup.findSpecial(GrandFather.class, "say", methodType, getClass());
                methodHandle.invoke(this);
            } catch (Throwable e) {
                PrintUtil.log(e);
            }
        }
    }

}
