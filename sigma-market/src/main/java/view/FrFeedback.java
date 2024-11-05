package view;

import controller.FeedbackController;
import model.entities.Feedback;
import model.entities.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrFeedback extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panButtons;
    private JButton btnNew;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSalvar;
    private JPanel panForm;
    private JLabel lblReview;
    private JTextField edtReview;
    private JLabel lblStars;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grdFeedbacks;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JPanel panMain;
    private JLabel lblOrder;
    private JTextField edtOrder;
    private JLabel lblSearchOrder;
    private JLabel lblStar1;
    private JLabel lblStar2;
    private JLabel lblStar3;
    private JLabel lblStar4;
    private JLabel lblStar5;

    private FeedbackController controller;
    private boolean isFormActive;
    private int editingId;
    private Order selectedOrder;
    private int totalStars;

    public FrFeedback(Frame parent, boolean modal){
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Endereço");
        setSize(1280, 680);

        totalStars = 1;
        controller = new FeedbackController();
        isFormActive = true;
        initCustomComponents();
        swapForm();
        controller.refreshTable(grdFeedbacks);

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

        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void initCustomComponents() {
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblSearchImg.setCursor(hand);
        lblSearchOrder.setCursor(hand);

        grdFeedbacks.setDefaultEditor(Object.class, null);
        grdFeedbacks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grdFeedbacks.getTableHeader().setReorderingAllowed(false);
    }

    private void swapForm() {
        isFormActive = !isFormActive;

        btnCancel.setVisible(isFormActive);
        btnSalvar.setVisible(isFormActive);
        btnDelete.setVisible(!isFormActive);
        btnEdit.setVisible(!isFormActive);
        btnNew.setVisible(!isFormActive);
        panForm.setVisible(isFormActive);
        panBot.setVisible(!isFormActive);
    }

    private void cleanForm() {
        edtReview.setText("");
        edtOrder.setText("");
        edtSearch.setText("");
        lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
        lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
        lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
        lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
        lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line.png")));
        totalStars = 1;
        selectedOrder = null;
    }

    private void loadForm(Feedback f) {
        edtReview.setText(f.getReview());
        edtOrder.setText(String.valueOf(f.getOrder().getId()));
        totalStars = f.getStars();

        for(int i = 1; i <= totalStars; i++) {
            if(i == 1) lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
            else if(i == 2) lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
            else if(i == 3) lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
            else if(i == 4) lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
            else if(i == 5) lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill.png")));
        }
    }
}
