package org.Practice.creational.singleton;

//This is Synchronized Singleton Pattern Implementation
public class SingletonPattern {

    private static SingletonPattern INSTANCE ;

    private SingletonPattern() {
        //Entire logic will be here
        //like in case of db connection
//        connection = createConnection();
    }

public synchronized  static SingletonPattern getInstance(){
        if(INSTANCE==null){
            INSTANCE = new SingletonPattern();
        }
        return INSTANCE;
}

}


class SingletonPatternTest {
    public static void main(String[] args) {
        SingletonPattern singleton = SingletonPattern.getInstance();
        SingletonPattern singleton1 = SingletonPattern.getInstance();
        System.out.println(singleton);
        System.out.println(singleton1);
    }
}
