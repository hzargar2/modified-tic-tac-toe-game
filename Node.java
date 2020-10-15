//Node class stores an data with type Data. Node objects are linked together in a LinkedList object

public class Node {

    // Data to be stored in node
    private Data data;
    //Reference to next node in the linked list
    private Node next;

    // Constructor initializes Node object with parameter Data object
    public Node(Data data){
        this.data = data;
    }
    //Sets Data object of Node object
    public void setData(Data data) {
        this.data = data;
    }
    //Sets the reference to the next Node in the linked list
    public void setNext(Node next) {
        this.next = next;
    }
    //Returns Node's data attribute which is of type Data
    public Data getData() {
        return data;
    }
    //Returns the next Node referenced in the list
    public Node getNext() {
        return next;
    }
}


