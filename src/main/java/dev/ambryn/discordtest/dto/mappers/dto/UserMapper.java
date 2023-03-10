package dev.ambryn.discordtest.dto.mappers.dto;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.UserCreateDTO;
import jakarta.enterprise.context.Dependent;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

@Dependent
public class UserMapper {
    public static User toUser(UserCreateDTO userDTO) {
        Pbkdf2PasswordHashImpl passwordHasher = new Pbkdf2PasswordHashImpl();
        String hashedPassword = passwordHasher.generate(userDTO.password().toCharArray());

        String email = userDTO.email();
        String lastname = userDTO.lastname();
        String firstname = userDTO.firstname();
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setLastname(lastname);
        user.setFirstname(firstname);
        return user;
    }

    public static UserGetDTO toDto(User user) {
        Long id = user.getId();
        String email = user.getEmail();
        String lastname = user.getLastname();
        String firstname = user.getFirstname();
        return new UserGetDTO(id, email, lastname, firstname);
    }
}
