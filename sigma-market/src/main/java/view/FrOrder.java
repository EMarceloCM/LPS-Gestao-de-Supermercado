package view;

import Auth.SessionManager;
import controller.ItemOrderController;
import controller.OrderController;
import controller.tableModel.utils.IconLabelRenderer;
import model.enums.Role;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrOrder extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grdOrders;
    private JPanel panMain;
    private JPanel panSearchForm;
    private JLabel lblSearchImg;
    private JTextField edtSearch;
    private JLabel lblSearch;

    private OrderController orderController;
    private ItemOrderController itemOrderController;

    public FrOrder(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Pedidos");
        setSize(1170, 680);

        orderController = new OrderController();
        itemOrderController = new ItemOrderController();
        orderController.refreshTable(grdOrders);

        initCustomComponents();
        configureGrdAfterTModel();
        swapForm();

        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO colocar maskara de inteiro no edtSearch
                try{
                    if(!edtSearch.getText().isBlank())
                        orderController.filterTableByCustomer(grdOrders, Integer.parseInt(edtSearch.getText()));
                    else
                        orderController.refreshTable(grdOrders);

                    configureGrdAfterTModel();
                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Insira um id numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void initCustomComponents() {
        grdOrders.setDefaultEditor(Object.class, null);
        grdOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grdOrders.getTableHeader().setReorderingAllowed(false);
    }

    private void configureGrdAfterTModel(){
        if(SessionManager.getLoggedUserRole() == Role.CUSTOMER) configCustomerGrid();
        else if(SessionManager.getLoggedUserRole() == Role.ADMIN) configAdminGrid();
    }

    private void configCustomerGrid()
    {
        grdOrders.getColumnModel().getColumn(8).setCellRenderer(new IconLabelRenderer());
        grdOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = grdOrders.rowAtPoint(e.getPoint());
                int column = grdOrders.columnAtPoint(e.getPoint());

                if (column == 8 && row >= 0) {
                    int order_id = (Integer) grdOrders.getValueAt(row, 0);
                    FrItemOrder dlg = new FrItemOrder(FrOrder.this, true, order_id);
                    dlg.setLocationRelativeTo(FrOrder.this);
                    dlg.setVisible(true);
                }
            }
        });
        grdOrders.getColumnModel().getColumn(0).setMinWidth(40);
        grdOrders.getColumnModel().getColumn(0).setMaxWidth(40);
        grdOrders.getColumnModel().getColumn(0).setPreferredWidth(40);
        grdOrders.getColumnModel().getColumn(1).setMinWidth(190);
        grdOrders.getColumnModel().getColumn(1).setMaxWidth(190);
        grdOrders.getColumnModel().getColumn(1).setPreferredWidth(190);
        grdOrders.getColumnModel().getColumn(2).setMinWidth(190);
        grdOrders.getColumnModel().getColumn(2).setMaxWidth(190);
        grdOrders.getColumnModel().getColumn(2).setPreferredWidth(190);
        grdOrders.getColumnModel().getColumn(3).setMinWidth(100);
        grdOrders.getColumnModel().getColumn(3).setMaxWidth(100);
        grdOrders.getColumnModel().getColumn(3).setPreferredWidth(100);
        grdOrders.getColumnModel().getColumn(4).setMinWidth(190);
        grdOrders.getColumnModel().getColumn(4).setMaxWidth(190);
        grdOrders.getColumnModel().getColumn(4).setPreferredWidth(190);
        grdOrders.getColumnModel().getColumn(5).setMinWidth(120);
        grdOrders.getColumnModel().getColumn(5).setMaxWidth(120);
        grdOrders.getColumnModel().getColumn(5).setPreferredWidth(120);
        grdOrders.getColumnModel().getColumn(6).setMinWidth(140);
        grdOrders.getColumnModel().getColumn(6).setMaxWidth(140);
        grdOrders.getColumnModel().getColumn(6).setPreferredWidth(140);
        grdOrders.getColumnModel().getColumn(7).setMinWidth(140);
        grdOrders.getColumnModel().getColumn(7).setMaxWidth(140);
        grdOrders.getColumnModel().getColumn(7).setPreferredWidth(140);
        grdOrders.getColumnModel().getColumn(8).setMinWidth(30);
        grdOrders.getColumnModel().getColumn(8).setMaxWidth(30);
        grdOrders.getColumnModel().getColumn(8).setPreferredWidth(30);
    }

    private void configAdminGrid(){
        grdOrders.getColumnModel().getColumn(9).setCellRenderer(new IconLabelRenderer());
        grdOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = grdOrders.rowAtPoint(e.getPoint());
                int column = grdOrders.columnAtPoint(e.getPoint());

                if (column == 9 && row >= 0) {
                    int order_id = (Integer) grdOrders.getValueAt(row, 0);
                    FrItemOrder dlg = new FrItemOrder(FrOrder.this, true, order_id);
                    dlg.setLocationRelativeTo(FrOrder.this);
                    dlg.setVisible(true);
                }
            }
        });
        grdOrders.getColumnModel().getColumn(0).setMinWidth(40);
        grdOrders.getColumnModel().getColumn(0).setMaxWidth(40);
        grdOrders.getColumnModel().getColumn(0).setPreferredWidth(40);
        grdOrders.getColumnModel().getColumn(1).setMinWidth(90);
        grdOrders.getColumnModel().getColumn(1).setMaxWidth(90);
        grdOrders.getColumnModel().getColumn(1).setPreferredWidth(90);
        grdOrders.getColumnModel().getColumn(2).setMinWidth(95);
        grdOrders.getColumnModel().getColumn(2).setMaxWidth(95);
        grdOrders.getColumnModel().getColumn(2).setPreferredWidth(95);
        grdOrders.getColumnModel().getColumn(3).setMinWidth(95);
        grdOrders.getColumnModel().getColumn(3).setMaxWidth(95);
        grdOrders.getColumnModel().getColumn(3).setPreferredWidth(95);
        grdOrders.getColumnModel().getColumn(4).setMinWidth(190);
        grdOrders.getColumnModel().getColumn(4).setMaxWidth(190);
        grdOrders.getColumnModel().getColumn(4).setPreferredWidth(190);
        grdOrders.getColumnModel().getColumn(5).setMinWidth(100);
        grdOrders.getColumnModel().getColumn(5).setMaxWidth(100);
        grdOrders.getColumnModel().getColumn(5).setPreferredWidth(100);
        grdOrders.getColumnModel().getColumn(6).setMinWidth(120);
        grdOrders.getColumnModel().getColumn(6).setMaxWidth(120);
        grdOrders.getColumnModel().getColumn(6).setPreferredWidth(120);
        grdOrders.getColumnModel().getColumn(7).setMinWidth(140);
        grdOrders.getColumnModel().getColumn(7).setMaxWidth(140);
        grdOrders.getColumnModel().getColumn(7).setPreferredWidth(140);
        grdOrders.getColumnModel().getColumn(8).setMinWidth(130);
        grdOrders.getColumnModel().getColumn(8).setMaxWidth(130);
        grdOrders.getColumnModel().getColumn(8).setPreferredWidth(130);
        grdOrders.getColumnModel().getColumn(9).setMinWidth(30);
        grdOrders.getColumnModel().getColumn(9).setMaxWidth(30);
        grdOrders.getColumnModel().getColumn(9).setPreferredWidth(30);
    }

    private void swapForm(){
        boolean isVisible = SessionManager.getLoggedUserRole() == Role.ADMIN;
        lblSearch.setVisible(isVisible);
        edtSearch.setVisible(isVisible);
        lblSearchImg.setVisible(isVisible);
    }
}