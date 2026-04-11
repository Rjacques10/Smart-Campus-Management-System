class CashPayment extends Payment {
    private String receiptNumber;

    public CashPayment(double amount, String payerName) {
        super(amount, payerName);
        this.receiptNumber = "CASH" + System.currentTimeMillis();
    }

    @Override
    public boolean processPayment() {
        System.out.println("\n=== CASH PAYMENT ===");
        displayPaymentDetails();
        System.out.println(" Processing cash payment...");
        System.out.println("✓ Cash received and counted");
        System.out.println(" Receipt Number: " + receiptNumber);
        System.out.println("Cash payment of GHS " + amount + " completed successfully!");
        return true;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }
}