package com.example.littlelemoncapstone.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemoncapstone.R
import com.example.littlelemoncapstone.navigation.Profile
import com.example.littlelemoncapstone.ui.theme.PrimaryGreen

@Composable
fun Home(context: Context, navHostController: NavHostController) {
    Column {
        Header(
            navHostController = navHostController
        )
    }
}

@Composable
fun Header(navHostController: NavHostController) {
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
                .clickable { navHostController.navigate(Profile.route) }
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

//
//@Preview(showBackground = true)
//@Composable
//fun HomePreview() {
//    Home()
//}