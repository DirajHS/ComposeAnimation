package com.example.composeanimation.ui.composables

import androidx.compose.foundation.lazy.items
import com.example.composeanimation.data.DemoData
import com.example.composeanimation.data.DetailCard
import com.example.composeanimation.data.InfoCard
import com.example.composeanimation.data.SectionInfo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.random.Random

val RandomColor
  get() = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

typealias ClickHandler = (Boolean) -> Unit

@Composable
fun DemoLayout(demoDataList: List<DemoData>, isExpanded: Boolean, clickHandler: ClickHandler) {
  LazyColumn {
    demoDataList.forEachIndexed { index, it ->
      when (it) {
        is DemoData.Header -> item(key = "cell_$index") { HeaderComposable(header = it) }
        is DemoData.BigCard -> item(key = "hero_$index") { BigCardComposable(bigCard = it) }
        is DemoData.Card -> item(key = "banner_$index") { CardComposable(card = it) }
        is DemoData.ExpandableSection -> {
          items(count = 2, key = { indexInner: Int -> "categories_first_half_$index$indexInner" }) { index ->
            Section(
              sectionInfo = it.sectionInfo[index]
            )
          }
          //Comment below and try another approach
          item(key = "first_approach_$index") {
            FirstApproach(
              expandableSection = DemoData.ExpandableSection(
                it.sectionInfo.subList(
                  3,
                  5
                )
              )
            )
          }

          //Second approach
          /*if (isExpanded)
            items(count = 3, key = { indexInner -> "categories_second_half_$index$indexInner" }) { index ->
              Section(
                sectionInfo = it.sectionInfo[index + 2]
              )
            }
          item(key = "button_$index") {
            ShowHideButton(isExpanded, clickHandler)
          }*/
        }
      }
    }
  }
}

@Composable
fun FirstApproach(expandableSection: DemoData.ExpandableSection) {
  var expanded by remember { mutableStateOf(false) }
  val density = LocalDensity.current
  Column {

    AnimatedVisibility(
      visible = expanded,
      enter = slideInVertically() +
        expandVertically(
          // Expand from the top.
          expandFrom = Alignment.Top,
          animationSpec = tween(durationMillis = 350, easing = FastOutLinearInEasing)
        ) + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
      ),
      exit = slideOutVertically(
        animationSpec = tween(durationMillis = 350, easing = FastOutLinearInEasing)
      ) + shrinkVertically(
        shrinkTowards = Alignment.Bottom,
        animationSpec = tween(durationMillis = 350, easing = FastOutLinearInEasing)
      ) + fadeOut(
        animationSpec = tween(durationMillis = 350, easing = FastOutLinearInEasing),
        targetAlpha = 0f
      )
    ) {
      Column {
        for (i in 0 until expandableSection.sectionInfo.size) {
          HeaderComposable(header = expandableSection.sectionInfo[i].header)
          InfoCardsComposable(expandableSection.sectionInfo[i].infoCard)
          DetailsCardComposable(expandableSection.sectionInfo[i].detailCard)
        }
      }
    }
    Button(
      modifier = Modifier
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth(),
      onClick = {
        expanded = !expanded
      }) {
      Text(text = if (expanded) "Hide" else "Show")
    }
  }
}

@Composable
fun HeaderComposable(header: DemoData.Header) {
  Row(
    modifier = Modifier
      .padding(top = 16.dp)
      .fillMaxWidth()
      .height(64.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = header.title, modifier = Modifier.padding(horizontal = 16.dp))
  }
}

@Composable
fun CardComposable(card: DemoData.Card) {
  Card(
    modifier = Modifier
      .padding(top = 16.dp)
      .size(164.dp),
    backgroundColor = RandomColor
  ) {
    Text(text = card.cardText, modifier = Modifier.padding(horizontal = 16.dp))
  }
}

@Composable
fun BigCardComposable(bigCard: DemoData.BigCard) {
  Card(
    modifier = Modifier
      .padding(top = 16.dp)
      .size(172.dp),
    backgroundColor = RandomColor
  ) {
    Text(text = bigCard.bigCardText, modifier = Modifier.padding(horizontal = 16.dp))
  }
}

@Composable
fun Section(sectionInfo: SectionInfo) {
  Column(
    modifier = Modifier.animateContentSize()
  ) {
    HeaderComposable(header = sectionInfo.header)
    InfoCardsComposable(sectionInfo.infoCard)
    DetailsCardComposable(sectionInfo.detailCard)
  }
}

@Composable
private fun ShowHideButton(isExpanded: Boolean, clickHandler: ClickHandler) {
  Button(
    modifier = Modifier
      .padding(top = 16.dp, start = 16.dp, end = 16.dp)
      .fillMaxWidth(),
    onClick = {
      clickHandler.invoke(
        !isExpanded
      )
    }) {
    Text(text = if (isExpanded) "Hide" else "Show")
  }
}

@Composable
fun DetailsCardComposable(detailCardsList: List<DetailCard>) {
  LazyRow(
    modifier = Modifier.padding(top = 16.dp)
  ) {
    items(detailCardsList) {
      DetailCardComposable(detailCard = it)
    }
  }
}

@Composable
fun InfoCardsComposable(infoCardsList: List<InfoCard>) {
  LazyRow(
    modifier = Modifier.padding(top = 16.dp)
  ) {
    items(infoCardsList) {
      InfoCardComposable(infoCard = it)
    }
  }
}

@Composable
fun InfoCardComposable(infoCard: InfoCard) {
  Card(
    modifier = Modifier
      .size(136.dp),
    backgroundColor = RandomColor
  ) {
    Text(text = infoCard.infoText, modifier = Modifier.padding(horizontal = 16.dp))
  }
}

@Composable
fun DetailCardComposable(detailCard: DetailCard) {
  Card(
    modifier = Modifier
      .size(156.dp),
    backgroundColor = RandomColor
  ) {
    Text(text = detailCard.detailText, modifier = Modifier.padding(horizontal = 16.dp))
  }
}
