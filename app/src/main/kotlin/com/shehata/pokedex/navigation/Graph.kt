@file:OptIn(ExperimentalAnimationApi::class)

package com.shehata.pokedex.navigation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.shehata.pokedex.MainActivity
import com.shehata.pokedex.PokedexApplication
import com.shehata.pokedex.ui.components.LeafScreen
import com.shehata.pokedex.ui.components.Screen
import com.shehata.pokedex.ui.screens.favourites.Favourites
import com.shehata.pokedex.ui.screens.favourites.FavouritesState
import com.shehata.pokedex.ui.screens.favourites.FavouritesViewModel
import com.shehata.pokedex.ui.screens.favourites.FavouritesViewModelFactory
import com.shehata.pokedex.ui.screens.pokemondetails.PokemonDetailsScreen
import com.shehata.pokedex.ui.screens.pokemondetails.PokemonDetailsState
import com.shehata.pokedex.ui.screens.pokemondetails.PokemonDetailsViewModel
import com.shehata.pokedex.ui.screens.pokemondetails.PokemonDetailsViewModelFactory
import com.shehata.pokedex.ui.screens.pokemonlist.PokemonListScreen
import com.shehata.pokedex.ui.screens.pokemonlist.PokemonListState
import com.shehata.pokedex.ui.screens.pokemonlist.PokemonListViewModel
import com.shehata.pokedex.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.addTopLevelPokemonList(
    navController: NavController,
    width: Int
) {
    navigation(
        route = Screen.PokemonList.route,
        startDestination = LeafScreen.PokemonList.createRoute(Screen.PokemonList)
    ) {
        addPokemonListScreen(navController, Screen.PokemonList, width)
        addPokemonDetailsScreen(navController, Screen.PokemonList, width)
    }
}

fun NavGraphBuilder.addTopLevelFavourites(
    navController: NavController,
    width: Int
) {
    navigation(
        route = Screen.Favourites.route,
        startDestination = LeafScreen.Favourites.createRoute(Screen.Favourites)
    ) {
        addFavouritesScreen(navController, Screen.Favourites, width)
        addPokemonDetailsScreen(navController, Screen.Favourites, width)
    }
}

fun NavGraphBuilder.addTopLevelProfile(
    navController: NavController,
    width: Int
) {
    navigation(
        route = Screen.Profile.route,
        startDestination = LeafScreen.Profile.createRoute(Screen.Profile)
    ) {
        addProfileScreen(navController, Screen.Profile, width)
    }
}

private fun NavGraphBuilder.addPokemonListScreen(
    navController: NavController,
    root: Screen,
    width: Int
) {
    val route = LeafScreen.PokemonList.createRoute(root)
    composable(
        route = route,
        enterTransition = {
            when (initialState.destination.route) {
                LeafScreen.PokemonDetails.createRoute(root) -> {
                    slideInHorizontally(
                        initialOffsetX = { width },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route?.contains(route)) {
                true -> {
                    slideInHorizontally(
                        initialOffsetX = { -width },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> null
            }
        },
        exitTransition = {
            null
//            val con = targetState.destination.route?.contains(route)
//            Log.i("POKEMON", "$con")
//            when (con) {
//                true -> {
//                    slideOutHorizontally(
//                        targetOffsetX = { -width },
//                        animationSpec = tween(
//                            durationMillis = 300,
//                            easing = FastOutSlowInEasing
//                        )
//                    ) + fadeOut(animationSpec = tween(300))
//                }
//                else -> null
//            }
        },
    ) {
        val vm: PokemonListViewModel = viewModel()
        val uiState by vm.uiState.observeAsState(PokemonListState())

        PokemonListScreen(
            state = uiState,
            fetchPokemon = vm::fetchPokemon,
            navigateToDetailScreen = { pokemonId ->
                navController.navigate(LeafScreen.PokemonDetails.createRoute(root, pokemonId))
            }
        )
    }
}

private fun NavGraphBuilder.addPokemonDetailsScreen(
    navController: NavController,
    root: Screen,
    width: Int,
) {
    val route = LeafScreen.PokemonDetails.createRoute(root)
    composable(
        route = route,
        arguments = listOf(
            navArgument("pokemonId") { type = NavType.IntType }
        ),
        enterTransition = { pokemonDetailsEnterTransition(initialState, width) },
        exitTransition = { pokemonDetailsExitTransition(width) },
        popExitTransition = { pokemonDetailsPopExitTransition(width) }
    ) { backStackEntry ->
//        val vm: PokemonDetailsViewModel = viewModel()
        val app = ((LocalContext.current as MainActivity).application as PokedexApplication)
        val vm: PokemonDetailsViewModel = viewModel(
            factory = PokemonDetailsViewModelFactory(
                app.favouritePokemonsRepository,
                backStackEntry,
                backStackEntry.arguments
            )
        )

        val uiState by vm.uiState.observeAsState(PokemonDetailsState.Empty)

        PokemonDetailsScreen(
            state = uiState,
            actions = vm::actionsHandler
        )
    }
}

private fun NavGraphBuilder.addFavouritesScreen(
    navController: NavController,
    root: Screen,
    width: Int,
) {
    composable(
        route = LeafScreen.Favourites.createRoute(root),
    ) { backStackEntry ->
        val app = ((LocalContext.current as MainActivity).application as PokedexApplication)
        val vm: FavouritesViewModel = viewModel(
            factory = FavouritesViewModelFactory(
                app.favouritePokemonsRepository,
                backStackEntry
            )
        )

        val state by vm.uiState.collectAsState(FavouritesState.Empty)

        Favourites(
            state = state,
            openPokemonDetails = { pokemonId ->
                navController.navigate(LeafScreen.PokemonDetails.createRoute(root, pokemonId))
            }
        )
    }
}


private fun NavGraphBuilder.addProfileScreen(
    navController: NavController,
    root: Screen,
    width: Int,
) {
    composable(
        route = LeafScreen.Profile.createRoute(root),
    ) {
        ProfileScreen(navController = navController)
    }
}

internal fun pokemonDetailsEnterTransition(
    initialState: NavBackStackEntry,
    width: Int
): EnterTransition = slideInHorizontally(
    initialOffsetX = { width },
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)
//+ fadeIn(animationSpec = tween(300))

internal fun pokemonDetailsExitTransition(
    width: Int
): ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { -width },
        animationSpec = tween(300)
    ) + fadeOut(animationSpec = tween(300))

internal fun pokemonDetailsPopExitTransition(
    width: Int
): ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { width },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(300))



