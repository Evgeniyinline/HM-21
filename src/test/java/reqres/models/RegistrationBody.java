package reqres.models;

import lombok.Data;

@Data
public class RegistrationBody {
    private String email;
    private String password;
}
