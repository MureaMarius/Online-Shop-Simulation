package utilities;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;

public class Accounts {

    private final List<String> usernames = new ArrayList<>();
    private final String password;

    public Accounts(){
        Dotenv dotenv = Dotenv.configure().load();

        for (int i = 1; i <= 6; i++) {
            String username = dotenv.get("USERNAME_" + i);
            usernames.add(username);
        }
        this.password = dotenv.get("PASSWORD");
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public String getPassword() {
        return password;
    }
}
