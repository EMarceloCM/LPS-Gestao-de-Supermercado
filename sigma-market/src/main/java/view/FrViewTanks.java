package view;

import javax.swing.*;
import java.awt.*;

public class FrViewTanks extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panForm;
    private JLabel lblDescription;
    private JLabel lblImage;
    private JPanel panMain;
//TODO melhorar essa view
    public FrViewTanks(FrShoppingCart parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Obrigado!");
        setSize(600, 500);
        setMinimumSize(new Dimension(600, 500));
        setMaximumSize(new Dimension(600, 500));
    }
}
