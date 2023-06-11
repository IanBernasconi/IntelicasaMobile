package com.example.intelicasamobile.data.network.data

import Api
import com.example.intelicasamobile.model.Action
import com.example.intelicasamobile.model.ActionTypes
import com.example.intelicasamobile.model.Routine
import org.json.JSONObject

object RoutineApi {

    private fun getUrl(slug: String? = null): String {
        return "${Api.BASE_URL}/routines${slug?.let { "/$it" } ?: ""}"
    }

    suspend fun getAll(): List<Routine> {
        val routinesJson = Api.get(getUrl())?.getJSONArray("result")
        return routinesJson?.let { array ->
            (0 until array.length()).map { index ->
                val routine = array.getJSONObject(index)
                Routine(
                    id = routine.getString("id"),
                    name = routine.getString("name"),
                    actions = routine.getJSONArray("actions").let { actions ->
                        (0 until actions.length()).map { actionIndex ->
                            val action = actions.getJSONObject(actionIndex)
                            Action(
                                action = ActionTypes.values().find{ it.apiName == action.optString("actionName") } ?: ActionTypes.TURN_ON,
                                deviceId = action.getJSONObject("device").getString("id"),
                                params = listOf(action.getJSONArray("params").opt(0)?.toString())
                            )
                        }
                    }
                )
            }
        } ?: emptyList()
    }

    suspend fun execute(routine: Routine): JSONObject? {
        return Api.put("${getUrl(routine.id)}/execute", JSONObject(routine.id))
    }

    suspend fun get(id: String): JSONObject? {
        return Api.get(getUrl(id))
    }
}
