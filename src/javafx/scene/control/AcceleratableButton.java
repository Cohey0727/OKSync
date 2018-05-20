package javafx.scene.control;

import com.sun.javafx.scene.control.skin.AcceleratableButtonSkin;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

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
        super();
    }

    /**
     * Creates a button with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public AcceleratableButton(String text) {
        super(text);
    }

    /**
     * Creates a button with the specified text and icon for its label.
     *
     * @param text A text string for its label.
     * @param graphic the icon for its label.
     */
    public AcceleratableButton(String text, Node graphic) {
        super(text, graphic);
        //        initialize();
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


        /***************************************************************************
         *                                                                         *
         * Methods                                                                 *
         *                                                                         *
         **************************************************************************/

        /** {@inheritDoc} */
        @Override
        public void fire() {
            if (!isDisabled()) {
                fireEvent(new ActionEvent());
            }
        }

        /** {@inheritDoc} */
        @Override
        protected Skin<?> createDefaultSkin() {
            return new AcceleratableButtonSkin(this);
        }

}
