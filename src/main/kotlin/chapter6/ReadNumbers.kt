package chapter6

import java.io.BufferedReader
import java.lang.NumberFormatException

fun readNumbers(reader: BufferedReader): List<Int?>{
    // 创建包含可空Int值的列表
    var result = ArrayList<Int?>()
    for (line in reader.lineSequence()){
        try {
            val number = line.toInt()
            // 向列表添加整数（非空值）
            result.add(number)
        }catch (e: NumberFormatException){
            // 向列表添加null，因为当前行不能被解析成整数
            result.add(null)
        }
    }
    return result
}