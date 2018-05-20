package com.sun.javafx.scene.control.skin;

import javafx.scene.control.AcceleratableButton;

@SuppressWarnings("restriction")
public class AcceleratableButtonSkin extends ButtonSkin {
    public AcceleratableButtonSkin(AcceleratableButton button) {
        super(button);
        if (button.getScene() != null && button.acceleratorProperty().get() != null) {
            button.getScene().getAccelerators().put(button.acceleratorProperty().get(), () -> {
                button.fire();
            });
        }
    }
}
