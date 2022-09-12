package com.example.acadaptercustomproject


import android.content.Context


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import java.nio.charset.Charset


class ACAdapterCustomProjectViewModel : ViewModel() {

    var itemsList: MutableList<ACAdapterCustomProjectUIModel> = mutableListOf()

    sealed class UseCaseLiveData {
        data class setListItems(val items: MutableList<String>) : UseCaseLiveData()
        data class setIcon(val icon: String) : UseCaseLiveData()
    }

    val useCaseLiveData = MutableLiveData<Event<UseCaseLiveData>>()

    fun loadList(context: Context) {
        val obj = JSONObject(loadJSONFromAsset(context))
        val objectsArray = obj.getJSONArray("items")
        for (i in 0 until objectsArray.length()) {
            val objDetail = objectsArray.getJSONObject(i)
            itemsList.add(
                ACAdapterCustomProjectUIModel(
                    id = objDetail.getString("id"),
                    name = objDetail.getString("name"),
                    iconUrl = objDetail.getString("iconUrl")
                )
            )
        }
        val namesList: MutableList<String> = mutableListOf()
        itemsList.forEach { namesList.add(it.name) }
        useCaseLiveData.value = Event(UseCaseLiveData.setListItems(namesList))
    }

    private fun loadJSONFromAsset(context: Context): String {
        val json: String?
        val inputStream = context.assets.open("itemsObjects.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        val charset: Charset = Charsets.UTF_8
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, charset)
        return json
    }

    fun setIcon(itemName: String) {
        var iconUrl = ""
        itemsList.forEach {
            if (it.name == itemName) {
                iconUrl = it.iconUrl
            }
        }
        useCaseLiveData.value = Event(UseCaseLiveData.setIcon(iconUrl))
    }
}



