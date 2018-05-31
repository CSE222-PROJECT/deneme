package Products;

import java.util.HashMap;

public class Product implements ProductInterface {

    public String name;
    public double price;
    public String size;
    public String gender;
    public String tag;
    public String content;
    public int numOfProduct;

    public String[] tagParts;
    public  String[] contentParts;
    public String tagP1, tagP2, tagP3, tagP4;
    public String contentP1, contentP2, contentP3, contentP4;

    public Product(){

    }
    /**
     * For Nutrients objects constructor
     *
     * @param name Products.Product's name
     * @param price Products.Product's price
     * @param tag Products.Product's tag
     * @param content Products.Product's content
     * @param numOfProduct Products.Product's numOfProduct
     */
    public Product(String name, double price, String tag, String content, int numOfProduct){
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.content = content;
        this.numOfProduct = numOfProduct;
    }
    /**
     * For Products.Clothes objects constructor
     *
     * @param name Products.Product's name
     * @param price Products.Product's price
     * @param size Products.Product's size
     * @param gender Products.Product's gender
     * @param tag Products.Product's tag
     * @param content Products.Product's content
     * @param numOfProduct Products.Product's numOfProduct
     */
    public Product(String name, double price,String size, String gender, String tag, String content, int numOfProduct){
        this.name = name;
        this.price = price;
        this.size= size;
        this.gender = gender;
        this.tag = tag;
        this.content = content;
        this.numOfProduct = numOfProduct;
    }
    /**
     * For Products.Cosmetics objects constructor
     *
     * @param name Products.Product's name
     * @param price Products.Product's price
     * @param gender Products.Product's gender
     * @param tag Products.Product's tag
     * @param content Products.Product's content
     * @param numOfProduct Products.Product's numOfProduct
     */
    public Product(String name, double price, String gender, String tag, String content, int numOfProduct) {
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.tag = tag;
        this.content = content;
        this.numOfProduct = numOfProduct;
    }
    /**
     * This method product's tag split piece
     *
     * @return true if piece size 4, if not return false
     */
    public boolean tagSplit(){
        if (splitSize(tag) ==4){
            tagParts = tag.split(",");
            tagP1 = tagParts[0];
            tagP2 = tagParts[1];
            tagP3 = tagParts[2];
            tagP4 = tagParts[3];
        }
        if (splitSize(tag) < 4){
            System.out.println("Eksik etiket inputu girdiniz, 4 etiket inputu giriniz");
            return false;
        }
        return true;
    }
    /**
     * This method product's content split piece
     *
     * @return true if piece size 4, if not return false
     */
    public boolean contentSplit(){
        if (splitSize(content) ==4){
            contentParts = content.split(",");
            contentP1 = contentParts[0];
            contentP2 = contentParts[1];
            contentP3 = contentParts[2];
            contentP4 = contentParts[3];
        }
        if (splitSize(content) <4){
            System.out.println("Eksik icerik inputu girdiniz, 4 icerik inputu giriniz");
            return false;
        }
        return true;
    }
    /**
     * This method takes string parameter to split
     *
     * @param str Split str
     * @return piece size
     */
    public int splitSize(String str){
        String[] strParts = str.split(",");
        return strParts.length;
    }
    @Override
    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public int getNumOfProduct() {
        return numOfProduct;
    }

    public String getSize() {
        return size;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(double price) {
        this.price= price;
    }

    @Override
    public void setNumberOfProduct(int number) {
        this.numOfProduct = number;
    }

    @Override
    public int getNumberOfProduct() {
        return numOfProduct;
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
        Product product= (Product)obj;
        return ((product.content==this.content) && product.tag==this.tag && product.gender==this.gender &&
                product.price==this.price && product.name==this.name && product.size==this.size);
    }

    @Override
    public String toString() {
        String str;
        if (getSize() == null && getGender()!= null){
            str = name+", "+price+", "+gender+", "+tag+", "+content+", "+numOfProduct;
        }
        else if (getGender() == null && getSize() == null){
            str = name+", "+price+", "+tag+", "+content+", "+numOfProduct;
        }
        else{
            str =name+ ","+ price+ ","+ size+ ","+ gender+","+ tag+","+ content+ ", "+ numOfProduct;
        }
        return str;
    }

}
