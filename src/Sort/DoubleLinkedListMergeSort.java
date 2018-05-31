package Sort;
public class DoubleLinkedListMergeSort<E extends Comparable<E>> {

    private int counter ;
    protected Node<E> head;
    protected Node<E> tail;
    
    /**
     * Constructor
     * DoubleLinkedList
     */
    public DoubleLinkedListMergeSort(){
        counter=0;
        head = null;
        tail = null;
    }

    /**
     *  the merge sort method
     */
    public void mergeSort(){
        if(isEmpty())
            return ;
        head = mergeSort1(this.head);
        tail = findIndex(size()-1,head,0);
    }

    /**
     *  the helper method for merge sort
     * @param root the root node
     * @return Node
     */
    private Node<E> mergeSort1(Node<E> root){
        if (root == null || root.next == null) {
            return root;
        }
        Node<E> second = divide2(root);

        root =   mergeSort1(root);
        second = mergeSort1(second);
        return mergeSort2(root, second);
    }

    /**
     * the helper method for merge sort
     * @param first the list first part
     * @param second the list second part
     * @return Node
     */
    private Node<E> mergeSort2(Node<E> first ,Node<E> second){
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        if (first.data.compareTo(second.data ) < 0 ) {

            first.next = mergeSort2(first.next, second);
            first.next.prev = first;
            first.prev = null;
            return first;
        }else{
            second.next = mergeSort2(first, second.next);
            second.next.prev = second;
            second.prev = null;
            return second;
        }

    }

    /**
     *
     * @param root
     * @return
     */
    private Node<E> divide2(Node<E> root){
        Node<E> first = root, second = root;
        while (first.next != null && first.next.next != null) {
            first =  first.next.next;
            second = second.next;
        }
        Node<E> temp = second.next;
        second.next = null;
        return temp;
    }

    /**
     * getter for head
     * @return node
     */
    public Node<E> getHead() {
        return head;
    }
    /**
     * getter for tail
     * @return node
     */
    public Node<E> getTail() {
        return tail;
    }

    /**
     * The node inner class
     * @param <E> data type
     */
    private class Node<E extends Comparable<E>>  implements Comparable{
        E data ;
        Node next;
        Node prev;

        /**
         * Node constructor
         * @param data the data
         * @param prev the previous node
         */
        private Node(E data, Node prev) {
            this.data = data;
            this.next = null;
            this.prev = prev;
        }
        /**
         * Compare the data
         * @param o the given Node
         * @return Integer
         */
        @Override
        public int compareTo(Object o) {
            if(o==null)
                return 1;
            return data.compareTo((E) o);
        }

        /**
         *  the node data toString
         * @return String
         */
        @Override
        public String toString(){
            return data.toString();
        }
    }


    /**
     * Add Last
     * @param e element
     * @return boolean
     */
    public boolean add(E e){
        if(e==null)
            return false;
        if(head==null){
            head = new Node<>(e,null);
            tail = head;
        }else {

            tail.next = new Node(e,tail);
            tail=tail.next;
        }
        counter ++;
        return true;
    }

    /**
     *  Find Given elements node
     * @param element an element
     * @param root the head of the list
     * @return node
     */
    private Node<E> find(E element,Node<E> root){

        if(root==null)
            return null;
        if(root.data.equals(element)){
            return root;
        }
        return  find(element,root.next);
    }

    /**
     *  Find the given index's element node
     * @param index the index
     * @param root the head of the list
     * @param count the counter
     * @return searching index's node
     */
    private Node<E> findIndex(int index,Node<E> root,int count)
    {
        if(count > counter || root==null)
            return null;

        if(index==count)
            return root;
        return findIndex(index,root.next,++count);
    }

    /**
     * Remove given element to list
     * @param o the element
     * @return boolean
     */
    public boolean remove(Object o) {
        if(isEmpty())
            return false;

        Node<E> temp = find((E) o,head);
        if(temp==null)
            return false;
        if(temp.next!=null)
            temp.next.prev=temp.prev;
        if(temp.prev!=null)
            temp.prev.next=temp.next;
        counter--;
        return true;
    }

    /**
     * Size of list
     * @return
     */
    public int size() {
        return counter;
    }

    /**
     *  List is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return head==null;
    }

    /**
     *  The list contain given element
     * @param o the element
     * @return boolean
     */
    public boolean contains(Object o) {
        return find((E) o,head)!=null;
    }

    /**
     *  find given index data
     * @param index the index
     * @return the data
     */
    public E get(int index) {
        return findIndex(index,head,0).data;
    }

    /**
     * set new element to given index's node
     * @param index the index
     * @param element the new element
     * @return old element
     */
    public E set(int index, E element) {
        if(element==null)
            return null;
        E data = null;
        Node<E> temp = findIndex(index,head,0);
        data = temp.data;
        temp.data=element;
        return data;
    }

    /**
     * Clear the list
     */
    public void clear() {
        head = null;
        tail = null;
        counter=0;
    }

    /**
     *  get the all list data to string
     * @return string
     */
    @Override
    public String toString(){
        String str = "";
        Node<E> node = head;
        while (node != null) {
            str += node.data.toString() + " ";
            node = node.next;
        }
        return str;
    }

}
