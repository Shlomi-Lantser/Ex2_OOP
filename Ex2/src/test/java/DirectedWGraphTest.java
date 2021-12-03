import static org.junit.jupiter.api.Assertions.*;

class DirectedWGraphTest {

    @org.junit.jupiter.api.Test
    void getNode() {
        Node testing = new Node(0,1,2,3);
        Node testing1 = new Node(1,2,3,4);
        assertEquals(testing1.getKey() , 1);
        assertEquals(testing.getKey() , 0);
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
        Edge testing = new Edge(0,1,4.222);
        assertEquals(testing.getWeight() , 4.222);
    }

    @org.junit.jupiter.api.Test
    void addNode() {
    }

    @org.junit.jupiter.api.Test
    void connect() {
    }

    @org.junit.jupiter.api.Test
    void nodeIter() {
    }

    @org.junit.jupiter.api.Test
    void edgeIter() {
    }

    @org.junit.jupiter.api.Test
    void testEdgeIter() {
    }

    @org.junit.jupiter.api.Test
    void removeNode() {
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
    }

    @org.junit.jupiter.api.Test
    void getMC() {
    }

    @org.junit.jupiter.api.Test
    void getNodes() {
    }
}