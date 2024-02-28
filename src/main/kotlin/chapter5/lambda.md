`Lambda`表达式，本质上就是可以传递给其他函数的一小段代码。有了`Lambda`，可以轻松地把通用的代码结构抽取成库函数，`Kotlin`标准库就大量地使用了它们。



# Lambda表达式和成员引用



## Lambda简介：作为函数参数的代码块

在代码中存储和传递一小段行为是常有的任务。例如，常常需要表达像这样的想法：“当一件事情发生的时候运行这个事件处理器”又或者是“把这个操作应用到这个数据结构中所有的元素上”。在老版本的`Java`中，可以用匿名内部类来实现。但是语法太罗嗦了。



函数式编程提供了另外一种解决问题的方法：把函数当作值来对待。可以直接传递函数，而不需要先声明一个类再传递这个类的实例。使用`lambda`表达式之后，代码会更加简洁。都不需要声明函数了：相反，可以高效地直接传递代码块作为函数参数。



来看一个例子。假设要定义一个点击按钮的行为，添加一个负责处理点击的监听器。监听器实现了相应的接口`OnClickListener`和它的一个方法`onClick`。

```java
/* 
	Java
	用匿名内部类实现监听器
*/
button.setOnClickListener(new OnClickListener(){
	@Override
	public void onClick(View view){
		/* 点击后执行的动作 */
	}
});	
```



这些啰嗦的声明匿名内部类的写法重复多次之后让人烦躁。这种行为的表示法——点击的时候要做什么——有助于消除冗余的代码。在`Kotlin`中，可以像`Java8`一样使用`lambda`。

```kotlin
button.setOnClickListener{
	/* 点击后执行的动作	*/
}
```

这段`Kotlin`代码和`Java`的匿名内部类做的事情一模一样，但是更简洁易读。



看过了这个`lambda` 如何被当作只有一个方法的匿名对象的替代品使用之后，继续再看看另一种`lambda`表达式的经典用途：和集合一起工作。



## Lambda和集合

良好编程风格的主要原则之一是避免代码中的任何重复。开发中对集合执行的大部分任务都遵循几个通用的模式，所以实现这几个模式的代码应该放在一个库里。但是没用`lambda`的帮助，很难为使用集合提供一个好用方便的库。



来看一个例子。会用到一个`Person`类，它包含了这个人的名字和年龄信息：

```kotlin
data class LambdaDataClassPerson(val name:String,val age:Int) {

}

fun findTheOldest(people: List<LambdaDataClassPerson>){
    var maxAge = 0
    var theOldest: LambdaDataClassPerson? = null;
    for (person in people){
        if (person.age > maxAge){
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun main(args: Array<String>){
    val people = listOf(LambdaDataClassPerson("Alice",29),LambdaDataClassPerson("Bob",31))
    findTheOldest(people)
    // LambdaDataClassPerson(name=Bob, age=31)
      
}
```

`Kotlin`有更好的方法，可以使用库函数。

```kotlin
val people = listOf(LambdaDataClassPerson("Alice",29),LambdaDataClassPerson("Bob",31))
println(people.maxBy { it.age })
// LambdaDataClassPerson(name=Bob, age=31)
```

`maxBy`函数可以在任何集合上调用，且只需要一个实参：一个函数，指定比较哪个值来找到最大元素。花括号中的代码`{it.age}`就是实现这个逻辑的`lambda`。它接收一个集合中的元素作为实参（使用it引用它）并且返回用来比较的值。



如果`lambda`刚好是函数或者属性的委托，可以用成员引用替换。

```kotlin
people.maxBy(Person::age)
```



## Lambda表达式的语法

如前所述，一个`lambda`把一小段行为进行编码，能把它当作值到处传递。它可以被独立地声明并存储到一个变量中。但是更常见的还是直接声明它并传递给函数。



`Kotlin`的`lambda`表达式始终用花括号包围。



可以把`lambda`表达式存储到一个变量中，把这个变量当作普通函数对待（即通过相应实参调用它）：

```kotlin
val sum = {x:Int,y:Int -> x + y}
println(sum(1,2))
```

如果愿意，还可以直接调用`lambda`表达式：

```kotlin
{println(42)}()
// 42
```

