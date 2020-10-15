import java.util.LinkedList;

public class Dictionary implements DictionaryADT{

    //Array of linked lists that store Data objects for the hashtable
    private LinkedList<Data>[] hashtable;

    //Hashtable is empty when first initialized. Keeps track of the number of Data objects in the hashtable
    private int numDataItems = 0;

    //Size of the hashtable
    private final int M;
    //x value in hash function, must stay constant to ensure hash function is the same for all inputs
    private final int x = 41;

    public Dictionary(int size){

        // Stores size of hashtable in a class variable for usage across methods
        this.M = size;

        // Constructs a 1-dimensional array of LinkedList objects with size -> int size for a hashtable with separate-chaining.
        //LinkedList takes Data objects.
        this.hashtable = new LinkedList[size];

        //initialize array elements to objects of LinkedList storing Data objects
        for (int i=0; i<size; i++)
            hashtable[i]= new LinkedList<Data>();
    }

    public int put(Data record) throws DuplicatedKeyException {

        // Assumes collision has already occurred. This is the case even if a DuplicatedKeyException is thrown.
        // Function changes it to 0 later if there is no collision.
        int return_val = 1;

        try {

            // Checks to see if record with the same key already exists in the hashtable
            Data found_record = get(record.getKey());

            // If the record found is not null, then it exists and we throw the DuplicatedKeyException. Otherwise, we
            // continue.
            if (found_record != null){
                throw new DuplicatedKeyException();
            }

            int pos = polynomialHashFunction(record.getKey());

            //Gets linked list at that index in the hashtable
            LinkedList<Data> linkedList = this.hashtable[pos];

            // Checks to see if the linked list is empty. If so, we add the record to the linked list, increment the
            // counter for the number of Data objects in the hashtable, and set return_val to 0 to indicate no collision
            // has occurred.
            if (linkedList.size() == 0) {
                linkedList.add(record);
                this.numDataItems+=1;
                return_val = 0;
            }

            // There are already elements in the linked list and we have a collision. Simply add the record,
            // increment the counter for the number of Data objects in the hashtable, and set return_val to 1 to indicate
            // a collision has occurred.
            else {
                linkedList.add(record);
                this.numDataItems+=1;
                return_val = 1;

            }

        }
        // Catches DuplicatedKeyException
        catch (DuplicatedKeyException e){
            System.out.println(e.getMessage());
        }

        // Returns return_val. If 1, then collision has occurred
        return return_val;

    }

    public void remove(String key) throws InexistentKeyException {

        try{
            int pos = polynomialHashFunction(key);

            //Gets linked list at that index in the hashtable
            LinkedList<Data> linkedList = this.hashtable[pos];

            // Linked list is empty so key doesn't exist and exception is thrown.
            if (linkedList.size() == 0) {
                throw new InexistentKeyException();
            }

            //Otherwise, iterates through Data objects in the linked list and removes any Data object that has the same
            // key as the parameter key
            else{

                for (Data record : linkedList) {
                    if (record.getKey().equals(key)) {

                        // Removes the Data object record and returns to break out of the function
                        linkedList.remove(record);
                        return;
                    }
                }
            }

            // No records were removed prior for the return statement to be executed and for it to not reach here.
            // Therefore, we assume that since it reached here then no records were removed and the key doesn't exist.
            throw new InexistentKeyException();

        }
        catch (InexistentKeyException e){
            System.out.println(e.getMessage());
        }

    }

    public Data get(String key) {

        int pos = polynomialHashFunction(key);

        //Gets linked list at that index in the hashtable
        LinkedList<Data> linkedList = this.hashtable[pos];

        for (Data record : linkedList) {
            if (record.getKey().equals(key)) {
                // Returns the Data object with the same key as the parameter
                return record;
            }
        }

        // Return statement not executed prior so we assume no record was found and we return null.
        return null;
    }

    // Returns the number of Data objects stored in the hashtable
    public int numDataItems() {
        return this.numDataItems;
    }

    public int polynomialHashFunction(String key){

        //Gets the length of the string
        int key_length = key.length();
        //Casts first char of String key to int
        int v = (int)(key.charAt(0));

        //Iterates through String key starting from second character
        for (int i = 1; i<key_length; i++){

            //Polynomial hash function, Horner's Rule
            v = (v*this.x+(int)(key.charAt(i))) % this.M;
        }
        //Returns index in hashtable
        return v;
    }

}
