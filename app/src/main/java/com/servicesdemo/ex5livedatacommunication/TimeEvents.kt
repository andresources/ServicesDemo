package com.servicesdemo.ex5livedatacommunication

sealed class TimeEvents{
    object START : TimeEvents()
    object END : TimeEvents()
}
