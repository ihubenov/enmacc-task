package de.enmacc.task.util;

import java.util.*;

public class GraphUtil {

    /**
     * Implementation of DFS in a Graph
     */
    public static List<List<String>> getAllPaths(Map<String, List<String>> graph,
                                          String source,
                                          String destination) {
        Set<String> visited = new HashSet<>();
        List<String> path = new LinkedList<>();

        path.add(source);

        return getAllPathsUtil(graph, source, destination, visited, path);

    }

    private static List<List<String>> getAllPathsUtil(Map<String, List<String>> graph,
                                 String source,
                                 String destination,
                                 Set<String> visited,
                                 List<String> localPathList) {
        List<List<String>> allPaths = new LinkedList<>();

        if (source.equals(destination)) {
            allPaths.add(new LinkedList<>(localPathList));
        } else {
            visited.add(source);

            for (String str : graph.get(source)) {
                if (!visited.contains(str)) {
                    localPathList.add(str);
                    allPaths.addAll(getAllPathsUtil(graph, str, destination, visited, localPathList));
                    localPathList.remove(str);
                }
            }

            visited.remove(source);
        }

        return allPaths;
    }

}
