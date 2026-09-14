# Solución para el error de 'use streaming syntax' en Ktor 3.x
-keepclassmembers class io.ktor.client.plugins.Messages {
    *;
}

# Evitar advertencias relacionadas con Ktor y Corrutinas
-dontwarn io.ktor.**
-dontwarn kotlinx.coroutines.debug.**
