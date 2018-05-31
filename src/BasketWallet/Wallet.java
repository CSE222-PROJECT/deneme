package BasketWallet;

public class Wallet {
    /**
     * id of wallet
     */
    private int ID;
    /**
     * amount of wallet
     */
    private double amount;

    /**
     * no parameter constructor
     */
    public Wallet() {}

    /**
     * contructor with id and amount
     * @param ID of wallet
     * @param amount of wallet
     */
    public Wallet(int ID, double amount) {
        this.ID = ID;
        this.amount = amount;
    }

    /**
     * getter
     * @return id
     */
    public int getID() {
        return ID;
    }

    /**
     * setter
     * @param ID of wallet
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * getter
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * setter
     * @param amount of wallet
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * to string method
     * @return string
     */
    @Override
    public String toString() {
        return "BasketWallet.Wallet{" +
                "ID=" + ID +
                ", amount=" + amount +
                '}';
    }
}
