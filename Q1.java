import java.util.LinkedList;
import java.util.Queue;

public class Q1 {

    static final int V = 7;

    static final int A = 0;
    static final int B = 1;
    static final int C = 2;
    static final int D = 3;
    static final int E = 4;
    static final int F = 5;
    static final int G = 6;

    static String[] vertex = {"A", "B", "C", "D", "E", "F", "G"};

    boolean bfs(int[][] residual, int source, int sink, int[] parent) {

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {

            int u = queue.poll();

            for (int v = 0; v < V; v++) {

                if (!visited[v] && residual[u][v] > 0) {

                    queue.offer(v);
                    visited[v] = true;
                    parent[v] = u;
                }
            }
        }

        return visited[sink];
    }

    void edmondsKarp(int[][] graph, int source, int sink) {

        int[][] residual = new int[V][V];

        for (int i = 0; i < V; i++)
            System.arraycopy(graph[i], 0, residual[i], 0, V);

        int[] parent = new int[V];

        int maxFlow = 0;

        while (bfs(residual, source, sink, parent)) {

            int pathFlow = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residual[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        System.out.println("Maximum Flow = " + maxFlow);

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {

            int u = queue.poll();

            for (int v = 0; v < V; v++) {

                if (!visited[v] && residual[u][v] > 0) {

                    visited[v] = true;
                    queue.offer(v);
                }
            }
        }

        System.out.println("\nMinimum Cut Edges:");

        for (int i = 0; i < V; i++) {

            for (int j = 0; j < V; j++) {

                if (visited[i] && !visited[j] && graph[i][j] > 0) {

                    System.out.println(vertex[i] + " -> " + vertex[j]
                            + " (Capacity = " + graph[i][j] + ")");
                }
            }
        }
    }

    public static void main(String[] args) {

        int[][] graph = new int[V][V];

        graph[A][D] = 3;
        graph[A][B] = 3;
        graph[C][A] = 3;
        graph[B][C] = 4;
        graph[C][D] = 1;
        graph[C][E] = 2;
        graph[D][F] = 6;
        graph[D][E] = 2;
        graph[E][B] = 1;
        graph[E][G] = 1;
        graph[F][G] = 9;

        Q1 obj = new Q1();
        obj.edmondsKarp(graph, A, G);
    }
}