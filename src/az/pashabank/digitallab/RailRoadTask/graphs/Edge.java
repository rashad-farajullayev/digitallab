package az.pashabank.digitallab.RailRoadTask.graphs;

public class Edge
{
    private String fromNode;
    private String toNode;
    private int distance;

    public Edge(String fromNode, String toNode, int distance) throws GraphException {

        if (fromNode== null || fromNode.length() == 0 || toNode== null || toNode.length() == 0)
            throw new GraphException ("Edge has been tried to be initialized with null or empty name. Nodes must have names");

        if (distance < 0)
            throw new GraphException ("Weigth of the edge must not be a negative number");

        this.fromNode = fromNode;
        this.toNode = toNode;
        this.distance = distance;
    }

    public String getFromNode() {
        return fromNode;
    }

    public String getToNode() {
        return toNode;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (!getFromNode().equals(edge.getFromNode())) return false;
        return getToNode().equals(edge.getToNode());
    }

    @Override
    public int hashCode() {
        int result = getFromNode().hashCode();
        result = 31 * result + getToNode().hashCode();
        return result;
    }
}
