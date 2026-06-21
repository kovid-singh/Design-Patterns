package org.Practice.creational.singleton;

public class BillPughSingleton {

    private BillPughSingleton() {
        //Entire logic will be here
    }

    public static BillPughSingleton getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final BillPughSingleton instance = new BillPughSingleton();
    }
}

 class BillPughSingletonTest {
    public static void main(String[] args) {
        BillPughSingleton singleton = BillPughSingleton.getInstance();
        BillPughSingleton singleton1 = BillPughSingleton.getInstance();
        System.out.println(singleton);
        System.out.println(singleton1);
    }
}