package com.lession.app

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

suspend fun registrarUsuario(
    emailInput: String,
    passwordInput: String,
    nombre: String,
    apellido: String
): Result<Unit> {
    return try {
        supabase.auth.signUpWith(Email) {
            this.email = emailInput
            this.password = passwordInput
            this.data = buildJsonObject {
                put("nombre", nombre)
                put("apellido", apellido)
            }
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun iniciarSesion(
    emailInput: String,
    passwordInput: String
): Result<Unit> {
    return try {
        supabase.auth.signInWith(Email) {
            this.email = emailInput
            this.password = passwordInput
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun guardarPerfil(
    nombre: String,
    apellido: String,
    edad: Int?,
    genero: String?,
    tipoCuerpo: String?,
    altura: Double?,
    peso: Double?,
    objetivo: String?,
    nivelActividad: String?
): Result<Unit> {
    return try {
        val user = supabase.auth.currentUserOrNull()
            ?: return Result.failure(Exception("Usuario no autenticado"))

        supabase.postgrest.from("profiles").update(
            {
                set("nombre", nombre)
                set("apellido", apellido)
                set("edad", edad)
                set("genero", genero)
                set("tipo_cuerpo", tipoCuerpo)
                set("altura", altura)
                set("peso", peso)
                set("objetivo", objetivo)
                set("nivel_actividad", nivelActividad)
            }
        ) {
            filter {
                eq("id", user.id)
            }
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun obtenerPerfil(): Profile? {
    return try {
        val user = supabase.auth.currentUserOrNull() ?: return null
        supabase.postgrest.from("profiles").select {
            filter {
                eq("id", user.id)
            }
        }.decodeSingleOrNull<Profile>()
    } catch (e: Exception) {
        null
    }
}

suspend fun cerrarSesion() {
    try {
        supabase.auth.signOut()
    } catch (e: Exception) {
        // Log error
    }
}
