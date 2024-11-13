package com.example.ualaapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ualaapp.data.models.City
import com.example.ualaapp.domain.usecase.GetCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class MapViewModel(private val getCityUseCase: GetCityUseCase): ViewModel() {
    private var cityData: MutableLiveData<City> =
        MutableLiveData<City>()

    fun setListData(result: City) {
        cityData.postValue(result)
    }

    fun getCity(idCity: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            setListData(getCityUseCase.call(idCity))
        }
    }

    fun getCityLiveData(): LiveData<City> {
        return cityData
    }

    class MapViewModelFactory(
        private val getCityUseCase: GetCityUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetCityUseCase::class.java)
                .newInstance(getCityUseCase)
        }
    }
}