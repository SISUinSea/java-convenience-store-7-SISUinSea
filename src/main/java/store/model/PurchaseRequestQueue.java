package store.model;

import java.util.Queue;

public class PurchaseRequestQueue {
    Queue<PurchaseRequest> queue;

    public PurchaseRequestQueue(Queue<PurchaseRequest> queue) {
        this.queue = queue;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public PurchaseRequest dequeue() {
        return queue.poll();
    }
}
