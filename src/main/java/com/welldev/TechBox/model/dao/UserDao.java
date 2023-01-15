package com.welldev.TechBox.model.dao;


import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public interface UserDao {
    User findByUsername(String username);

    void save(User user);

    User findByEmail(String email);

    List findAllEmail();

    void deleteByEmail(String email);
    void deleteById(int uid);

//    String buyProductByID(int id, HttpServletRequest request);

    void updateUserProductList(User user, Product product);

    List<Product> productsList(HttpServletRequest request);

    void productsDeleteById(int pid, HttpServletRequest request);
}
