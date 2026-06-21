package org.Practice.creational.factoryMethod;


//🚨 Problem Factory Method solves:
//It moves object creation logic to one place, making code cleaner, flexible, and easier to maintain.

public class MainClass {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();

        Notication emailNotification = factory.SendNotification("EMAIL");
        emailNotification.notifyUser();

        Notication smsNotification = factory.SendNotification("SMS");
        smsNotification.notifyUser();

        Notication pushNotification = factory.SendNotification("PUSH");
        pushNotification.notifyUser();
    }
}
