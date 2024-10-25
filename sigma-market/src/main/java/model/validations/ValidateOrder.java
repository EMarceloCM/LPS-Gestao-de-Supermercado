package model.validations;

import model.entities.Address;
import model.entities.Customer;
import model.entities.Order;
import model.enums.OrderStatus;
import model.enums.PaymentStatus;
import model.enums.PaymentType;
import model.exceptions.OrderException;
import model.utils.EnumUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ValidateOrder {
    public static Order Validate(String date, String totalAmount, String orderStatus, String paymentStatus, String paymentType, Customer customer, Address address) throws OrderException, DateTimeParseException, NumberFormatException {
        Order o = new Order();

        LocalDateTime localDate = null;
        try {
            localDate = LocalDateTime.parse(date);
        } catch (DateTimeParseException ex) {
            throw new DateTimeParseException("Error - Campo inválido: 'data'.", ex.getParsedString(), ex.getErrorIndex());
        }
        o.setDate(localDate);

        float floatValue = 0.0f;
        try {
            floatValue = Float.parseFloat(totalAmount);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'valor total'.");
        }
        if(floatValue == 0.0f)
            throw new OrderException("Error - Campo inválido: 'valor total' não pode ser 0.");
        o.setTotalAmount(floatValue);

        EnumUtils.EnumConverter<OrderStatus> converter = new EnumUtils.EnumConverter<>(OrderStatus.class);
        OrderStatus os = (OrderStatus) converter.convert(orderStatus);
        if(os == null)
            throw new OrderException("Error - Campo inválido: 'status do pedido'.");
        o.setOrderStatus(os);

        EnumUtils.EnumConverter<PaymentStatus> converter1 = new EnumUtils.EnumConverter<>(PaymentStatus.class);
        PaymentStatus ps = (PaymentStatus) converter1.convert(paymentStatus);
        if(ps == null)
            throw new OrderException("Error - Campo inválido: 'status do pagamento'.");
        o.setPaymentStatus(ps);

        EnumUtils.EnumConverter<PaymentType> converter2 = new EnumUtils.EnumConverter<>(PaymentType.class);
        PaymentType pt = (PaymentType) converter2.convert(paymentType);
        if(pt == null)
            throw new OrderException("Error - Campo inválido: 'tipo de pagamento'.");
        o.setPaymentType(pt);

        if(customer == null)
            throw new OrderException("Error - Campo inválido: 'costumer_id'.");
        o.setCustomer(customer);

        if(address == null)
            throw new OrderException("Error - Campo inválido: 'address_id'.");
        o.setAddress(address);

        return o;
    }
}