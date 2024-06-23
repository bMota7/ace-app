package br.com.ace.service;

import br.com.ace.dto.UserRequestDTO;
import br.com.ace.model.User;
import br.com.ace.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Cadastro de novo usuario
    public User toInsert(UserRequestDTO userDTO) {
        User user = userDTO.convert();
        return userRepository.save(user);
    }

    // Autenticacao do usuario
    public boolean authenticate(UserRequestDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        return user != null && user.getPassword().equals(userDTO.getPassword());
    }

    // Usuario consegue alterar o valor do salario bruto que ele recebe
    public User updateGrossSalary(int userId, double newGrossSalary) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setGroosSalary((newGrossSalary));
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + userId);
        }
    }

    // Busca o usuario pelo Id
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

    // Atualiza informacoes do usuario existente
    public User updateUser(int userId, UserRequestDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BeanUtils.copyProperties(userDTO, user);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + userId);
        }
    }

    // Usuario consegue ver informacoes pessoais cadastradas dele
    public User getUserDetails(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    }

}
