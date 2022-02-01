package me.ztvr.android.model

import kotlinx.coroutines.delay


class LoginModel {
    suspend fun tryToLogin(login:String, password:String):LoginResult{
        delay(500)
        return if(password=="123")
            LoginResult.successLoginResult("Bearer x")
        else
            LoginResult.errorLoginResult("Password incorrect")
    }
}