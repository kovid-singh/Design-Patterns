package org.Practice.creational.factoryMethod;

import org.Practice.creational.factoryMethod.EmailNotification;
import org.Practice.creational.factoryMethod.Notication;
import org.Practice.creational.factoryMethod.PushNotification;
import org.Practice.creational.factoryMethod.SMSNotification;

public class NotificationFactory {
    Notication SendNotification(String channel) {
        if (channel == null || channel.isEmpty()) return null;
        else if (channel.equals("SMS")) {
            return new SMSNotification();
        } else if (channel.equals("EMAIL")) {
            return new EmailNotification();
        } else if (channel.equals("PUSH")) {
            return new PushNotification();
        } else {
            throw new IllegalArgumentException("Unknown channel " + channel);

        }
    }
}



