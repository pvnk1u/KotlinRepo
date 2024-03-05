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



来看另一个返回函数的函数非常实用的例子。假设正在开发一个带 `GUI` 的联系人管理应用，需要通过 `UI` 的状态来决定显示哪一个联系人。例如，可以在 `UI`上输入一个字符串，然后只显示那些姓名以这个宇符串开头的联系人： 还可以隐藏没有电话号码的联系人 。 用 `ContactListFilters` 这个类来保存这些选项的状态 。

```kotlin
class ContactListFilters{
	var prefix: String = ""
	var onlyWithPhoneNumber: Boolean = false
}
```

当用户输入 `D` 来查看姓或者名以 `D` 开头的联系人，`prefix` 的值会被更新。这里省略了必须要更改的代码。



为了让展示联系人列表的逻辑代码和输入过滤条件的 `UI` 代码解棋，可以定义一个函数来创建一个判断式，用它来过滤联系人列表。 判断式检查前缀 ，如果有需要也会检查电话号码是否存在。

```kotlin
data class Person(val firstName:String,
                val lastName: String,
                val phoneNumber: String?)
                
class ContactListFilters{
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person) -> Boolean{
        val startsWithPrefix = {p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber){
            // 返回一个函数类型的变量
            return startsWithPrefix
        }
        // 从这个函数返回一个lambda
        return {startsWithPrefix(it)
                && it.phoneNumber != null}
    }
}

fun main() {
    val contacts = listOf(Person("Dmitry","Jemerov","123-4567"),
                        Person("Svetlana","Isakova",null)
    )
    val contactListFilters = ContactListFilters()
    with(contactListFilters){
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    // 将getPredicate返回的函数作为参数传递给filter函数
    println(contacts.filter(contactListFilters.getPredicate()))
    // [Person(firstName=Dmitry, lastName=Jemerov, phoneNumber=123-4567)]
}
```

**`getPredicate` 方法返回一个函数（类型）的值，这个值被传递给 `filter` 作为参数。`Kotlin` 的函数类型可以让这一切变得简单，就跟处理其他类型的值一 样，比如字符串。**



## 通过lambda去除重复代码

函数类型和 `lambda` 表达式一起组成了一个创建可重用代码的好工具 。许多以前只能通过复杂笨重的结构来避免的重复代码，现在可以通过使用简洁的 `lambda` 表达式被消除 。



来看一个分析网站访问的例子。`SiteVisit` 类用来保存每次访问的路径、持续时间和用户的操作系统。不同的操作系统使用枚举类型来表示。

```kotlin
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
```

想象一下如果需要显示来自 `Windows` 机器的平均访问时间，可以用`average` 函数来完成这个任务。

```kotlin
val averageWindowsDuration = log
    .filter { it.os == OS.WINDOWS }
    .map ( SiteVisit::duration )
    .average()

fun main() {
    println(averageWindowsDuration)
    // 23.0
}
```

现在假设要计算来自 `Mac` 用户的相同数据，为了避免重复，可以将平台类型抽象为一个参数。

```kotlin
fun List<SiteVisit>.averageDurationFor(os: OS) =
    filter { it.os == os }.map ( SiteVisit::duration ).average()

fun main() {
    println(log.averageDurationFor(OS.WINDOWS))
    // 23.0
    println(log.averageDurationFor(OS.MAC))
    // 22.0
}
```

注意将这个函数作为扩展函数增强了可读性 。 如果它只在局部的上下文中有用，甚至可以将这个函数声明为局部扩展函数 。



但这还远远不够。想象一下，如果对来自移动平台（目前识别出来的只有两种：`iOS` 和 `Android` ）的访问的平均时间非常有兴趣。

```kotlin
/**
 * 用一个复杂的硬编码函数分析站点访问数据
 */
var averageMobileDuration = log
    .filter { it.os in setOf(OS.IOS,OS.ANDROID) }
    .map(SiteVisit::duration)
    .average()

fun main() {
    println(averageMobileDuration)
    // 12.15
}
```

现在己经无法再用一个简单的参数表示不同的平台了 。 可能还需要使用 更加复杂的条件查询日志，比如“来自iOS 平台对注册页面的访问的平均时间是 多少？” `Lambda` 可以帮上忙。可以用函数类型将需要的条件抽取到一个参数中 。

