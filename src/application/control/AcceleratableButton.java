package application.control;

import java.util.ArrayList;
import java.util.List;

import application.control.skin.AcceleratableButtonSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;

public class AcceleratableButton extends Button {
    private static final String DEFAULT_STYLE_CLASS = "acceleratable-button";

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    /**
     * Creates a button with an empty string for its label.
     */
    public AcceleratableButton() {
        this(null);
    }

    /**
     * Creates a button with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public AcceleratableButton(String text) {
        this(text, null);
    }

    /**
     * Creates a button with the specified text and icon for its label.
     *
     * @param text A text string for its label.
     * @param graphic the icon for its label.
     */
    public AcceleratableButton(String text, Node graphic) {
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

    /**
     * A default Button is the button that receives
     * a keyboard VK_ENTER press, if no other node in the scene consumes it.
     */
    private ObjectProperty<KeyCombination> accelerator;
    public final void setAccelerator(KeyCombination value) {
        acceleratorProperty().set(value);
    }
    public final KeyCombination getAccelerator() {
        return accelerator == null ? null : accelerator.get();
    }
    public final ObjectProperty<KeyCombination> acceleratorProperty() {
        if (accelerator == null) {
            accelerator = new SimpleObjectProperty<KeyCombination>(this, "accelerator");
        }
        return accelerator;
    }

    /**
     * Control Down
     *
     */
    private BooleanProperty controlDown;
    public final void setControlDown(boolean value) {
    }
    public final boolean isControlDown() {
        return false;
    }

    public final BooleanProperty controlDownProperty() {
        if (controlDown == null) {
            controlDown = new BooleanPropertyBase(false) {

                @Override
                public Object getBean() {
                    return AcceleratableButton.this;
                }

                @Override
                public String getName() {
                    return "controlDown";
                }
            };
        }
        return controlDown;
    }

    /**
     * A default Button is the button that receives
     * a keyboard VK_ENTER press, if no other node in the scene consumes it.
     */
    private StringProperty shortCut;
    public final void setShortCut(String value) {
        shortCutProperty().set(value);
    }
    public final String getShortCut() {
        return shortCut == null ? "" : shortCut.getValue();
    }
    public final StringProperty shortCutProperty() {
        if (shortCut == null) {
            shortCut = new SimpleStringProperty(this, "text", "");
        }
        return shortCut;
    }

    /**
     * Alt Down
     *
     */
    private BooleanProperty altDown;
    public final void setAltDown(boolean value) {
        altDownProperty().set(value);
    }
    public final boolean isAltDown() {
        return altDown == null ? false : altDown.get();
    }

    public final BooleanProperty altDownProperty() {
        if (altDown == null) {
            altDown = new BooleanPropertyBase(false) {

                @Override
                public Object getBean() {
                    return AcceleratableButton.this;
                }

                @Override
                public String getName() {
                    return "altDown";
                }
            };
        }
        return altDown;
    }

    /**
     * Shift Down
     *
     */
    private BooleanProperty shiftDown;
    public final void setShiftDown(boolean value) {
        shiftDownProperty().set(value);
    }
    public final boolean isShiftDown() {
        return shiftDown == null ? false : shiftDown.get();
    }

    public final BooleanProperty shiftDownProperty() {
        if (shiftDown == null) {
            shiftDown = new BooleanPropertyBase(false) {

                @Override
                public Object getBean() {
                    return AcceleratableButton.this;
                }

                @Override
                public String getName() {
                    return "shiftDown";
                }
            };
        }
        return shiftDown;
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new AcceleratableButtonSkin(this);
    }

    public boolean isReadyAcceleratable() {
        return (controlDownProperty().get() || altDownProperty().get() || shiftDownProperty().get()) && !shortCutProperty().get().isEmpty();
    }

    public KeyCodeCombination getKeyCodeCombination() {
        if (isReadyAcceleratable()) {
            return new KeyCodeCombination(KeyCode.valueOf(getShortCut().substring(0, 1).toUpperCase()), getModifiers());
        } else {
            return null;
        }
    }

    private Modifier[] getModifiers() {
        List<Modifier> keyCombi = new ArrayList<Modifier>();
        if (controlDownProperty().get()) {
            keyCombi.add(KeyCombination.CONTROL_DOWN);
        }
        if (altDownProperty().get()) {
            keyCombi.add(KeyCombination.ALT_DOWN);
        }
        if (shiftDownProperty().get()) {
            keyCombi.add(KeyCombination.SHIFT_DOWN);
        }
        return keyCombi.toArray(new Modifier[keyCombi.size()]);
    }

    public void setDefaul() {
        if (getText() == null || getText().isEmpty()) {
            setText("Button");
        }
    }
}
