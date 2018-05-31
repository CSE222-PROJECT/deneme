

package Products;

import java.util.ArrayList;

public class Clothes extends Product implements Comparable<Clothes>{

    public ArrayList<String> features = new ArrayList<String>();

    //public String tagP1, tagP2, tagP3, tagP4;
    //String contentP1, contentP2, contentP3, contentP4;

    public Clothes() {}

    public Clothes(String name,double price, String size, String gender, String tag, String content, int numOfProduct){
        super(name,price,size, gender,tag,content,numOfProduct);
        addFeatures(name,price,size,gender,tag,content,numOfProduct);
    }

    /**
     * This method take all product's parameter and add features ArrayList
     * for other modules usage
     *
     * @param name Product 's name
     * @param price Product 's price
     * @param size Product 's size
     * @param gender Product 's gender
     * @param tag Product 's tag
     * @param content Product 's content
     * @param numOfProduct Product 's numOfProduct
     */
    protected void addFeatures(String name, double price, String size, String gender, String tag, String content, int numOfProduct) {
        features.add(name);
        features.add( String.valueOf(price) );
        features.add(size);
        features.add(gender);
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
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setNumberOfProduct(int number){this.numOfProduct=number;}
    public int getNumberOfProduct(){return numOfProduct;}

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(Clothes other)
    {
        return (Integer)Double.compare(this.getPrice(),other.getPrice());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
