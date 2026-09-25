package com.agrismart.backend.service.impl;

import com.agrismart.backend.dto.user.UpdateUserRequest;
import com.agrismart.backend.dto.user.UserResponse;
import com.agrismart.backend.entity.User;
import com.agrismart.backend.exception.ResourceNotFoundException;
import com.agrismart.backend.mapper.UserMapper;
import com.agrismart.backend.repository.UserRepository;
import com.agrismart.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(UUID userId) {

        return getUserById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        )
                );

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateCurrentUser(
            UUID userId,
            UpdateUserRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        )
                );

        user.setFullName(request.fullName().trim());

        if (request.phone() == null ||
                request.phone().isBlank()) {

            user.setPhone(null);

        } else {

            user.setPhone(request.phone().trim());
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}
