package com.guru2test.junitDemo;

public class DemoUtils {
    /*public static void main(String[] args) {
        System.out.println("Hello world!");
    }*/

    private String academy = "Guru 2 test Academy";
    private String academyDuplicate = academy;

    public String getAcademy() {
        return academy;
    }

    public String getAcademyDuplicate() {
        return academyDuplicate;
    }
    public Boolean isGreater(int n1, int n2) {
        if (n1 > n2) {
            return true;
        }
        return false;
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