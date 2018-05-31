package Sort;

import Panels.*;

import java.util.ArrayList;

public class Sort<E extends Comparable<E> > {

    private ArrayList inc ;
    private ArrayList dec ;
    private DoubleLinkedListMergeSort<E> merge ;
    public Sort(){
        inc = new ArrayList();
        dec = new ArrayList();
        merge = new DoubleLinkedListMergeSort<E>();
    }

    public void addAll(KWPriorityQueue<E> list){
       for (int i=0; i<list.size(); ++i)
           merge.add(list.getTheData(i));

    }

    public ArrayList sortIncreasingOrder(){
        merge.mergeSort();
        for (int i =0; i<merge.size(); i++)
            inc.add(merge.get(i));
        return inc;
    }

    public ArrayList sortDecrasingOrder(){
        merge.mergeSort();
        for (int i =1; i<=merge.size(); i++)
            dec.add(merge.get(merge.size()-i));
        return dec;
    }

}
