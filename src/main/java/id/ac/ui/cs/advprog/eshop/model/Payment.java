package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    private static final String PAYMENT_METHOD_VOUCHER = "Voucher Code";
    private static final String PAYMENT_METHOD_COD = "Cash on Delivery";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final int VOUCHER_CODE_LENGTH = 16;
    private static final String VOUCHER_PREFIX = "ESHOP";

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePayment(method, paymentData);
    }

    private String validatePayment(String method, Map<String, String> paymentData) {
        return switch (method) {
            case PAYMENT_METHOD_VOUCHER -> validateVoucherCode(paymentData.get("voucherCode"));
            case PAYMENT_METHOD_COD -> validateCashOnDelivery(paymentData);
            default -> STATUS_REJECTED;
        };
    }

    private String validateVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != VOUCHER_CODE_LENGTH ||
                !voucherCode.startsWith(VOUCHER_PREFIX) ||
                voucherCode.replaceAll("\\D", "").length() != 8) {
            return STATUS_REJECTED;
        }
        return STATUS_SUCCESS;
    }

    private String validateCashOnDelivery(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.isEmpty() || deliveryFee == null || deliveryFee.isEmpty()) {
            return STATUS_REJECTED;
        }
        return STATUS_SUCCESS;
    }

    public void setStatus(String status) {
        if (!STATUS_SUCCESS.equals(status) && !STATUS_REJECTED.equals(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }
}