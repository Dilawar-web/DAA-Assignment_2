import java.util.Arrays;

public class Q3{

    static final int V = 9;
    static final int INF = Integer.MAX_VALUE;

    static int minDistance(int[] dist, boolean[] visited) {

        int min = INF;
        int minIndex = -1;

        for (int i = 0; i < V; i++) {
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    static void printTable(int[] dist, int[] parent) {

        System.out.print("Vertex :      ");
        for (int i = 0; i < V; i++)
            System.out.printf("%4d", i);

        System.out.println();

        System.out.print("Distance :    ");

        for (int i = 0; i < V; i++) {

            if (dist[i] == INF)
                System.out.printf("%4s", "INF");
            else
                System.out.printf("%4d", dist[i]);
        }

        System.out.println();

        System.out.print("Parent :      ");

        for (int i = 0; i < V; i++) {

            if (parent[i] == -1)
                System.out.printf("%4s", "-");
            else
                System.out.printf("%4d", parent[i]);
        }

        System.out.println("\n");
    }

    static void dijkstra(int[][] graph, int source) {

        int[] dist = new int[V];
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        dist[source] = 0;

        System.out.println("Source Vertex : " + source);
        System.out.println();
        System.out.println("=================================================");
        System.out.println("Step 0 : Initialization\n");

        printTable(dist, parent);

        int step = 1;
        for (int count = 0; count < V - 1; count++) {

            int u = minDistance(dist, visited);

            if (u == -1)
                break;

            visited[u] = true;

            System.out.println("=================================================");
            System.out.println("Step " + step++);
            System.out.println();
            System.out.println("Selected Vertex : " + u);
            System.out.println();

            boolean updated = false;

            System.out.println("Updated Vertices");

            for (int v = 0; v < V; v++) {

                if (!visited[v] &&
                        graph[u][v] != 0 &&
                        dist[u] != INF &&
                        dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;

                    updated = true;

                    System.out.println(
                            v + " -> Distance = " +
                                    dist[v] +
                                    "   Parent = " +
                                    u);
                }
            }

            if (!updated)
                System.out.println("No Vertex Updated");

            System.out.println();
            System.out.println("Current Distance Table\n");

            printTable(dist, parent);
        }

        System.out.println("=================================================");
        System.out.println("Final Distance Table\n");

        System.out.printf("%-10s%-12s%-10s\n",
                "Vertex", "Distance", "Parent");

        for (int i = 0; i < V; i++) {

            System.out.printf("%-10d", i);

            if (dist[i] == INF)
                System.out.printf("%-12s", "INF");
            else
                System.out.printf("%-12d", dist[i]);

            if (parent[i] == -1)
                System.out.printf("%-10s\n", "-");
            else
                System.out.printf("%-10d\n", parent[i]);
        }

        System.out.println();
        System.out.println("=================================================");
        System.out.println("Shortest Path Tree\n");

        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " -> " + i);
        }
    }
    public static void main(String[] args) {

        int[][] graph = {

                {0,4,0,0,0,0,0,8,0},
                {4,0,8,0,0,0,0,11,0},
                {0,8,0,7,0,4,0,0,2},
                {0,0,7,0,9,14,0,0,0},
                {0,0,0,9,0,10,0,0,0},
                {0,0,4,14,10,0,2,0,0},
                {0,0,0,0,0,2,0,1,6},
                {8,11,0,0,0,0,1,0,7},
                {0,0,2,0,0,0,6,7,0}

        };

        dijkstra(graph, 0);
    }
}