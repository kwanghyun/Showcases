package algorithm.greedy;

class Job implements Comparable<Job> {
	char id;
	int deadline;
	int profit;

	public Job(char id, int d, int p) {
		this.id = id;
		this.deadline = d;
		this.profit = p;
	}

	@Override
	public int compareTo(Job o) {
		return o.profit - this.profit;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", deadline=" + deadline + ", profit=" + profit + "]\n";
	}

}
