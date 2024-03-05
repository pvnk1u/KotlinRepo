package chapter8



data class SiteVisit(val path:String,
                    val duration: Double,
                    val os: OS)


enum class OS{WINDOWS,LINUX,MAC,IOS,ANDROID}

val log = listOf(
    SiteVisit("/",34.0,OS.WINDOWS),
    SiteVisit("/",22.0,OS.MAC),
    SiteVisit("/login",12.0,OS.WINDOWS),
    SiteVisit("/signup",8.0,OS.IOS),
    SiteVisit("/",16.3,OS.ANDROID),
)

/**
 * 使用硬编码的过滤器分析站点访问数据
 */
val averageWindowsDuration = log
    .filter { it.os == OS.WINDOWS }
    .map ( SiteVisit::duration )
    .average()

/**
 * 用一个普通方法来去除重复代码
 */
fun List<SiteVisit>.averageDurationFor(os: OS) =
    filter { it.os == os }.map ( SiteVisit::duration ).average()


/**
 * 用一个复杂的硬编码函数分析站点访问数据
 */
var averageMobileDuration = log
    .filter { it.os in setOf(OS.IOS,OS.ANDROID) }
    .map(SiteVisit::duration)
    .average()

/**
 * 用一个高阶函数去除重复代码
 */
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()

fun main() {
    println(averageWindowsDuration)
    // 23.0

    println(log.averageDurationFor(OS.WINDOWS))
    // 23.0
    println(log.averageDurationFor(OS.MAC))
    // 22.0

    println(averageMobileDuration)
    // 12.15


    println(log.averageDurationFor { it.os in setOf(OS.ANDROID,OS.IOS) })
    // 12.15
    println(log.averageDurationFor {
        it.os == OS.IOS && it.path == "/signup"
    })
    // 8.0
}