但是这样的语法毫无可读性，也没有什么意义（等价于直接执行`lambda`函数体中的代码）。如果确实需要把一小段代码封闭在一个代码块中，可以使用库函数`run`来执行传给它的`lambda`：

```kotlin
run {println(42)}
// 42
```



回到之前寻找年龄最大的人的代码：

```kotlin
val people = listOf(LambdaDataClassPerson("Alice",29),LambdaDataClassPerson("Bob",31))
println(people.maxBy{it.age})
```

如果不用任何简明语法来重写这个例子，会得到下面的代码：

```kotlin
people.maxBy({p:Person -> p.age})
```

这段代码一目了然：花括号中的代码片段是`lambda`表达式，把它作为实参传给函数。这个`lambda`接收一个类型为`Person`的参数并返回它的年龄。



但是这段代码有点啰嗦。首先，过多的标点符号破坏了可读性。其次，类型可以从上下文推断出来并可以省略。最后，这种情况下不需要给`lambda`的参数分配一个名称。



来改进这些地方，先从花括号开始。`Kotlin`有这样一种语法约定，如果`lambda`表达式是函数调用的最后一个实参，它可以放到花括号的外边。这个例子中，`lambda`是唯一的实参，所以可以放到括号的后边：

```kotlin
people.maxBy(){p:Person -> p.age}
```

当`lambda`是函数唯一的实参时，还可以去掉调用代码中的空括号对：

```kotlin
people.maxBy{p:Person -> p.age}
```

三种语法形式含义都是一样的，但最后一种最易读。如果`lambda`是唯一的实参，当然愿意在写代码的时候省掉这些括号。而当有多个实参时，既可以把`lambda`留在括号内来强调它是一个实参，也可以把它放在括号的外边。如果想传递多个`lambda`，不能把超过一个的`lambda`放到外边。这时使用常规语法来传递它们是更好的选择。



`Kotlin`标准库有一个函数是`joinToString`函数，这个函数可以用`toString`函数以外的方法来把一个元素转换成字符串：

```kotlin
val people = listOf(Person("Alice",29),Person("Bob",31))
val names = people.joinToString(separator = " ",
								transform = {p:Person -> p.name})
println(names)
// Alice  Bob
```

下面的例子展示了可以怎样重写这个调用，把`lambda`放在括号外。

```kotlin
people.joinToString(" "){p: Person -> p.name}
```



继续简化语法，移除参数的类型：

```kotlin
people.maxBy{p:Person -> p.age}
people.maxBy{p -> p.age}
```

和局部变量一样，如果`lambda`参数的类型可以被推导出来，就不需要显式地指定它。以这里的`maxBy`函数为例，其参数类型始终和集合地元素类型相同。编译器知道是对一个`Person`对象的集合调用`maxBy`函数，所以它能推断`lambda`也会是`Person`类型。



这个例子中能做的最后简化是使用默认参数名称`it`代替命名参数。**如果当前上下文期望的是只有一个参数的`lambda`且这个参数的类型可以推断出来，就会生成这个名称。**

```kotlin
people.maxBy{it.age}
```

**仅在实参名称没有显式地指定时这个默认的名称才会生成。**



如果用变量存储`lambda`，那么就没有可以推断出参数类型的上下文，所以必须显式地指定参数类型。

```kotlin
val getAge = {p: Person ->p.age}
people.maxBy(getAge)
```



迄今为止，看到的例子都是由单个表达式或语句构成的`lambda`。但是`lambda`并没有被限制在这样小的规模，它可以包含更多的语句。下面这种情况，最后一个表达式就是`lambda`的结果：

```kotlin
val sum = {x:Int,y:Int -> 
			println("Computing the sum of $x and $y...")
			x + y
}

println(sum(1,2))
// Computing the sum of 1 and 2...
3
```



## 在作用域中访问变量

当在函数内声明一个匿名内部类的时候，能够在这个匿名类内部引这个函数的参数和局部变量。也可以用`lambda`做同样的事情。如果在函数内部使用`lambda`，也可以访问这个函数的参数，还有在`lambda`之前定义的局部变量。



用标准库函数`forEach`来展示这种行为。它是最基本的集合操作函数之一：它所做的全部事情就是在集合中的每一个元素上都调用给定的`lambda`。`forEach`函数比普通的`for`循环更简洁一些。



