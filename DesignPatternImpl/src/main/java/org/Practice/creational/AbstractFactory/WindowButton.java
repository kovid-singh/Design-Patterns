package org.Practice.creational.AbstractFactory;

public class WindowButton implements Button {
    @Override
    public void render() {
        System.out.println("Creating a Window Button");
    }
}
