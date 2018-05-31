
package Products;
import java.util.ArrayList;

public class Nutrient extends Product implements Comparable<Nutrient>{
    public ArrayList<String> features = new ArrayList<String>();

    public static void main(String[] args){


    }
    public Nutrient() {}
    public Nutrient(String name, double price, String tag, String content,int numOfProduct){
        super(name,price,tag,content,numOfProduct);
        addFeatures(name,price,tag,content,numOfProduct);

    }

    /**
     * This method take all product's parameter and add features ArrayList
     * for other modules usage
     *
     * @param name Product 's name
     * @param price Product 's price
     * @param tag Product 's tag
     * @param content Product 's content
     * @param numOfProduct Product 's numOfProduct
     */
    private void addFeatures(String name, double price,String tag, String content, int numOfProduct) {
        features.add(name);
        features.add(String.valueOf(price));
        if (tagSplit()){
            features.add(tagP1);
            features.add(tagP2);
            features.add(tagP3);
            features.add(tagP4);
        }
        if (contentSplit()){
            features.add(contentP1);
            features.add(contentP2);
            features.add(contentP3);
            features.add(contentP4);
        }
        features.add(String.valueOf(numOfProduct));


    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumberOfProduct(int number){this.numOfProduct=number;}
    public int getNumberOfProduct(){return numOfProduct;}

    @Override
    public int compareTo(Nutrient other)
    {
        return (Integer)Double.compare(this.getPrice(),other.getPrice());
    }
}
