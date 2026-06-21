package org.Practice.creational.factoryMethod;

public class EmailNotification implements Notication {
    @Override
    public void notifyUser() {
        System.out.println("Sending an email notification");
    }
}
