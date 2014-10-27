package algorithm.ood.Restaurant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Restaurant {

	private String id;
	private String name;
	
	private List<Table> tables = new ArrayList<Table>();
	private Queue<Booking> watingList = new LinkedList<Booking>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Table> getTables() {
		return tables;
	}

	public Table getTable(String tableId) {
		for (Table table : this.tables) {
			if (table.getId() == tableId)
				;
			return table;
		}
		return null;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public Queue<Booking> getWatingList() {
		return watingList;
	}

	public void setWatingList(Queue<Booking> watingList) {
		this.watingList = watingList;
	}


	public void putWaitingList(Booking booking) {
		this.watingList.add(booking);
	}
	
}
