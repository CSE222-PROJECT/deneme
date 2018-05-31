package BasketWallet;

import Products.Product;
import Products.ProductInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class Basket {


    protected ArrayList<Basket> basket= new ArrayList<>();

    protected String userName;
    protected Product userProduct = new Product();
    protected int ProductId;

    /**
     * default
     */
    public Basket() {
    }

    /**
     * constructor with 3 parameter
     */
    public Basket(String name, int id, Product p){
        this.userProduct=p;
        this.ProductId=id;
        this.userName=name;
    }

    public void setUserProduct(Product userProduct) {
        this.userProduct = userProduct;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Product getUserProduct() {

        return userProduct;
    }

    public int getProductId() {
        return ProductId;
    }

    public String getUserName() {
        return userName;
    }
    public String getDataFields(){
        //username;Id;ProductName;Productprice;ProductSize;ProductGender;ProductTag;ProductContent;
        return this.userName+";"+this.ProductId+";"+this.userProduct.getName()+";"+this.userProduct.getPrice()+";"
                +this.userProduct.getSize()+";"+this.userProduct.getGender()+";"+this.userProduct.getTag()+";"+
                this.userProduct.getContent()+";"+this.userProduct.getNumberOfProduct()+"\n";
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        Basket temp=(Basket)obj;
        return (this.userName==temp.userName && this.ProductId==temp.getProductId() && this.userProduct.getName()==temp.getUserProduct().getName() &&  this.userProduct.getTag()==temp.getUserProduct().getTag() && this.userProduct.getContent().equals(temp.getUserProduct().getContent()));
    }

    @Override
    public String toString() {
        return "UserName= "+userName +" ProductId= "+ProductId+">>>  "+userProduct+"\n";
    }

}
