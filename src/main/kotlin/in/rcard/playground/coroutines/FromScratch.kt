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

fun multiply(a: Int, b: Int, cont: Cont<Int>) {
    cont.apply(a * b)
}

fun print(n: Int, cont: Cont<Unit>) {
    println(n)
    cont.apply(Unit)
}

// We try to implement this small program using a continuation-passing style
// System.out.println(1 + 2 + 3);
// System.exit(0);

// static long sum(int from, int to) {
//   long sum = 0;
//   for (int i = from; i <= to; i++) {
//     sum += i;
//   }
//   return sum;
// }

// }

// static long sum_rec(int from, int to) {
//   if (from > to) {
//     return 0;
//   } else {
//     return from + sum_rec(from + 1, to);
//   }
fun sum_rec(from: Int, to: Int, cont: Cont<Int>): Unit =
    if (from > to) {
        cont.apply(0)
    } else {
        add(from, 1, Cont { from1 -> sum_rec(from1, to, Cont { r2 -> add(from, r2, cont) }) })
    }

// static int factorial(int n) {
//   if (n == 0) return 1;
//   return factorial(n-1)*n;
// }
fun factorial(n: Int, cont: Cont<Int>): Unit =
    if (n == 0) cont.apply(1)
    else {
        add(n, -1, Cont { n1 -> factorial(n1, Cont { r -> multiply(n, r, cont) }) })
    }

// fun fib1(n: Int): Int {
//    return if (n < 2) 1 else fib1(n - 1) + fib1(n - 2)
// }
// TODO

val exit = Cont<Unit> { exitProcess(0) }

fun main() {
    factorial(6, Cont { result -> print(result, exit) })
}
