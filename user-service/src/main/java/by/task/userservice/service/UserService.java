package by.task.userservice.service;

import by.task.userservice.dto.UserRequestDTO;
import by.task.userservice.dto.UserResponseDTO;
import by.task.userservice.exception.service.EmptyUserListException;
import by.task.userservice.exception.service.InvalidUserException;
import by.task.userservice.exception.service.UserNotFoundException;
import by.task.userservice.mapper.UserMapper;
import by.task.userservice.model.User;
import by.task.userservice.repository.UserRepository;
import by.task.userservice.kafka.producer.UserEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserEventProducer userEventProducer;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
            UserRepository userRepository, UserMapper userMapper, UserEventProducer userEventProducer
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userEventProducer = userEventProducer;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        logger.info("Creating new user: {}", userDTO.getEmail());
        User user = userMapper.toEntity(userDTO);
        validateUser(user);
        User savedUser = userRepository.save(user);
        userEventProducer.sendUserCreatedEvent(userDTO.getEmail());
        return userMapper.toDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EmptyUserListException();
        }
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        logger.info("Updating user ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userMapper.updateEntityFromDTO(userDTO, existingUser);
        validateUser(existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        logger.info("Deleting user ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        String userEmail = user.getEmail();
        userRepository.deleteById(id);
        userEventProducer.sendUserDeletedEvent(userEmail);
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new InvalidUserException("User name is required");
        }
    }
}