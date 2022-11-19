package reqres.models;

import lombok.Data;

@Data
public class RegistrationResponse {
    private String token;
    private Integer id;
}
