import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String args[]) {

        // ================
        // Initialize Graph
        // ================

        Integer m = 8;

        // ===============
        // Connected Graph
        // ===============

        ArrayList<ArrayList<Boolean> > adj1 = new ArrayList<ArrayList<Boolean> >(m);

        Boolean isConnected = true;

        for (int i = 0; i < m; i++) {
            ArrayList<Boolean> row = new ArrayList<Boolean>(m);
            for (int j = 0; j < m; j++) {
                row.add(!isConnected);
            }

            adj1.add(row);
        }

        GraphGraph g1 = new GraphGraph(adj1);

        Scanner scan = new Scanner(System.in);
        System.out.print("Press enter to start...");
        scan.nextLine();

        g1.setValue(0, 1, isConnected);
        g1.setValue(0, 4, isConnected);
        g1.setValue(1, 2, isConnected);
        g1.setValue(1, 5, isConnected);
        g1.setValue(2, 3, isConnected);
        g1.setValue(2, 6, isConnected);
        g1.setValue(3, 7, isConnected);
        g1.setValue(4, 5, isConnected);
        g1.setValue(5, 6, isConnected);
        g1.setValue(6, 7, isConnected);

        System.out.println("<-----o-----o-------o-----o---------o----->");
        System.out.println("Graph 1:\n\n" + g1.toString());
        System.out.println("The graph is" + (g1.isGraphConnected() ? " " : " not ") + "connected.");
        System.out.println("The graph has" + (g1.hasEulerCycle() ? " an " : " no ") + "Euler Cycle.");
        System.out.println("The graph has" + (g1.hasEulerTrail() ? " an " : " no ") + "Euler Trail.");
        System.out.println("<--o-----------o-----o------------o-----o->");

        System.out.print("Press enter to continue...");
        scan.nextLine();

        // ======================
        // Graph with Euler Cycle
        // ======================

        ArrayList<ArrayList<Boolean> > adj2 = new ArrayList<ArrayList<Boolean> >(m);

        for (int i = 0; i < m; i++) {
            ArrayList<Boolean> row = new ArrayList<Boolean>(m);
            for (int j = 0; j < m; j++) {
                row.add(!isConnected);
            }

            adj2.add(row);
        }

        GraphGraph g2 = new GraphGraph(adj2);

        g2.setValue(0, 3, isConnected);
        g2.setValue(0, 4, isConnected);
        g2.setValue(1, 2, isConnected);
        g2.setValue(1, 5, isConnected);
        g2.setValue(2, 6, isConnected);
        g2.setValue(3, 7, isConnected);
        g2.setValue(4, 5, isConnected);
        g2.setValue(6, 7, isConnected);

        System.out.println("<-----o-----o-------o-----o---------o----->");
        System.out.println("Graph 2:\n\n" + g2.toString());
        System.out.println("The graph is" + (g2.isGraphConnected() ? " " : " not ") + "connected.");
        System.out.println("The graph has" + (g2.hasEulerCycle() ? " an " : " no ") + "Euler Cycle.");
        System.out.print("\tThe cycle is:");
        ArrayList<Integer> cycle = g2.eulerCycle();
        for (int i = 0; i < cycle.size(); i++) {
            System.out.print(" " + cycle.get(i));
        }
        System.out.println(".\nThe graph has" + (g2.hasEulerTrail() ? " an " : " no ") + "Euler Trail.");
        System.out.println("<--o-----------o-----o------------o-----o->");

        System.out.print("Press enter to continue...");
        scan.nextLine();

        // ======================
        // Graph with Euler Trail
        // ======================

        ArrayList<ArrayList<Boolean> > adj3 = new ArrayList<ArrayList<Boolean> >(m);

        for (int i = 0; i < m; i++) {
            ArrayList<Boolean> row = new ArrayList<Boolean>(m);
            for (int j = 0; j < m; j++) {
                row.add(!isConnected);
            }

            adj3.add(row);
        }

        GraphGraph g3 = new GraphGraph(adj3);

        g3.setValue(0, 4, isConnected);
        g3.setValue(1, 2, isConnected);
        g3.setValue(1, 5, isConnected);
        g3.setValue(2, 6, isConnected);
        g3.setValue(3, 7, isConnected);
        g3.setValue(4, 5, isConnected);
        g3.setValue(6, 7, isConnected);

        System.out.println("<-----o-----o-------o-----o---------o----->");
        System.out.println("Graph 3:\n\n" + g3.toString());
        System.out.println("The graph is" + (g3.isGraphConnected() ? " " : " not ") + "connected.");
        System.out.println("The graph has" + (g3.hasEulerCycle() ? " an " : " no ") + "Euler Cycle.");
        System.out.println("The graph has" + (g3.hasEulerTrail() ? " an " : " no ") + "Euler Trail.");
        System.out.print("\tThe trail is:");
        ArrayList<Integer> trail = g3.eulerTrail();
        for (int i = 0; i < trail.size(); i++) {
            System.out.print(" " + trail.get(i));
        }
        System.out.println(".\n<--o-----------o-----o------------o-----o->");

        System.out.print("Press enter to continue...");
        scan.nextLine();

        scan.close();
    }
}