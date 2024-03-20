package com.example.restaurantreview.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.CustomerReviewsItem
import com.example.restaurantreview.data.response.PostReviewResponse
import com.example.restaurantreview.data.response.Restaurant
import com.example.restaurantreview.data.response.RestaurantResponse
import com.example.restaurantreview.data.retrofit.ApiConfig
import com.example.restaurantreview.utils.Event

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private fun findRestaurant() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object :
            retrofit2.Callback<RestaurantResponse> {
            override fun onResponse(
                call: retrofit2.Call<RestaurantResponse>,
                response: retrofit2.Response<RestaurantResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _restaurant.value = responseBody.restaurant
                        _listReview.value = responseBody.restaurant.customerReviews
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun postReview(review: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Raihan", review)
        client.enqueue(object : retrofit2.Callback<PostReviewResponse> {
            override fun onResponse(
                call: retrofit2.Call<PostReviewResponse>,
                response: retrofit2.Response<PostReviewResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listReview.value = responseBody.customerReviews
                    _snackbarText.value = Event(responseBody.message)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: retrofit2.Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    init {
        findRestaurant()
    }

}