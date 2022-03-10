public class HashTable<T>
{
    private HashObject[] table;     // The table in which to store HashObjects
    private boolean doubleHash;     // True if using double hashing, false if using linear hashing.

    /**
     * Default constructor for a new HashTable object
     * @param size the capacity of the table
     * @param doubleHash the hashing mode - true for double hashing, false for linear hashing
     */
    public HashTable (int size, boolean doubleHash)
    {
        table = new HashObject[size];
        this.doubleHash = doubleHash;
    }
}
