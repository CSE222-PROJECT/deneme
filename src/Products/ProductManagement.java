package Products;
import Panels.*;
import Users.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductManagement {

    public ArrayList<Product> allProducts= new ArrayList<>();
    public ArrayList<Clothes> ClothesList = new ArrayList<>();
    public ArrayList<Nutrient> NutrientList = new ArrayList<>();
    public ArrayList<Cosmetics> CosmeticsList = new ArrayList<>();

    private User currentGuest=new Company();

    public ProductManagement(){
        
        readToCsv();
       
        
    }
    public ProductManagement(User company){
        currentGuest=company;
        readToCsv();
        menuForProductManagement();

    }

    /**
     * This method create for interface user
     */

    public void menuForProductManagement(){

        System.out.println("## ORGANICS ' E HOSGELDINIZ "+ currentGuest.getName() + " ##");

        System.out.println("##############################");
        System.out.println("#        GIRIS PANELI        #");
        System.out.println("##############################");
        System.out.println("# 1-Urun ekle                #");
        System.out.println("# 2-Urunleri Goster          #");
        System.out.println("# 3-Urun sil                 #");
        System.out.println("# 4-Cıkıs                    #");
        System.out.println("##############################");

        Scanner scanner = new Scanner(System.in);
        int choice;
        int indexDelete;
        choice = scanner.nextInt();
        while(choice!=4){
            if(choice == 1){
                addProduct();
            }
            else if(choice ==2){
                printAllProduct();
            }
            else if(choice ==3){
                printAllProduct();
                System.out.print("Urunu silmek icin index' i giriniz -->");
                indexDelete = scanner.nextInt();
                deleteProduct(indexDelete);
                printAllProduct();

            }
            System.out.print("\n(1: Urun ekle)\n"+ "(2: Urunleri Goster)\n" +"(3: Urun Sil)\n" +
                    "(4: Cıkıs) -->");
            System.out.println("\nDevam etmek icin seciminizi giriniz :\n");
            choice = scanner.nextInt();
        }


    }
    /**
     * This method adding product for product type and information
     * Add product's ArrayLists and allProduct list
     * After addition write to Products.csv file
     *
     * @return allProduct
     */
    protected ArrayList<Product> addProduct() {
        int productType;
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n(1: Giyim)\n"+ "(2: Gıda&Beslenme)\n" +"(3: Kozmetik)\n");
        System.out.print("Lutfen urun tipini seciniz :\n");

        productType =scanner.nextInt();
        while (productType != 1 && productType != 2  && productType != 3 ) {

            System.out.println("Yanlis urun tipi secimi !\nLutfen tekrar deneyiniz : ");
            productType =scanner.nextInt();
        }
        scanner.nextLine();

        String name, size, gender, tag, content;
        double price;
        int numberOfProduct;
        if(productType == 1){

            System.out.print("Urun adini giriniz --> ");
            name = scanner.nextLine();
            System.out.print("Urun fiyatini giriniz --> ");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Urun bedenini giriniz --> ");
            size = scanner.nextLine();
            System.out.print("Urun cinsiyetini giriniz --> ");
            gender = scanner.nextLine();
            System.out.print("Urun etiketini giriniz --> ");
            tag = scanner.nextLine();

            while (splitSize(tag) !=4){
                System.out.println("Eksik etiket inputu girdiniz, 4 etiket inputu giriniz");
                tag = scanner.nextLine();
            }

            System.out.print("Urun icerigini giriniz --> ");
            content = scanner.nextLine();
            while (splitSize(content) !=4) {
                System.out.println("Eksik icerik inputu girdiniz, 4 icerik inputu giriniz");
                content = scanner.nextLine();
            }

            System.out.print("Urun sayisini giriniz --> ");
            numberOfProduct = scanner.nextInt();

            Clothes clothes = new Clothes(name, price, size, gender, tag, content, numberOfProduct);

            ClothesList.add(clothes);
            allProducts.add(allProducts.size(),clothes);
        }
        else if(productType == 2){

            System.out.print("Urun adini giriniz --> ");
            name = scanner.nextLine();
            System.out.print("Urun fiyatini giriniz --> ");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Urun etiketini giriniz --> ");
            tag = scanner.nextLine();
            while (splitSize(tag) !=4){
                System.out.println("Eksik etiket inputu girdiniz, 4 etiket inputu giriniz");
                tag = scanner.nextLine();
            }

            System.out.print("Urun icerigini giriniz --> ");
            content = scanner.nextLine();
            while (splitSize(content) !=4){
                System.out.println("Eksik icerik inputu girdiniz, 4 icerik inputu giriniz");
                content = scanner.nextLine();
            }

            System.out.print("Urun sayisini giriniz --> ");
            numberOfProduct = scanner.nextInt();

            Nutrient nutrient = new Nutrient(name, price,tag,content, numberOfProduct);
            NutrientList.add(nutrient);
            allProducts.add(allProducts.size(),nutrient);
        }
        else if(productType == 3){

            System.out.print("Urun adini giriniz --> ");
            name = scanner.nextLine();
            System.out.print("Urun fiyatini giriniz --> ");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Urun cinsiyetini giriniz --> ");
            gender = scanner.nextLine();
            System.out.print("Urun etiketini giriniz --> ");
            tag = scanner.nextLine();
            while (splitSize(tag) !=4){
                System.out.println("Eksik etiket inputu girdiniz, 4 etiket inputu giriniz");
                tag = scanner.nextLine();
            }

            System.out.print("Urun icerigini giriniz --> ");
            content = scanner.nextLine();
            while (splitSize(content) !=4){
                System.out.println("Eksik icerik inputu girdiniz, 4 icerik inputu giriniz");
                content = scanner.nextLine();
            }

            System.out.print("Urun sayisini giriniz --> ");
            numberOfProduct = scanner.nextInt();

            Cosmetics cosmetics = new Cosmetics(name, price,gender,tag, content, numberOfProduct);
            CosmeticsList.add(cosmetics);
            allProducts.add(allProducts.size(),cosmetics);
        }
        writeToCsv();
        return  allProducts;
    }
    public int splitSize(String str){
        String[] strParts = str.split(",");
        return strParts.length;
    }

    /**
     * This method delete product in given index
     *
     * @param indexToDelete delete product index
     * @return allProduct
     * @throws IllegalArgumentException if index out of bound
     */
    public ArrayList<Product> deleteProduct(int indexToDelete) throws IllegalArgumentException{
        // index varsa sil degilse exception
        if (!(indexToDelete>=0 && indexToDelete< allProducts.size())){
            throw new IllegalArgumentException("Wrong index to delete product !");
        }
        if (indexToDelete>=0 && indexToDelete< allProducts.size()){
            allProducts.remove(indexToDelete);
            writeToCsv();
        }
        return allProducts;
    }

    /**
     * This method use after addition or delete operation, csv file update
     */
    public void writeToCsv()
    {
        //listeye  urunler eklendiginde cagrılacak (Products.csv)
        PrintWriter pWrite = null;
        try {
            pWrite = new PrintWriter(new File("Products.csv"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder  sBuilder = new StringBuilder();
        for (Product theProduct : allProducts ) {
            if (theProduct.getSize() == null && theProduct.getGender()!= null){
                sBuilder.append(theProduct.getName() + ";" + theProduct.getPrice() +  ";"
                        + theProduct.getGender() + ";" + theProduct.getTag() + ";"
                        + theProduct.getContent() + ";" + theProduct.getNumberOfProduct() + "\n");
            }
            else if(theProduct.getGender() == null && theProduct.getSize() == null){
                sBuilder.append( theProduct.getName() + ";" + theProduct.getPrice() +  ";"
                        + theProduct.getTag() + ";"+
                        theProduct.getContent() + ";" + theProduct.getNumberOfProduct() + "\n");
            }
            else {
                sBuilder.append( theProduct.getName() + ";" + theProduct.getPrice() + ";" + theProduct.getSize() + ";"
                        + (theProduct).getGender() + ";" + theProduct.getTag() + ";"
                        + theProduct.getContent() + ";" + theProduct.getNumberOfProduct() + "\n");
            }

        }
        pWrite.write(sBuilder.toString());
        pWrite.flush();
        pWrite.close();
    }

    /**
     * This method use read Products.csv file
     */
    public void readToCsv()  {

        String seperator = ";";

        try (BufferedReader reader = new BufferedReader(
                new FileReader("Products.csv"))){

        String line = null;
        for(int i=1; (line = reader.readLine()) != null; i++) {
            String[] fields = line.split(seperator, -1);

            // For nutrients objects to read
            if(fields.length ==5){
                Nutrient object= new Nutrient();
                object.name = fields[0];
                object.price = Double.parseDouble(fields[1]);
                object.tag=fields[2];
                object.content = fields[3];
                object.numOfProduct = Integer.parseInt(fields[4]);
                //object.addFeatures(object.name,object.price,object.size,object.gender,object.tag,object.content,object.numOfProduct);
                NutrientList.add(object);
                allProducts.add(object);
            }
            // For Cosmetics objects to read
            if (fields.length==6){
                Cosmetics object2=new Cosmetics();
                object2.name = fields[0];
                object2.price = Double.parseDouble(fields[1]);
                object2.gender = fields[2];
                object2.tag=fields[3];
                object2.content = fields[4];
                object2.numOfProduct = Integer.parseInt(fields[5]);
                //object2.addFeatures(object2.name,object2.price,object2.size,object2.gender,object2.tag,object2.content,object2.numOfProduct);
                CosmeticsList.add(object2);
                allProducts.add(object2);
            }
            // For clothes objects to read
            if(fields.length ==7){

                Clothes object3=new Clothes();
                object3.name = fields[0];
                object3.price = Double.parseDouble(fields[1]);
                object3.size= fields[2];
                object3.tag=fields[4];
                object3.content = fields[5];
                object3.numOfProduct = Integer.parseInt(fields[6]);
                object3.addFeatures(object3.name,object3.price,object3.size,"E",object3.tag,object3.content,object3.numOfProduct);
                ClothesList.add(object3);
                allProducts.add(object3);
            }

        }}
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printAllProduct(){
        System.out.println("## Urun listesi ##");
        for (int i=0; i<allProducts.size(); i++){
            System.out.println("Index "+i+ ": "+ allProducts.get(i).toString());
        }
    }

    /**
     * return index of the given product
     * @param p to find
     * @return the index of the given product
     */
    public int getIndexOfProduct(Product p){
        for (int i = 0; i <allProducts.size() ; i++) {
            if(p.equals(allProducts.get(i))){
                return i ;
            }
        }
        return -1;
    }

}
