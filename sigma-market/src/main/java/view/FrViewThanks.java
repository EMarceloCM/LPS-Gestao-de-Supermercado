package view;

import javax.swing.*;
import java.awt.*;

public class FrViewThanks extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panForm;
    private JLabel lblDescription;
    private JLabel lblImage;
    private JPanel panMain;

    public FrViewThanks(FrShoppingCart parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Obrigado!");
        setSize(600, 480);
        setMinimumSize(new Dimension(600, 500));
        setMaximumSize(new Dimension(600, 500));

        resizeLabelIcon();
    }

    private void resizeLabelIcon(){
        if (lblImage.getIcon() != null) {
            ImageIcon originalIcon = (ImageIcon) lblImage.getIcon();
            Image originalImage = originalIcon.getImage();

            Image resizedImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            lblImage.setIcon(resizedIcon);
        }
    }
}
