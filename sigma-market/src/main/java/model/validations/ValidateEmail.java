package model.validations;

public class ValidateEmail {
    public static boolean Validate(String email){
        return !email.isEmpty() && !email.isBlank() && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
}