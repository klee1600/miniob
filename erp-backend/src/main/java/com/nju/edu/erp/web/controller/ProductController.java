package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.CreateProductVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response createProduct(@RequestBody CreateProductVO createProductVO) {
        return Response.buildSuccess(productService.createProduct(createProductVO));
    }

    @PostMapping("/initial_create")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response initial_createProduct(@RequestBody CreateProductVO createProductVO) {
        productService.initial_createProduct(createProductVO);
        return Response.buildSuccess();
    }

    @PostMapping("/update")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response updateProduct(@RequestBody ProductInfoVO productInfoVO) {
        return Response.buildSuccess(productService.updateProduct(productInfoVO));
    }

    @GetMapping("/queryAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER, Role.SALE_STAFF, Role.SALE_MANAGER})
    public Response findAllProduct() {
        return Response.buildSuccess(productService.queryAllProduct());
    }

    @GetMapping("/queryInitialAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER, Role.SALE_STAFF, Role.SALE_MANAGER})
    public Response findAllInitialProduct() {
        return Response.buildSuccess(productService.queryAllInitialProduct());
    }

    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response deleteProduct(@RequestParam String id) {
        productService.deleteById(id);
        return Response.buildSuccess();
    }

}
