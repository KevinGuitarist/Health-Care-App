package com.kevinguitarist.healthcareappown1

interface Destinations {
    val route : String
}
object WelcomeScreen : Destinations{
    override val route: String = "WelcomeScreen"
}
object LoginScreen : Destinations{
    override val route: String = "LoginScreen"
}
object SignUpScreen : Destinations{
    override val route: String = "SignUpScreen"
}
object HomeScreen : Destinations {
    override val route: String = "HomeScreen"
}