下面的代码接收一个消息列表，把每条消息都加上相同的前缀打印出来：

```kotlin
fun printMessageWithPrefix(messages: Collection<String>,prefix: String){
    // 接收lambda作为实参，指定对每个元素的操作
    messages.forEach{
        // 在lambda中访问prefix参数
        println("$prefix $it")
    }
}



fun main(args:Array<String>){
    val erros = listOf("403 Forbidden","404 Not Found")
    printMessageWithPrefix(erros,"Error:")
    // Error: 403 Forbidden
    // Error: 404 Not Found
}
```

**这里`kotlin`和`Java`的一个显著区别是，在`Kotlin`中不会仅限于访问`final`变量，在`lambda`内部也可以修改这些变量。**下面这个代码清单对给定的响应状态码`set`中的客户端和服务器错误分别计数：

```kotlin
fun printProblemCounts(responses: Collection<String>){
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach{
        if(it.startsWith("4")){
            clientErrors++
        }else if(it.startsWith("5")){
            serverErrors++
        }
    }
    println("$clientErrors client errors,$serverErrors server errors")
}

fun main(args:Array<String>){
    val responses = listOf("200 OK","418 I`m a teapot",
                            "500 Internal Server Error")
    printProblemCounts(responses)
    // 1 client errors,1 server errors
}
```

和`Java`不一样，`Kotlin`允许在`lambda`内部访问非`final`变量甚至修改它们。从`lambda`内访问外部变量，我们称这些变量被`lambda`捕捉，就像这个例子中的`prefix`、`clientErrors`以及`serverErrors`一样。



注意：默认情况下，局部变量的声明周期被限制在声明这个变量的函数中。但是如果它被`lambda`捕捉了，使用这个变量的代码可以被存储并稍后再执行。





## 成员引用

已经试验过把代码块作为参数传递给函数。但是如果想要当作参数传递的代码已经被定义成了函数，该怎么办？当然可以传递一个调用这个函数的`lambda`，但这样做有点多余，那么能直接传递函数吗？



`Koltin`和 `Java8`一样，如果把函数转换成一个值，就可以传递它。使用`::`运算符来转换：

```kotlin
val getAge = Person::age
```

**这种表达式称为成员引用，它提供了简明语法，来创建一个调用单个方法或者访问单个属性的函数值。双冒号把类名称与要引用的成员（一个方法或者一个属性）名称隔开。**



这是一个更简洁的`lambda`表达式，它做同样的事情：

```kotlin
val getAge = {person : Person -> person.age}
```

注意，不管引用的是函数还是属性，都不要在成员引用的名称后面加括号。



成员引用和调用该函数的`lambda`具有一样的类型，所以可以互换使用：

```kotlin
people.maxBy(Person::age)
```

还可以引用顶层函数（不是类的成员）：

```kotlin
fun salute() = println("Salute!")
// 引用顶层函数
run(::salute)
// Salute!
```

这种情况下，省略了类名称，直接以`::`开头。成员引用`::salute`被当作实参传递给库函数`run`，它会调用相应的函数。



如果`lambda`要委托给一个接收多个参数的函数，提供成员引用代替它将会非常方便：

```kotlin
// 这个lambda委托给sendEmail函数
val action = {person: Person,message:String ->
			sendEmail(person,message)}

// 可以用成员引用代替
val nextAction = ::sendEmail
```

可以用构造方法引用存储或者延期执行创建类实例的动作。构造方法引用的形式是在双冒号后指定类名称：

```kotlin
data class LambdaConstructor(val name:String,val age:Int) {
}

