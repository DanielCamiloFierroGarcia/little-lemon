package com.example.littlelemoncapstone.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemoncapstone.R
import com.example.littlelemoncapstone.database.MenuItemRoom
import com.example.littlelemoncapstone.database.ViewModel
import com.example.littlelemoncapstone.navigation.Profile
import com.example.littlelemoncapstone.ui.theme.PrimaryGreen

@Composable
fun Home(context: Context, navHostController: NavHostController?) {

    val viewModel: ViewModel = viewModel()
    val databaseMenuItems = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value

    val searchPhrase = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        viewModel.fetchMenuDataIfNeeded()
    }

    Column {
        Header(navHostController = navHostController)
        UpperPanel(){
            searchPhrase.value = it
        }
        LowerPanel(databaseMenuItems = databaseMenuItems, search = searchPhrase)
    }
}

@Composable
fun Header(navHostController: NavHostController?) {
    Row (
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Spacer(modifier = Modifier.width(50.dp))
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Little Lemon Logo", modifier = Modifier.fillMaxWidth(0.65f))

        Box (
            modifier = Modifier
                .size(50.dp)
                .clickable { navHostController!!.navigate(Profile.route) }
        ){
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = PrimaryGreen,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpperPanel(searchPhrase: (parameter: String)->Unit) {
    val search = remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
            .background(PrimaryGreen)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ){
        Text(text = "Little Lemon", style = MaterialTheme.typography.headlineMedium, color = Color.Yellow)
        Text(text = "New York", style = MaterialTheme.typography.headlineSmall, color = Color.White)
        Row (
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                modifier = Modifier.fillMaxWidth(0.7f),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
                )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        OutlinedTextField(
            value = search.value,
            onValueChange = {
                search.value = it
                searchPhrase(search.value)
            },
            placeholder = {
                Text(text = "Enter Search Phrase")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LowerPanel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map {
        it.category.replaceFirstChar {char ->
            char.uppercase()
        }
    }.toSet()

    val selectedCategory = remember {
        mutableStateOf("")
    }
    val items = if(search.value == ""){
        databaseMenuItems
    }else{
        databaseMenuItems.filter {
            it.title.contains(search.value, ignoreCase = true)
        }
    }

    val filteredItems = if(selectedCategory.value == "" || selectedCategory.value == "all"){
        items
    }else{
        items.filter {
            it.category.contains(selectedCategory.value, ignoreCase = true)
        }
    }
    Column {
        MenuCategories(categories = categories){
            selectedCategory.value = it
        }
        Spacer(modifier = Modifier.size(2.dp))
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            for(item in filteredItems){
                MenuItem(menuItems = item)
            }
        }
    }
}

@Composable
fun MenuCategories(categories: Set<String>, categoryLambda: (sel: String) -> Unit) {
    val cat = remember {
        mutableStateOf("")
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ){
            Text(text = "ORDER FOR DELIVERY", fontWeight = FontWeight.Bold)
            
            Row (
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                CategoryButton(category = "All"){
                    cat.value = it.lowercase()
                    categoryLambda(it.lowercase())
                }
                
                for(category in categories){
                    CategoryButton(category = category){
                        cat.value = it
                        categoryLambda(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory: (sel: String) -> Unit) {
    val isClicked = remember {
        mutableStateOf(false)
    }
    Button(onClick = { 
        isClicked.value = !isClicked.value
        selectedCategory(category)
    },
        colors = ButtonDefaults.buttonColors(
            contentColor = PrimaryGreen,
        )) {
        Text(text = category)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItems: MenuItemRoom) {
    val itemDescription = if(menuItems.description.length > 100){
        menuItems.description.substring(0,100) + "..."
    }else{
        menuItems.description
    }

    Card (
        modifier = Modifier.clickable {

        }
    ){
        Row (
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (
                Modifier.fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = menuItems.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = "$ ${menuItems.price}", fontWeight = FontWeight.Bold)
            }

            GlideImage(model = menuItems.imageUrl, contentDescription = "", Modifier.size(100.dp, 100.dp), contentScale = ContentScale.Crop)
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun HomePreview() {
//    val context = LocalContext.current
//    Home(context, null)
//}