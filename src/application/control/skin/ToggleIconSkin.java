package application.control.skin;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import application.control.ToggleIcon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("restriction")
public class ToggleIconSkin extends ButtonSkin {
    public ToggleIconSkin(ToggleIcon button) {
        super(button);
        try {
            Image obverseImage = button.getObverseImage();
            Image reverseImage = button.getReverseImage();
            if (obverseImage != null) {
                ImageView iv = new ImageView(obverseImage);
                iv.setFitWidth(button.getIconWidth());
                iv.setFitHeight(button.getIconHeight());
                button.setGraphic(iv);
            }
        } catch (Throwable e) {
            button.setText(e.getMessage());
        }
    }
}
