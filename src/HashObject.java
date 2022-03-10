/**
 * Represents a HashObject to be stored in a HashTable.
 * Contains a keyObject, duplicate count, and probe count.
 * @param <T> Generic data type of keyObject
 */
public class HashObject<T>
{
    private T keyObject;            // The generic data to be stored in the HashObject, acts as a key object for hashing
    private int duplicateCount;     // The number of additional duplicates of the object



    private int probeCount;         // The number of probes required to store object in open addressed HashTable
    private boolean deleted;        // Whether this HashObject has been deleted or not

    /**
     * Default constructor of the HashObject class
     * @param keyObject the object to be stored in the HashObject
     */
    public HashObject (T keyObject)
    {
        this.keyObject = keyObject;
        duplicateCount = 0;
        probeCount = 0;
    }

    /**
     * Increments the HashObject's duplicate count by 1
     */
    public void incrementDuplicateCount()
    {
        duplicateCount++;
    }

    /**
     * Increments the HashObject's probe count by 1
     */
    public void incrementProbeCount()
    {
        probeCount++;
    }

    /**
     * equals - Checks whether the keyObject of each HashObject are equal
     * @param object - The object to compare to
     * @return True if equivalent, false otherwise
     */
    @Override
    public boolean equals(Object object)
    {
        return keyObject.equals(object);
    }

    /**
     * @return a string representation of the HashObject, consisting of they keyObject.toString, duplicate count, and probe count.
     */
    @Override
    public String toString()
    {
        return (keyObject.toString()) + " " + duplicateCount + " " + probeCount;
    }

    /**
     * Gets the hash code of the HashObject's key object.
     * @return The stored object's hash code
     */
    public int getKey()
    {
        return keyObject.hashCode();
    }

    /**
     * Gets the HashObject's key object.
     * @return the object stored in the HashObject
     */
    public T getKeyObject()
    {
        return keyObject;
    }

    /**
     * Gets if the object has the deleted flag
     * @return true if deleted
     */
    public boolean isDeleted()
    {
        return deleted;
    }

    /**
     * Gets the duplicate count
     * @return duplicate count
     */
    public int getDuplicateCount() {
        return duplicateCount;
    }

    /**
     * Gets the probe count
     * @return probe count
     */
    public int getProbeCount() {
        return probeCount;
    }
}
