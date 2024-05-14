package test;

//instance variables of the SinglyLinkedList
public class SinglyLinkedList<E> {

 private Node<E> head;
 private Node<E> tail;
 public Node<E> curr; // Acess to current element
 private int size;

 public SinglyLinkedList() {

     head = null;
     tail = null;
     curr = null;
     size = 0;
 }
 // Remove all elements

 public void clear() {
     head = tail = null;
     size = 0;
 }
 // access methods

 public int size() {
     return size;
 }

 public boolean isEmpty() {
     return size == 0;
 }

 public E first() { // returns (but does not remove) the first element
     if (isEmpty()) {
         return null;
     }
     return head.getElement();
 }

 public E last() { // returns (but does not remove) the last element
     if (isEmpty()) {
         return null;
     }
     return tail.getElement();
 }

 // update methods
 public void addFirst(E e) { // adds element e to the front of the list
     head = new Node<>(e, head); // create and link a new node
     if (size == 0) {
         tail = head; // special case: new node becomes tail also
     }
     size++;
 }

 public void addLast(E e) { // adds element e to the end of the list
     Node<E> newest = new Node<>(e, null); // node will eventually be the tail
     if (isEmpty()) {
         head = newest; // special case: previously empty list
     } else {
         tail.setNext(newest); // new node after existing tail
     }
     tail = newest; // new node becomes the tail
     size++;
 }

 public E removeFirst() { // removes and returns the first element
     if (isEmpty()) {
         return null; // nothing to remove
     }
     E answer = head.getElement();
     head = head.getNext(); // will become null if list had only one node
     size--;
     if (size == 0) {
         tail = null; // special case as list is now empty
     }
     return answer;
 }

 // Add
 public void add(E element, int index) {
     if (index < 0 || index > size) {
         System.out.println("Out of bound!");
         return;
     }
     Node<E> newest = new Node<E>(element, null);
     if (index == 0) // add at front
     {
         newest.setNext(head);
         head = newest;
         if (tail == null) {
             tail = head;
         }
     } else // add the middle
     {
         curr = head;
         for (int i = 0; i < index - 1; i++) {
             curr = curr.getNext();
         }
         newest.setNext(curr.getNext());
         curr.setNext(newest);
         if (tail == curr) {
             tail = tail.getNext();
         }
     }
     size++;
 }

 // removing node at index i
 public E remove(int index) {// O(n)
     if (index < 0 || index >= size) {
         System.out.println("Out of bound!");
         return null;
     }
     E element;
     if (index == 0) // remove from front
     {
         element = head.getElement();
         head = head.getNext();
         if (head == null) {
             tail = null;
         }
     } else {
         curr = head;
         for (int i = 0; i < index - 1; i++) {
             curr = curr.getNext();
         }
         element = curr.getNext().getElement();
         if (tail == curr.getNext()) {
             tail = curr;
         }
         curr.setNext(curr.getNext().getNext());
     }

     size--;
     return element;
 }

 //
 public void moveToStart() {
     curr = head;
 }

 public void moveToEnd() {
     curr = tail;
 }

 //move the curr 
 public void next() {
     if (curr != tail) {
         curr = curr.getNext();
     }
 }

 public E getValue() {
     return curr.getElement();
 }

 // return the postion of current element
 public int CurrPos() {
     Node<E> temp = head;
     int i = 0;
     while (temp != curr) {
         temp = temp.getNext();
         i++;
     }
     return i;
 }

 // move curr to postion
 public void moveToPos(int pos) {
     if (pos < 0 || pos >= size) {
         System.out.println("Position out of range!");
     } else {
         curr = head;
         for (int i = 0; i < pos; i++) {
             curr = curr.getNext();
         }
     }
 }

 public void print() {
     curr = head;
     while (curr != null) {
         System.out.println(curr.getElement());
         curr = curr.getNext();
     }
 }



}