package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.UserProfile;

import java.util.List;

@ApplicationScoped
public class UserProfileRepository implements PanacheRepository<UserProfile> {

    public UserProfile findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public UserProfile findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<UserProfile> findByNationality(String nationality) {
        return find("nationality", nationality).list();
    }

    public void deleteByUsername(String username) {
        delete("DELETE FROM userprofile WHERE username=?", username);
    }
}
