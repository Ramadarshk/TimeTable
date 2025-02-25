package com.example.timetable.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.color.colorProviders
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.timetable.dataclass.DataSlot
import java.util.Calendar

object WidgetA:GlanceAppWidget() {
    private val calendar: Calendar = Calendar.getInstance()
    private var dayNo = calendar.get(Calendar.DAY_OF_WEEK) - 1
    val countKey = intPreferencesKey("count")
    override suspend fun provideGlance(context: Context , id: GlanceId) {

        provideContent {
           GlanceTheme{
               val count= currentState(key = countKey)?: 0
               androidx.glance.layout.Column(
                   modifier = GlanceModifier.fillMaxSize().background(Color.Gray),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalAlignment = Alignment.CenterHorizontally,
               ) {
                   Text(
                       text = count.toString(),
                       style = TextStyle(
                           fontWeight = FontWeight.Bold,
                           color = ColorProvider(Color.White,Color.White),
                           fontSize = 27.sp
                       )
                   )
                   Button(
                       text = "Increase",
                       onClick = actionRunCallback(ActionCallbackWidget::class.java)
                   )


               }
           }
        }
    }
}
class SimpleCounterWidgetReceiver : GlanceAppWidgetReceiver(){
    override val glanceAppWidget: GlanceAppWidget
        get() = WidgetA
}
object ActionCallbackWidget: ActionCallback {
    override suspend fun onAction(
        context: Context ,
        glanceId: GlanceId ,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(
            context = context, glanceId = glanceId
        ){ prefs->
            val currentCount =prefs[WidgetA.countKey]
            if (currentCount!=null){
                prefs[WidgetA.countKey] = currentCount+1
            }else{
                prefs[WidgetA.countKey] = 1
            }

        }
        WidgetA.update(context = context, id = glanceId)
    }
}

