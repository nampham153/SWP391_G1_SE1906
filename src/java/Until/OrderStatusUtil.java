/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Until;

/**
 *
 * @author namp0
 */
public class OrderStatusUtil {
    public static String getStatusName(int statusId) {
        return switch (statusId) {
            case 0 -> "Chờ xác nhận / Chờ thanh toán (COD)";
            case 1 -> "Đã thanh toán (VNPay)";
            case 2 -> "Thanh toán thất bại";
            case 3 -> "Đã xác nhận đơn hàng";
            case 4 -> "Đang giao hàng";
            case 5 -> "Đã giao hàng";
            case 6 -> "Đã hủy";
            default -> "Không xác định";
        };
    }
}
