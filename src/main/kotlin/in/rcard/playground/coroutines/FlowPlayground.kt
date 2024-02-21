package `in`.rcard.playground.coroutines

import kotlinx.coroutines.delay

fun interface FlowCollector<T> {
    suspend fun emit(value: T)
}

interface Flow<T> {
    suspend fun collect(collector: FlowCollector<T>)
}

fun <T> flow(builder: suspend FlowCollector<T>.() -> Unit) =
    object : Flow<T> {
        override suspend fun collect(collector: FlowCollector<T>) {
            builder.invoke(collector)
        }
    }

suspend fun main() {
    val flow =
        flow { // The body of f
            emit("A")
            delay(1000L)
            emit("B")
            delay(1000L)
            emit("C")
        }

    flow.collect { print(it) } // The lambda is the body of emit, since FlowCollector is a functional interface
    println()
    flow.collect { print(it) }
}
