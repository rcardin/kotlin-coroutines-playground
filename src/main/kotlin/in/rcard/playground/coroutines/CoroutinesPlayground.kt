package `in`.rcard.playground.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("CoroutinesPlayground")
suspend fun main() {
    logger.info("Starting the morning routine")
    noStructuralConcurrencyMorningRoutine()
    logger.info("Ending the morning routine")
}

suspend fun sequentialMorningRoutine() {
    coroutineScope {
        bathTime()
    }
    coroutineScope {
        boilingWater()
    }
}

suspend fun concurrentMorningRoutine() {
    coroutineScope {
        launch {
            bathTime()
        }
        launch {
            boilingWater()
        }
    }
}

suspend fun noStructuralConcurrencyMorningRoutine() {
    GlobalScope.launch {
        bathTime()
    }
    GlobalScope.launch {
        boilingWater()
    }
    Thread.sleep(1500L)
}

suspend fun bathTime() {
    logger.info("Going to the bathroom")
    delay(500L)
    logger.info("Exiting the bathroom")
}

suspend fun boilingWater() {
    logger.info("Boiling water")
    delay(1000L)
    logger.info("Water boiled")
}