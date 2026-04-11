class CardPayment extends Payment {
    private String cardNumber;
    private String expiryDate;
    private String transactionId;

    public CardPayment(double amount, String payerName) {
        super(amount, payerName);
        this.transactionId = "CARD" + System.currentTimeMillis();
    }

    @Override
    public boolean processPayment() {
        System.out.println("\n=== CARD PAYMENT ===");
        displayPaymentDetails();

        // Validation du numéro de carte (16 chiffres)
        String cardNumber;
        while (true) {
            System.out.print("Enter card number (16 digits, e.g., 4846 8099 2628 5416): ");
            cardNumber = Main.scanner.nextLine().replaceAll("\\s+", "");

            if (cardNumber.matches("\\d{16}")) {
                this.cardNumber = cardNumber.substring(0, 4) + " " + cardNumber.substring(4, 8) + " " +
                        cardNumber.substring(8, 12) + " " + cardNumber.substring(12);
                System.out.println("Card Number: " + this.cardNumber);
                break;
            } else {
                System.out.println(" Invalid card number! Must be exactly 16 digits.");
            }
        }

        // Validation de la date d'expiration
        while (true) {
            System.out.print("Enter expiry date (MM/YY, e.g., 05/29): ");
            expiryDate = Main.scanner.nextLine();

            if (expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
                System.out.println("Expiry Date: " + expiryDate);
                break;
            } else {
                System.out.println(" Invalid expiry date! Use format MM/YY (e.g., 05/29).");
            }
        }

        // Validation du PIN carte (3 chiffres)
        String pin;
        while (true) {
            System.out.print("Enter card PIN (3 digits): ");
            pin =Main.scanner.nextLine();

            if (pin.matches("\\d{3}")) {
                System.out.println("✓ PIN accepted");
                break;
            } else {
                System.out.println(" Invalid PIN! Must be exactly 3 digits.");
            }
        }

        // Simulation de la transaction carte
        System.out.println("\n Processing card transaction...");
        System.out.println("Contacting Ecobank...");
        System.out.println(" Card validated");
        System.out.println(" PIN verified");
        System.out.println(" Authorization approved");
        System.out.println(" Transaction ID: " + transactionId);
        System.out.println(" Card payment of GHS " + amount + " completed successfully!");

        return true;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getTransactionId() {
        return transactionId;
    }
}