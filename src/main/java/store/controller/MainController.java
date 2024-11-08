package store.controller;

import java.io.IOException;

import store.model.Discount.Discount;
import store.model.Product.ProductTable;
import store.model.Transaction.TransactionTable;
import store.utils.ErrorMessage;
import store.model.PurchaseRequest.PurchaseRequests;
import store.model.Receipt.Receipt;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.controller.SystemBootloader.bootProductTable;
import static store.controller.RequestProcessor.processRequests;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createPurchaseRequests;
import static store.model.Discount.DiscountFactory.createMembershipDiscount;
import static store.view.OutputView.printProductTable;
import static store.view.OutputView.printReceipt;
import static store.view.InputView.askApplyMembershipDiscount;
import static store.view.InputView.askToContinuePurchase;
import static store.view.InputView.askUntilGetValidAnswer;

public class MainController {
    public static void run() throws IOException {
        ProductTable productTable = bootProductTable();
        purchaseUntilGetStopCommand(productTable);
    }

    public static void purchaseUntilGetStopCommand(ProductTable productTable) {
        while (true) {
            printProductTable(productTable);
            PurchaseRequests purchaseRequests = getPurchaseRequests(productTable);
            TransactionTable transactionTable = processRequests(productTable, purchaseRequests, now());
            Discount membershipDiscount = suggestMembershipDiscount();
            Receipt receipt = new Receipt(transactionTable, membershipDiscount);
            printReceipt(receipt);
            if (askToContinuePurchase().equals("N")) {
                break;
            }
        }
    }

    public static PurchaseRequests getPurchaseRequests(ProductTable productTable) {
        PurchaseRequests purchaseRequests = createPurchaseRequests(productTable);
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
