package chapter3.strings

/**
 * 对java.lang.String字符串类进行方法扩展，新增获取最后一个字符的lastChar方法
 */
fun String.lastChar():Char = this.get(this.length-1)