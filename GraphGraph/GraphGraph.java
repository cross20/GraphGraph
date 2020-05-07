import java.util.*;

/**
 * @author Chad Ross
 * @version 0.1
 * @since 2020-04-20
 */
public class GraphGraph {
    // Represents the number of rows and columns in the adjacency matrix adj (also the number
    // of verticies in the graph).
    private Integer m;

    // Stores the rows of the matrix where each row is stored as an ArrayList<Integer>.
    // Edges between verticies are two-directional. One directional edges are not supported.
    // Also, self loops are allowed but not recommended.
    private ArrayList<ArrayList<Boolean> > adj;

    public GraphGraph(ArrayList<ArrayList<Boolean> > adj) {
        if(adj != null) {
            this.m = adj.size();

            // TODO: Check whether adj is a valid matrix.
            this.adj = adj;
        }
    }

    /**
     * Updates both entries associated with two verticies in the adjacency matrix.
     * @param vertexA A vertex whose connection state will be updated based on isConnected.
     * @param vertexB A vertex whose connection state will be updated based on isConnected.
     * @param isConnected Determines whether {@code vertexA} and {@code vartexB} are
     * connected or not.
     */
    public void setValue(Integer vertexA, Integer vertexB, Boolean isConnected) {
        adj.get(vertexB).set(vertexA, isConnected);
        adj.get(vertexA).set(vertexB, isConnected);
    }

    /**
     * Updates all entries associated with an individual vertex in the adjacency matrix.
     * @param vertex The vertex whose entries in the adjacency matrix will be updated for.
     * @param isConnected A list of Bollean values that determines whether {@code vertex} 
     * is connected to another vertex represented by the index of {@code isConnected}
     */
    public void setValuesOnVertex(Integer vertex, ArrayList<Boolean> isConnected) {

        adj.set(vertex, isConnected);
        
        for (int i = 0; i < m; i++) {
            adj.get(i).set(vertex, isConnected.get(i));
        }
    }

    /**
     * Uses {@code isConnected} to determine whether neighboring verticies in {@code 
     * verticeis} are connected or not. If a vertex is at index {@code i}, it's neighboring
     * verticies are at {@code i + 1} and {@code i - 1} from a vertex. The front of the list
     * is not a neighbor to the back of the list.
     * @param verticies An array list of verticies represented as {@code Integer} values.
     * @param isConnected A {@code Boolean} value which determines whether verticies in the
     * {@code verticies} array list are connected or not.
     */
    public void setValues(ArrayList<Integer> verticies, Boolean isConnected) {
        // Index i -1 is always connected to index i where i > 0.
        for (int i = 1; i < verticies.size(); i++) {
            this.setValue(verticies.get(i-1), verticies.get(i), isConnected);
        }
    }

    /**
     * The number of verticies in the graph is the same as the number of rows and columns in
     * the adjacency matrix for the graph. This function provides all three of these values
     * using one {@code Integer} value.
     * @return The number of rows and columns in the graph.
     */
    public Integer size() {
        return m;
    }

    /**
     * @return The adjacency matrix for the graph.
     */
    public ArrayList<ArrayList<Boolean>> matrix() {
        return adj;
    }

    /**
     * Searched the graph to determine if there is a path between every vertex in the graph.
     * It does this using a depth-first-search approach to searching the graph.
     * @return A {@code Boolean} value which represents whether there is a path between every
     * vertex in the graph or not.
     */
    public Boolean isGraphConnected() {
        // Initialize.
        GraphGraph g = this;
        Integer row = 0, stepBackBy = 2;
        Boolean nearlyTrapped = false, trapped = false;

        ArrayList<Integer> foundVerticies = new ArrayList<Integer>();

        // Searches row by row until either all of the connections on all of the verticies
        // have been searched or all of the verticies have been found.
        while (!trapped && foundVerticies.size() != m) {
            ArrayList<Boolean> curRow = g.adj.get(row);
            Boolean noConnections = true;

            // Checks to see if the vertex that cooresponds with the current row has been
            // found yet to avoid duplicate entires.
            if (!foundVerticies.contains(row)) {
                foundVerticies.add(row);
            }

            // Searches for the next connection in the adjacency matrix.
            for (int i = 0; i < m; i++) {
                // Check to see if the current row is connected to the vertex represented by
                // i and makes sure it hasn't been visited yet.
                if (curRow.get(i) && !foundVerticies.contains(i)) {
                    row = i;
                    noConnections = false;
                    nearlyTrapped = false;
                    stepBackBy = 2;
                    break;
                }
            }

            // Step back to search the previous vertex if not all of the verticies have
            // been found when a row has no more connections.
            if (noConnections) {
                Integer index = foundVerticies.size() - stepBackBy++;
                if (index >= 0) {
                    row = foundVerticies.get(index);
                } else {
                    return false;
                }
            }

            // Check to see if we might be trapped or if we are trapped. We can only be
            // trapped after nearlyTrapped has been triggered. We might be trapped if
            // we have come all the way back to the first row in the adjacency matrix.
            trapped = (nearlyTrapped) ? true : false;
            nearlyTrapped = (row == foundVerticies.get(0)) ? true : false;
        }


        return (foundVerticies.size() == m) ? true : false;
    }

