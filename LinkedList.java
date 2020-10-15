public class LinkedList{
    private Node head = null;

    public void insert(Data data){

        //Creates new Node object with parameter data and sets the next Node reference to null
        Node new_node = new Node(data);
        new_node.setNext(null);

        // If linked list is empty, set the new node as the head node (first node in the linked list)
        if (this.head == null){
            this.head = new_node;
        }
        //Otherwise, insert the new node at the end of the list
        else{
            //Find the last node by iterating through all nodes starting from the head node
            Node last_node = this.head;
            while (last_node.getNext() != null) {
                last_node = last_node.getNext();
            }
            // We arrive at a node which its next node reference is null, meaning we last_node is now the last
            // node in the linked list. Now set the the next_node reference that is null to the new_node Node object
            last_node.setNext(new_node);
        }
    }


}
