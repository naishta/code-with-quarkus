package org.acme.service;

import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.acme.domain.UserProfileProto;
import org.acme.domain.UserProfilesService;
import org.acme.model.UserProfile;
import org.acme.repository.UserProfileRepository;

import java.util.List;

@GrpcService
public class UserProfileService implements UserProfilesService {

    UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override // (6)
    public Uni<UserProfileProto.UserProfile> findByUsername(StringValue request) {
        UserProfile profile = userProfileRepository.findByUsername(request.getValue());
        return Uni.createFrom().item(() -> mapToUserProfile(profile));
    }

    @Override
    public Uni<UserProfileProto.UserProfile> findByEmail(StringValue request) {
        UserProfile profile = userProfileRepository.findByEmail(request.getValue());
        return Uni.createFrom().item(() -> mapToUserProfile(profile));
    }

    @Override
    public Uni<UserProfileProto.UserProfiles> findByNationality(StringValue request) {
        List<UserProfile> profiles = userProfileRepository.findByNationality(request.getValue());
        return Uni.createFrom().item(() -> mapToUserProfiles(profiles));
    }

    @Override
    public Uni<UserProfileProto.UserProfiles> findAll(Empty request) {
        List<UserProfile> profiles = userProfileRepository.findAll().list();
        return Uni.createFrom().item(() -> mapToUserProfiles(profiles));
    }

    @Override
    public Uni<UserProfileProto.UserProfile> addUserProfile(UserProfileProto.UserProfile request) {
        UserProfile entity = new UserProfile();
        entity.role = request.getRole();
        entity.nationality = request.getNationality();
        entity.phoneNumber = request.getPhoneNumber();
        entity.password = request.getPassword();
        entity.email = request.getEmail();
        entity.username = request.getUsername();
        entity.id = request.getId();
        userProfileRepository.persist(entity);
        return Uni.createFrom().item(() -> mapToUserProfile(entity));
    }

    @Override
    public Uni<Empty> deleteUserProfile(StringValue request) {
        return null;
    }

    private UserProfileProto.UserProfiles mapToUserProfiles(List<UserProfile> list) {
        UserProfileProto.UserProfiles.Builder builder =
                UserProfileProto.UserProfiles.newBuilder();
        list.forEach(p -> builder.addUserProfile(mapToUserProfile(p)));
        return builder.build();
    }

    private UserProfileProto.UserProfile mapToUserProfile(UserProfile entity) {
        UserProfileProto.UserProfile.Builder builder =
                UserProfileProto.UserProfile.newBuilder();
        if (entity != null) {
            return builder
                    .setEmail(entity.email)
                    .setNationality(entity.nationality)
                    .setPhoneNumber(entity.phoneNumber)
                    .setRole(entity.role)
                    .setUsername(entity.username)
                    .setPassword(entity.password)
                    .setId(entity.id)
                    .build();
        } else {
            return null;
        }
    }
}
