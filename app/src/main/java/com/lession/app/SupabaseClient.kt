package com.lession.app

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "TU_SUPABASE_URL",
    supabaseKey = "TU_SUPABASE_PUBLISHABLE_KEY"
) {
    install(Auth)
    install(Postgrest)
}
