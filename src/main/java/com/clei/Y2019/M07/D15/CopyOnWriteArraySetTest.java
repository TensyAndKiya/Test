package com.clei.Y2019.M07.D15;

import com.clei.utils.PrintUtil;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArraySet竟然可以在遍历的时候删除，厉害了
 */
public class CopyOnWriteArraySetTest {
    public static void main(String[] args) {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            set.add("" + i);
        }

        for (int i = 0; i < 9; i++) {
            for(String s : set){
                if(!s.equals("0")){
                    set.remove(s);
                }
            }
        }

        PrintUtil.log(set);
    }
}
