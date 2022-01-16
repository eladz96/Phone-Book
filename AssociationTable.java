import java.util.Iterator;
import java.util.TreeMap;

public class AssociationTable<K extends Comparable<K>, V> implements Iterable<K> {

	private TreeMap<K, V> table;

	// constructors
	public AssociationTable() {
		table = new TreeMap<K, V>();
	}

	public AssociationTable(K[] keyArr, V[] valArr) throws IllegalArgumentException {
		if (keyArr.length != valArr.length)//comparing the length
			throw new IllegalArgumentException("Key array and value array differ in size!");
		table = new TreeMap<K, V>();//creating the table
		for (int i = 0; i < keyArr.length; ++i) {//adding the data
			table.put(keyArr[i], valArr[i]);
		}
	}

	// Methods
	public void add(K key, V val) {//adding data
		table.put(key, val);
	}

	public V get(K key) {//getting value of the key
		return table.get(key);
	}

	public boolean contains(K key) {//check if the key is in the table
		return table.containsKey(key);
	}

	public boolean remove(K key) {//delete the value-key pair
		if (table.remove(key) != null)
			return true;
		else
			return false;
	}

	public int size() {//check how many pairs are in the table
		return table.size();
	}

	@Override
	public Iterator<K> iterator() {//create iterator

		return new AssociationTableIterator<K>();
	}

	private class AssociationTableIterator<K> implements Iterator<K> {//iterator class
		private int curr = 0;
		private K[] arr;

		public AssociationTableIterator() {
			arr = (K[]) table.keySet().toArray();
		}

		public boolean hasNext() {
			return curr < arr.length;
		}

		public K next() {
			K key = arr[curr++];
			return key;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
