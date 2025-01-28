package com.example.ualaapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ualaapp.data.models.City
import com.example.ualaapp.domain.usecase.GetCityUseCase
import com.example.ualaapp.domain.usecase.UpdateCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class MapViewModel(
    private val getCityUseCase: GetCityUseCase,
    private val updateCityUseCase: UpdateCityUseCase): ViewModel() {

    private var cityData: MutableLiveData<City> =
        MutableLiveData<City>()

    private fun setListData(result: City) {
        cityData.postValue(result)
    }

    fun getCity(idCity: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            setListData(getCityUseCase.call(idCity))
        }
    }

    fun getCityLiveData(): LiveData<City> {
        return cityData
    }

    fun updateCity(city: City){
        viewModelScope.launch(Dispatchers.IO) {
            updateCityUseCase.call(city)
        }
    }

    fun reset(){
        cityData = MutableLiveData<City>()
    }

    class MapViewModelFactory(
        private val getCityUseCase: GetCityUseCase,
        private val updateCityUseCase: UpdateCityUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetCityUseCase::class.java, UpdateCityUseCase::class.java)
                .newInstance(getCityUseCase, updateCityUseCase)
        }
    }
}