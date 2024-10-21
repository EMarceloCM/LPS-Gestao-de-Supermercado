package model.validations;

import model.enums.PaymentStatus;
import model.enums.PaymentType;
import model.enums.Role;

public class ValidateEnum {
    public static Role ValidateRole(String role) {
        return switch (role) {
            case "ADMIN" -> Role.ADMIN;
            case "COSTUMER" -> Role.COSTUMER;
            default -> null;
        };
    }

//    public static PaymentType ValidatePaymentType(String paymentType) {
//        switch (paymentType) {
//        };
//    }
}