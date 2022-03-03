package com.example.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeanimation.data.Response
import com.example.composeanimation.ui.composables.DemoLayout
import com.example.composeanimation.ui.theme.ComposeAnimationTheme

class MainActivity : ComponentActivity() {

  private val demoViewModel by viewModels<DemoViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeAnimationTheme {
        val state = demoViewModel.dataFlow.collectAsState()
        if (state.value is Response.Content) {
          DemoLayout((state.value as Response.Content).demoDataList, (state.value as Response.Content).expanded) {
            demoViewModel.updateExpanded(it)
          }
        }
      }
    }
  }
}
