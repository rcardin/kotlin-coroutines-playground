package `in`.rcard.playground.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("CoroutinesPlayground")
suspend fun main() {
    logger.info("Starting the morning routine")
    forgettingTheBirthDayRoutine()
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
//    Thread.sleep(1500L)
}

suspend fun morningRoutineWithCoffee() {
    coroutineScope {
        val bathTimeJob: Job = launch {
            bathTime()
        }
        val boilingWaterJob: Job = launch {
            boilingWater()
        }
        bathTimeJob.join()
        boilingWaterJob.join()
        launch {
            preparingCoffee()
        }
    }
}

suspend fun structuralConcurrentMorningRoutineWithCoffee() {
    coroutineScope {
        coroutineScope {
            launch {
                bathTime()
            }
            launch {
                boilingWater()
            }
        }
        launch {
            preparingCoffee()
        }
    }
}

suspend fun breakfastPreparation() {
    coroutineScope {
        val coffee: Deferred<String> = async {
            preparingJavaCoffee()
        }
        val toast: Deferred<String> = async {
            toastingBread()
        }
        logger.info("I'm eating ${coffee.await()} and ${toast.await()}")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun workingHardRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1)
    coroutineScope {
        launch(dispatcher) {
            workingHard()
        }
        launch(dispatcher) {
            takeABreak()
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun workingConsciousnessRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1)
    coroutineScope {
        launch(dispatcher) {
            workingConsciousness()
        }
        launch(dispatcher) {
            takeABreak()
        }
    }
}

suspend fun forgettingTheBirthDayRoutine() {
    coroutineScope {
        val workingJob = launch {
            workingConsciousness()
        }
        launch {
            delay(2000L)
            workingJob.cancel()
            workingJob.join()
            logger.info("I forgot the birthday! Let's go to the mall!")
        }
    }
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

suspend fun preparingCoffee() {
    logger.info("Preparing coffee")
    delay(500L)
    logger.info("Coffee prepared")
}

suspend fun preparingJavaCoffee(): String {
    logger.info("Preparing coffee")
    delay(500L)
    logger.info("Coffee prepared")
    return "Java coffee"
}

suspend fun toastingBread(): String {
    logger.info("Toasting bread")
    delay(1000L)
    logger.info("Bread toasted")
    return "Toasted bread"
}

suspend fun workingHard() {
    logger.info("Working")
    while (true) {
        // Do nothing
    }
    delay(100L)
    logger.info("Work done")
}

suspend fun takeABreak() {
    logger.info("Taking a break")
    delay(1000L)
    logger.info("Break done")
}

suspend fun workingConsciousness() {
    logger.info("Working")
    while (true) {
        delay(100L)
    }
    logger.info("Work done")
}