fun main(args:Array<String>){
    val createObj = ::LambdaConstructor
    val p = createObj("Alice",29)
    println(p)
}
```

还可以用同样的方式引用扩展函数：

```kotlin
fun Person.isAdult() = age >= 21
val predicate = Person::isAdult
```

尽管`isAdult`不是`Person`类的成员，还是可以通过引用访问它，这和访问实例的成员没什么两样：`person.isAdult()`。



# 集合的函数式API

函数式编程风格在操作集合时提供了很多优势。大多数任务都可以通过库函数完成来简化代码。



## 基础：filter和map

`filter`和`map`函数形成了集合操作的基础，很多集合操作都是借助它们来表达的。



每个函数都会有两个例子，一个使用数字，另一个使用熟悉的`Person`类：

```kotlin
data class Person(val name:String,val age:Int)
```

`filter`函数遍历集合并选出应用给定`lambda`后会返回`true`的那些元素：

```kotlin
val list = listOf(1,2,3,4)
println(list.filter{it % 2 == 0})
// [2,4]
```

上面的结果是一个新的集合，它只包含输入集合中那些满足判断式的元素。



如果想留下超过30岁的人，可以这样：

```kotlin
val people = listOf(Person("Alice",29),Person("Bob",31))
println(people.filter{it.age > 30})
// [Person(name=Bob,age=31)]
```

`filter`函数可以从集合中移除不想要的元素，但是它并不会改变这些元素。元素的变换是`map`的用武之地。



`map`函数对集合中的每一个元素应用给定的函数并把结果收集到一个新集合。可以把数字列表变换成它们平方的列表，比如：

```kotlin
val list = listOf(1,2,3,4)
println(list.map{it * it})
// [1,4,9,16]
```

结果是一个新集合，包含的元素个数不变，但是每个元素根据给定的判断式做了变换。



如果想打印的只是一个名字列表，而不是人的完整信息列表，可以使用`map`来变换列表：

```kotlin
val people = listOf(Person("Alice",29),Person("Bob",31))
println(people.map{it.name})
// [Alice,Bob]
```

注意，这个例子可以用成员引用漂亮地重写：

```kotlin
people.map(Person::name)
```

可以轻松地把多次这样的调用链接起来。例如，打印出年龄超过30岁的人的名字：

```kotlin
people.filter{it.age >30 }.map(Person::name)
```



还可以对`map`应用过滤和变换函数：

```kotlin
val numbers = mapOf(0 to "zero",1 to "one")
println(numbers.mapValues{it.value.toUpperCase()})
// {0=zero,1=one}
```

键和值分别由各自的函数来处理。`filterKeys`和`mapKeys`过滤和变换`map`的键，而另外的`filterValues`和`mapValues`过滤和变换对应的值。



## all、any、count和find：对集合应用判断式

另一种常见的任务是检查集合中的所有元素是否都符合某个条件（或者它的变种，是否存在符合的元素）。`Kotlin`中，它们是通过`all`和`any`函数表达的。`count`函数检查有多少元素满足判断式，而`find`函数返回第一个符合条件的元素。



为了演示这些函数，定义一个判断式`canBeInClub27`，来检查一个人是否还没有到28岁：

```kotlin
val canBeInClub27 = {p:Person -> p.age <= 27}
```



使用这些函数：

```kotlin
data class Person(val name:String,val age:Int) {

}

fun main(args: Array<String>){
    val canBeInClub27 = {p:Person -> p.age <= 27}
    val people = listOf(Person("Alice",27),Person("Bob",31))
    println(people.all(canBeInClub27))
    // false
    println(people.any(canBeInClub27))
    // true
    println(people.count(canBeInClub27))
    // 1
    // 如果有多个匹配的元素就返回其中第一个元素，或者返回null，还有一个同义方法firstOrNull
    println(people.find(canBeInClub27))
    // Person(name=Alice,age=27)

    val list = listOf(1,2,3)
    // !否定不明显，这种情况下最好使用any
    println(!list.all { it == 3 })
    // true
    // lambda参数中的条件要取反
    println(list.any{it != 3})
    // true
}
```



## groupBy：把列表转换成分组的map

假设需要把所有元素按照不同的特征划分成不同的分组。例如，想把人按年龄分组，相同年龄的人放在一组。把这个特征直接当作参数传递十分方便。`groupBy`函数可以做到这一点：

```kotlin
val people = listOf(Person("Alice",31),
				Person("Bob",29),Person("Carol",31))
