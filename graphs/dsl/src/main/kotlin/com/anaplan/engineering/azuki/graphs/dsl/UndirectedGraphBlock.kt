package com.anaplan.engineering.azuki.graphs.dsl

import com.anaplan.engineering.azuki.core.dsl.ScenarioDsl
import com.anaplan.engineering.azuki.core.system.Action
import com.anaplan.engineering.azuki.graphs.adapter.api.GraphActionFactory

@ScenarioDsl
class UndirectedGraphBlock(
    private val graphName: String,
    private val actionFactory: GraphActionFactory<*>,
) {

    private val actionList = mutableListOf<Action>()

    fun actions(): List<Action> = actionList

    fun vertex(vertex: Any) {
        actionList.add(actionFactory.undirected.addVertex(graphName, vertex))
    }

    fun edge(source: Any, target: Any) {
        actionList.add(actionFactory.undirected.addEdge(graphName, source, target))
    }
}
