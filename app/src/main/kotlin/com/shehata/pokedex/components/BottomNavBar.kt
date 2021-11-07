package com.shehata.pokedex.components

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavControllerVisibleEntries
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shehata.pokedex.BottomNavigationItem

val navItems = arrayOf(
    BottomNavigationItem.PokemonList,
    BottomNavigationItem.Favourites,
    BottomNavigationItem.Profile,
)

@OptIn(NavControllerVisibleEntries::class)
@Composable
fun BottomNavBar(navController: NavController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary
    ) {
        navItems.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = selected,
                unselectedContentColor = MaterialTheme.colors.primary,
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                onClick = {
                    navigate(navController, item)
                }
            )
        }
    }
}

fun navigate(navController: NavController, item: BottomNavigationItem) {
    when (item.route) {
        BottomNavigationItem.Profile.route,
        BottomNavigationItem.Favourites.route -> {
            if (item.route == navController.currentDestination?.route) return
            val route = navController.backQueue.find { it.destination.route == item.route }
            if (route != null) {
                navController.popBackStackAllInstances(
                    navController.currentBackStackEntry?.destination?.route!!,
                    true
                )
            } else {
                navController.navigate(item.route)
            }
        }
        else -> navController.navigateUp()
    }
}

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBack by rememberUpdatedState(onBack)
    // Remember in Composition a back callback that calls the `onBack` lambda
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    // On every successful composition, update the callback with the `enabled` value
    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }
}

fun NavController.popBackStackAllInstances(destination: String, inclusive: Boolean): Boolean {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
    return popped
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
//     BottomNavBar()
}