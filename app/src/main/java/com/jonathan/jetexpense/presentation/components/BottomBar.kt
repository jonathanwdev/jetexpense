package com.jonathan.jetexpense.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jonathan.jetexpense.presentation.navigation.JetExpenseDestination

private val TabHeight = 56.dp
private val InactiveTabOpactity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDelay = 100


@Composable
fun JetExpenseRowTab(
    text: String,
    @DrawableRes icon: Int,
    onTabSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colorScheme.onSurface
    val duration = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDelay
    val animationSpec = remember {
        tween<Color>(
            durationMillis = duration,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpactity),
        animationSpec = animationSpec,
        label = ""
    )

    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onTabSelected,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = text, tint = tabTintColor)
        if(selected) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text.uppercase(), color = tabTintColor)
        }
    }
}

@Composable
fun JetExpFab(
    onFabClicked: () -> Unit,
    selectedTab: JetExpenseDestination
) {
    FloatingActionButton(onClick = { onFabClicked() }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
fun JetExpenseBottomBar(
    allScreens: List<JetExpenseDestination>,
    onTabSelected: (JetExpenseDestination) -> Unit,
    selectedTab: JetExpenseDestination,
    onFabClicked: () -> Unit
) {

    BottomAppBar(
        floatingActionButton = {
            JetExpFab(onFabClicked, selectedTab)
        },
        actions = {
            allScreens.forEach { screen ->
                JetExpenseRowTab(
                    text = screen.pageTitle,
                    icon = screen.iconResId,
                    onTabSelected = {
                        onTabSelected(screen)
                    },
                    selected = selectedTab == screen
                )
            }
            
        }
    )
}