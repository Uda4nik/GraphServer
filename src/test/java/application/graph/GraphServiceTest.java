package application.graph;

import application.graph.command.AddEdgeCommand;
import application.graph.command.AddNodeCommand;
import application.ports.command.CommandResult;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GraphServiceTest {
    GraphService cut;
    DirectedWeightedMultigraph mockedGraph;

    @BeforeMethod
    public void setUp() throws Exception {
        mockedGraph = mock(DirectedWeightedMultigraph.class);
        cut = new GraphService(mockedGraph);
    }

    @Test
    public void addNodeCommandWillLeadToAddedVertex() throws Exception {
        cut.process(new AddNodeCommand("test node"));
        verify(mockedGraph).addVertex("test node");
    }

    @Test
    public void returnNodeAddedMessageIfSuccessfullyAddedVertex() throws Exception {
        when(mockedGraph.addVertex("test node")).thenReturn(Boolean.TRUE);
        CommandResult result = cut.process(new AddNodeCommand("test node"));
        assertThat(result).isEqualTo(CommandResult.of("NODE ADDED"));
    }

    @Test
    public void returnErrorMessageIfVertexAlreadyExist() throws Exception {
        when(mockedGraph.addVertex("test node")).thenReturn(Boolean.FALSE);
        CommandResult result = cut.process(new AddNodeCommand("test node"));
        assertThat(result).isEqualTo(CommandResult.of("ERROR: NODE ALREADY EXISTS"));
    }


    @Test
    public void addEdgeCommandWillLeadToAddedEdge() throws Exception {
        DefaultWeightedEdge edge = new DefaultWeightedEdge();
        when(mockedGraph.addEdge(any(),any())).thenReturn(edge);
        cut.process(new AddEdgeCommand("node1","node2",42));
        verify(mockedGraph).addEdge("node1", "node2");
        verify(mockedGraph).setEdgeWeight(edge, 42);
    }

    @Test
    public void returnEdgeAddedMessageIfSuccessfullyAddedVertex() throws Exception {
        CommandResult result = cut.process(new AddEdgeCommand("node1", "node2", 42));
        assertThat(result).isEqualTo(CommandResult.of("EDGE ADDED"));
    }

    @Test
    public void returnErrorMessageIfEdgeCantBeAdded() throws Exception {
        when(mockedGraph.addEdge(any(),any())).thenThrow(IllegalArgumentException.class);
        CommandResult result = cut.process(new AddEdgeCommand("node1", "node2", 42));
        assertThat(result).isEqualTo(CommandResult.of("ERROR: NODE NOT FOUND"));
    }

    /* to lazy to finish :) */
}