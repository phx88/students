package vladimir.chugunov.List;

import java.util.Arrays;
import java.util.Iterator;

import lisa.kapitonova.Lists.Visitor;

public interface IMyList<V> {

	/**
	 * Returns the number of elements in this list. If this list contains
	 * more than <tt>Integer.MAX_VALUE</tt> elements, returns <tt>Integer.MAX_VALUE</tt>.
	 * 
	 * @return the number of elements in this list
	 */
	public int size();

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * 
	 * @return <tt>true</tt> if this list contains no elements
	 */
	public boolean isEmpty();

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * More formally, returns <tt>true</tt> if and only if this list contains
	 * at least one element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * 
	 * @param o
	 *            element whose presence in this list is to be tested
	 * @return <tt>true</tt> if this list contains the specified element
	 * @throws ClassCastException
	 *             if the type of the specified element
	 *             is incompatible with this list
	 *             (<a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if the specified element is null and this
	 *             list does not permit null elements
	 *             (<a href="Collection.html#optional-restrictions">optional</a>)
	 */
	public boolean contains(Object value);

	/**
	 * @return default iterator; does not support {@link Iterator#remove()}
	 */
	public Iterator<V> getIterator();

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element).
	 * 
	 * <p>
	 * The returned array will be "safe" in that no references to it are maintained by this list.
	 * (In other words, this method must allocate a new array even if this list is backed by an
	 * array). The caller is thus free to modify the returned array.
	 * 
	 * <p>
	 * This method acts as bridge between array-based and collection-based APIs.
	 * 
	 * @return an array containing all of the elements in this list in proper
	 *         sequence
	 * @see Arrays#asList(Object[])
	 */
	public Object[] toArray();

	/**
	 * Returns an array containing all of the elements in this list in
	 * proper sequence (from first to last element); the runtime type of
	 * the returned array is that of the specified array. If the list fits
	 * in the specified array, it is returned therein. Otherwise, a new
	 * array is allocated with the runtime type of the specified array and
	 * the size of this list.
	 * 
	 * <p>
	 * If the list fits in the specified array with room to spare (i.e., the array has more elements
	 * than the list), the element in the array immediately following the end of the list is set to
	 * <tt>null</tt>. (This is useful in determining the length of the list <i>only</i> if the
	 * caller knows that the list does not contain any null elements.)
	 * 
	 * <p>
	 * Like the {@link #toArray()} method, this method acts as bridge between array-based and
	 * collection-based APIs. Further, this method allows precise control over the runtime type of
	 * the output array, and may, under certain circumstances, be used to save allocation costs.
	 * 
	 * <p>
	 * Suppose <tt>x</tt> is a list known to contain only strings. The following code can be used to
	 * dump the list into a newly allocated array of <tt>String</tt>:
	 * 
	 * <pre>
	 * 
	 * String[]	y	= x.toArray(new String[0]);
	 * </pre>
	 * 
	 * Note that <tt>toArray(new Object[0])</tt> is identical in function to <tt>toArray()</tt>.
	 * 
	 * @param a
	 *            the array into which the elements of this list are to
	 *            be stored, if it is big enough; otherwise, a new array of the
	 *            same runtime type is allocated for this purpose.
	 * @return an array containing the elements of this list
	 * @throws ArrayStoreException
	 *             if the runtime type of the specified array
	 *             is not a supertype of the runtime type of every element in
	 *             this list
	 * @throws NullPointerException
	 *             if the specified array is null
	 */
	public <T> T[] toArray(T[] array);

	/**
	 * Adds values to end of the list preserving the order
	 * 
	 * @param values
	 *            some objects to store
	 */
	public abstract void addItemsToEnd(V... values);

	/**
	 * Adds values to head of the list preserving the order
	 * 
	 * @param values
	 */
	public abstract void addItemsToHead(V... values);

	/**
	 * Removes first element
	 * 
	 * @return removed value
	 */
	public abstract V removeFirst();

	/**
	 * Removes last element
	 * 
	 * @return removed value
	 */
	public abstract V removeLast();

	/**
	 * Removes all of the elements from this list (optional operation).
	 * The list will be empty after this call returns.
	 * 
	 * @throws UnsupportedOperationException
	 *             if the <tt>clear</tt> operation
	 *             is not supported by this list
	 */
	public void clear();

	/**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    V get(int index);
	
	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element (optional operation).
	 * 
	 * @param index
	 *            index of the element to replace
	 * @param element
	 *            element to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws UnsupportedOperationException
	 *             if the <tt>set</tt> operation
	 *             is not supported by this list
	 * @throws ClassCastException
	 *             if the class of the specified element
	 *             prevents it from being added to this list
	 * @throws NullPointerException
	 *             if the specified element is null and
	 *             this list does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified
	 *             element prevents it from being added to this list
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range
	 *             (<tt>index &lt; 0 || index &gt;= size()</tt>)
	 */
	public V set(int index, V value);

	/**
	 * Removes the element at the specified position in this list (optional
	 * operation). Shifts any subsequent elements to the left (subtracts one
	 * from their indices). Returns the element that was removed from the
	 * list.
	 * 
	 * @param index
	 *            the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws UnsupportedOperationException
	 *             if the <tt>remove</tt> operation
	 *             is not supported by this list
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range
	 *             (<tt>index &lt; 0 || index &gt;= size()</tt>)
	 */
	public V remove(int index);

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the lowest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 * 
	 * @param o
	 *            element to search for
	 * @return the index of the first occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 * @throws ClassCastException
	 *             if the type of the specified element
	 *             is incompatible with this list
	 *             (<a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if the specified element is null and this
	 *             list does not permit null elements
	 *             (<a href="Collection.html#optional-restrictions">optional</a>)
	 */
	public int indexOf(Object value);

}