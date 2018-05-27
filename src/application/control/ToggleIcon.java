package application.control;

import application.control.skin.ToggleIconSkin;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;

public class ToggleIcon extends Button {
    private static final String DEFAULT_STYLE_CLASS = "toggle-icon";

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    /**
     * Creates a button with an empty string for its label.
     */
    public ToggleIcon() {
        this(null);
    }

    /**
     * Creates a button with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public ToggleIcon(String text) {
        this(text, null);
    }

    /**
     * Creates a button with the specified text and icon for its label.
     *
     * @param text A text string for its label.
     * @param graphic the icon for its label.
     */
    public ToggleIcon(String text, Node graphic) {
        super(text, graphic);
        initialize();
    }

    private void initialize() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS, "button");
        setAccessibleRole(AccessibleRole.BUTTON);
        setMnemonicParsing(true); // enable mnemonic auto-parsing by default
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/

    private Image fromImage;
    public final void setFromImage(Image value) {
        fromImage = value;
    }
    public final Image getFromImage() {
        return fromImage;
    }

    private Image toImage;
    public final void setToImage(Image value) {
        toImage = value;
    }
    public final Image getToImage() {
        return toImage;
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ToggleIconSkin(this);
    }

    public void setDefaul() {
        setText(null);
        setBackground(Background.EMPTY);
    }
}
