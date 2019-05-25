package ltdt.tree;

public class Edge implements Comparable<Edge> {
	private int start;
	private int end;
	private int weight;

	public Edge(int start, int end, int weight) {
		super();
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	@Override
    public int compareTo(Edge other) {
        if(this.getWeight() > other.getWeight()) {
            return 1;
        } else if (this.getWeight() < other.getWeight()) {
            return -1;
        } else {
            return 0;
        }
    }

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
