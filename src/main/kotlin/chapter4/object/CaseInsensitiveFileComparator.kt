package chapter4.`object`

import java.io.File

object CaseInsensitiveFileComparator: Comparator<File> {

    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path,ignoreCase = true)
    }
}

fun main(args: Array<String>){
    val files = listOf(File("/Z"),File("/a"))
    println(files.sortedWith(CaseInsensitiveFileComparator))
    // [\a, \Z]
}