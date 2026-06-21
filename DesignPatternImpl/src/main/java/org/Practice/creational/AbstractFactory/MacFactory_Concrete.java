package org.Practice.creational.AbstractFactory;

public class MacFactory_Concrete  implements AbstractGUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public CheckBox createCheckBox() {
        return new MacCheckBox()    ;
    }
}
