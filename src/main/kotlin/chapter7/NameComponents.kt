package chapter7

data class NameComponents(val name:String,
                            val extension: String)

fun splitFilename(fullname:String): NameComponents{
    val result = fullname.split(".", limit = 2)
    return NameComponents(result[0],result[1])
}

fun splitFilenameNew(fullname:String): NameComponents{
    val (name,extension) = fullname.split(".", limit = 2)
    return NameComponents(name,extension)
}

fun main() {
    val (name,ext) = splitFilename("example.kt")
    println(name)
    // example
    println(ext)
    // kt

    val (nameNew,extNew) = splitFilenameNew("example.kt")
    println(nameNew)
    // example
    println(extNew)
    // kt
}
