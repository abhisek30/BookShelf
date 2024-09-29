package com.abhisek.project.bookshelf.ui.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abhisek.project.bookshelf.domain.models.Country

@Composable
fun CountryListDropDown(
    modifier: Modifier = Modifier,
    itemPosition: MutableIntState,
    countries: List<Country>
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isDropDownExpanded.value = true
            }
        ) {
            Text(text = countries[itemPosition.intValue].country)
            Image(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "DropDown Icon"
            )
        }
        DropdownMenu(
            expanded = isDropDownExpanded.value,
            onDismissRequest = {
                isDropDownExpanded.value = false
            }) {
            countries.forEachIndexed { index, country ->
                DropdownMenuItem(text = {
                    Text(text = country.country)
                },
                    onClick = {
                        isDropDownExpanded.value = false
                        itemPosition.intValue = index
                    })
            }
        }
    }
}