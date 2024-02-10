package chapter2

import java.util.TreeMap



fun main(args: Array<String>) {
    // 使用TreeMap让键排序
    val binaryReps = TreeMap<Char,String>()
    // 使用字符区间迭代从A到F之间的字符
    for(c in 'A'..'F'){
        // 把ASC LL码转换成二进制
        val binary = Integer.toBinaryString(c.code)
        // 根据键c把值存储到map中
        binaryReps[c] = binary
    }

    // 迭代map，把键和值赋给两个变量
    for ((letter,binary) in binaryReps){
        println("$letter = $binary")
    }
}