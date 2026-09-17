-- Base de datos de Lession
-- Ejecutar este script en Supabase SQL Editor.
-- Las contraseñas NO se guardan en esta tabla: Supabase Auth las almacena
-- de forma segura en auth.users y la app solo usa signUp/signIn.

create table if not exists public.profiles (
    id uuid primary key references auth.users(id) on delete cascade,
    nombre text not null default '',
    apellido text not null default '',
    email text not null default '',
    edad integer check (edad is null or edad between 1 and 120),
    genero text,
    tipo_cuerpo text,
    altura numeric(5,2) check (altura is null or altura between 30 and 300),
    peso numeric(5,2) check (peso is null or peso between 1 and 500),
    objetivo text,
    nivel_actividad text,
    avatar_url text,
    bluetooth_conectado boolean not null default false,
    wifi_conectado boolean not null default false,
    idioma text not null default 'Español',
    created_at timestamptz not null default timezone('utc', now()),
    updated_at timestamptz not null default timezone('utc', now())
);

alter table public.profiles enable row level security;

drop policy if exists "Los usuarios pueden ver su perfil" on public.profiles;
create policy "Los usuarios pueden ver su perfil"
    on public.profiles for select to authenticated
    using ((select auth.uid()) = id);

drop policy if exists "Los usuarios pueden actualizar su perfil" on public.profiles;
create policy "Los usuarios pueden actualizar su perfil"
    on public.profiles for update to authenticated
    using ((select auth.uid()) = id)
    with check ((select auth.uid()) = id);

create or replace function public.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = public
as $$
begin
    insert into public.profiles (id, nombre, apellido, email)
    values (
        new.id,
        coalesce(new.raw_user_meta_data ->> 'nombre', ''),
        coalesce(new.raw_user_meta_data ->> 'apellido', ''),
        coalesce(new.email, '')
    );
    return new;
end;
$$;

drop trigger if exists on_auth_user_created on auth.users;
create trigger on_auth_user_created
    after insert on auth.users
    for each row execute procedure public.handle_new_user();

create or replace function public.set_updated_at()
returns trigger
language plpgsql
as $$
begin
    new.updated_at = timezone('utc', now());
    return new;
end;
$$;

drop trigger if exists profiles_updated_at on public.profiles;
create trigger profiles_updated_at
    before update on public.profiles
    for each row execute procedure public.set_updated_at();
