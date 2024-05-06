package utilities;

import java.util.Iterator;
import java.util.List;

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

    public static boolean isSortedNames(List<String> listOfStrings, boolean ascending) {
        if(listOfStrings.isEmpty() || listOfStrings.size() == 1) {
            return true;
        }

        Iterator<String> iter = listOfStrings.iterator();
        String current, previous = iter.next();

        while(iter.hasNext()) {
            current = iter.next();

            if(ascending) {
                if (previous.compareTo(current) > 0) {
                    return false;
                }
            }
            else {
                if (previous.compareTo(current) < 0) {
                    return false;
                }
            }

            previous = current;
        }

        return true;
    }

    public static boolean isSortedPrices(List<Float> listOfFloats, boolean ascending) {
        for (int i = 0; i < listOfFloats.size() - 1; i++) {
            if (ascending) {
                if (listOfFloats.get(i) > listOfFloats.get(i + 1)) {
                    return false;
                }
            }
            else {
                if (listOfFloats.get(i) < listOfFloats.get(i + 1)) {
                    return false;
                }
            }
        }

        return true;
    }
}
