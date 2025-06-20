package com.kitching.main.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitching.data.repository.OrderRepositoryImpl
import com.kitching.data.repository.ScheduleRepositoryImpl
import com.kitching.data.repository.TeamRepositoryImpl
import com.kitching.main.view.model.DrawerViewModel
import com.kitching.main.view.model.FcmViewModel
import com.kitching.main.view.model.OrderViewModel
import com.kitching.main.view.model.ScheduleViewModel

@Suppress("UNCHECKED_CAST")
val viewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(ScheduleViewModel::class.java) ->
                    ScheduleViewModel(ScheduleRepositoryImpl())
                isAssignableFrom(FcmViewModel::class.java) ->
                    FcmViewModel()
                isAssignableFrom(OrderViewModel::class.java) ->
                    OrderViewModel(OrderRepositoryImpl())
                isAssignableFrom(DrawerViewModel::class.java) ->
                    DrawerViewModel(TeamRepositoryImpl())
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}