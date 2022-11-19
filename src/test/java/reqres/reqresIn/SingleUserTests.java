package reqres.reqresIn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reqres.models.SingleUserResponse;
import reqres.models.UserData;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static reqres.specs.SingleUserSpecs.*;


public class SingleUserTests {
    @Test
    @DisplayName("Поиск конкретного пользователя")
    public void singleUserSuccessfulTest() {
        int userId = 2;

        SingleUserResponse response = given()
                .spec(singleUserRequestSpec)
                .pathParam("userId", userId)
                .when()
                .get()
                .then()
                .spec(singleUserResponseSpec)
                .extract()
                .as(SingleUserResponse.class);

        UserData userData = response.getData();

        assertThat(userData.getId()).isEqualTo(userId);
        assertThat(userData.getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(userData.getFirstName()).isEqualTo("Janet");
        assertThat(userData.getLastName()).isEqualTo("Weaver");
        assertThat(userData.getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @ValueSource(strings = {"22", "one"})
    @ParameterizedTest
    public void singleUserNotFoundTest(String userId) {
        given()
                .spec(singleUserRequestSpec)
                .pathParam("userId", userId)
                .when()
                .get()
                .then()
                .spec(errorResponseSpec);
    }
}
