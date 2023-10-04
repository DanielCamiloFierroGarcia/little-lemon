package com.example.littlelemoncapstone

import com.example.littlelemoncapstone.database.AppDatabase
import com.example.littlelemoncapstone.database.MenuItemNetwork
import com.example.littlelemoncapstone.database.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

fun validateData(firstName: String, lastName: String, email: String): Boolean{
    if(firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()){
        return true
    }
    return false
}

fun saveMenuToDatabase(database: AppDatabase, menuItemsNetwork: List<MenuItemNetwork>){
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
}

suspend fun fetchMenu(url: String): List<MenuItemNetwork>{
    val httpClient = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text", "plain"))
        }
    }
    val httpResponse: MenuNetwork = httpClient.get(url).body()
    return httpResponse.items
}