package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.ProductCategoryService;
import com.javafsd.SportyShoes.services.ProductService;
import com.javafsd.SportyShoes.utilities.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductService productService;
    @Autowired
    HelperClass helperClass;
    @Value("${sporty.page.returnv}")
    private String returnv;

    @GetMapping("/addProduct")
    public String addProduct(Product product, Model model, HttpSession session) {
        String foundAdmin = helperClass.adminAuxilliary(session);
        if (!foundAdmin.isEmpty()) {
            List<ProductCategory> productCategoryList = productCategoryService.findAllProductCategory();
            model.addAttribute("Product", product);
            model.addAttribute("user", "");
            model.addAttribute("ProductCategory", productCategoryList);
            model.addAttribute("addProduct", "active");
            returnv = "addProduct";
        }
        return returnv;
    }

    @PostMapping("/proc/addProduct")
    public String storeProduct(@ModelAttribute Product product, Model model,HttpSession session) {
        String addProductResult = productService.addProduct(product);
        model.addAttribute("msg", addProductResult);
        return addProduct(product,model,session);
    }

    @GetMapping("/viewProduct")
    public String viewProduct(HttpSession session, Model model) {
        String foundCustomer = helperClass.customerAuxilliary(session);
        String returnCV = "signIn";
        if (!foundCustomer.isEmpty()) {
            Customer customer=(Customer) session.getAttribute("sessionuser");
            Map<String, String> mAttributes = new HashMap<>();
            String cartQuantity = helperClass.cartHelper(session);
            List<Product> productList = productService.findAllProducts();
            String msg = productList.size() > 0 ? "" : "No products to display";
            mAttributes.put("cquantity", cartQuantity);
            mAttributes.put("msg", msg);
            mAttributes.put("user",customer.getEmail());
            mAttributes.put("prodCatalog","active");
            modelHelper(model, mAttributes);
            model.addAttribute("ProductList", productList);
            returnCV = "viewProducts";
        }
        return returnCV;
    }

    @GetMapping("/viewProduct/{cId}")
    public String viewProduct(@PathVariable("cId") Long categoryId, Model model, HttpSession session) {
        String foundCustomer = helperClass.customerAuxilliary(session);
        String returnCV = "signIn";
        if (!foundCustomer.isEmpty()) {
            ProductCategory productCategory = productCategoryService.findProductCategory(categoryId);
            List<Product> productListByCategory = productService.findAllProductsByCategory(productCategory);
            model.addAttribute("ProductListByCat", productListByCategory);
            model.addAttribute("ProductListByCat", productListByCategory);
            model.addAttribute("prodCatalog","active");
            returnCV = viewProduct(session, model);
        }
        return returnCV;
    }

    private Model modelHelper(Model model, Map<String, String> attributes) {
        for (String key : attributes.keySet()) {
            model.addAttribute(key, attributes.get(key));
        }
        return model;
    }
}
