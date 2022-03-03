package com.example.composeanimation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeanimation.data.DemoData
import com.example.composeanimation.data.DetailCard
import com.example.composeanimation.data.InfoCard
import com.example.composeanimation.data.Response
import com.example.composeanimation.data.SectionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {

  private val _dataFlow: MutableStateFlow<Response> = MutableStateFlow(Response.Loading)
  val dataFlow: StateFlow<Response>
    get() = _dataFlow

  init {
    addData()
  }

  private fun addData() {
    val demoDataList = mutableListOf<DemoData>()
    viewModelScope.launch(Dispatchers.IO) {
      demoDataList.add(DemoData.Header("This is a header"))

      demoDataList.add(DemoData.Header("This is another header"))

      demoDataList.add(DemoData.Card("This is a card"))

      demoDataList.add(DemoData.BigCard("Big card 1"))

      demoDataList.add(DemoData.BigCard("Big card 2"))

      val sections = mutableListOf<SectionInfo>()

      for (i in 0 until 5) {
        val section = DemoData.Header("This is section: $i")
        val infoCardList = mutableListOf<InfoCard>()
        val detailCardList = mutableListOf<DetailCard>()
        for (j in 0 until 10) {
          infoCardList.add(InfoCard("This is info: $j"))
          detailCardList.add(DetailCard("This is detail: $j"))
        }
        sections.add(SectionInfo(section, infoCardList, detailCardList))
      }
      demoDataList.add(DemoData.ExpandableSection(sectionInfo = sections))

      demoDataList.add(DemoData.BigCard("Big card 3"))

      demoDataList.add(DemoData.BigCard("Big card 4"))

      demoDataList.add(DemoData.BigCard("Big card 5"))


      demoDataList.add(DemoData.Card("This is a card 2"))

      demoDataList.add(DemoData.Card("This is a card 3"))

      demoDataList.add(DemoData.Card("This is a card 4"))

      demoDataList.add(DemoData.Card("This is a card 5"))

      _dataFlow.update {
        Response.Content(false, demoDataList)
      }
    }
  }

  fun updateExpanded(isExpanded: Boolean) {
    _dataFlow.getAndUpdate {
      (it as Response.Content).let { responseContent ->
        val updatedData = responseContent.copy(expanded = isExpanded)
        updatedData
      }
    }
  }
}