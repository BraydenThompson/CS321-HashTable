public class HashTable<T>
{
    private HashObject[] table;     // The table in which to store HashObjects
    private boolean doubleHash;     // True if using double hashing, false if using linear hashing.
    private int size;               // The size of the table
    private int totalNumElements;   // The total number of filled elements in array.
    private long globalDuplicateCount; // The global duplicate count
    private int globalProbeCount;   // The global probe count (not counting duplicates)

    /**
     * Default constructor for a new HashTable object
     * @param size the capacity of the table
     * @param doubleHash the hashing mode - true for double hashing, false for linear hashing
     */
    public HashTable (int size, boolean doubleHash)
    {
        this.size = size;
        table = new HashObject[size];
        this.doubleHash = doubleHash;
        globalProbeCount = 0;
        totalNumElements = 0;
        globalDuplicateCount = 0;
    }

    /**
     * Inserts an object into the HashTable using either a double hashing probe sequence, or a linear
     * hashing probe sequence based on constructor input.
     * @param object the object to insert into the table.
     * @return the index inserted upon success, -1 if no available index was found.
     */
    public int insert (T object)
    {
        HashObject hashObject = new HashObject<T>(object);  // Create new hashObject
        int i = 0;                                          // Index to search through table
        boolean found = false;                              // Whether a valid position has been found
        int retVal = -1;                                    // The value to return
        int j = 0;                                          // The current probing sequence index

        while (i < table.length && !found)
        {
            if (doubleHash)
                j = doubleHashProbe(hashObject.getKey(), i);
            else
                j = linearProbe(hashObject.getKey(), i);

            hashObject.incrementProbeCount();                   // Increment probe count

            if ((table[j] == null) || (table[j].isDeleted()))   // Check if entry is empty
            {
                globalProbeCount += hashObject.getProbeCount(); // Add probes to global probe count
                totalNumElements++;
                table[j] = hashObject;
                retVal = j;
                found = true;
            }
            else if (hashObject.equals(table[j]))               // Check if duplicate
            {
                table[j].incrementDuplicateCount();
                globalDuplicateCount++;
                retVal = j;
                found = true;
            }
            i++;
        }
        //System.out.println(retVal); // TODO: TEST FOR DUPE COUNT, WORKS IN DEBUGGER, BREAKS WHEN RUNNING?
        return retVal;
    }

    public HashObject getObject(int i)
    {
        if (i < 0 || i > table.length)
        {
            throw new IndexOutOfBoundsException("Index out of bounds of table");
        }
        return table[i];
    }

    /**
     * Generates a linear probing sequence based on an objects hashCode and the index i
     * @param key the hash code of the object to generate a probing sequence for
     * @param i the index of the probing sequence to generate
     * @return the probing index at index i
     */
    private int linearProbe (int key, int i)
    {
        int h = positiveMod(key, table.length);
        return (h + i) % table.length;
    }

    /**
     * Generates a double hashed probing sequence based on an objects hashCode and the index i
     * @param key the hash code of the object to generate a probing sequence for
     * @param i the index of the probing sequence to generate
     * @return the probing index at index i
     */
    private int doubleHashProbe (int key, int i)
    {
        int h1 = positiveMod(key, table.length);
        int h2 = 1 + positiveMod(key, table.length - 2);
        return (h1 + (i * h2)) % table.length;
    }

    /**
     * Performs a mod operation that always returns positive integers
     * @param dividend
     * @param divisor
     * @return  The positive mod of the two inputs
     */
    private int positiveMod (int dividend, int divisor)
    {
        int value = dividend % divisor;
        if (value < 0)
        {
            value += divisor;
        }
        return value;
    }

    /**
     * Gets the total number of probes for every item in table
     * @return global probe count
     */
    public int getGlobalProbeCount()
    {
        return globalProbeCount;
    }

    /**
     * Returns the number of filled elements in table
     * @return totalNumElements
     */
    public int getNumElements()
    {
        return totalNumElements;
    }

    /**
     * Returns the number of duplicates in the table
     * @return globalDuplicateCount
     */
    public long getNumDuplicates()
    {
        return globalDuplicateCount;
    }

    /**
     * Returns the array of HashObjects that composes the HashTable
     * @return HashTable
     */
    public HashObject[] getTable()
    {
        return table;
    }

}
