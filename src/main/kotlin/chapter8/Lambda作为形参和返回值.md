在第 5 章已经初步认识了 `lambda`，探讨了 `lambda` 的基本概念和标准库中使用`lambda` 的函数 。`lambda` 是一个构建抽象概念的强大工具，当然它的能力并不局限于标准库中提供的集合和其他类。在这一章将学到如何创建高阶函数一一属于自己的，使用`lambda` 作为参数或者返回值的函数。将会看到高阶函数如何帮助简化代码，去除代码重复，以及构建漂亮的抽象概念。也将认识内联函数——`Kotlin` 的 一个强大特性，它能够消除 `lambda` 带来的性能开销 ，还能够使`lambda` 内的控制流更加灵活 。



# 声明高阶函数

按照定义，**高阶函数就是以另一个函数作为参数或者返回值的函数。**在`Kotlin` 中 ，函数可以用 `lambda` 或者函数引用来表示 。



因此，任何以 `lambda` 或者函数引用作为参数的函数，或者返回值为 `lambda` 或函数引用的函数，或者两者都满足的函数都是高阶函数 。 例如，标准库中的 `filter` 函数将一个判断式函数作为参数，因此它就是一个高阶函数：

```kotlin
list.filter{x > 0}
```

在第 5 章，己经见过很多 `Kotlin` 标准库中的其他高阶函数： `map` 、 `with` ，等等 。现在将学习如何在自己的代码中声明高阶函数 。 要达到这个目标，首先必须要知道什么是函数类型。



## 函数类型

为了声明一个以 `lambda` 作为实参的函数，需要知道如何声明对应形参的类型 。在这之前，先来看一个简单的例子，把 `lambda` 表达式保存在局部变量中。其实己经见过在不声明类型的情况下如何做到这一 点，这依赖于 `Kotlin` 的类型推导：

```kotlin
val sum = {x:Int,y:Int -> x + y}
val action = {println(42)}
```

在这个例子中，编译器推导出 `sum` 和 `action` 这两个变量具有函数类型。现在来看看这些变量的显式类型声明是什么样的：

```kotlin
val sum:(Int,Int) -> Int = {x,y -> x + y}
val action:() -> Unit = {println(42)}
```

声明函数类型，需要将函数参数类型放在括号中，紧接着是一个箭头和函数的返回类型。



**`Unit` 类型用于表示函数不返回任何有用的值 。 在声明一个普通的函数时，`Unit` 类型的返回值是可以省略的，但是一个函数类型声明总是需要一个显式的返回类型，所以在这种场景下 `Unit` 是不能省略的 。**



**注意在 `lambda` 表示式`{x,y -> x + y}`中是如何省略参数 `x` 、 `y` 的类型的 。因为它们的类型己经在函数类型的变量声明部分指定了，不需要在 `lambda` 本身的定义当中再重复声明。**



就像其他方法一样，函数类型的返回值也可以标记为可空类型：

```kotlin
var canReturnNull : (Int,Int) -> Int? = {null}
```

**也可以定义一个函数类型的可空变量。为了明确表示是变量本身可空，而不是函数类型的返回类型可空，需要将整个函数类型的定义包含在括号内并在括号后面添加一个问号：**

```kotlin
var funOrNull: ((Int,Int) -> Int)? = null
```

**注意这个例子和前一个例子的微妙区别。如果省略了括号，声明的将会是一个返回值可空的函数类型，而不是一个可空的函数类型的变量。**



## 调用作为参数的函数

知道了怎样声明一个高阶函数，现在来讨论如何去实现它。第一个例子会尽量简单并且使用之前的 `lambda` `sum` 同样的声明。这个函数实现两个数字 2 和 3 的任意操作，然后打印结果。

```kotlin
fun twoAndThree(operation: (Int,Int) -> Int){
    // 调用函数类型的参数
    val result = operation(2,3)
    println("The Result is $result")
}

fun main() {
    twoAndThree{a,b -> a + b }
    // The result is 5
    twoAndThree{a,b -> a * b}
    // The Result is 6
}
```

调用作为参数的函数和调用普通函数的语法是一样的：把括号放在函数名后，并把参数放在括号内。



来看一个更有趣的例子 ，来实现最常用的标准库函数： `filter` 函数 。为了让事情简单一 点，将实现基于 `String` 类型的 `filter` 函数 ，但和作用于集合的泛型版本的原理是相似的。



`filter` 函数以一个判断式作为参数。判断式的类型是一个函数，以字符作为参数并返回 `boolean` 类型的值。如果要让传递给判断式的字符出现在最终返回的字符串中，判断式需要返回 `true` ， 反之返回 `false` 。以下是这个方法的实现 。

```kotlin
fun String.filter(predicate:(Char) -> Boolean):String{
    val sb = StringBuilder()
    for (index in 0 until length){
        val element = get(index)
        // 调用作为参数传递给predicate的函数
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

fun main() {
    // 传递一个lambda作为predicate参数
    println("ab1c".filter { it in 'a'..'z' })
    // abc
}
```

`filter` 函数的实现非常简单明了。它检查每一个字符是否满足判断式，如果满足就将字符添加到包含结果的 `StringBuilder` 中 。



