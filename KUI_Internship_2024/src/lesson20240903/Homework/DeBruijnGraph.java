package lesson20240903.Homework;


import java.util.*;

public class DeBruijnGraph {
    private Map<String, Queue<String>> adjacencyList;
    private Map<String, Integer> inDegree;
    private Map<String, Integer> outDegree;

    public DeBruijnGraph() {
        this.adjacencyList = new HashMap<>();
        this.inDegree = new HashMap<>();
        this.outDegree = new HashMap<>();
    }

    public void buildGraph(String text, int k) {
        for (int i = 0; i <= text.length() - k; i++) {
            String prefix = text.substring(i, i + k - 1);
            String suffix = text.substring(i + 1, i + k);

            adjacencyList.putIfAbsent(prefix, new LinkedList<>());
            adjacencyList.get(prefix).add(suffix);

            outDegree.put(prefix, outDegree.getOrDefault(prefix, 0) + 1);
            inDegree.put(suffix, inDegree.getOrDefault(suffix, 0) + 1);
        }
    }

    private String findStartNode() {
        for (String node : outDegree.keySet()) {
            if (outDegree.get(node) - inDegree.getOrDefault(node, 0) == 1) {
                return node;
            }
        }
        // If no node with outDegree - inDegree = 1, start from any node
        return adjacencyList.keySet().iterator().next();
    }

    public List<String> findEulerianPath() {
        Stack<String> stack = new Stack<>();
        List<String> path = new ArrayList<>();
        String currentNode = findStartNode();

        stack.push(currentNode);
        while (!stack.isEmpty()) {
            if (adjacencyList.containsKey(currentNode) && !adjacencyList.get(currentNode).isEmpty()) {
                stack.push(currentNode);
                String nextNode = adjacencyList.get(currentNode).poll();
                currentNode = nextNode;
            } else {
                path.add(currentNode);
                currentNode = stack.pop();
            }
        }
        Collections.reverse(path);
        return path;
    }

    public String reconstructGenome(List<String> path, int k) {
        StringBuilder genome = new StringBuilder(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            genome.append(path.get(i).charAt(k - 2));
        }
        return genome.toString();
    }

    public void printGraph() {
        for (Map.Entry<String, Queue<String>> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
