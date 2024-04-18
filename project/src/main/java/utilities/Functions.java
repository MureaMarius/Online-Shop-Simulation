package utilities;

public class Functions {
    public String createRandomString() {
        int stringLength = 10;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 97; i < 122 && stringLength != 0; i++) {
            stringBuilder.append((char) i);
            stringLength--;
        }

        return stringBuilder.toString();
    }
}
