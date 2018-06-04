package application.control;

import application.control.skin.ToggleIconSkin;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.text.TextAlignment;

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
        //super(text, graphic);
        initialize();
    }

    private void initialize() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        setAccessibleRole(AccessibleRole.BUTTON);
        setMnemonicParsing(true); // enable mnemonic auto-parsing by default
        setText(null);
        setBackground(Background.EMPTY);
        setAlignment(Pos.CENTER);
        setContentDisplay(ContentDisplay.CENTER);
        setTextAlignment(TextAlignment.CENTER);
        setOnAction((e) -> {
            reverse();
        });
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/

    private Image obverseImage;
    public final void setObverseImage(Image value) {
        obverseImage = value;
    }
    public final Image getObverseImage() {
        return obverseImage;
    }

    private Image reverseImage;
    public final void setReverseImage(Image value) {
        reverseImage = value;
    }
    public final Image getReverseImage() {
        return reverseImage;
    }


    private DoubleProperty iconWidth;
    public final void setIconWidth(double value) {
        layoutXProperty().set(value);
    }
    public final double getIconWidth() {
        return iconWidth == null ? 36.0 : iconWidth.get();
    }

    public final DoubleProperty iconWidthProperty() {
        if (iconWidth == null) {
            iconWidth = new DoublePropertyBase(36.0) {
                @Override
                public Object getBean() {
                    return ToggleIcon.this;
                }
                @Override
                public String getName() {
                    return "iconWidth";
                }
            };
        }
        return iconWidth;
    }

    private DoubleProperty iconHeight;
    public final void setIconHeight(double value) {
        layoutXProperty().set(value);
    }
    public final double getIconHeight() {
        return iconHeight == null ? 36.0 : iconHeight.get();
    }

    public final DoubleProperty iconHeightProperty() {
        if (iconHeight == null) {
            iconHeight = new DoublePropertyBase(36.0) {
                @Override
                public Object getBean() {
                    return ToggleIcon.this;
                }
                @Override
                public String getName() {
                    return "iconHeight";
                }
            };
        }
        return iconHeight;
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

    public final ObjectProperty<Runnable> onReverseActionProperty() {
        return onReverseAction;
    }
    public final void setOnReverseAction(Runnable value) {
        onReverseActionProperty().set(value);
    }
    public final EventHandler<ActionEvent> getOnReverseAction() {
        return onActionProperty().get();
    }
    private ObjectProperty<Runnable> onReverseAction = new ObjectPropertyBase<Runnable>() {
        @Override
        public Object getBean() {
            return ToggleIcon.this;
        }

        @Override
        public String getName() {
            return "onReverseAction";
        }
    };

    public final ObjectProperty<Runnable> onObverseActionProperty() {
        return onObverseAction;
    }
    public final void setOnObverseAction(Runnable value) {
        onObverseActionProperty().set(value);
    }
    public final EventHandler<ActionEvent> getOnObverseAction() {
        return onActionProperty().get();
    }
    private ObjectProperty<Runnable> onObverseAction = new ObjectPropertyBase<Runnable>() {
        @Override
        public Object getBean() {
            return ToggleIcon.this;
        }

        @Override
        public String getName() {
            return "onObverseAction";
        }
    };


    private void obverse() {
        Runnable action = onObverseActionProperty().get();
        if (action != null) {
            action.run();
        }
        if (obverseImage != null) {
            ImageView iv = new ImageView(obverseImage);
            setGraphic(iv);
            iv.setFitWidth(iconWidthProperty().get());
            iv.setFitHeight(iconHeightProperty().get());
        }
        setOnAction((e) -> {
            reverse();
        });
    }

    private void reverse() {
        Runnable action = onReverseActionProperty().get();
        if (action != null) {
            action.run();
        }
        if (reverseImage != null) {
            ImageView iv = new ImageView(reverseImage);
            setGraphic(iv);
            iv.setFitWidth(iconWidthProperty().get());
            iv.setFitHeight(iconHeightProperty().get());
        }
        setOnAction((e) -> {
            obverse();
        });
    }
}
