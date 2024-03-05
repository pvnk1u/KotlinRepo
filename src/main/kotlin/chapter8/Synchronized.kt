package chapter8

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

inline fun <T> synchronized(lock: Lock,action:() -> T):T{
    lock.lock()
    try {
        return action()
    }finally {
        lock.unlock()
    }
}

fun foo(l: Lock){
    println("Before sync")
    synchronized(l){
        println("Action")
    }
    println("After sync")
}

fun main() {
    val l = ReentrantLock()
    synchronized(l){
        // ...
    }

    foo(l)
    //    Before sync
    //    Action
    //    After sync
}