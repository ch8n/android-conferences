package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.actions.DisplayLoading
import `in`.jitinsharma.asg.conf.redux.observeAsState
import `in`.jitinsharma.asg.conf.redux.state.AppState
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.rekotlin.Store

@Composable
fun ConferencePage(
    store: Store<AppState>
) {
    store.dispatch(DisplayLoading())
    Box(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        val appState = store.observeAsState()
        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
            Header(modifier = Modifier.padding(bottom = 16.dp))
            Crossfade(current = appState.value?.conferenceListState) { state ->
                state?.run {
                    when {
                        isLoading -> {
                            LoadingView()
                        }
                        conferenceDataList.isNotEmpty() -> {
                            val list = if (filteredConferenceDataList.isNotEmpty()) {
                                filteredConferenceDataList
                            } else {
                                conferenceDataList
                            }
                            ConferenceCardList(conferenceDataList = list)
                            appState.value?.filterState?.let { filterState ->
                                FilterDialog(
                                    filterState = filterState
                                )
                            }
                        }
                        displayError -> {
                            WtfView()
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}