## 函数类型的参数默认值和null值

声明函数类型的参数的时候可以指定参数的默认值 。 要知道默认值的用处，回头看一看第 3 章讨论过的 `joinToString` 函数。以下是它的最终实现。

```kotlin
fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix:String = ""
): String{
    val result = StringBuilder(prefix)

    for ((index,element) in this.withIndex()){
        if (index > 0) result.append(separator)
        // 使用默认的toString方法将对象转换为字符串
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}
```

这个实现很灵活，但是它并没有让你控制转换的关键点：集合中的元素是如何转换为字符串的。代码中使用了 `StringBuilder.append(o:Any?)`，它总是使用 `toString` 方法将对象转换为字符串。在大多数情况下这样就可以了，但并不总是这样。现在己经知道可以传递一个 `lambda` 去指定如何将对象转换为字符串。但是要求所有调用者都传递 `lambda` 是比较烦人的事情，因为大部分调用者使用默认的行为就可以了。为了解决这个问题，可以定义一个函数类型的参数井用 一个`lambda` 作为它的默认值 。

```kotlin
fun <T> Collection<T>.joinToStringWithLambda(
    separator:String = ",",
    prefix:String = "",
    postfix:String = "",
    transform: (T) -> String = {it.toString()}
):String{
    val result = StringBuilder(prefix)

    for ((index,element) in this.withIndex()){
        if (index > 0) result.append(separator)
        result.append(transform(element))
    }

    result.append(postfix)
    return result.toString()
}

fun main() {
    val letters = listOf("Alpha","Beta")
    println(letters.joinToString())
    // Alpha,Beta
    println(letters.joinToStringWithLambda { it.lowercase() })
    // alpha,beta
    println(letters.joinToString(separator = "! ", postfix = "! ", transform = {it.uppercase()}))
    // ALPHA! BETA!
}
```

注意这是一个泛型函数： 它有一个类型参数 `T` 表示集合中的元素的类型。`Lambda transform` 将接收这个类型的参数。



声明函数类型的默认值并不需要特殊的语法一一只需要把 `lambda` 作为值放在`=`号后面。上面的例子展示了不同的函数调用方式： 省略整个 `lambda` （使用默认的`toString()`做转换），在括号以外传递 `lambda`，或者以命名参数形式传递。



**另一种选择是声明一个参数为可空的函数类型。注意这里不能直接调用作为参数传递进来的函数： `Kotlin` 会因为检测到潜在的空指针异常而导致编译失败。一种可边的办法是显式地检查 `null`:**

```kotlin
fun foo(callback: (()-> Unit)?){
	// ...
	if(callback != null){
		callback()
	}
}
```

**还有一个更简单的版本，它利用了这样一个事实，函数类型是一个包含`invoke` 方法的接口的具体实现。作为一个普通方法 ，`invoke` 可以通过安全调用语法被调用：`callback?.invoke()`。**



下面介绍使用这项技术重写 `joinToString` 函数。

```kotlin
/**
 * 可空Lambda作为函数形参
 */
fun <T> Collection<T>.joinToStringWithNullableLambda(
    separator:String = ",",
    prefix:String = "",
    postfix:String = "",
    transform: ((T) -> String)? = null    // 声明一个函数类型的可空参数
):String{
    val result = StringBuilder(prefix)

    for ((index,element) in this.withIndex()){
        if (index > 0) result.append(separator)
        // 使用安全调用语法调用函数
        val str = transform?.invoke(element)
            ?: element.toString()       // 使用Elvis运算符处理回调没有被指定的情况
        result.append(str)
    }

    result.append(postfix)
    return result.toString()
}

```



## 返回函数的函数

从函数中返回另一个函数并没有将函数作为参数传递那么常用，但它仍然非常有用。想象一下程序中的一段逻辑可能会因为程序的状态或者其他条件而产生变化——比如说，运输费用的计算依赖于选择的运输方式 。可以定义一个函数用来选择恰当的逻辑变体并将它作为另一个函数返回。以下是具体的代码。

```kotlin
enum class Delivery{STANDARD,EXPEDITED}

class Order(val itemCount:Int)

fun getShippingCostCalculator(
    delivery: Delivery):(Order) ->Double{         // 声明一个返回函数的函数
        if (delivery == Delivery.EXPEDITED){
            return {order -> 6 + 2.1 * order.itemCount }    // 返回lambda
        }
        return {order -> 1.2 *order.itemCount }            // 返回lambda
}


fun main() {
    // 将返回的函数保存在变量中
    val calculator =
        getShippingCostCalculator(Delivery.EXPEDITED)
    // 调用返回的函数
    println("Shipping costs ${calculator(Order(3))}")
    // Shipping costs 12.3
}
```

**声明一个返回另一个函数的函数，需要指定一个函数类型作为返回类型。在代码清单，`getShippingCostCalculator` 返回了 一个函数 ，这个函数以 `Order` 作为参数并返回 一个 `Double` 类型的值。要返回一个函数，需要写一个`return` 表达式，跟上一个 `lambda`、一个成员引用，或者其他的函数类型的表达式，比如一个（函数类型的）局部变量。**