    /**
     * A graph has an Euler Cycle if and only if it is connected and has all vertices of even
     * degree. This method check for both of these properties.
     * @return A {@code Boolean} value representing whether the graph contains an Euler Cycle
     * or not.
     */
    public Boolean hasEulerCycle() {
        // Make sure the graph is connected!
        if (!isGraphConnected()) {
            return false;
        }

        // Make sure that there's no verticies of an odd degree!
        if (numOddDegree() != 0) {
            return false;
        }

        return true;
    }

    /**
     * A graph has an Euler Trail if and only if it is connected and has exactly two 
     * vertices of odd degree. This method check for both of these properties.
     * @return A {@code Boolean} value representing whether the graph contains an 
     * Euler Trail or not.
     */
    public Boolean hasEulerTrail() {
        // Make sure the graph is connected!
        if (!isGraphConnected()) {
            return false;
        }

        // Make sure that there's exactly two verticies of an odd degree!
        if (numOddDegree() != 2) {
            return false;
        }

        return true;
    }

    /**
     * A graph has an Euler Cycle if and only if it is connected and has all vertices of
     * even degree. If this graph has an Euler Cycle, it finds it by finding cycles within 
     * the graph and joining them together. Once all cycles have been found, the Euler 
     * cycle is complete.</br></br>
     * 
     * Two neigboring verticies in the list represent an edge. For example, if A = the 
     * vertex at index 0 and B = the vertex at index 1, then a single edge is formed from 
     * both vertices and is called edge (A, B).</br></br>
     * 
     * The first and last vertex in the array list are the same vertex.
     * 
     * @return An array list which contains the Euler Cycle represented by verticies.
     */
    public ArrayList<Integer> eulerCycle() {
        ArrayList<Integer> eulerCycle = new ArrayList<Integer>(m);

        // Make sure the that graph does have an Euler Cycle.
        if (hasEulerCycle()) {
            // Initialize.
            eulerCycle.add(0);
            GraphGraph g = this;
            ArrayList<Integer> curCycle = g.cycle();
            Integer n = 0;
            
            // Find cycles within the graph until no more cycles remain and the full Euler
            // Cycle has been found.
            while (curCycle.size() > 0) {
                Integer injectionIndex = 0;
                Integer tempRemovedVertex = curCycle.get(0);
                curCycle.remove(0);


                // Find a point that is in both the Euler Cycle and the current cycle.
                for (injectionIndex = 0; injectionIndex < curCycle.size(); injectionIndex++) {
                    if (eulerCycle.contains(curCycle.get(injectionIndex))) {
                        break;
                    }
                }

                // Inject the current cycle into the Euler Cycle after the injection point
                // has been identified.
                for (int i = 0; i < eulerCycle.size(); i++) {
                    // Check to see if the injection point has been found in the Euler Cycle.
                    if (eulerCycle.get(i) == curCycle.get(injectionIndex)) {
                        // For each vertex in the current cycle, inject the verticies into
                        // the Euler Cycle.
                        injectionIndex++;
                        for (int j = 0; j < curCycle.size(); j++) {
                            if (injectionIndex >= curCycle.size()) {
                                injectionIndex = 0;
                            }

                            eulerCycle.add(++i, curCycle.get(injectionIndex++));
                        }
                        break;
                    }
                }

                // Remove edges from the graph as to not use them in the future. Every
                // removed edge is restored to the graph after the Euler Cycle is found.
                curCycle.add(0, tempRemovedVertex);
                for (int i = 1; i < curCycle.size(); i++) {
                    g.setValue(curCycle.get(i-1), curCycle.get(i), false);
                }

                // Find a new cycle using the remaining edges in the graph.
                curCycle = g.cycle();
            }

        }

        // Resote the removed edges from the adjecency matrix for the graph.
        setValues(eulerCycle, true);

        return eulerCycle;
    }