```kotlin
/**
 * 用一个高阶函数去除重复代码
 */
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()

fun main() {
    println(log.averageDurationFor { it.os in setOf(OS.ANDROID,OS.IOS) })
    // 12.15
    println(log.averageDurationFor {
        it.os == OS.IOS && it.path == "/signup"
    })
    // 8.0
}
```

函数类型可以帮助去除重复代码。如果禁不住复制粘贴了 一段代码，那么很可能这段重复代码是可以避免的。使用 `lambda`，不仅可以抽取重复的数据，也可以抽取重复的行为。



# 内联函数：消除lambda带来的运行时开销

`Kotlin` 中传递 `lambda` 作为函数参数的简明语法与普通的表达式（例如 `if` 和 `for` ）语法很相似。在第 5 章讨论 `with` 和 `apply` 这两个函数的时候已经见过。但是它的性能如何呢？要是定义了类似 `Java` 语句的函数但运行起来却慢得多，这难道不是出人意料的不爽吗？



在第 5 章中，已经解释了 **`lambda` 表达式会被正常地编译成匿名类 。这表示每调用 一次 `lambda` 表达式，一个额外的类就会被创建 。并且如果 `lambda` 捕捉了某个变量，那么每次调用的时候都会创建一个新的对象 。 这会带来运行时的额外开销，导致使用 `lambda` 比使用 一个直接执行相同代码的函数效率更低 。**



有没有可能让编译器生成跟 `Java` 语句同样高效的代码，但还是能把重复的逻辑抽取到库函数中呢？是的，`Kotiln` 的编译器能够做到。**如果使用 `inline` 修饰符标记一个函数，在函数被使用的时候编译器并不会生成函数调用的代码，而是使用函数实现的真实代码替换每一次的函数调用。**通过一个具体的例子来看看这到底是怎么运作的 。



## 内联函数如何运作

**当一个函数被声明为 `inline` 时，它的函数体是内联的一一换句话说，函数体会被直接替换到函数被调用的地方，而不是被正常调用。**来看一个例子以便理解生成的最终代码。



下面的函数用于确保一个共享资源不会并发地被多个线程访问。函数锁住一个 `Lock` 对象，执行代码块，然后释放锁。

```kotlin
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

fun main() {
    val l = ReentrantLock()
    synchronized(l){
        // ...
    }
}
```

调用这个函数的语法跟 `Java` 中使用 `synchronized` 语句完全一样。区别在于`Java` 的 `synchronized` 语句可以用于任何对象，而这个函数则要求传入一个 `Lock`实例。这里展示的定义只是一个示例，`Kotlin` 标准库中定义了一个可以接收任何对象作为参数的 `synchronized` 函数的版本。



因为己经将 `synchronized` 函数声明为 `inline` ，所以每次调用它所生成的代码跟 `Java` 的 `synchronized` 语句是一样的。看看下面这个使用 `synchronized()`的例子：

```kotlin
fun foo(l: Lock){
    println("Before sync")
    synchronized(l){
        println("Action")
    }
    println("After sync")
}

fun main() {
    val l = ReentrantLock()
    foo(l)
    //    Before sync
    //    Action
    //    After sync
}
```

下面这段代码展示的是作用相同的代码，将会被编译成同样的字节码：

```kotlin
fun _foo_(l:Lock){
	println("Before sync")
	l.lock()
	try{
		println("Action")
	}finally{
		l.unlock()
	}
	println("After sync")
}
```

**注意 `lambda` 表达式和 `synchronized` 函数的实现都被内联了。由`lambda` 生成 的字节码成为了函数调用者定义的一部分，而不是被包含在一个实现了函数接口的匿名类中。**



注意，在调用内联函数的时候也可以传递函数类型的变量作为参数：

```kotlin
class LockOwner(val lock:Lock){
	fun runUnderLock(body: () -> Unit){
		// 传递一个函数类型的变量作为参数，而不是一个lambda
		synchronized(lock,body)
	}
}
```

在这种情况下 ，`lambda` 的代码在内联函数被调用点是不可用的，因此并不会被内联。只有 `synchronized` 的函数体被内联了，`lambda` 才会被正常调用 。`runUnderLock` 函数会被编译成类似于以下函数的字节码：

