package jbomb.common.exception;

public class NotAvailableId extends Exception {
    
    public NotAvailableId() {
        super("All IDs are used");
    }
}
