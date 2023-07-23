package com.bera.collegesearch.screens.cutoffsbyrank

sealed class CBRAction {
    data class Rank(val rank: Int): CBRAction()
    data class Exam(val exam: String): CBRAction()
    data class State(val state: String): CBRAction()
    data class Gender(val gender: String): CBRAction()
    data class Quota(val quota: String): CBRAction()
    data class PwD(val pwd: Boolean) : CBRAction()
    object ExpandFilters : CBRAction()
    data class Sort(val sortBy: String) : CBRAction()
}
