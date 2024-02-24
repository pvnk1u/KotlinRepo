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



