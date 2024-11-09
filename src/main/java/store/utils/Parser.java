package store.utils;

import java.util.Arrays;
import java.util.HashSet;
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
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_REQUEST_SYNTAX.getDescription());
        }
        Arrays.stream(requestsLine.split(",", -1)).forEach(Parser::validateSingleRequestSyntax);
        validateNoDuplicateProductRequests(requestsLine.split(",", -1));
        return requestsLine;
    }

    private static void validateSingleRequestSyntax(String requestLine) {
        validateRequestNotNull(requestLine);
        validateRequestHasSquareBracket(requestLine);
        validateRequestHasDash(requestLine);
        requestLine = removeSquareBrakets(requestLine);
        String[] splittedRequestLine = requestLine.split("-", -1);
        validateRequestsHaveOnlyNameAndQuantity(splittedRequestLine);
        validateParsedRequestsNotNull(splittedRequestLine);
        validateRequestQuantityIsNotZero(splittedRequestLine);
    }

    private static void validateRequestNotNull(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_REQUEST_SYNTAX.getDescription());
        }
    }

    private static void validateRequestHasSquareBracket(String requestLine) {
        if (!requestLine.startsWith("[") || !requestLine.endsWith("]")) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_REQUEST_SYNTAX.getDescription());
        }
    }

    private static void validateRequestHasDash(String requestLine) {
        if (!requestLine.contains("-")) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_REQUEST_SYNTAX.getDescription());
        }
    }

    private static void validateRequestsHaveOnlyNameAndQuantity(String[] splittedRequestLine) {
        if (splittedRequestLine.length != 2) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_REQUEST_SYNTAX.getDescription());
        }
    }

    private static void validateParsedRequestsNotNull(String[] splittedRequestLine) {
        for (String string : splittedRequestLine) {
            if (string.length() == 0 || string == null) {
                throw new IllegalArgumentException(ErrorMessage.ILLEGAL_REQUEST_SYNTAX.getDescription());
            }
        }
    }

    private static void validateNoDuplicateProductRequests(String[] splittedRequestLine) {
        List<String> productNames = Arrays.stream(splittedRequestLine).map(s -> s.split("-")[0]).toList();
        if (splittedRequestLine.length != new HashSet<String>(productNames).size()) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_INPUT.getDescription());
        }
    }

    private static void validateRequestQuantityIsNotZero(String[] splittedRequestLine) {
        Integer quantity;
        try {
            quantity = Integer.parseInt(splittedRequestLine[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_INPUT.getDescription());
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_INPUT.getDescription());
        }
    }

    public static void validateYNAnswer(String answer) {
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_INPUT.getDescription());
        }
    }
}
