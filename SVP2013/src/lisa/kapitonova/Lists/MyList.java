package lisa.kapitonova.Lists;

public class MyList {

	class Element {
		int grayscale;

		public void invert() {
			grayscale = 255 - grayscale;
		}

		Element next;

		public Element(int a, Element b) {
			grayscale = a;
			next = b;
		}
	}

	private Element first;

	public void iterator(Visitor visitor) {
		for (Element curr = first; curr != null; curr = curr.next) {
			visitor.visit(curr);
		}
	}

	public MyList() {
	}

	public Element getLast() {
		Element runner = first;
		while (runner.next != null) {
			runner = runner.next;
		}

		return runner;
	}

	public void addLast(int colour) {
		Element newLast = new Element(colour, null);
		if (first == null) {
			first = newLast;
		} else {
			Element last = getLast();
			last.next = newLast;
		}
	}

	public void removeLast() {
		Element walker = first;
		if (first.next == null) {
			first = null;
		} else {
			while (walker.next.next != null) {
				walker = walker.next;
			}
			walker.next = null;
		}
	}

	public Element getFirst() {
		return first;
	}

	public void addFirst(int colour) {
		Element newFirst = new Element(colour, first);
		first = newFirst;
	}

	public void removeFirst() {
		if (first == null) {
			return;
		}
		Element a = first.next;
		first.next = null;
		first = a;
	}

	public String toString() {
		if (first == null) {
			return "( )";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		Element x = first;
		while (x.next != null) {
			sb.append(x.grayscale);
			sb.append(", ");
			x = x.next;
		}
		sb.append(x.grayscale);
		sb.append(")");
		return sb.toString();
	}

	
	
	public static void main(String[] arg) {
		MyList list = new MyList();
		for(int i = 0 ;i<255; i++) {
			list.addLast(i);
		}
		System.out.println(list);
		list.iterator(new Inverter());
		System.out.println(list);
		
	}
}
