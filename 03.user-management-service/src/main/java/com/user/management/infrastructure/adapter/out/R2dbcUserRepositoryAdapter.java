package com.user.management.infrastructure.adapter.out;


import com.user.management.domian.model.UserProfile;
import com.user.management.domian.port.UserRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class R2dbcUserRepositoryAdapter implements UserRepositoryPort {

    private final UserR2dbcRepository userR2dbcRepository;

    @Override
    public Mono<UserProfile> save(UserProfile userProfile) {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userProfile.getEmail());
        userEntity.setFullName(userProfile.getFullName());
        userEntity.setEmailVerified(userProfile.isEmailVerified());

        return userR2dbcRepository.save(userEntity)
                .map(save -> {
                    userProfile.setId(save.getId());
                    return userProfile;
                });
    }

    @Override
    public Mono<UserProfile> findById(long id) {
        return userR2dbcRepository.findById(id)
                .map(entity -> {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setId(entity.getId());
                    userProfile.setFullName(entity.getFullName());
                    userProfile.setEmail(entity.getEmail());
                    userProfile.setEmailVerified(entity.isEmailVerified());
                    return userProfile;
                });
    }

    @Override
    public Mono<UserProfile> findByEmail(String email) {
        return userR2dbcRepository.findByEmail(email)
                .map(entity -> {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setId(entity.getId());
                    userProfile.setFullName(entity.getFullName());
                    userProfile.setEmail(entity.getEmail());
                    userProfile.setEmailVerified(entity.isEmailVerified());

                    return userProfile;
                });
    }

    @Override
    public Flux<UserProfile> findAll() {
        return userR2dbcRepository.findAll()
                .map(entity -> {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setId(entity.getId());
                    userProfile.setFullName(entity.getFullName());
                    userProfile.setEmail(entity.getEmail());
                    userProfile.setEmailVerified(entity.isEmailVerified());
                    return userProfile;
                });
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return userR2dbcRepository.deleteById(id);
    }
}
