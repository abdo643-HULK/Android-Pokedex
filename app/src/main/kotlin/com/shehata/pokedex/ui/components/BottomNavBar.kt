package com.shehata.pokedex.ui.components

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavControllerVisibleEntries
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shehata.pokedex.R

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
            val selected =
                currentDestination?.hierarchy?.any { it.route == item.screen.route } == true

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = stringResource(id = item.contentDescriptionResId),
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = { Text(text = stringResource(id = item.labelResId)) },
                alwaysShowLabel = true,
                selected = selected,
                unselectedContentColor = MaterialTheme.colors.primary,
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                onClick = {
                    navigate(navController, item.screen)
                }
            )
        }
    }
}

private sealed class BottomNavigationItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    class ResourceIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : BottomNavigationItem(screen, labelResId, contentDescriptionResId)

//    class ImageVectorIcon(
//        screen: Screen,
//        @StringRes labelResId: Int,
//        @StringRes contentDescriptionResId: Int,
//        val iconImageVector: ImageVector,
//        val selectedImageVector: ImageVector? = null,
//    ) : BottomNavigationItem(screen, labelResId, contentDescriptionResId)
}


private val navItems = arrayOf(
    BottomNavigationItem.ResourceIcon(
        screen = Screen.PokemonList,
        labelResId = R.string.bottom_navigation_home_title,
        contentDescriptionResId = R.string.cd_bottom_navigation_home_title,
        iconResId = R.drawable.ic_pokeball,
    ),
    BottomNavigationItem.ResourceIcon(
        screen = Screen.Favourites,
        labelResId = R.string.bottom_navigation_favourites_title,
        contentDescriptionResId = R.string.cd_bottom_navigation_favourites_title,
        iconResId = R.drawable.ic_baseline_favorite_border_24,
    ),
    BottomNavigationItem.ResourceIcon(
        screen = Screen.Profile,
        labelResId = R.string.bottom_navigation_profile_title,
        contentDescriptionResId = R.string.cd_bottom_navigation_profile_title,
        iconResId = R.drawable.ic_outline_person_24,
    ),
)

fun navigate(navController: NavController, item: Screen) {
    for (navBackStackEntry in navController.backQueue) {
        Log.i("POKEMON", navBackStackEntry.destination.route.toString())
    }
    navController.navigate(item.route) {
        launchSingleTop = true
        restoreState = true

        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }
}

//fun navigate(navController: NavController, item: Screen) {
//    if (item.route == navController.currentDestination?.route) return
//    val route = navController.backQueue.find { it.destination.route == item.route }
//
//    if (route != null) {
//        navController.popBackStackAllInstances(
//            navController.currentBackStackEntry?.destination?.route!!,
//            true
//        )
//    } else {
//        navController.navigate(item.route) {
//            launchSingleTop = true
//            restoreState = true
//        }
//    }
//
////    when (item.route) {
////        Screen.PokemonList.route -> navController.navigateUp()
////        else -> {
////            if (item.route == navController.currentDestination?.route) return
////            val route = navController.backQueue.find { it.destination.route == item.route }
////            if (route != null) {
////                navController.popBackStackAllInstances(
////                    navController.currentBackStackEntry?.destination?.route!!,
////                    true
////                )
////            } else {
////                navController.navigate(item.route)
////            }
////        }
////    }
//}


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