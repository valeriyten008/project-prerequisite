package web.dao;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    void updateUser(Long id,User updatedUser);
    void deleteById(Long id);
}
