package chapter7

import java.beans.PropertyChangeSupport
import kotlin.reflect.KProperty

class ObservableProperty(
    var propValue: Int,
    val changeSupport: PropertyChangeSupport
) {

    operator fun getValue(p:Person,prop:KProperty<*>): Int = propValue

    operator fun setValue(p:Person,prop: KProperty<*>,newValue:Int){
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name,oldValue,newValue)
    }
}