package com.erkindilekci.rickandmorty.util

sealed class Screen(val route: String) {
    object ListScreen : Screen("list_screen")
    object DetailsScreen : Screen("details_screen/{id}") {
        fun passId(id: Int): String {
            return "details_screen/$id"
        }
    }
}
