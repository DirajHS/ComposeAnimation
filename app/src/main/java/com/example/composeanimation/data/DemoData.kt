package com.example.composeanimation.data

sealed class DemoData {
  data class Header(val title: String): DemoData()

  data class Card(val cardText: String): DemoData()

  data class BigCard(val bigCardText: String): DemoData()

  data class ExpandableSection(val sectionInfo: List<SectionInfo>): DemoData()
}

data class InfoCard(val infoText: String)

data class DetailCard(val detailText: String)

data class SectionInfo(val header: DemoData.Header, val infoCard: List<InfoCard>, val detailCard: List<DetailCard>)

sealed class Response {
  data class Content(val expanded: Boolean, val demoDataList: List<DemoData>): Response()

  object Loading: Response()
}