package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = findById(id);

        if (optionalUser.isPresent()) {
            User userToBeUpdated = optionalUser.get();

            userToBeUpdated.setFirstName(updatedUser.getFirstName());
            userToBeUpdated.setLastName(updatedUser.getLastName());
            userToBeUpdated.setEmail(updatedUser.getEmail());
            entityManager.merge(userToBeUpdated);
        }
    }


    @Override
    public void deleteById(Long id) {
        Optional<User> userById = findById(id);
        userById.ifPresent(user -> entityManager.remove(user));
    }
}
