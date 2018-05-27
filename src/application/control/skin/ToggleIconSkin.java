package application.control.skin;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import application.control.ToggleIcon;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

@SuppressWarnings("restriction")
public class ToggleIconSkin extends ButtonSkin {
    public ToggleIconSkin(ToggleIcon button) {
        super(button);
        button.setDefaul();
        button.setAlignment(Pos.CENTER);
        button.setTextAlignment(TextAlignment.CENTER);
        try {
            Image image = button.getFromImage();

            if (image != null) {
                ImageView iv = new ImageView(image);
                iv.setFitWidth(40);
                iv.setFitHeight(40);
                //                button.setText(String.valueOf(button.getWidth()) + ":" + String.valueOf(iv.getFitWidth()));
                getChildren().add(iv);
            }
        } catch (Throwable e) {
            button.setText(e.getMessage());
        }
    }
}
