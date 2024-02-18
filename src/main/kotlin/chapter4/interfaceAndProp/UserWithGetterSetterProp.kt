package chapter4.interfaceAndProp

interface UserWithGetterSetterProp {

    val email: String
    // 属性没有支持字段：结果值在每次访问时通过计算得到
    val nickname: String
        get() = email.substringBefore('@')
}