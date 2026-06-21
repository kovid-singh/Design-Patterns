package org.Practice.creational.singleton;

public  enum EnumSingleton {
    INSTANCE;

    private EnumSingleton() {
        //one time logic will be here
    }

    public void doWork() {
      System.out.println("Doing work in Enum Singleton");
    }

}


 class EnumSingletonTest {
    public static void main(String[] args) {
        EnumSingleton singleton = EnumSingleton.INSTANCE;
        singleton.doWork();
    }
}