package application.control.skin;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import application.control.AcceleratableButton;
import javafx.scene.Scene;

@SuppressWarnings("restriction")
public class AcceleratableButtonSkin extends ButtonSkin {
    public AcceleratableButtonSkin(AcceleratableButton button) {
        super(button);
        button.setDefaul();
        Scene scene = button.getScene();
        try {
            if (scene != null && scene.getAccelerators() != null) {
                if (button.getAccelerator() != null) {
                    scene.getAccelerators().put(button.getAccelerator(), () -> {
                        button.fire();
                    });
                }

                if (button.isReadyAcceleratable()) {
                    scene.getAccelerators().put(button.getKeyCodeCombination(), () -> {
                        button.fire();
                    });
                }
            }
        } catch (Throwable e) {
            button.setText(e.getMessage());
        }

    }
}
