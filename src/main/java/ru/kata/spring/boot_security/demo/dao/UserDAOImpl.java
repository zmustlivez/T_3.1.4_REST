package ru.kata.spring.boot_security.demo.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO<User> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean addUser(User user) {
        if (loadUserByUsername(user.getUsername()) == null) {
            entityManager.persist(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User loadUserByUsername(String name) {
        try {
            return entityManager.createQuery("from User where username=:name", User.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }
}
