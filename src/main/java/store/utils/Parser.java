package store.utils;

import java.util.Arrays;
import java.util.List;

public class Parser {
    public static List<String> removeHeader(List<String> productStrings) {
        productStrings.remove(0);
        return productStrings;
    }

    public static String parsePromotionName(String promotionName) {
        if (promotionName.equals("null")) {
            return null;
        }
        return promotionName;
    }

    public static String removeSquareBrakets(String wrappedString) {
        return wrappedString.substring(1, wrappedString.length() - 1);
    }

    public static String validateRequestsSyntax(String requestsLine) {
        if (requestsLine.contains("--") || requestsLine.contains(",,")) {
            throw new IllegalArgumentException();
        }
        Arrays.stream(requestsLine.split(",", -1)).forEach(Parser::validateSingleRequestSyntax);
        return requestsLine;
    }

    private static void validateSingleRequestSyntax(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException();
        }
        if (!requestLine.startsWith("[") || !requestLine.endsWith("]")) {
            throw new IllegalArgumentException();
        }
        if (!requestLine.contains("-")) {
            throw new IllegalArgumentException();
        }
        requestLine = removeSquareBrakets(requestLine);
        String[] splittedRequestLine = requestLine.split("-", -1);
        if (splittedRequestLine.length != 2) {
            throw new IllegalArgumentException();
        }
        for (String string : splittedRequestLine) {
            if (string.length() == 0 || string == null) {
                throw new IllegalArgumentException();
            }
        }
    }
}
