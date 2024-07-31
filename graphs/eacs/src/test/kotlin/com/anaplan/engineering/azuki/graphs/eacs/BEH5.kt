package com.anaplan.engineering.azuki.graphs.eacs

import com.anaplan.engineering.azuki.core.runner.Eac
import com.anaplan.engineering.azuki.core.system.BEH
import com.anaplan.engineering.azuki.graphs.adapter.api.GraphBehaviours
import com.anaplan.engineering.azuki.graphs.adapter.api.GraphFunctionalElements
import com.anaplan.engineering.azuki.graphs.dsl.GraphScenario
import com.anaplan.engineering.azuki.graphs.graphA

@BEH(GraphBehaviours.GetShortestPath, GraphFunctionalElements.GraphFunction, """

""")
class BEH5 : GraphScenario() {

    @Eac("""
        Given a graph that contains at least path from a beginning vertex to an ending vertex,
        the graph will have a shortest path between these vertices
    """)
    fun onePath() {
        given {
            thereIsAnUndirectedGraph(graphA) {
                edge("a", "b")
            }
        }
        then {
            hasShortestPath(graphA, "a", "b")
        }
    }

    @Eac("""
        Given a graph that contains more then one path between two vertices,
        there is a shortest path
    """, """
        If two paths have the same number of vertices and are both the shortest only
        the first path defined will be recognised as the shortest path.
    """)
    fun multiplePaths() {
        given {
            thereIsAnUndirectedGraph(graphA) {
                edge("a", "b")
                edge("b", "c")
                edge("c", "d")
                edge("d", "e")
                edge("a", "f")
                edge("f", "e")
            }
        }
        then {
            hasShortestPath(graphA, "a", "f", "e")
        }
    }

    @Eac("""
        Given a directed graph,
        the shortest path wont include an edge that is declared in the opposite direction to the path
    """)
    fun directedGraph() {
        given {
            thereIsADirectedGraph(graphA) {
                edge("a", "b")
                edge("b", "c")
                edge("c", "d")
                edge("a", "e")
                edge("d", "e")
            }
        }
        then {
            hasShortestPath(graphA, "a", "b", "c", "d")
        }
    }

    @Eac("""
        Given a graph that has a path with a cycle, the cycle will not be included in the shortest path
    """, """
        Vertices from the cycle, that are not part of the actual shortest path, will not be included.
    """)
    fun graphWithCycle() {
        given {
            thereIsAnUndirectedGraph(graphA) {
                edge("a", "b")
                edge("b", "c")
                edge("c", "d")
                edge("d", "b")
                edge("b", "e") //TODO make into cycle
            }
        }
        then {
            hasShortestPath(graphA, "a", "b", "e")
        }
    }

    @Eac("""
        Given a graph that contains a vertex that is not connected by any edge to another vertex,
        the shortest path can only be from the vertex to itself
    """)
    fun noPath() {
        given {
            thereIsAnUndirectedGraph(graphA) {
                vertex("a")
                vertex("b")
            }
        }
        then {
            hasShortestPath(graphA, "a")
        }
    }

    @Eac("""
        Given a graph that has a cycle from a vertex back to the same vertex,
        the shortest path will be defined as staying at the same node
    """, """
        the shortest path will not take the path of the loop as it is a shorter path to stay at the same vertex
    """)
    fun graphIsCycle() {
        given {
            thereIsAnUndirectedGraph(graphA) {
                edge("a", "b")
                edge("b", "c")
                edge("c", "a")
            }
        }
        then {
            hasShortestPath(graphA, "a")
        }
    }
}
