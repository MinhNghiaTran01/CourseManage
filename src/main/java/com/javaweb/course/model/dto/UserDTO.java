package com.javaweb.course.model.dto;

import com.javaweb.course.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String sub;

    private List<String> roles;

    @Builder
    public UserDTO(Integer id, String username, String password, String sub, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sub = sub;
        this.roles = roles;
    }
}
