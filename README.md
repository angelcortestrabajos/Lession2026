# LESSSION — Android Studio / Kotlin

Proyecto Android en Kotlin + Jetpack Compose basado visualmente en las seis pantallas de la captura suministrada.

## Pantallas
- Bienvenida
- Login
- Configuración de perfil
- Dashboard
- Menú lateral
- Perfil

## Abrir
1. Descomprime `LessionAndroid.zip`.
2. Abre la carpeta raíz en Android Studio.
3. Espera a que Gradle sincronice.
4. Ejecuta el módulo `app` en un dispositivo/emulador Android.

Las imágenes de referencia se incluyen como recursos locales y se muestran a escala completa para conservar la composición original.

## Autenticación y base de datos

La autenticación usa Supabase Auth. Las contraseñas son gestionadas por Supabase y se almacenan con hash; la app nunca las guarda en texto plano. Los datos complementarios del usuario se guardan en `public.profiles`.

1. Crea un proyecto en Supabase y ejecuta [`supabase/schema.sql`](supabase/schema.sql) desde el SQL Editor.
2. En `gradle.properties` local agrega `SUPABASE_URL=...` y `SUPABASE_PUBLISHABLE_KEY=...`. También se pueden pasar como propiedades Gradle con `-PSUPABASE_URL=... -PSUPABASE_PUBLISHABLE_KEY=...`.
3. En Supabase Authentication configura la confirmación de correo según el entorno. Para producción se recomienda mantenerla activada.

La aplicación registra e inicia sesión mediante Supabase Auth y aplica las políticas RLS para que cada usuario solo pueda consultar o actualizar su propio perfil.
