package rest;

import lombok.Getter;
@Getter
public class UpdateUser {
    private User userNewValues;
    private User userToChange;

    public UpdateUser(User userNewValues, User userToChange) {
        this.userNewValues = userNewValues;
        this.userToChange = userToChange;
    }
}
