package me.ztvr.android.model

class LoginResult (public val successfulLogged:Boolean,public val bearer:String,public val errorMessage:String) {
    companion object {
        fun successLoginResult(bearer: String): LoginResult {
            return LoginResult(true,bearer,"")
        }
        fun errorLoginResult(errorMessage: String): LoginResult {
            return LoginResult(false,"",errorMessage)
        }
    }
}

