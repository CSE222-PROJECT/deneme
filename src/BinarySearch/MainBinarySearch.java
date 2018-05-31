package BinarySearch;

import java.util.ArrayList;

public class MainBinarySearch {

    public static void main(String[]a){

        BinarySearchTree<Integer> tree = new BinarySearchTree<>();


        tree.add(15);
        tree.add(17);
        tree.add(23);
        tree.add(23);
        tree.add(55);
        tree.add(36);
        tree.add(45);
        tree.add(12);

        ArrayList<Integer> list = tree.getDescendingOrder(24);

        for (int i=0; i<list.size(); i++)
            System.out.println(list.get(i).toString());

    }


}
