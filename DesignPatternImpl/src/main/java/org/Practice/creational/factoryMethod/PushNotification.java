package org.Practice.creational.factoryMethod;

public class PushNotification implements Notication {
    @Override
    public void notifyUser() {
        System.out.println("Sending a push notification");
    }
}
