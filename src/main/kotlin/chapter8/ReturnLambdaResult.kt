package chapter8

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