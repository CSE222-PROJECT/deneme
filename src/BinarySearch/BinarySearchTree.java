package BinarySearch;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> implements SearchTree<E> {

    private Node<E> root = null;
    private Node<E> parent = null;
    private boolean finder = false;
    private boolean addFlag = false;

    @Override
    public boolean add(E item) {
        if(item==null)
            return false;
        if(root==null){
            root = new Node<>(item);
            parent = root;
            return true;
        }
        addHelper(root, item);
        if(!addFlag)
            return false;
        addFlag=false;
        return true;
    }

    private void addHelper(Node<E> root,E item){
        if(addFlag)
            return;
        if(root.data.compareTo(item)>0){
            if(root.right==null) {
                root.right = new Node<>(item);
                addFlag=true;
                return;
            }else
                addHelper(root.right,item);
        }
        if(root.data.compareTo(item)<=0){

            if(root.left==null){
                root.left= new Node<>(item);
                addFlag=true;
                return;
            }else
                addHelper(root.left,item);
        }
    }

    @Override
    public boolean contains(E target) {
        finder=false;
        if(find(target)!=null)
            return true;
        return false;
    }

    @Override
    public E find(E target) {
        if(target==null)
            return null;
        Node<E> finding = null;
        findHelper(root,target, finding);
        if(finder)
            return target;
        finder = false;
        return null;
    }

    private void findHelper(Node<E> root,E item,Node<E>find){
        if(finder)
            return;
        if(root.data.compareTo(item)>0){

            if(root.data.equals(item)){
                parent=find;
                finder=true;
                return;
            }
            else
                findHelper(root.right,item,root);
        }
        if(root.data.compareTo(item)<=0){

            if(root.data.equals(item)){
                parent=find;
                finder=true;
                return;
            }
            else
                findHelper(root.left,item,root);
        }
    }

    @Override
    public E delete(E target) {
        if(target==null)
            return null;
        finder=false;

        Node<E> child  = null;
        Node<E> watch  = null;
        findHelper(this.root,target, watch);

        if(!finder)
            return null;
        finder =false;

        if(parent.left!=null)
            if(parent.left.data.equals(target))
                child=parent.left;
        if(parent.right!=null)
            if(parent.right.data.equals(target))
                child=parent.right;

        List<E> data = new ArrayList<>();
        preOrderTraverse(child,data);

        if(parent.left!=null)
            if(parent.left.data.equals(target))
                parent.left = null;
        if(parent.right!=null)
            if(parent.right.data.equals(target))
                parent.right = null;

        for (int i=1; i<data.size(); i++){
            add(data.get(i));
        }
        return target;
    }

    private void preOrderTraverse(Node<E> root,List<E> list){
        if(root!=null){
            list.add(root.data);
            preOrderTraverse(root.left, list);
            preOrderTraverse(root.right,list);
        }
    }
    @Override
    public boolean remove(E target) {
        if(delete(target)!=null)
            return true;
        return false;
    }
    /**
     * get tree and add to string
     * @return String
     */
    @Override
    public String toString(){
        String str = "";
        List<E> list = new ArrayList<>();
        preOrderTraverse(root,list);
        for (int i=0; i<list.size(); i++)
            str += list.get(i).toString()+" ";
        return str;
    }

    public ArrayList<E> getDescendingOrder(E uplimit){
        List<E> list = new ArrayList<>();
        ArrayList<E> list2 = new ArrayList<>();
        preOrderTraverse(root,list);
        for (int i=0; i<list.size(); i++)
            if(list.get(i).compareTo(uplimit)<=0)
                list2.add(list.get(i));
        return list2;
    }

}
