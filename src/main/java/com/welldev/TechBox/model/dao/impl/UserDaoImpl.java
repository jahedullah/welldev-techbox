package com.welldev.TechBox.model.dao.impl;

import com.welldev.TechBox.model.dao.ProductDao;
import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.entity.User;
import com.welldev.TechBox.model.service.JwtService;
import com.welldev.TechBox.utils.HibernateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final ProductDao productDao;

    private final JwtService jwtService;

    public User findByUsername(String Username) {

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.get(User.class, Username);
        session.getTransaction().commit();
        session.close();

        return user;

    }

    @Override
    public void save(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public User findByEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from User where email = :e";
        Query q = session.createQuery(query);
        q.setParameter("e", email);
        User user = (User) q.uniqueResult();
        session.getTransaction().commit();
        session.close();

        return user;

    }

    @Override
    public List findAllEmail() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "select email from User";
        Query q = session.createQuery(query);
        ArrayList<String> emailList = (ArrayList<String>) q.list();
        session.getTransaction().commit();
        session.close();

        return emailList;
    }

    @Override
    public void deleteByEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        User user = findByEmail(email);
        int userId = user.getId();
        User userToDelete = session.get(User.class, userId);
        session.beginTransaction();
        session.delete(userToDelete);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteById(int uid) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from User where id = :id";
        Query q = session.createQuery(query);
        q.setParameter("id", uid);
        User user = (User) q.uniqueResult();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateUserProductList(User user, Product product) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        user.getProductList().add(product);
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

//    @Override
//    public String buyProductByID(int id, HttpServletRequest request) {
//        Session session = HibernateUtils.getSessionFactory().openSession();
//        Product product = productDao.getProduct(id);
//        String accessToken = request.getHeader("Authorization");
//        if (accessToken != null) {
//            String jwt = accessToken.substring(7);
//            String userEmail = jwtService.extractUsername(jwt);
//            session.beginTransaction();
//            User user = findByEmail(userEmail);
//            updateUserProductList(user, product);
//            productDao.updateProductCount(product);
//            productDao.updateProductUserList(product, user);
//            session.getTransaction().commit();
//            session.close();
//
//            return "Product " + id + " has been added to User.";
//        } else {
//            return "No Authentication Token Found. :(";
//        }
//    }

    public List<Product> productsList(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String jwt = accessToken.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = findByEmail(userEmail);
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        List<Product> productList = new ArrayList<>(user.getProductList());

        session.close();

        return productList;
    }

    public void productsDeleteById(int pid, HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String jwt = accessToken.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        User user = findByEmail(userEmail);
        List<Product> productList = user.getProductList().stream().filter(product -> product.getId() != pid).collect(Collectors.toList());

        user.setProductList(productList);
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();

    }

}
