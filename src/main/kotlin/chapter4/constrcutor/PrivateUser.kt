package chapter4.constrcutor

import chapter4.interfaceAndProp.UserInterfaceWithProp

class PrivateUser(override val nickname:String): UserInterfaceWithProp

class SubscribingUser(val email: String): UserInterfaceWithProp {
    override val nickname: String
        // 自定义getter
        get() = email.substringBefore('@')
}

class FacebookUser(val accountId: Int): UserInterfaceWithProp {
    // 属性初始化
    override val nickname =  getFacebookName(accountId)

    fun getFacebookName(accountId: Int):String{
        return ""
    }
}

