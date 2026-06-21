package org.Practice.creational.AbstractFactory;

public class WindowCheckBox implements CheckBox {
    @Override
    public void render() {
        System.out.println("Creating a Windows CheckBox");
    }
}
