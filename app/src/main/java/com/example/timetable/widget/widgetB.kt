package com.example.timetable.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.RowScope
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import com.example.timetable.R

object widgetB:GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WifiButtons()
        }
    }
}

class BReceiver:GlanceAppWidgetReceiver(){
    override val glanceAppWidget: GlanceAppWidget
        get() = widgetB
}

@Composable
fun WifiButtons() {
    GlanceTheme {
        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WifiButton(
                uri = "http://172.18.10.10:1000/login?",
                icon = ImageProvider(R.drawable.wifi),
                contentDescription = "wifi sign in only college"
            )

            Spacer(modifier = GlanceModifier.width(16.dp))

            WifiButton(
                uri = "http://172.18.10.10:1000/logout?",
                icon = ImageProvider(R.drawable.wifi_off),
                contentDescription = "wifi sign off only college"
            )
        }
    }
}

@Composable
fun RowScope.WifiButton(
    uri: String,
    icon: ImageProvider,
    contentDescription: String
) {
//    Box(
//        modifier = GlanceModifier
//            .defaultWeight()
//            .padding(2.dp)
//            .background(Color.White)
//            .cornerRadius(26.dp)
//            ,
//        contentAlignment = Alignment.Center
//    ) {
        Box(
            modifier = GlanceModifier
                .size(48.dp)
                .background(Color.White)
                .padding(3.dp)
                .cornerRadius(24.dp).clickable(
                    actionStartActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(uri)
                        }
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                provider = icon,
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
                modifier = GlanceModifier.fillMaxSize()
            )
        }
    }
//}