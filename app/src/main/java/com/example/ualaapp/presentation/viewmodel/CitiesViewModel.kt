package com.example.ualaapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.domain.usecase.GetAllCitiesUseCase
import com.example.ualaapp.domain.usecase.GetCitiesByNameUseCase
import com.example.ualaapp.domain.usecase.UpdateCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class CitiesViewModel(
    private val getAllCitiesUseCase: GetAllCitiesUseCase,
    private val getCitiesByNameUseCase: GetCitiesByNameUseCase,
    private val updateCityUseCase: UpdateCityUseCase
): ViewModel() {

    private var listData: MutableLiveData<DataCities> =
        MutableLiveData<DataCities>()

    fun setListData(result: DataCities) {
        listData.postValue(result)
    }

    fun reset(){
        listData = MutableLiveData<DataCities>()
    }

    fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            setListData(getAllCitiesUseCase.call())
        }
    }

    fun filterByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setListData(getCitiesByNameUseCase.call(name))
        }
    }

    fun updateCity(city: City){
        viewModelScope.launch(Dispatchers.IO) {
            updateCityUseCase.call(city)
        }
    }

    fun getCitiesLiveData(): LiveData<DataCities> {
        return listData
    }

    class CitiesViewModelFactory(
        private val getAllCitiesUseCase: GetAllCitiesUseCase,
        private val getCitiesByNameUseCase: GetCitiesByNameUseCase,
        private val updateCityUseCase: UpdateCityUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCitiesUseCase::class.java, GetCitiesByNameUseCase::class.java, UpdateCityUseCase::class.java)
                .newInstance(getAllCitiesUseCase, getCitiesByNameUseCase, updateCityUseCase)
        }
    }
}