    /**
     * Searches the graph for a cycle. This is done by searching the adjacency row by row
     * for other connected verticies. If the graph contains no cycles, then the returned
     * array list will be empty.</br></br>
     * 
     * Two neigboring verticies in the list represent an edge. For example, if A = the 
     * vertex at index 0 and B = the vertex at index 1, then a single edge is formed from 
     * both vertices and is called edge (A, B).</br></br>
     * 
     * The first and last vertex in the array list are the same vertex.
     * 
     * @return An array list which contains the Euler Cycle represented by verticies.
     */
    public ArrayList<Integer> cycle() {
        ArrayList<Integer> cycle = new ArrayList<Integer>();

        // Initialize.
        GraphGraph g = this;
        Integer i = 0;

        // Find a suitable vertex to start from. This will be the first vertex that
        // is found that is connected to another vertex and is part of a cycle.
        while (i < m && (cycle.size() == 0 || cycle.get(0) != cycle.get(cycle.size()-1))) {
            // Initialize.
            
            Boolean foundVertex = false;
            Integer row = i;

            ArrayList<Boolean> curRow;

            // Search each row until a vertex is found which is connected to another vertex.
            while (row < m) {
                // Initialize.
                curRow = g.adj.get(row);
                Integer column = 0;

                // Search the current vertex for a connection to another vertex.
                while (column < m) {
                    if (curRow.get(column)) {
                        foundVertex = true;
                        break;
                    }

                    column++;
                }

                if (foundVertex) {
                    break;
                }

                row++;
            }

            // From the selected vertex, find the cycle that that vertex is part of. If the
            // selected vertex is not part of a cycle, the search will continue until all
            // options are exhausted.
            cycle = g.cycleFromVertex(row);
            i++;
        }

        return (cycle.size() > 1 && cycle.get(0) == cycle.get(cycle.size()-1)) ? cycle : new ArrayList<Integer>();
    }

    /**
     * Searches the graph for a cycle. This is done by searching specified vertex for other
     * connected verticies. If the specified vertex is not part of any cycle, then the
     * returned array list will be empty.</br></br>
     * 
     * Two neigboring verticies in the list represent an edge. For example, if A = the 
     * vertex at index 0 and B = the vertex at index 1, then a single edge is formed from 
     * both vertices and is called edge (A, B).</br></br>
     * 
     * The first and last vertex in the array list are the same vertex.
     * 
     * @param vertex The vertex that will start and end the cycle.
     * @return An array list which contains the Euler Cycle represented by verticies.
     */
    public ArrayList<Integer> cycleFromVertex(Integer vertex) {
        ArrayList<Integer> cycle = new ArrayList<Integer>();
        
        // Initialize.
        cycle.add(vertex);
        GraphGraph g = this;
        Integer row = vertex, i = 0;

        // Finds a cycle by jumping from vertex to vertex until the starting vertex is
        // returned to.
        while (i <= m && row < m && (cycle.get(0) != cycle.get(cycle.size() - 1) || ( cycle.size() == 1 && m != 1))) {
            for (int column = 0; column < m; column++) {
                if (g.adj.get(row).get(column)) {
                    g.setValue(row, column, false); // Remove the edge so it isn't used 2x.
                    cycle.add(column);
                    row = column;
                    break;
                }
            }
            i++;
        }

        // Resote the removed edges from the adjecency matrix for the graph.
        setValues(cycle, true);

        return (cycle.size() > 1 && cycle.get(0) == cycle.get(cycle.size()-1)) ? cycle : new ArrayList<Integer>();
    }