println(people.groupBy{it.age})
// {31=[Person(name=Alice, age=31), Person(name=Carol, age=31)], 29=[Person(name=Bob, age=29)]}
```

每一个分组都是存储在一个列表中，结果的类型就是`Map<Int,List<Person>>`。可以使用像`mapKeys`和`mapValues`这样的函数对这个`map`做进一步的修改。



再来看另外一个例子，使用成员引用把字符串按照首字母分组：

```kotlin
val list = listOf("a","ab","b")
println(list.groupBy(String::first))
// {a={a,ab},b={b}}
```

注意，这里`first`并不是`String`类的成员，而是一个扩展。然而，可以把它当作成员引用访问。



## floatMap和flatten：处理嵌套集合中的元素

假设有一堆数据，使用`Book`类表示：

```kotlin
class Book(val title:String,val authors: List<String>) {
}
```

每本书都可能有一个或者多个作者，可以统计出图书馆中的所有作者的`set`：

```kotlin
// 统计出所有的作者
println(books.flatMap { it.authors }.toSet())
```

`flatMap`函数做了两件事情：首先根据作为实参给定的函数对集合中的每个元素做变换（或者说映射），然后把多个列表合并（或者说平铺）成一个列表。下面的例子很好地阐明了这个概念：

```kotlin
val strings = listOf("abc","def")
println(strings.flatMap { it.toList() })
// [a,b,c,d,e,f]
```

字符串上的`toList`函数把它转换成字符列表。如果和`toList`一起使用的是`map`函数，会得到一个字符列表的列表，`flatMap`函数还会执行后面的步骤，并返回一个包含所有元素(字符)的列表。



回到书籍作者的例子：

```kotlin
fun main(args:Array<String>){
    val books = listOf(Book("Thursday Next", listOf("Jasper Fforde")),
                        Book("Mort", listOf("Terry Pratchett")),
                        Book("Good omens", listOf("Terry Pratchett",
                                                        "Neil Gaiman")))

    // 统计出所有的作者
    println(books.flatMap { it.authors }.toSet())
    // [Jasper Fforde, Terry Pratchett, Neil Gaiman]
}
```

每一本书都可能有多位作者，属性`book.authors`存储了每本书籍的作者集合。`flatMap`函数把所有书籍的作者合并成了一个扁平的列表。`toSet`调用移除了结果集合中的所有重复元素。



当卡壳在元素集合的集合不得不合并成一个的时候，可能会想起`flatMap`来。注意，如果不需要做任何变换，只是需要平铺一个集合，可以使用`flatten`函数：`listOfLists.flatten()`。



# 惰性集合操作：序列

前面已经看到了许多链式集合函数调用的例子，比如`map`和`filter`。这些函数会及早地创建中间集合，也就是说每一步的中间结果都被存储到一个临时列表。序列则提供了这些操作的另一种选择，可以避免创建这些临时中间对象。



先来看个例子：

```kotlin
people.map(Person::name).filter{it.startsWith("A")}
```

`filter`和`map`都会返回一个列表。**这意味着上面例子中的链式调用会创建两个列表：一个保存`filter`函数的结果，另一个保存`map`函数的结果。如果源列表只有两个元素，这不是什么问题。但是如果有一百万个元素，这种链式调用就会变得十分低效。**



为了提高效率，可以把操作变成使用序列，而不是直接使用集合：

```kotlin
// 把初始集合转换成序列
people.asSequence()
	.map(Person::name)
	.filter{it.startsWith("A")
    // 把结果序列转换回列表
	.toList()}
