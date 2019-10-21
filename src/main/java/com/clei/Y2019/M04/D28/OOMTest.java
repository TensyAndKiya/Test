package com.clei.Y2019.M04.D28;

import java.util.ArrayList;
import java.util.List;

public class OOMTest {
    public static void main(String[] args){
        List<Entity> list = new ArrayList<>();
        while(true){
            Entity e = new Entity();
            list.add(e);
        }
    }

    private static class Entity{
        List<String> list = new ArrayList<>(9999);
    }
}
