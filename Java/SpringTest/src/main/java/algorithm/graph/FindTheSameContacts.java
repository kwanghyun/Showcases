package algorithm.graph;

import java.util.ArrayList;
import java.util.List;

public class FindTheSameContacts {


	class ContactGraph {
		int noOfContacts;
		ArrayList<ArrayList<Integer>> allContacts;

		public ContactGraph(int noOfContatcts) {
			this.noOfContacts = noOfContatcts;
			this.allContacts = new ArrayList<>();

			for (int i = 0; i < noOfContatcts; i++)
				allContacts.add(new ArrayList<>());
		}

		void addEdge(int u, int v) {
			allContacts.get(u).add(v);
			allContacts.get(v).add(u);
		}

		void dfs(int u, boolean[] visited) {
			System.out.print(u + " ");
			visited[u] = true;

			for (Integer v : allContacts.get(u)) {
				if (!visited[v])
					dfs(v, visited);
			}
		}

		void printConnectedComponents() {
			boolean[] visited = new boolean[noOfContacts];

			for (int i = 0; i < noOfContacts; i++) {
				if (!visited[i]) {
					dfs(i, visited);
					System.out.println();
				}
			}
		}

		void printGraph() {
			int i = 0;
			for (List<Integer> l : allContacts) {
				System.out.print(i++ + " : ");
				for (Integer val : l)
					System.out.print(val + " ");
				System.out.println();
			}
		}
	}

	public ContactGraph buildGraph(Contact[] contacts) {
		ContactGraph g = new ContactGraph(contacts.length);

		for (int i = 0; i < contacts.length; i++) {
			for (int j = i + 1; j < contacts.length; j++) {
				if (contacts[i].str1.equals(contacts[j].str1) || contacts[i].str1.equals(contacts[j].str2)
						|| contacts[i].str1.equals(contacts[j].str3) || contacts[i].str2.equals(contacts[j].str1)
						|| contacts[i].str2.equals(contacts[j].str2) || contacts[i].str2.equals(contacts[j].str3)
						|| contacts[i].str3.equals(contacts[j].str1) || contacts[i].str3.equals(contacts[j].str2)
						|| contacts[i].str3.equals(contacts[j].str3)) {
					g.addEdge(i, j);
					break;
				}
			}
		}

		return g;

	}

	public void printSameContacts(Contact[] contacts) {
		ContactGraph g = buildGraph(contacts);

		g.printConnectedComponents();
	}

	public void printContacts(Contact[] contacts) {
		for (Contact c : contacts)
			System.out.println("'" + c.str1 + "' '" + c.str2 + "' '" + c.str3 + "'");
	}

	public static void main(String[] args) {
		FindTheSameContacts ob = new FindTheSameContacts();

		Contact[] contacts = new Contact[6];

		contacts[0] = new Contact("Gaurav", "gaurav@gmail.com", "gaurav@gfgQA.com");
		contacts[1] = new Contact("Lucky", "lucky@gmail.com", "+1234567");
		contacts[2] = new Contact("gaurav123", "+5412312", "gaurav123@skype.com");
		contacts[3] = new Contact("gaurav1993", "+5412312", "gaurav@gfgQA.com");
		contacts[4] = new Contact("bahubali", "+878312", "raja");
		contacts[5] = new Contact("raja", "+2231210", "raja@gfg.com");

		System.out.println("Contacts:");
		ob.printContacts(contacts);

		System.out.println("\nList of same contacts:");
		ob.printSameContacts(contacts);

	}

}
