package com.java.javafx.list;

public class GroceryLinkedList {

	Link first;

	public void add(String item) {
		Link newLink = new Link(item);
		newLink.next = null;
		if (first == null)
			first = newLink;
		else {
			Link current = first;
			while (current.next != null)
				current = current.next;
			current.next = newLink;
		}
	}

	public String printList() {
		String days = "";
		if (first == null)
			return days;
		Link current = first;
		while (current != null) {
			if (current.next == null)
				days += current.data;
			else if (current.next.next == null)
				days += current.data + " and ";
			else
				days += current.data += ",";

			current = current.next;
		}
		return days;
	}

	public int size() {
		int size = 0;
		if (first == null)
			return size;
		Link current = first;
		while (current != null) {
			current = current.next;
			++size;
		}
		return size;
	}
}