```

应用这次操作后的结果和前面的例子一模一样，但是第二个例子没有创建任何用于存储元素的中间集合，所以元素数量巨大的情况下性能将显著提升。



`Kotlin`惰性集合操作的入口就是`Sequence`接口。这个接口表示的就是一个可以逐个列举元素的元素序列。`Sequence`只提供了一个方法，`iterator`，用来从序列中获取值。



`Sequence`接口的强大之处在于其操作的实现方式。序列中的元素求值是惰性的。因此，可以使用序列更高效地对集合元素执行链式操作，而不需要创建额外的集合来保存过程中产生地中间结果。

可以调用扩展函数`asSequence`把任意集合转换成序列，调用`toList`来做反向的转换。



## 执行序列操作：中间和末端操作

**序列操作分为两类：中间的和末端的。一次中间操作返回的是另一个序列，这个新序列知道如何变换原始序列中的元素。而一次末端操作返回的是另一个结果，这个结果可能是集合、元素、数字，或者其他从初始集合的变换序列中获取的任意对象。**



中间操作始终是惰性的。先看下面这个缺少了末端操作的例子：

```kotlin
fun main(args:Array<String>){
    listOf(1,2,3,4).asSequence()
        .map { println("map($it)");it* it }
        .filter { print("filter($it)");it % 2 == 0 }
}
```

**执行这段代码并不会在控制台上输出任何内容。这意味着`map`和`filter`变换被延期了，它们只有在获取结果的时候才会被应用（即末端操作被调用的时候）：**

```kotlin
fun main(args:Array<String>){
    listOf(1,2,3,4).asSequence()
        .map { println("map($it)");it* it }
        .filter { print("filter($it)");it % 2 == 0 }
        .toList()
}
```

**末端操作触发执行了所有的延期计算。**



这个例子中另外一件值得注意的重要事情是计算执行的顺序。一个笨方法是先在每个元素上调用`map`函数，然后在结果序列的每个元素上再调用`filter`函数。`map`和`filter`对集合就是这样做的，而序列不一样。对序列来说，所有操作是按顺序应用在每一个元素上：处理完第一个元素（先映射再过滤），然后完成第二个元素的处理，以此类推。



这种方法意味着部分元素根本不会发生任何变换，如果在轮到它们之前就已经取得了结果。来看一个`map`和`find`的例子。首先把一个数字映射成它的平方，然后找到第一个比数字3大的条目：

```kotlin
println(listOf(1,2,3,4).asSequence()
						.map{it * it}.find{it > 3})
```

如果同样的操作被应用在集合而不是序列上时，那么`map`的结果首先被求出来，即变换初始集合中的所有元素。第二步，中间集合满足判断式的一个元素会被找出来。而对于序列来说，惰性方法意味着可以跳过处理部分元素。这两种方式的区别在于：一种是及早求值（使用集合），一种是惰性求值（使用序列）。



当使用集合的时候，列表被变换成了另一个列表，所以`map`变换应用到每一个元素上，包括了数字3和4。然后，第一个满足判断式的元素被找到了：数字2的平方。



第二种情况，`find`调用一开始就逐个地处理元素。从原始序列中取一个数字，用`map`变换它，然后再检查它是否满足传给`find`的判断式。当进行到数字2时，发现它的平方已经比数字3大，就把它作为`find`操作结果返回了。不再需要继续检查数字3和4，因为已经找到了结果。



使用集合函数式API时应该注意这个特性，提高代码性能。



## 创建序列

前面的例子都是使用同一个方法创建序列：在集合上调用`asSequence()`。另一种可能性是使用`generateSequence`函数。给定序列中的前一个元素，这个函数会计算出下一个元素。下面的例子就是如何使用`generateSequence`计算100以内所有自然数之和：

```kotlin
fun main(args:Array<String>){
    val natureNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = natureNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())
}
```



这个例子中的`natureNumbers`和`numbersTo100`都是有延期操作的序列。这些序列中的实际数字直到调用末端操作（这里是sum）的时候才会求值。



# 带接收者的lambda：with与apply

在`kotlin`的`lambda`函数体内可以调用一个不同对象的方法，而且无须借助任何额外限定符；这种能力在`Java`中是找不到的。这样的`lambda`叫作带接收者的`lambda`。



## with函数

很多语言都有这样的语句，可以用它对同一个对象执行多次操作，而不需要反复把对象的名称写出来。`Kotlin`也不例外，但它提供的是一个叫`with`的库函数，而不是某种特殊的语言结构。



要理解这种用法，先看看下面的例子：

```kotlin
fun alphabet():String{
    val result = StringBuilder()
    for (letter in 'A'..'Z'){
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

fun main(args:Array<String>){
    println(alphabet())
    /*
    ABCDEFGHIJKLMNOPQRSTUVWXYZ
    Now I know the alphabet!
    * */
}
```



下面的例子展示了如何使用`with`来重写这段代码：

```kotlin
/**
 * 使用with重构
 */
fun alphabetUsingWith():String{
    val result = StringBuilder()
    // 指定接收者的值，代码块中会调用它的方法
    return with(result){
        for (letter in 'A'..'Z'){
            // 通过显式地this来调用接收者值的方法
            this.append(letter)
        }
        append("\nNow I know the alphabet!")
        // 从lambda返回值
        this.toString()
    }
}

```

