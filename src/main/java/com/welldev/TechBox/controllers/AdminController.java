package com.welldev.TechBox.controllers;

import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.string.ADMIN_URL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private UserDao userDao;

    @DeleteMapping(ADMIN_URL.USER_DELETE_BY_EMAIL)
    public ResponseEntity<HttpStatus> userDelete(@PathVariable int uid) {
        try {
            userDao.deleteById(uid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
