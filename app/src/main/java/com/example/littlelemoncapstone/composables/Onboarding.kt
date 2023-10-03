package com.example.littlelemoncapstone.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemoncapstone.R
import com.example.littlelemoncapstone.navigation.Home
import com.example.littlelemoncapstone.navigation.Onboarding
import com.example.littlelemoncapstone.ui.theme.LLGreen
import com.example.littlelemoncapstone.ui.theme.PrimaryGreen
import com.example.littlelemoncapstone.validateData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(context: Context, navHostController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    //val imeState = rememberImeState()
    val scrollState = rememberScrollState()


    Column(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo"
            )
        }
        Row(
            modifier =
            Modifier
                .background(color = LLGreen)
                .height(150.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Lets get to know you",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }

        Text(
            text = "Personal Information",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryGreen
        )
        OutlinedTextField(
            value = firstName.value,
            onValueChange = {
                firstName.value = it
            },
            label = { Text(text = "First Name")},
            singleLine = true,
            placeholder = { Text(text = "Daniel")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName.value,
            onValueChange = {
                lastName.value = it
            },
            label = { Text(text = "Last Name")},
            singleLine = true,
            placeholder = { Text(text = "Fierro")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = { Text(text = "Email")},
            singleLine = true,
            placeholder = { Text(text = "abc@mail.com")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(onClick = {
            if(validateData(firstName.value, lastName.value, email.value)){
                sharedPreferences.edit()
                    .putString("firstName", firstName.value)
                    .putString("lastName", lastName.value)
                    .putString("email", email.value)
                    .putBoolean("userRegistered", true)
                    .apply()

                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()

                navHostController.navigate(Home.route){
                    popUpTo(Onboarding.route){inclusive = true}
                    launchSingleTop = true
                }
            }
            else{
                Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
            Text(text = "Register")
        }
    }

}
//
//@Preview(showBackground = true)
//@Composable
//fun OnboardingPreview() {
//    Onboarding()
//}