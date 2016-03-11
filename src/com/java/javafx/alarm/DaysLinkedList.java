package com.java.javafx.alarm;

public class DaysLinkedList {

	Link first;

	public void add(String day) {
		Link newLink = new Link(day);
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
