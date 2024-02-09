# 枚举
```kotlin
enum class Color {
    RED,ORANGE,YELLOW,GREEN,BLUE,INDIGO,VIOLET
}
```

`Kotlin`声明枚举用了`enum class`两个关键字，而`Java`只有`enum`一个关键字。`Kotlin`中，`enum`是一个所谓的软关键字：只有当它出现在`class`前面时才有特殊的意义，在其他地方可以把它当作普通的名称使用。与此不同的是，`class`仍然是一个关键字，要继续使用名称`clazz`和`aClass` 来声明变量。

和`Java`一样，枚举并不是值的列表：可以给枚举类声明属性和方法：
```kotlin
enum class Color(
    // 声明枚举常量的属性
    val r:Int,val g:Int,val b:Int
){
    RED(255,0,0),ORANGE(255,165,0),
    YELLOW(255,255,0),GREEN(0,255,0),BLUE(0,0,255),
    INDIGO(75,0,130),VIOLET(238,130,238);

    // 枚举类中的方法
    fun rgb() = (r*256 +g) * 256 +b
}
```

# when
在`Java`中使用`switch`来进行相应值的选择和判断，而`Kotlin`对应的结构是`when`，和`if`相似，`when`是一个有返回值的表达式，因此可以写一个直接返回`when`表达式的表达式体函数：
```kotlin
fun getMnemonic(color: Color)=
    when(color){
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }
println(getMnemonic(Color.BLUE))
// Battle
```

和`Java`不一样，不需要在每个分支上都写上`break`语句（在`Java`中遗漏`break`通常会导致bug）。如果匹配成功，只有对应的分支会执行。也可以把多个值合并到同一个分支，只需要用逗号隔开这些值：
```kotlin
import java.awt.Color

fun getWarmth(color: Color) = when(color){
    Color.RED,Color.ORANGE,Color.YELLOW -> "warm"
    Color.GREEN-> "neutral"
    Color.BLUE,Color.INDIGO,Color.VIOLET -> "cold"
}

println(getWarmth(Color.ORANGE))
// warm
```

这些例子用的都是枚举常量的完整名称，即指定了枚举类的名称`Color`。可以通过导入这些常量值来简化代码：
```kotlin
import chapter2.Color.*

fun getWarmth(color: Color) = when(color){
    RED,ORANGE,YELLOW -> "warm"
    GREEN-> "neutral"
    BLUE,INDIGO,VIOLET -> "cold"
}
```

## 在when结构中使用任何对象
`Kotlin`中的`when`结构比`Java`中的`switch`强大得多。`switch`要求必须使用常量（枚举常量、字符串或者数字字面值）作为分支条件，和它不一样，`when`允许使用任何对象：
```kotlin

fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {//when表达式的参数可以是任何实例，用来被分支条件检查
        setOf(RED, YELLOW) -> ORANGE//枚举可以混合的颜色对
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("Dirty color")//执行这个，如果没有分支可以匹配
    }

println(mix(BLUE,YELLOW))
// 抛出异常
```

## 使用不带参数的when
上面的代码效率多少有点低，每次调用这个函数的时候，它都会创建一些`Set`实例，仅仅用来检查两种给定的颜色是否和另外两种颜色匹配。一般这不是什么大问题，但是如果这个函数调用很频繁，它就非常值得用另一种方式重写，来避免创建额外的垃圾对象。代码可读性会变差，但这是为了达到更好性能而必须付出的代价:
```kotlin
fun mixOptimized(c1:Color,c2: Color) = 
    when{
        (c1 == RED && c2 == YELLOW) || 
        (c1 == YELLOW && c2 == RED) -> ORANGE

        (c1 == YELLOW && c2 == BLUE) || 
        (c1 == BLUE && c2 == YELLOW) -> GREEN

        (c1 == BLUE && c2 == VIOLET) || 
        (c1 == VIOLET && c2 == BLUE) -> INDIGO
        else -> throw Exception("Dirty color")
    }

println(mixOptimized(BLUE,YELLOW))
// 抛出异常
```

## 智能转换：合并类型检查和转换

