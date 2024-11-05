package view.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntegerInputValidator extends KeyAdapter {
    private final JTextField textField;

    public IntegerInputValidator(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // allow only decimal numbers
        char c = e.getKeyChar();
        String currText = textField.getText();

        if (e.isControlDown()) return;

        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
