package com.example.finalproject.welfare

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "wantedList")
data class WelfareResponse(
    @PropertyElement val totalCount: Int?,
    @PropertyElement val pageNo: Int?,
    @PropertyElement val numOfRows: Int?,
    @PropertyElement val resultCode: String?,
    @PropertyElement val resultMessage: String?,
    @Element(name = "servList")
    val serviceList: MutableList<WelfareItem>? = null

)
