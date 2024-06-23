package br.com.ace.dto;

import br.com.ace.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private double groosSalary;

    public User convert() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
