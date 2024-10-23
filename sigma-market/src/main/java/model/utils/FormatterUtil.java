package model.utils;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FormatterUtil {
    public static void applyDateMask(JFormattedTextField field) {
        applyMask(field, "##/##/####", '_');
    }

    public static void applyCpfMask(JFormattedTextField field) {
        applyMask(field, "###.###.###-##", '_');
    }

    public static void applyMask(JFormattedTextField field, String mask, char placeholder) {
        try {
            MaskFormatter mascara = new MaskFormatter(mask);
            mascara.setPlaceholderCharacter(placeholder); // aonde nao tiver inserido vai ficar um "_" (ex.: 10/__/____)

            field.setFormatterFactory(new DefaultFormatterFactory(mascara));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}