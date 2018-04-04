package az.pashabank.digitallab.RailRoadTask.graphs;

import java.util.LinkedList;
import java.util.List;

public class Node {

    private String name;

    private List<Edge> outboundEdges;

    public Node(String name) throws GraphException {

        if (name == null || name.length() == 0)
            throw new GraphException("Node has been tried to be initialized with null or empty name. Nodes must have names");

        this.name = name;
        outboundEdges = new LinkedList<>();
    }

    public void addOutboutEdge(Edge edge) {
        if (!outboundEdges.contains(edge))
            outboundEdges.add(edge);
    }

    public String getName() {
        return name;
    }

    public List<Edge> getOutboundEdges() {
        return outboundEdges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return name != null ? name.equals(node.name) : node.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
