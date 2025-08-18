package com.kitching.main.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitching.data.repository.OrderRepositoryImpl
import com.kitching.data.repository.PrepRepositoryImpl
import com.kitching.data.repository.RecipeRepositoryImpl
import com.kitching.data.repository.ScheduleRepositoryImpl
import com.kitching.data.repository.TeamRepositoryImpl
import com.kitching.main.view.model.DrawerViewModel
import com.kitching.main.view.model.OrderViewModel
import com.kitching.main.view.model.OtherViewModel
import com.kitching.main.view.model.PrepViewModel
import com.kitching.main.view.model.RecipeViewModel
import com.kitching.main.view.model.ScheduleViewModel

@Suppress("UNCHECKED_CAST")
val viewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(ScheduleViewModel::class.java) ->
                    ScheduleViewModel(ScheduleRepositoryImpl())
                isAssignableFrom(OrderViewModel::class.java) ->
                    OrderViewModel(OrderRepositoryImpl())
                isAssignableFrom(DrawerViewModel::class.java) ->
                    DrawerViewModel(TeamRepositoryImpl())
                isAssignableFrom(RecipeViewModel::class.java) ->
                    RecipeViewModel(RecipeRepositoryImpl())
                isAssignableFrom(PrepViewModel::class.java) ->
                    PrepViewModel(PrepRepositoryImpl())
                isAssignableFrom(OtherViewModel::class.java) ->
                    OtherViewModel(TeamRepositoryImpl())
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}