    /**
     * A graph has an Euler Trail if and only if it is connected and has exactly two 
     * vertices of odd degree. If this graph has an Euler Trail, it finds it by connecting
     * the two verticies of odd degree and finding the Euler Cycle that is formed by adding
     * that connection. Before the Euler Trail is removed, the added edge is removed and
     * the Euler Trail is formed.</br></br>
     * 
     * Two neigboring verticies in the list represent an edge. For example, if A = the 
     * vertex at index 0 and B = the vertex at index 1, then a single edge is formed from 
     * both vertices and is called edge (A, B).</br></br>
     * 
     * The first and last vertex in the array list are the starting and ending verticies
     * respectively. If there is not Euler Trail, the method will return with an empty
     * {@code ArrayList}.
     * 
     * @return An array list which contains the Euler Trail represented by verticies.
     */
    public ArrayList<Integer> eulerTrail() {
        ArrayList<Integer> eulerTrail = new ArrayList<Integer>(m);

        if (hasEulerTrail()) {
            // Initialize.
            GraphGraph g = this;
            ArrayList<Integer> oddDegree = g.oddDegree();
            ArrayList<Integer> curTrail = new ArrayList<Integer>(m);

            // Add an edge between the two verticies of odd degree sequence so that the graph
            // has an Euler Cycle.
            g.setValue(oddDegree.get(0), oddDegree.get(1), true);

            curTrail = g.eulerCycle();

            Integer splitIndex = 0;
            Boolean findFirstOdd = true;

            // Identify where the two verticies of odd degree are connected in the Euler
            // Cycle. The Euler Trail will need to be split at that point.
            for (int i = 0; i < m; i++) {
                if (findFirstOdd && curTrail.get(i) == oddDegree.get(0)) {
                    findFirstOdd = false;
                } else if (!findFirstOdd && curTrail.get(i) == oddDegree.get(1)) {
                    splitIndex = i;
                    break;
                } else {
                    findFirstOdd = true;
                }
            }

            // Store and remove the front portion of the slip to append to the back of the
            // Euler Trail later.
            ArrayList<Integer> splitOne = new ArrayList<Integer>(splitIndex);
            
            for (int i = 0; i < splitIndex; i++) {
                splitOne.add(curTrail.get(0));
                curTrail.remove(i);
            }

            // Append the back of the Euler Trail with the front half that was removed
            // earlier.
            for (int i = 1; i < splitOne.size(); i++) {
                curTrail.add(splitOne.get(i));
            }
            
            // Move the current list into the final list.
            Integer index = curTrail.indexOf(oddDegree.get(0));
            for (int i = 0; i < curTrail.size() - 1; i++) {
                if (index >= curTrail.size()) {
                    index = 1;
                }

                eulerTrail.add(curTrail.get(index++));
            }

            // Remove the edge that was added to make the Euler Trail into an Euler Cycle.
            g.setValue(oddDegree.get(0), oddDegree.get(1), false);
        }

        return eulerTrail;
    }

    /**
     * Examines the connection for each vertex and tracks how many have an odd degree
     * sequence.
     * @return The number of verticies with an odd degree sequence.
     */
    public Integer numOddDegree() {
        return oddDegree().size();
    }

    /**
     * Examines the connection for each vertex and tracks which ones have an odd degree
     * sequence.
     * @return A list of verticies that have an odd degree sequence.
     */
    public ArrayList<Integer> oddDegree() {
        ArrayList<Integer> oddDegree = new ArrayList<Integer>();

        // Search each row for connected verticies.
        for (int row = 0; row < m; row++) {
            if (degreeSequence(row) % 2 != 0) {
                oddDegree.add(row);
            }
        }

        return oddDegree;
    }

    /**
     * Counts the numeber of other verticies that are connected to {@code vertex} by
     * searching the row associated with {@code vertex} in the adjacency matrix for
     * connections.
     * @param vertex The vertex to check the degree sequence for.
     * @return The degree sequence of {@code vertex}.
     */
    public Integer degreeSequence(Integer vertex) {
        Integer numDegree = 0;
        ArrayList<Boolean> curRow = adj.get(vertex);

        // Search the verticies connected to the current vertex (current row).
        for (int column = 0; column < m; column++) {
            if (curRow.get(column)) {
                numDegree++;
            }
        }

        return numDegree;
    }

    @Override
    public String toString() {
        String str = "";

        for (ArrayList<Boolean> row : adj) {
            for (Boolean isConnected : row) {
                str += (isConnected ? 1 : 0) + " ";
            }
            str += "\n";
        }

        return str;
    }
}