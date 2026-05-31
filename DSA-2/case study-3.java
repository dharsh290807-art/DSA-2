import java.util.*;

public class TwitterReachPrediction {

    static class NodeDepth {
        String user;
        int depth;

        NodeDepth(String user, int depth) {
            this.user = user;
            this.depth = depth;
        }
    }

    public static Set<String> bfsBounded(
            Map<String, List<String>> adj,
            String source,
            int maxDepth) {

        Set<String> visited = new HashSet<>();
        Set<String> reached = new HashSet<>();

        Queue<NodeDepth> queue = new LinkedList<>();

        visited.add(source);
        queue.offer(new NodeDepth(source, 0));

        while (!queue.isEmpty()) {

            NodeDepth current = queue.poll();

            String u = current.user;
            int depth = current.depth;

            // Stop expanding beyond maxDepth
            if (depth == maxDepth)
                continue;

            List<String> neighbours =
                    adj.getOrDefault(u, new ArrayList<>());

            Collections.sort(neighbours);

            for (String v : neighbours) {

                if (!visited.contains(v)) {

                    visited.add(v);
                    reached.add(v);

                    queue.offer(
                        new NodeDepth(v, depth + 1)
                    );
                }
            }
        }

        return reached;
    }

    public static void main(String[] args) {

        Map<String, List<String>> graph =
                new HashMap<>();

        graph.put("A", Arrays.asList("B", "C"));

        graph.put("B", Arrays.asList("D", "E"));

        graph.put("C", Arrays.asList("E", "F"));

        graph.put("D", Arrays.asList("G"));

        graph.put("E", Arrays.asList("G", "H"));

        graph.put("F", Arrays.asList("H", "I"));

        graph.put("G", new ArrayList<>());
        graph.put("H", new ArrayList<>());
        graph.put("I", new ArrayList<>());

        Set<String> reached =
                bfsBounded(graph, "A", 3);

        System.out.println(
                "Users reached within depth 3:"
        );

        System.out.println(reached);

        System.out.println(
                "\nTotal Reach = "
                + reached.size()
        );
    }
}