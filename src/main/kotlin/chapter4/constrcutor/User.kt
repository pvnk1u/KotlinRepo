package chapter4.constrcutor


class User constructor(_nickname: String) { // 带一个参数的主构造方法
    val nickname: String

    // 初始化语句块
    init {
        nickname = _nickname
    }
}