package com.example.collectionmanagement.collection_book.prentation.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel.HomeViewModel
import com.example.collectionmanagement.collection_book.prentation.theme.CollectionManagementTheme
import com.example.collectionmanagement.collection_book.prentation.theme.dailyColor


@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
        ) {
            customButton(f = { viewModel.increment() })
        }
    }
}

@Composable
fun customButton(
    iconResource: Int = R.drawable.daily_pay,
    text: String = "Daily Pay",
    color:Color= dailyColor,
    f: () -> Unit = {}
    ) {
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 50.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Black),
                shape = RoundedCornerShape(100)
            )
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.size(50.dp),
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .border(
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            shape = CircleShape
                        ).background(color= color)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(iconResource),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(35.dp)
                    )
                }

            }
            Text(text = text, style = TextStyle(color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
        }
    }

}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun d() {
    CollectionManagementTheme {

        HomePage()
    }
}
