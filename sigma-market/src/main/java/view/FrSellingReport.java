package view;

import controller.ItemOrderController;
import controller.OrderController;
import model.entities.ItemOrder;
import model.entities.Order;
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
    private JFormattedTextField edtInitialDate;
    private JFormattedTextField edtFinalDate;
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

//TODO colocar máskara nos campos de data do tipo -> dd/MM/yyyy HH:mm:ss
    public FrSellingReport(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setSize(740, 380);
        setTitle("Relatório de Vendas");

        orderController = new OrderController();
        itemOrderController = new ItemOrderController();

        LoadForm(LocalDate.now().withDayOfMonth(1).atStartOfDay(), LocalDate.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .atTime(23, 59, 59, 999_999_999));

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                LoadForm(LocalDateTime.parse(edtInitialDate.getText(), formatter),
                        LocalDateTime.parse(edtFinalDate.getText(), formatter));
            }
        });
    }

    private void LoadForm(LocalDateTime initialDate, LocalDateTime finalDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        edtInitialDate.setText(initialDate.format(formatter));
        edtFinalDate.setText(finalDate.format(formatter));

        orders = orderController.findWithinDateRange(initialDate, finalDate);

        int itemsCount = 0;
        float avarageItemsPerOrder = 0;
        float avaragePurchasePerOrder = 0;
        float totalPurchase = 0;

        for(Order order : orders){
            itemOrders = itemOrderController.findByOrderId(order.getId());

            itemsCount += itemOrders.size();
            totalPurchase += order.getTotalAmount();
        }

        avarageItemsPerOrder = (float) itemsCount / orders.size();
        avaragePurchasePerOrder = totalPurchase / orders.size();

        lblOrdersValue.setText(String.valueOf(orders.size()));
        lblItemOrderQuantityValue.setText(String.valueOf(itemsCount));
        lblAvarageItemOrderPerOrderValue.setText(String.valueOf(avarageItemsPerOrder));
        lblAvaragePurchasePerOrderValue.setText("R$ " + String.format("%.2f", avaragePurchasePerOrder));
        lblTotalPurchaseValue.setText("R$ " + String.format("%.2f", totalPurchase));
    }
}