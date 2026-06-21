package org.Practice.creational.factoryMethod;

public class SMSNotification  implements Notication {
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS notification");
    }
}


