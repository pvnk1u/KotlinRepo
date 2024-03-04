package chapter7

import java.beans.PropertyChangeListener
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Person(val name:String,age:Int,salary:Int):PropertyChangeAware() {

    private val observer = {
        prop:KProperty<*>,oldValue:Int,newValue:Int ->
        changeSupport.firePropertyChange(prop.name,oldValue,newValue)
    }

    var age: Int by Delegates.observable(age,observer)

    var salary: Int by Delegates.observable(salary,observer)
}

fun main() {
    val p = Person("Dmitry",34,2000)
    p.addPropertyChangeListener(
        // 关联监听器，用于监听属性修改
        PropertyChangeListener { event ->
            println("Property ${event.propertyName} changed " +
                "from ${event.oldValue} to ${event.newValue}")
        }
    )
    p.age = 35
    // Property age changed from 34 to 35
    p.salary = 2100
    // Property salary changed from 2000 to 2100
}