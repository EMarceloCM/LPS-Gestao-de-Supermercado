package view;

import Auth.SessionManager;
import controller.FeedbackController;
import model.entities.Feedback;
import model.entities.Order;
import model.exceptions.FeedbackException;
import view.dialogs.DlgChooseOrder;
import view.utils.TableUtils;
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
    private JTextArea txtReview;
    private JPanel panStar;

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
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                totalStars = 1;
            }
        });
        lblStar2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                totalStars = 2;
            }
        });
        lblStar3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                totalStars = 3;
            }
        });
        lblStar4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
                totalStars = 4;
            }
        });
        lblStar5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
                totalStars = 5;
            }
        });

        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editingId = -1;
                swapForm();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editingId = -1;
                swapForm();
                cleanForm();
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Feedback marked = (Feedback) TableUtils.getObjectSelected(grdFeedbacks);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição.", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                editingId = marked.getId();

                selectedOrder = marked.getOrder();

                loadForm(marked);
                swapForm();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Feedback marked = (Feedback) TableUtils.getObjectSelected(grdFeedbacks);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir o feedback '" + marked.getId() + " - " + marked.getReview() + "'?",
                        "Confirmar exclusão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    try {
                        controller.deleteFeedback(marked.getId());
                        controller.refreshTable(grdFeedbacks);
                    } catch (FeedbackException ex) {
                        JOptionPane.showMessageDialog(null, "Este pedido já possui um feedback!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (editingId == -1)
                        controller.createFeedback(txtReview.getText(), String.valueOf(totalStars), SessionManager.getLoggedUser(), selectedOrder);
                    else
                        controller.updateFeedback(editingId, txtReview.getText(), String.valueOf(totalStars), SessionManager.getLoggedUser(), selectedOrder);

                    controller.refreshTable(grdFeedbacks);
                    swapForm();
                    cleanForm();
                } catch (FeedbackException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        lblSearchOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DlgChooseOrder dlg = new DlgChooseOrder(null, true);
                dlg.setLocationRelativeTo(FrFeedback.this);
                dlg.setVisible(true);

                selectedOrder = dlg.getSelected();

                if (selectedOrder != null)
                    edtOrder.setText("id: " + selectedOrder.getId());
            }
        });
        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.filterTable(grdFeedbacks, edtSearch.getText());
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
        txtReview.setText("");
        edtOrder.setText("");
        edtSearch.setText("");
        lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
        lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
        lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
        lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
        lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-line-yellow.png")));
        totalStars = 1;
        selectedOrder = null;
    }

    private void loadForm(Feedback f) {
        txtReview.setText(f.getReview());
        edtOrder.setText(String.valueOf(f.getOrder().getId()));
        totalStars = f.getStars();

        for(int i = 1; i <= totalStars; i++) {
            if(i == 1) lblStar1.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
            else if(i == 2) lblStar2.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
            else if(i == 3) lblStar3.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
            else if(i == 4) lblStar4.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
            else if(i == 5) lblStar5.setIcon(new ImageIcon(getClass().getResource("/icons/star-fill-yellow.png")));
        }
    }
}
