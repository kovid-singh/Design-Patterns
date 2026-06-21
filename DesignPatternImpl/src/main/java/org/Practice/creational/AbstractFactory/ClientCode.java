package org.Practice.creational.AbstractFactory;

public class ClientCode {


    public static void main(String[] args) {
        AbstractGUIFactory factory = new WindowFactory_Concrete();

        factory.createButton().render();
        factory.createCheckBox().render();

        //✅ Benefit:
//    To switch from Windows to Mac, change only the factory object.
        factory = new MacFactory_Concrete();

        factory.createButton().render();
        factory.createCheckBox().render();

    }


}