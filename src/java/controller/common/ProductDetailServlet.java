package controller.common;

import dao.ItemDAO;
import dao.ProductComponentDAO;
import dao.ProductSpecDetailDAO;
import model.Item;
import model.ProductComponent;
import model.ProductSpec;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();
    private final ProductSpecDetailDAO specDetailDAO = new ProductSpecDetailDAO();
    private final ProductComponentDAO componentDAO = new ProductComponentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pid = req.getParameter("pid"); // e.g. "PC001"
        Item pcItem = itemDAO.getItemById(pid);
        Map<ProductSpec, List<Item>> specMap = specDetailDAO.getSpecDetailsByProduct(pid);
        List<ProductComponent> pcList = componentDAO.getComponentsByProductId(pid);

        // ✅ Xác định lựa chọn được chọn (RAM, SSD...) và default đúng linh kiện đang dùng
        Map<String, Item> defaultMap = new HashMap<>();
        Set<String> selectableSN = new HashSet<>();

        for (Map.Entry<ProductSpec, List<Item>> entry : specMap.entrySet()) {
            ProductSpec spec = entry.getKey();
            List<Item> items = entry.getValue();

            for (Item i : items) {
                selectableSN.add(i.getSerialNumber());
            }

            for (Item item : items) {
                for (ProductComponent pc : pcList) {
                    if (item.getSerialNumber().equals(pc.getComponentId())) {
                        defaultMap.put(spec.getSpecName(), item); // ✅ chọn đúng default
                        break;
                    }
                }
                if (defaultMap.containsKey(spec.getSpecName())) break;
            }

            if (!defaultMap.containsKey(spec.getSpecName()) && !items.isEmpty()) {
                defaultMap.put(spec.getSpecName(), items.get(0)); // fallback
            }
        }

        // ✅ Tính tổng giá các linh kiện cố định (không cho chọn)
        BigDecimal fixedComponentPrice = BigDecimal.ZERO;
        for (ProductComponent pc : pcList) {
            if (!selectableSN.contains(pc.getComponentId())) {
                Item comp = itemDAO.getItemById(pc.getComponentId());
                if (comp != null && comp.getPrice() != null) {
                    fixedComponentPrice = fixedComponentPrice.add(comp.getPrice().multiply(BigDecimal.valueOf(pc.getQuantity())));
                }
            }
        }

        req.setAttribute("pcItem", pcItem);
        req.setAttribute("specMap", specMap);
        req.setAttribute("defaultMap", defaultMap);
        req.setAttribute("fixedComponentPrice", fixedComponentPrice);
        req.setAttribute("pageContent1", "product-detail.jsp");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
