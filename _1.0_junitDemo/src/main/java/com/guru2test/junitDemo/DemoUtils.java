package com.guru2test.junitDemo;

public class DemoUtils {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public int add(int a, int b){
        return a + b ;
    }

    public Object checkNull(Object obj){

        if( obj != null){
            return obj;
        }
        return null;
    }

}