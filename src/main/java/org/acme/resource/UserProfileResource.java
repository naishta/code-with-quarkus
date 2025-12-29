package org.acme.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.acme.model.UserProfile;
import org.acme.repository.UserProfileRepository;

import java.util.List;

@Path("/user-profiles")
public class UserProfileResource {

    UserProfileRepository userProfileRepository;

    public UserProfileResource(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GET
    public List<UserProfile> getAll() {
        return userProfileRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public UserProfile getOne(Long id) {
        return userProfileRepository.findById(id);
    }

    @POST
    @Transactional
    public UserProfile create(UserProfile userProfile) {
        userProfileRepository.persist(userProfile);
        return userProfile;
    }

    @PUT
    @Transactional
    public UserProfile update(UserProfile userProfile) {
        UserProfile up = userProfileRepository.findById(userProfile.id);
        up.role = userProfile.role;
        up.nationality = userProfile.nationality;
        up.phoneNumber = userProfile.phoneNumber;
        up.password = userProfile.password;
        userProfileRepository.persist(up);
        return userProfile;
    }

    @DELETE
    public void delete(UserProfile userProfile) {
        userProfileRepository.delete(userProfile);
    }

    @DELETE
    @Path("/{username}")
    public void deleteByUsername(String username) {
        userProfileRepository.deleteByUsername(username);
    }
}
