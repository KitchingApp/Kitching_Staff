package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Order
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.repository.OrderRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _orderCategories = MutableStateFlow(UiState<List<OrderCategory>>())
    val orderCategories get() = _orderCategories.asStateFlow()

    fun fetchOrderCategories(teamId: String) {
        viewModelScope.launch {
            orderRepository.getOrderCategories(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _orderCategories.value = _orderCategories.value
                    }

                    is AppResult.Loading -> {
                        _orderCategories.value = _orderCategories.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _orderCategories.value = _orderCategories.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _orderCategories.value =
                            _orderCategories.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private val _orderItems = MutableStateFlow(UiState<List<Order>>())
    val orderItems get() = _orderItems.asStateFlow()

    fun fetchOrderItems(teamId: String) {
        viewModelScope.launch {
            orderRepository.getOrderItems(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _orderItems.value = _orderItems.value
                    }

                    is AppResult.Loading -> {
                        _orderItems.value = _orderItems.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _orderItems.value = _orderItems.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _orderItems.value =
                            _orderItems.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }
}