class MobileMoneyPayment extends Payment {
    private String mobileNumber;
    private String transactionId;

    public MobileMoneyPayment(double amount, String payerName) {
        super(amount, payerName);
        this.transactionId = "MOMO" + System.currentTimeMillis();
    }

    @Override
    public boolean processPayment() {
        System.out.println("\n=== MOBILE MONEY PAYMENT ===");
        displayPaymentDetails();

        // Validation du numéro MoMo (10 chiffres, format ghanéen)
        while (true) {
            System.out.print("Enter mobile money number (10 digits, e.g., 0541234567): ");
            mobileNumber = Main.scanner.nextLine().replaceAll("\\s+", "");

            if (mobileNumber.matches("\\d{10}")) {
                this.mobileNumber = mobileNumber.substring(0, 3) + " " + mobileNumber.substring(3, 6) + " " + mobileNumber.substring(6);
                System.out.println("Mobile Number: " + this.mobileNumber);
                break;
            } else {
                System.out.println(" Invalid mobile money number! Must be exactly 10 digits (e.g., 0541234567).");
            }
        }

        // Validation du PIN (4 chiffres)
        String pin;
        while (true) {
            System.out.print("Enter MoMo PIN (4 digits): ");
            pin =Main.scanner.nextLine();

            if (pin.matches("\\d{4}")) {
                System.out.println("✓ PIN accepted");
                break;
            } else {
                System.out.println(" Invalid PIN! Must be exactly 4 digits.");
            }
        }

        // Simulation de la transaction MoMo
        System.out.println("\n Processing MoMo transaction...");
        System.out.println("Sending authorization request...");
        System.out.println(" Transaction approved with PIN");
        System.out.println(" Transaction ID: " + transactionId);
        System.out.println(" Mobile Money payment of GHS " + amount + " completed successfully!");

        return true;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }
}