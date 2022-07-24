package com.demo.github.utils

import org.junit.Assert.*
import org.junit.Test

class UtilsAndroidTest {
    
    @Test
    fun getInfoIconRight(){
        val infoIcon = getInfoIcon("closed")
        assertEquals(infoIcon, com.demo.github.R.drawable.icon_close_pull_request)
    }
    
    @Test
    fun getInfoIconWrong(){
        val infoIcon = getInfoIcon("open")
        assertNotEquals(infoIcon, com.demo.github.R.drawable.icon_close_pull_request)
        assertEquals(infoIcon, com.demo.github.R.drawable.icon_git)
    }
    
    @Test
    fun getInfoStatusTextRight(){
        val infoStatusText = getStatusText("closed")
        assertEquals(infoStatusText, "closed")
    }
    
    @Test
    fun getInfoStatusTextWrong(){
        val infoStatusText = getStatusText("open")
        assertEquals(infoStatusText, "closed")
    }
    
    @Test
    fun getStatusTextColorRight(){
        val textColor = getStatusTextColor("closed")
        assertEquals(textColor, com.demo.github.R.color.redColor)
    }
    
    @Test
    fun getStatusTextColorWrong(){
        val textColor = getStatusTextColor("open")
        assertEquals(textColor, com.demo.github.R.color.colorAccent)
    }
    
    @Test
    fun getDateRight(){
        val date = getDateAsDay("2016-08-20T15:08:37Z")
        assertEquals(date, "20 Aug 2016")
    }
    
    @Test
    fun getDateWithWrongFormatter(){
        val date = getDateAsDay("2016-08-20T15:08:37")
        assertEquals(date, "")
    }
    
    @Test
    fun getDateWithEmptyDate(){
        val date = getDateAsDay("")
        assertEquals(date, "")
    }
    
}