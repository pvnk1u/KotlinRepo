package chapter7

import java.beans.PropertyChangeListener

class Person(val name:String,age:Int,salary:Int):PropertyChangeAware() {

    var age:Int =age
        set(newValue){
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("age",oldValue,newValue)
        }

    var salary:Int = salary
        set(newValue){
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("salary",oldValue,newValue)
        }
}

fun main() {
    val p = Person("Dmitry",34,2000)
    p.addPropertyChangeListener(
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