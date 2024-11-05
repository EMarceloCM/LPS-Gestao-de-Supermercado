package view;

import Auth.SessionManager;
import controller.FeedbackController;
import controller.OrderController;
import model.entities.Feedback;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrEditFeedback extends JDialog{
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panForm;
    private JPanel panFormCore;
    private JLabel lblReview;
    private JLabel lblRate;
    private JButton btnSubmit;
    private JPanel panMain;
    private JTextField edtReview;
    private JLabel lblStar1;
    private JLabel lblStar2;
    private JLabel lblStar3;
    private JLabel lblStar4;
    private JLabel lblStar5;
    private JTextArea txtReview;
    private JPanel panStar;

    private FeedbackController feedbackController;
    private OrderController orderController;
    private Feedback feedback;
    private int order_id;
    private int totalStars;
    public FrEditFeedback(FrItemOrder parent, boolean modal, int order_id) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Feedback");
        setSize(550, 400);

        this.totalStars = 1;
        this.order_id = order_id;
        feedbackController = new FeedbackController();
        orderController = new OrderController();
        feedback = feedbackController.findByOrderId(order_id);

        initCustomComponents();

        lblStar1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                totalStars = 1;
            }
        });
        lblStar2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                totalStars = 2;
            }
        });
        lblStar3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                totalStars = 3;
            }
        });
        lblStar4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
                totalStars = 4;
            }
        });
        lblStar5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                totalStars = 5;
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtReview.getText().isBlank()) JOptionPane.showMessageDialog(null, "Deixe uma mensagem em seu feedback!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                else submitFeedback(totalStars);
            }
        });
    }

    private void initCustomComponents() {
        if(feedback != null) {
            txtReview.setText(feedback.getReview());

            int stars = feedback.getStars();
            for(int i = 1; i <= stars; i++) {
                if(i == 1) lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                else if(i == 2) lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                else if(i == 3) lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                else if(i == 4) lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
                else if(i == 5) lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
            }
        }else{
            txtReview.setText("");
            lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
            lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
            lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
            lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
            lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
        }
    }

    private void submitFeedback(int stars) {
        if(feedback == null)
            feedbackController.createFeedback(txtReview.getText(), String.valueOf(stars), SessionManager.getLoggedUser(), orderController.findById(order_id));
        else
            feedbackController.updateFeedback(feedback.getId(), txtReview.getText(), String.valueOf(stars), SessionManager.getLoggedUser(), orderController.findById(order_id));

        dispose();
    }
}