public abstract class Payment {
    protected double amount;
    protected String payerName;
    protected String paymentId;
    protected String timestamp;

    public Payment(double amount, String payerName) {
        this.amount = amount;
        this.payerName = payerName;
        this.paymentId = generatePaymentId();
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPayerName() { return payerName; }
    public void setPayerName(String payerName) { this.payerName = payerName; }

    public String getPaymentId() { return paymentId; }
    public String getTimestamp() { return timestamp; }

    private String generatePaymentId() {
        return "PAY" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    // Abstraction: subclasses implement processPayment (runtime polymorphism)
    public abstract boolean processPayment();

    public void displayPaymentDetails() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Payer: " + payerName);
        System.out.println("Amount: GHS " + amount);
        System.out.println("Date: " + timestamp);
    }
}