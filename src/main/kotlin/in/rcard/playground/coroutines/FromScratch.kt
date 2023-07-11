package `in`.rcard.playground.coroutines

import kotlin.system.exitProcess

interface Cont<R> {
    fun apply(result: R)
}

fun <R> Cont(apply: (R) -> Unit): Cont<R> = object : Cont<R> {
    override fun apply(result: R) = apply(result)
}

// The continuation is always the last parameter of the function
fun add(a: Int, b: Int, cont: Cont<Int>) {
    cont.apply(a + b)
}

fun print(n: Int, cont: Cont<Unit>) {
    println(n)
    cont.apply(Unit)
}

// We try to implement this small program using a continuation-passing style
// System.out.println(1 + 2 + 3);
// System.exit(0);

fun main() {
    add(
        1,
        2,
        Cont { r1 ->
            add(
                r1,
                3,
                Cont { r2 ->
                    print(r2, Cont { exitProcess(0) })
                },
            )
        },
    )
}
