package view.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DecimalInputValidator extends KeyAdapter {
    private final JTextField textField;

    public DecimalInputValidator(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // allow only decimal numbers
        char c = e.getKeyChar();
        String currText = textField.getText();

        if (!Character.isDigit(c) && c != '.' && c != ',' && c != KeyEvent.VK_BACK_SPACE) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        if (currText.isEmpty() && (c == '.' || c == ',')) {
            textField.setText("0.");
            e.consume();
            return;
        }

        if (c == ',') {
            e.setKeyChar('.');
        }

        if (c == '.' && currText.contains(".")) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        if (currText.contains(".")) {
            int postDot = currText.indexOf('.');
            if (currText.length() - postDot > 2) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

}
