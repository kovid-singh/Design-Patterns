package org.Practice.creational.AbstractFactory;

public class WindowFactory_Concrete  implements AbstractGUIFactory {
    @Override
    public Button createButton() {
        return new WindowButton();
    }

    @Override
    public CheckBox createCheckBox() {
        return new WindowCheckBox();
    }
}
