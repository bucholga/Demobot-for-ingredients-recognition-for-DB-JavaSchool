package com.restriction.analyzer.dao;

import com.restriction.analyzer.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    SessionFactory sessionFactory; // здесь создается фабрика для подключения к базе. Она должна быть бином

    @Override
    @Transactional
    public Category getCategory(int categoryId) {
        Session session = sessionFactory.openSession();
        Category category = session.get(Category.class, categoryId);
        session.close();
        return category;
    }
}
