package com.mmk.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.MR
import com.mmk.common.ui.theme.OrangeDark
import com.mmk.common.ui.theme.OrangeLight
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource("drawable/ic_person.xml"),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = stringResource(MR.strings.profile_name),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
            modifier = Modifier.padding(top = 16.dp),
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .border(
                    1.dp,
                    OrangeDark,
                    RoundedCornerShape(8.dp)
                )
                .background(
                    color = OrangeLight,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(68.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(MR.strings.profile_bio),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
            )
        }
    }
}
