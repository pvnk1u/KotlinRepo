package chapter4.`object`

class CompanionUser private constructor(val nickname: String){



    companion object{
        fun newSubScribingUser(email:String) =
            CompanionUser(email.substringBefore('@'))

        fun newFacebookUser(accountId: Int) =
            CompanionUser(getFaceBookName(accountId))

        fun getFaceBookName(accountId: Int) = ""
    }
}

fun main(args: Array<String>){
    val subscribingUser = CompanionUser.newSubScribingUser("bob@gmail.com")
    val facebookUser = CompanionUser.newFacebookUser(4)

    println(subscribingUser.nickname)
    // bob
}