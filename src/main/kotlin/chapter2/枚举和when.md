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
使用函数实现对像`(1+2)+4`这样简单的算术表达式求值。这个表达式只包含一种运算：对两个数字求和。其他的算术运算（减法、乘法、除法）都可以用相似的方式实现。

首先，使用树状结构的形式编码这种表达式，树状结构中每个节点要么是一次求和（Sum）要么是一个数字（Num）。Num永远都是叶子节点，而Sum节点有两个子节点：它们是求和运算的两个参数。
```kotlin
// 声明接口
interface Expr
//  简单的值对象类，只有一个属性value，实现了Expr接口
class Num(val value:Int) : Expr

// Sum运算的实参可以是任何Expr:Num或者另一个Sum，实现了Expr接口
class Sum(val left:Expr,val right:Expr) : Expr


fun eval(e:Expr):Int{
    if (e is Num){
        // 显式地转换成类型Num是多余的
        val n = e as Num
        return n.value
    }
    if (e is Sum){
        // 变量e被智能地转换了类型
        return eval(e.right) + eval(e.left)
    }
    throw IllegalArgumentException("Unknown expression")
}

fun main(args: Array<String>){
    println(eval(Sum(Sum(Num(1),Num(2)),Num(4))))
}
```
在这段代码中，一个叫作Expr的接口和它的两个实现类Num和Sum。注意Expr接口没有声明任何方法，它只是一个标记接口，用来给不同种类的表达式提供一个公共的类型。声明类的时候，使用一个冒号(:)后面跟上接口名称，来标记这个类实现了这个接口。

Sum存储了Expr类型的实参left和right的引用。在这个小例子中，它们要么是Num要么是Sum。为了存储前面提到的表达式(1+2)+4，会创建这样一个对象`Sum(Sum(Num(1),Num(2)),Num(4))`。

`Expr`接口有两种实现，所以为了计算出表达式的结果值，得尝试两种选项：

1. 如果表达式是一个数字，直接返回它的值。
2. 如果是一次求和，得先计算左右两个表达式的值，再返回它们的和。

在`Kotlin`中，要使用`is`检查来判断一个变量是否是某种类型。`is`检查和`Java`中的`instanceOf`相似。但是在`Java`中，如果已经检查过一个变量是某种类型并且要把它当作这种类型来访问其成员时，在`instanceOf`检查之后还需要显式地加上类型转换。如果最初的变量会使用超过一次，常常选择把类型转换的结果存储在另一个单独的变量里。在`Kotlin`中，编译器完成了这些工作。如果检查过一个变量是某种类型，后面就不再需要转换它，可以就把它当作检查过的类型使用。事实上编译器自动执行了类型转换，这种行为被称为智能转换。

在`eval`函数中，在检查过变量`e`是否为`Num`类型之后，编译器就把它当成`Num`类型的变量解释。于是不需要显式转换就可以像这样访问`Num`的属性`value: e.value`。`Sum`的属性`left`和`right`也是这样：在对应的上下文中，只需要写`e.right`和`e.left`。在`IDE`中，这种智能转换过的值会用不同的背景颜色着重表示，这样更容易发现这个值的类型是事先检查过的。

`智能转换只在变量经过`is`检查且之后不再发生变化的情况下有效。当你对一个类的属性进行智能转换的时候，就像这个例子中一样，这个属性必须是一个`val`属性，而且不能有自定义的访问器。否则，每次对属性的访问是否都能返回同样的值将无从验证。`

`使用`as`关键字来表示到特定类型的显式转换`：
```kotlin
val n = e as Num
```





