package `in`.rcard.playground.coroutines

import kotlinx.coroutines.delay

object CoroutinesPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

    }

    suspend fun bathTime() {
        println("Going to the bathroom")
        delay(500L)
        println("Exiting the bathroom")
    }
}