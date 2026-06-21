package org.Practice.creational.AbstractFactory;

public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Creating a Mac Button");
    }
}
