package view;

import controller.ItemOrderController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrItemOrder extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grdItemOrder;
    private JPanel panBot;
    private JButton btnRate;
    private JPanel panMain;

    private ItemOrderController itemOrderController;

    public FrItemOrder(FrOrder parent, boolean modal, int order_id) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Itens do Pedido");
        setSize(550, 400);

        itemOrderController = new ItemOrderController();
        itemOrderController.filterTableByOrder(grdItemOrder, order_id);

        initCustomComponents();
        btnRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrEditFeedback dlg = new FrEditFeedback(FrItemOrder.this, true, order_id);
                dlg.setLocationRelativeTo(FrItemOrder.this);
                dlg.setVisible(true);
                dispose();
            }
        });
    }

    private void initCustomComponents() {
        grdItemOrder.setDefaultEditor(Object.class, null);
        grdItemOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grdItemOrder.getTableHeader().setReorderingAllowed(false);
        grdItemOrder.getColumnModel().getColumn(0).setMinWidth(30);
        grdItemOrder.getColumnModel().getColumn(0).setMaxWidth(30);
        grdItemOrder.getColumnModel().getColumn(0).setPreferredWidth(30);
        grdItemOrder.getColumnModel().getColumn(1).setMinWidth(150);
        grdItemOrder.getColumnModel().getColumn(1).setMaxWidth(1000);
        grdItemOrder.getColumnModel().getColumn(1).setPreferredWidth(150);
        grdItemOrder.getColumnModel().getColumn(2).setMinWidth(90);
        grdItemOrder.getColumnModel().getColumn(2).setMaxWidth(90);
        grdItemOrder.getColumnModel().getColumn(2).setPreferredWidth(90);
        grdItemOrder.getColumnModel().getColumn(3).setMinWidth(100);
        grdItemOrder.getColumnModel().getColumn(3).setMaxWidth(100);
        grdItemOrder.getColumnModel().getColumn(3).setPreferredWidth(100);
    }
}