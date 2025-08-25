package com.example.finalproject.welfare

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "servList")
data class WelfareItem(
    @PropertyElement val servId: String?,
    @PropertyElement val servNm: String?,
    @PropertyElement val servDgst: String?,
    @PropertyElement val jurMnofNm: String?,
    @PropertyElement val lifeArray: String?,
    @PropertyElement val srvPvsnNm: String?
) {
    constructor() : this(null, null, null, null, null, null)
}