package vladimir.chugunov.List;

import interfaces.IVisitor;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class MyList<V> implements IMyList<V> {

	private int	size;

	/**
	 * An item of the list
	 * 
	 * @author Alpen Ditrix
	 * 
	 */
	private static class Node<V> {

		V		value;
		Node<V>	next;

		private void setAll(final V value, final Node<V> next) {
			this.value = value;
			this.next = next;
		}

		public Node(final V value) {
			setAll(value, null);
		}

		public Node(final V value, final Node<V> next) {
			setAll(value, next);
		}

		public Node() {
			setAll((V) null, null);
		}

		@Override
		public String toString() {
			return value.toString();
		}
	}

	/**
	 * Item which only stores link to the first item
	 */
	private final Node<V>	headItem;

	/**
	 * That's the last item of list. His {@link Node#next} is always equals {@code null}
	 */
	private Node<V>			lastItem;

	/**
	 * @return first item of list
	 */
	private Node<V> getFirst() {
		return headItem.next;
	}

	private void checkIndex(final int index) {
		if (index < 0 || index > size - 1) { throw new IndexOutOfBoundsException(String.format(
				"Index: %s Size: %s", index, size)); }
	}

	public MyList() {
		headItem = new Node<V>();
		lastItem = headItem;
	}

	public void addItemsToEnd(final V... values) {
		if (values.length == 0) { return; }
		for (final Object v : values) {
			final Node<V> newItem = new Node<V>((V) v);
			lastItem.next = newItem;
			lastItem = newItem;
			size++;
		}
	}

	public void addItemsToHead(final V... values) {
		for (int i = values.length - 1; i >= 0; i--) {
			// set before first
			headItem.next = new Node<V>(values[i], getFirst());
			size++;
		}
	}

	public V set(final int index, final V element) {
		checkIndex(index);
		Node<V> item = getFirst();
		for (int i = 0; i < index; i++) {
			item = item.next;
		}
		final V old = item.value;
		item.value = element;
		return old;
	}

	public V remove(final int index) {
		checkIndex(index);
		if (index == size - 1) {
			return removeLast();
		} else {
			Node<V> item = getFirst();
			for (int i = 0; i < index; i++) {
				item = item.next;
			}
			final V old = item.value;
			item.next = item.next.next;
			return old;
		}
	}

	public V removeFirst() {
		final V tmp = getFirst().value;
		headItem.next = getFirst().next;
		size--;
		return tmp;
	}

	public V removeLast() {
		if (getFirst().next == null) { return removeFirst(); }

		Node<V> runnerFirst = getFirst().next;
		Node<V> runnerSecond = getFirst();

		while (runnerFirst.next != null) {
			runnerFirst = runnerFirst.next;
			runnerSecond = runnerSecond.next;
		}
		runnerSecond.next = null;
		size--;
		return runnerFirst.value;
	}

	public void clear() {
		headItem.next = null;
		size = 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		Node<V> item = getFirst();
		sb.append("(");
		while (item.next != null) {
			sb.append(item.toString());
			sb.append(", ");
			item = item.next;
		}
		sb.append(item.toString());
		sb.append(")");
		return sb.toString();
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return headItem.next == null;
	}

	public boolean contains(final Object o) {
		return indexOf(o) > -1;
	}

	public Object[] toArray() {
		final Object[] result = new Object[size];
		int i = 0;
		for (Node<V> x = getFirst(); x != null; x = x.next) {
			result[i++] = x.value;
		}
		return result;
	}

	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		}
		int i = 0;
		final Object[] result = a;
		for (Node<V> x = getFirst(); x != null; x = x.next) {
			result[i++] = x.value;
		}

		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}

	public int indexOf(final Object o) {
		int index = 0;
		if (o == null) {
			for (Node<V> x = getFirst(); x.next != null; x = x.next) {
				if (x.value == null) {
					return index;
				} else {
					index++;
				}
			}
			return -1;
		} else {
			for (Node<V> x = getFirst(); x.next != null; x = x.next) {
				if (o.equals(x.value)) {
					return index;
				} else {
					index++;
				}
			}
			return -1;

		}
	}

	public Iterator<V> getIterator() {
		return new ListItr();
	}

	public void iterator(final IVisitor IVisitor) {
		for (Node<V> cur = getFirst(); cur != null; cur = cur.next) {
			IVisitor.visit(cur.value);
		}
	}

	public class Counter implements IVisitor {

		public int	count	= 0;

		public void visit(final Object item) {
			count++;
		}

		public int getAmount() {
			return count;
		}
	}

	private class ListItr implements Iterator<V> {

		Node<V>	next;

		public boolean hasNext() {
			return next != null;
		}

		public V next() {
			if (next == null) { throw new NoSuchElementException(); }

			final V value = next.value;
			next = next.next;
			return value;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public V get(int index) {
		checkIndex(index);
		Node<V> runner = getFirst();
		
		for(int i = 0; i<index; i++){
			runner = runner.next;
		}
		
		return runner.value;
	}

}