```
class LockOwner(val lock:Lock){
	// 这个函数类似于真正的runUnderLock被编译成的字节码
	fun _runUnderLock_(body: () -> Unit){
		lock.lock()
		try{
			// body没有被内联，因为在调用的地方还没有lambda
			body()
		}finally{
			lock.unlock()
		}
	}
}
```

如果在两个不同的位置使用同一个内联函数，但是用的是不同的 `lambda` ，那么内联函数会在每一个被调用的位置被分别内联。内联函数的代码会被拷贝到使用它的两个不同位置，并把不同的 `lambda` 替换到其中。



## 内联函数的限制

鉴于内联的运作方式，不是所有使用`lambda` 的函数都可以被内联。当函数被内联的时候，作为参数的`lambda` 表达式的函数体会被直接替换到最终生成的代码中 。这将限制函数体中的对应(lambda)参数的使用 。 如果（lambda)参数被调用，这样的代码能被容易地内联。但如果(lambda)参数在某个地方被保存起来，以便后面可以继续使用，lambda 表达式的代码将不能被内联 ，因为必须要有一个包含这些代码的对象存在。



一般来说，参数如果被直接调用或者作为参数传递给另外一个 `inline` 函数，它是可以被内联的 。否则，编译器会禁止参数被内联并给出错误信息“ Illegal usageof inline-parameter”（非法使用内联参数）。



例如，许多作用于序列的函数会返回一些类的实例 ， 这些类代表对应的序列操作并接收`lambda` 作为构造方法的参数。以下是 `Sequence.map` 函数 的定义：

```kotlin
fun <T,R> Sequence<T>.map(transform: (T) -> R): Sequence<R>{
	return TransformingSequence(this,transform)
}
```

`map` 函数没有直接调用作为 `transform` 参数传递进来的函数。而是将这个函数传递给一个类的构造方法，构造方法将它保存在一个属性中。为了支持这一点 ，作为 `transform` 参数传递的 `lambda` 需要被编译成标准的非内联的表示法，即一个实现了函数接口的匿名类。



如果一个函数期望两个或更多 `lambda` 参数 ， 可以选择只内联其中 一些参数。这是有道理的，因为一个`lambda` 可能会包含很多代码或者以不允许内联的方式使用。接收这样的非内联`lambda` 的参数，可以用 `noinline` 修饰符来标记它：

```kotlin
inline fun foo(inlined: () -> Unit,noinline notInlined: () -> Unit){
	//...
}
```



## 内联集合操作

来仔细看一看 `Kotlin` 标准库中操作集合的函数的性能。大部分标准库中的集合函数都带有 `lambda` 参数。相比于使用标准库函数，直接实现这些操作不是更高效吗？



例如，来比较以下两个代码清单中用来过滤一个人员列表的方式：

```kotlin
data class Person(val name:String,val age:Int)

val people = listOf(Person("Alice",29),Person("Bob",31))

>>> println(people.filter{it.age < 30})
[Person(name=Alice,age=29)]
```

前面的代码不用 `lambda` 表达式也可以实现，代码如下：

```kotlin
>>> val result = mutableListOf<Person>()
>>> for (person in people){
>>> 	if(person.age < 30) result.add(person)
>>> }
>>> println(result)
[Person(name=Alice,age=29)]
```

在 `Kotlin` 中，`filter` 函数被声明为内联函数。这意味着 `filter` 函数，以及传递给它的 `lambda` 的字节码会被一起内联到 `filter` 被调用的地方。最终，第 一种实现所产生的字节码和第二种实现所产生的字节码大致是一样的。可以很安全地使用符合语言习惯的集合操作，`Kotlin` 对内联函数的支持使得不必担心性能的问题 。



想象一下现在连续调用 `filter` 和 `map` 两个操作。

```kotlin
>>> println(people.filter{it.age > 30})
			.map(Person::name)
[Bob]
```

**这个例子使用了 一个 `lambda` 表达式和一个成员引用。再一 次，`filter` 和 `map`函数都被声明为 `inline` 函数，所以它们的函数体会被内联，因此不会产生额外的类或者对象。但是上面的代码却创建了一个中间集合来保存列表过滤的结果，由`filter` 函数生成的代码会向这个集合中添加元素，而由 `map` 函数生成的代码会读取这个集合。**



