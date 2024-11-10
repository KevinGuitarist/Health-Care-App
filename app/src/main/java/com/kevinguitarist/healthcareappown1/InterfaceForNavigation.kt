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
object LoginDoctorScreen : Destinations {
    override val route: String = "LoginDoctorScreen"
}
object SignUpDoctorScreen : Destinations {
    override val route: String = "SignUpDoctorScreen"
}
object HomeScreenDoctor : Destinations {
    override val route: String = "HomeScreenDoctor"
}
