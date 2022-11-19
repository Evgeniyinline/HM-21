package reqres.reqresIn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static reqres.specs.UserListSpec.userListRequestSpec;
import static reqres.specs.UserListSpec.userListResponseSpec;


public class UserListTests {
    @Test
    @DisplayName("Проверка пользователя по почте")
    public void checkUserEmailsTest() {
        given()
                .spec(userListRequestSpec)
                .when()
                .get()
                .then()
                .spec(userListResponseSpec)
                .body("data.email",
                        everyItem(endsWith("@reqres.in")));
    }

    @Test
    @DisplayName("Проверка пользователя по аватарке")
    public void checkAvatarForUserTest() {
        given()
                .spec(userListRequestSpec)
                .queryParam("page", 2)
                .when()
                .get()
                .then()
                .spec(userListResponseSpec)
                .body("data.findAll{it.id == 10}.avatar",
                        hasItem("https://reqres.in/img/faces/10-image.jpg"));
    }
}