**如果有大量元素需要处理，中间集合的运行开销将成为不可忽视的问题，这时可以在调用链后加上一个 `as Sequence` 调用，用序列来替代集合。但正如在前一节中看到的，用来处理序列的 `lambda` 没有被内联。每一个中间序列被表示成把`lambda` 保存在其字段中的对象，而末端操作会导致由每一个中间序列调用组成的调用链被执行。因此，即便序列上的操作是惰性的，也不应该总是试图在集合操作的调用链后加上 `asSeqence` 。这只在处理大量数据的集合时有用，小的集合可以用普通的集合操作处理。**



## 决定何时将函数声明成内联

现在已经知道了 `inline` 关键宇带来的好处，可能己经想要开始在代码中使用 `inline` ，试图让代码运行得更快 。事实证明，这并不是一个好主意。**使用`inline` 关键字只能提高带有 `lambda` 参数的函数的性能，其他的情况需要额外的度量和研究。**



对于普通的函数调用，`JVM`己经提供了强大的内联支持。它会分析代码的执行，并在任何通过内联能够带来好处的时候将函数调用内联。这是在将字节码转换成机器代码时自动完成的。在字节码中，每一个函数的实现只会出现一次，并不需要跟`Kotlin` 的内联函数一样，每个调用的地方都拷贝一次。再说，如果函数被直接调用，调用栈会更加清晰。



另 一方面，将带有 `lambda` 参数的函数内联能带来好处。首先，通过内联避免的运行时开销更明显了。不仅节约了函数调用的开销，而且节约了为 `lambda` 创建匿名类，以及创建 `lambda` 实例对象的开销。其次， `JVM` 目前并没有聪明到总是能将函数调用内联。最后 ，内联使得我们可以使用 一些不可能被普通 `lambda` 使用的特性，比如非局部返回。



## 使用内联lambda管理资源

`Lambda` 可以去除重复代码的一个常见模式是资源管理：先获取一个资源，完成一个操作，然后释放资源。这里 的资源可以表示很多不同的东西： 一个文件、 一个锁、一个数据库事务等。实现这个模式的标准做法是使用 `try/finally` 语句。资源在 `try` 代码块之前被获取，在 `finally` 代码块中被释放 。



在本节的前面部分看到过一个例子，将 `try /finally` 的逻辑封装在一个函数中，然后将使用资源的代码作为 `lambda` 传递给这个方法 。 那个例子展示了`synchronized` 函数，它跟 `Java` 中的 `synchronized` 语句语法一样：将一个锁对象作为参数。`Kotlin` 标准库定义了另 一个叫作 `withLock` 的函数，它提供了实现同样功能的更符合语言习惯的 `API`：它是 `Lock` 接口的扩展函数 。下面来看如何使用它 ：

```kotlin
val l:Lock = ...
l.withLock{
	// ...
}
```

这是`Kotlin`库中`withLock`函数的定义：

```kotlin
fun <T> Lock.withLock(action: () -> T):T{
	lock()
	try{
		return action()
	}finally{
		unlock()
	}
}
```

文件是另一种可以使用这种模式的常见资源类型 。`Java 7` 甚至为这种模式引 入了特殊的语法：`try-with-resource` 语句。下面的代码清单展示了 一个使用这个语句来读取文件第一行的 `Java` 方法。

```java
/** Java */
static String readFirstLineFromFile(String path) throws IOException{
	try(BufferedReader br = 
			new BufferedReader(new FileReader(path))){
				return br.readLine();
			}
}
```

`Kotlin` 中并没有等价的语法，因为通过使用 一个带有函数类型参数的函数 （接收 `lambda` 参数）可以无缝地完成相同的事情 。这个 `Kotlin` 标准库中的函数叫作`use` 。现在使用 `use`函数将上面的代码重写为 `Kotlin` 代码 。

```kotlin
fun readFirstLineFromFile(path:String):String{
	// 构建BufferedReader，调用use函数，传递一个lambda执行文件操作
    BufferedReader(FileReader(path)).use{ br ->
        // 从函数中返回文件的一行
		return br.readLine()
	}
}
```

`use` 函数是一个扩展函数，被用来操作可关闭的资源，它接收一个 `lambda` 作为参数。这个方法调用 `lambda` 并且确保资源被关闭，无论`lambda` 正常执行还是抛出了异常 。当然，`use` 函数是内联函数 ，所以使用它并不会引发任何性能开销。



