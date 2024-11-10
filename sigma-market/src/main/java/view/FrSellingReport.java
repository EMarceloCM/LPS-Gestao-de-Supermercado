package view;

import controller.ItemOrderController;
import controller.OrderController;
import model.entities.ItemOrder;
import model.entities.Order;
import model.exceptions.OrderException;
import model.exceptions.ProductException;
import view.utils.FormatterUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FrSellingReport extends JDialog {
    private JPanel panForm;
    private JLabel lblInitialDate;
    private JLabel lblFinalDate;
    private JFormattedTextField fEdtDateI;
    private JFormattedTextField fEdtDateF;
    private JButton btnFilter;
    private JLabel lblTitle;
    private JPanel panInfo;
    private JLabel lblOrders;
    private JPanel panMain;
    private JLabel lblOrdersValue;
    private JLabel lblTotalPurchase;
    private JLabel lblTotalPurchaseValue;
    private JLabel lblAvaragePurchasePerOrder;
    private JLabel lblAvaragePurchasePerOrderValue;
    private JLabel lblAvarageItemOrderPerOrder;
    private JLabel lblAvarageItemOrderPerOrderValue;
    private JLabel lblItemOrderQuantity;
    private JLabel lblItemOrderQuantityValue;

    private OrderController orderController;
    private ItemOrderController itemOrderController;
    private LocalDateTime startDate, endDate;
    private List<Order> orders;
    private List<ItemOrder> itemOrders;

    public FrSellingReport(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setSize(740, 380);
        setTitle("Relatório de Vendas");

        FormatterUtils.applyDateMask(fEdtDateF);
        FormatterUtils.applyDateMask(fEdtDateI);

        orderController = new OrderController();
        itemOrderController = new ItemOrderController();

        LoadForm(LocalDate.now().withDayOfMonth(1).atStartOfDay(), LocalDate.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .atTime(23, 59, 59, 999_999_999));

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                
                LocalDate initialDate = null;
                LocalDate finalDate = null;

                try {
                    initialDate = LocalDate.parse(fEdtDateI.getText(), displayFormatter);
                    finalDate = LocalDate.parse(fEdtDateF.getText(), displayFormatter);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }

                LoadForm(initialDate.atStartOfDay(), finalDate.atTime(23, 59, 59, 999_999_999));
            }
        });
    }

    private void LoadForm(LocalDateTime initialDate, LocalDateTime finalDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        fEdtDateI.setText(initialDate.format(formatter));
        fEdtDateF.setText(finalDate.format(formatter));

        try {
            orders = orderController.findWithinDateRange(initialDate, finalDate);
        } catch (OrderException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }

        int itemsCount = 0;
        float avarageItemsPerOrder = 0;
        float avaragePurchasePerOrder = 0;
        float totalPurchase = 0;
        float totalItens = 0;

        for(Order order : orders){
            itemOrders = itemOrderController.findByOrderId(order.getId());

            for (ItemOrder item: itemOrders) {
                totalItens += item.getQuantity();
            }

            itemsCount += itemOrders.size();
            totalPurchase += order.getTotalAmount();
        }

        avarageItemsPerOrder = totalItens / orders.size();
        avaragePurchasePerOrder = totalPurchase / orders.size();

        lblOrdersValue.setText(String.valueOf(orders.size()));
        lblItemOrderQuantityValue.setText(String.valueOf(itemsCount));
        lblAvarageItemOrderPerOrderValue.setText(String.valueOf(avarageItemsPerOrder >= 0 ? avarageItemsPerOrder : 0));
        lblAvaragePurchasePerOrderValue.setText("R$ " + String.format("%.2f", avaragePurchasePerOrder >= 0 ? avaragePurchasePerOrder : 0));
        lblTotalPurchaseValue.setText("R$ " + String.format("%.2f", totalPurchase));
    }
}