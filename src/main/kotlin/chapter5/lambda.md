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

