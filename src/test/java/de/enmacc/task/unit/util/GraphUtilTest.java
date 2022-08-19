package de.enmacc.task.unit.util;

import de.enmacc.task.util.GraphUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphUtilTest {

    @Test
    public void testGetAllPaths() {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C", "D", "E"));
        graph.put("B", List.of("C", "E"));
        graph.put("C", List.of("A", "B"));
        graph.put("D", List.of("A"));
        graph.put("E", List.of("A", "C"));

        List<List<String>> result = GraphUtil.getAllPaths(graph, "A", "B");
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(l -> l.containsAll(List.of("A", "B"))));
        assertTrue(result.stream().anyMatch(l -> l.containsAll(List.of("A", "C", "B"))));
        assertTrue(result.stream().anyMatch(l -> l.containsAll(List.of("A", "E", "B"))));
    }
}
