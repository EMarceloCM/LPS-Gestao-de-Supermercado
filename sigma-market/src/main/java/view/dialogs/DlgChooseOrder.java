package view.dialogs;

import Auth.SessionManager;
import controller.OrderController;
import controller.tableModel.utils.IconLabelRenderer;
import lombok.Getter;
import model.entities.Order;
import model.enums.Role;
import view.utils.TableUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgChooseOrder extends JDialog {
    private JPanel panMain;
    private JPanel panTop;
    private JLabel lblTitle;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grd;
    private JPanel panBot;
    private JButton btnConfirm;

    private OrderController controller;
    @Getter
    private Order selected;

    public DlgChooseOrder(Frame parent, boolean modal) {
        setContentPane(panMain);
        setModal(true);
        setTitle("Selecionar Pedido");
        setSize(1045, 600);

        controller = new OrderController();
        selected = null;

        initCustomComponents();
        controller.refreshTable(grd);
        configureGrdAfterTModel();

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order marked = (Order) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um pedido!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selected = marked;
                dispose();
            }
        });
    }

    private void initCustomComponents() {
        grd.setDefaultEditor(Object.class, null);
        grd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grd.getTableHeader().setReorderingAllowed(false);
    }

    private void configureGrdAfterTModel(){
        if(SessionManager.getLoggedUserRole() == Role.CUSTOMER) configCustomerGrid();
        else if(SessionManager.getLoggedUserRole() == Role.ADMIN) configAdminGrid();
    }

    private void configCustomerGrid()
    {
        grd.getColumnModel().getColumn(8).setCellRenderer(new IconLabelRenderer());
        grd.getColumnModel().getColumn(0).setMinWidth(40);
        grd.getColumnModel().getColumn(0).setMaxWidth(40);
        grd.getColumnModel().getColumn(0).setPreferredWidth(40);
        grd.getColumnModel().getColumn(1).setMinWidth(190);
        grd.getColumnModel().getColumn(1).setMaxWidth(190);
        grd.getColumnModel().getColumn(1).setPreferredWidth(190);
        grd.getColumnModel().getColumn(2).setMinWidth(190);
        grd.getColumnModel().getColumn(2).setMaxWidth(190);
        grd.getColumnModel().getColumn(2).setPreferredWidth(190);
        grd.getColumnModel().getColumn(3).setMinWidth(100);
        grd.getColumnModel().getColumn(3).setMaxWidth(100);
        grd.getColumnModel().getColumn(3).setPreferredWidth(100);
        grd.getColumnModel().getColumn(4).setMinWidth(190);
        grd.getColumnModel().getColumn(4).setMaxWidth(190);
        grd.getColumnModel().getColumn(4).setPreferredWidth(190);
        grd.getColumnModel().getColumn(5).setMinWidth(120);
        grd.getColumnModel().getColumn(5).setMaxWidth(120);
        grd.getColumnModel().getColumn(5).setPreferredWidth(120);
        grd.getColumnModel().getColumn(6).setMinWidth(140);
        grd.getColumnModel().getColumn(6).setMaxWidth(140);
        grd.getColumnModel().getColumn(6).setPreferredWidth(140);
        grd.getColumnModel().getColumn(7).setMinWidth(140);
        grd.getColumnModel().getColumn(7).setMaxWidth(140);
        grd.getColumnModel().getColumn(7).setPreferredWidth(140);
        grd.getColumnModel().getColumn(8).setMinWidth(0);
        grd.getColumnModel().getColumn(8).setMaxWidth(0);
        grd.getColumnModel().getColumn(8).setPreferredWidth(0);
    }

    private void configAdminGrid(){
        grd.getColumnModel().getColumn(9).setCellRenderer(new IconLabelRenderer());
        grd.getColumnModel().getColumn(0).setMinWidth(40);
        grd.getColumnModel().getColumn(0).setMaxWidth(40);
        grd.getColumnModel().getColumn(0).setPreferredWidth(40);
        grd.getColumnModel().getColumn(1).setMinWidth(90);
        grd.getColumnModel().getColumn(1).setMaxWidth(90);
        grd.getColumnModel().getColumn(1).setPreferredWidth(90);
        grd.getColumnModel().getColumn(2).setMinWidth(95);
        grd.getColumnModel().getColumn(2).setMaxWidth(95);
        grd.getColumnModel().getColumn(2).setPreferredWidth(95);
        grd.getColumnModel().getColumn(3).setMinWidth(95);
        grd.getColumnModel().getColumn(3).setMaxWidth(95);
        grd.getColumnModel().getColumn(3).setPreferredWidth(95);
        grd.getColumnModel().getColumn(4).setMinWidth(190);
        grd.getColumnModel().getColumn(4).setMaxWidth(190);
        grd.getColumnModel().getColumn(4).setPreferredWidth(190);
        grd.getColumnModel().getColumn(5).setMinWidth(100);
        grd.getColumnModel().getColumn(5).setMaxWidth(100);
        grd.getColumnModel().getColumn(5).setPreferredWidth(100);
        grd.getColumnModel().getColumn(6).setMinWidth(120);
        grd.getColumnModel().getColumn(6).setMaxWidth(120);
        grd.getColumnModel().getColumn(6).setPreferredWidth(120);
        grd.getColumnModel().getColumn(7).setMinWidth(140);
        grd.getColumnModel().getColumn(7).setMaxWidth(140);
        grd.getColumnModel().getColumn(7).setPreferredWidth(140);
        grd.getColumnModel().getColumn(8).setMinWidth(130);
        grd.getColumnModel().getColumn(8).setMaxWidth(130);
        grd.getColumnModel().getColumn(8).setPreferredWidth(130);
        grd.getColumnModel().getColumn(9).setMinWidth(0);
        grd.getColumnModel().getColumn(9).setMaxWidth(0);
        grd.getColumnModel().getColumn(9).setPreferredWidth(0);
    }
}