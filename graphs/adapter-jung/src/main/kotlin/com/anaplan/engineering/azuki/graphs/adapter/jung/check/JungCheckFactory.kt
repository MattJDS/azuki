package com.anaplan.engineering.azuki.graphs.adapter.jung.check

import com.anaplan.engineering.azuki.core.system.Check
import com.anaplan.engineering.azuki.graphs.adapter.api.GraphCheckFactory
import com.anaplan.engineering.azuki.graphs.adapter.jung.execution.ExecutionEnvironment
import org.slf4j.LoggerFactory

class JungCheckFactory : GraphCheckFactory {

    override fun hasVertexCount(graphName: String, count: Long) = HasVertexCountCheck(graphName, count)

    override fun hasShortestPath(graphName: String, path: List<Any>) =
        HasShortestPathCheck(graphName, path)

    override fun pathExists(graphName: String, from: Any, to: Any, result: Boolean) =
        PathExistsCheck(graphName, from, to, result)

    override fun hasEdgeCount(graphName: String, count: Long) = HasEdgeCountCheck(graphName, count)
}

interface JungCheck : Check {
    fun check(env: ExecutionEnvironment): Boolean

    fun checkEqual(expected: Any, actual: Any): Boolean {
        val equal = expected == actual
        if (!equal) {
            Log.error("Equality check failed: expected=$expected actual=$actual")
        }
        return equal
    }

    companion object {
        private val Log = LoggerFactory.getLogger(JungCheck::class.java)
    }
}


val toJungCheck: (Check) -> JungCheck = {
    @Suppress("UNCHECKED_CAST")
    it as? JungCheck ?: throw IllegalArgumentException("Invalid check: $it")
}
