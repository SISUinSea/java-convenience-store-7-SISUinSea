package store.controller;

import java.io.IOException;

import store.model.Discount.Discount;
import store.model.Product.ProductTable;
import store.model.Transaction.TransactionTable;
import store.utils.ErrorMessage;
import store.model.PurchaseRequest.PurchaseRequests;
import store.model.Receipt.Receipt;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createPurchaseRequests;
import static store.service.RequestProcessor.processRequests;
import static store.model.Discount.DiscountFactory.createMembershipDiscount;
import static store.view.OutputView.printProductTable;
import static store.view.OutputView.printReceipt;
import static store.view.InputView.askApplyMembershipDiscount;
import static store.view.InputView.askToContinuePurchase;
import static store.view.InputView.askUntilGetValidAnswer;

public class MainController {
    public static void run() {
        new ProductTable();
        purchaseUntilGetStopCommand();
    }

    public static void purchaseUntilGetStopCommand() {
        String answer;
        do {
            printProductTable();
            PurchaseRequests purchaseRequests = getPurchaseRequests();
            TransactionTable transactionTable = processRequests(purchaseRequests, now());
            Discount membershipDiscount = suggestMembershipDiscount();
            Receipt receipt = new Receipt(transactionTable, membershipDiscount);
            printReceipt(receipt);
            answer = (String) askUntilGetValidAnswer(() -> askToContinuePurchase());
        } while ((answer).equals("Y"));
    }

    public static PurchaseRequests getPurchaseRequests() {
        PurchaseRequests purchaseRequests = createPurchaseRequests();
        return purchaseRequests;
    }

    public static Discount suggestMembershipDiscount() {
        return (Discount) askUntilGetValidAnswer(() -> {
            String answer = askApplyMembershipDiscount();
            if (answer.equals("Y")) {
                return createMembershipDiscount();
            }
            if (answer.equals("N")) {
                return null;
            }
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_INPUT.getDescription());
        });
    }
}
