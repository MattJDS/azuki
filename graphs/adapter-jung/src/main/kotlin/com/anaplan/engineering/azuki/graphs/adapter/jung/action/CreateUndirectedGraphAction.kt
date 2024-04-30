package com.anaplan.engineering.azuki.graphs.adapter.jung.action

import com.anaplan.engineering.azuki.graphs.adapter.declaration.action.CreateUndirectedGraphDeclarableAction
import com.anaplan.engineering.azuki.graphs.adapter.jung.execution.ExecutionEnvironment
import com.google.common.graph.NetworkBuilder

class CreateUndirectedGraphAction(graphName: String) :
    CreateUndirectedGraphDeclarableAction(graphName), JungAction {
    override fun act(env: ExecutionEnvironment) {
        env.addGraph(graphName, NetworkBuilder.undirected().build<Any, Pair<*, *>>())
    }

}
