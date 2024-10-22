package model.utils;

import javax.swing.*;

public class TableUtils {
    public static Object getObjectSelected(JTable table) {
        int selectedIndex = table.getSelectedRow();
        Object obj = null;

        if (selectedIndex >= 0)
            obj = table.getModel().getValueAt(selectedIndex, -1);

        return obj;
    }
}
