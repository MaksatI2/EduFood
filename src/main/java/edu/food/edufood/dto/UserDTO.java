package edu.food.edufood.dto;

import edu.food.edufood.model.AccountType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private AccountType accountType;
}
