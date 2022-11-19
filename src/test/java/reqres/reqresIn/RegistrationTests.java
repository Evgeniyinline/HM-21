package reqres.reqresIn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reqres.models.ErrorResponse;
import reqres.models.RegistrationBody;
import reqres.models.RegistrationResponse;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static reqres.specs.RegistrationSpecs.*;
import static reqres.specs.SingleUserSpecs.errorResponseSpec2;


public class RegistrationTests {
    @Test
    @DisplayName("Успешная регистрация")
    public void registrationSuccessfulTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setEmail("eve.holt@reqres.in");
        registration.setPassword("123");

        RegistrationResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .extract()
                .as(RegistrationResponse.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo(4);
    }

    @Test
    @DisplayName("Без ввода пароля")
    public void negativeMissingPasswordRegistrationTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setEmail("eve.holt@reqres.in");

        ErrorResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Без ввода почты")
    public void negativeMissingEmailRegistrationTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setPassword("123");

        ErrorResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }

    @Test
    @DisplayName("Не правильная почта")
    public void negativeWrongEmailRegistrationTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setEmail("test@test.io");
        registration.setPassword("123");

        ErrorResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Note: Only defined users succeed registration");
    }
}
