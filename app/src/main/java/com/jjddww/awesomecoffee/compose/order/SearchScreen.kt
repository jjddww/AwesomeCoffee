package com.jjddww.awesomecoffee.compose.order

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.ui.theme.outlineLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel, onMenuSelected: (Int) -> Unit,
                 onBackButtonPressed : () -> Unit){

    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(viewModel.text.value, TextRange(0, 7)))
    }

    val searchResult by viewModel.result.observeAsState(initial = emptyList())

    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchMenu = { keyword: String -> viewModel.menuSearch(keyword)}

    BackHandler{
        onBackButtonPressed()
    }

    Column (
        modifier = Modifier.fillMaxSize(),
    ){

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .height(65.dp),
            shape = RoundedCornerShape(20.dp),
            value = text,
            onValueChange = { text = it
                onSearchMenu(text.text)},
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchMenu(text.text)
                    keyboardController?.hide() }),

            label = { Text(stringResource(id = R.string.input_keyword), style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = tertiaryLight,
                unfocusedBorderColor = outlineLight
            ))

        Spacer(Modifier.height(30.dp))

        SearchResultView(searchResult, onMenuSelected)
    }
}

@Composable
fun SearchResultView(
    searchResult: List<Menu>,
    onMenuSelected: (Int) -> Unit,
){

    LazyColumn(
        Modifier.fillMaxSize()){

        if(searchResult.isEmpty()){
            item{
                Text(text = stringResource(id = R.string.empty_search_result),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally))
            }
        }

        else{
            items(searchResult){
                MenuItem(menu = it, onMenuSelected = onMenuSelected)
            }
        }
    }
}