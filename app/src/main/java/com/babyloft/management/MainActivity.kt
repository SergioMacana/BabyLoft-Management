package com.babyloft.management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babyloft.management.data.SampleData
import com.babyloft.management.ui.screens.AdminDashboardScreen
import com.babyloft.management.ui.screens.OrderCreateScreen
import com.babyloft.management.ui.screens.OrderDetailScreen
import com.babyloft.management.ui.screens.OrderListScreen
import com.babyloft.management.ui.screens.ProductCatalogScreen
import com.babyloft.management.ui.theme.BabyLoftManagementTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BabyLoftManagementTheme {

                val navController = rememberNavController()

                var selectedOrder by remember {
                    mutableStateOf(SampleData.orders.first())
                }

                NavHost(
                    navController = navController,
                    startDestination = "dashboard"
                ) {

                    // ==========================================
                    // INICIO / DASHBOARD
                    // ==========================================

                    composable("dashboard") {

                        AdminDashboardScreen(

                            onNavigateToOrders = {
                                navController.navigate("orders")
                            },

                            onNavigateToCreate = {
                                navController.navigate("create")
                            },

                            onNavigateToDetail = { order ->

                                selectedOrder = order

                                navController.navigate("detail")
                            },

                            onNavigateToCatalog = {
                                navController.navigate("catalog")
                            }
                        )
                    }

                    // ==========================================
                    // LISTA DE PEDIDOS
                    // ==========================================

                    composable("orders") {

                        OrderListScreen(

                            onBack = {
                                navController.popBackStack()
                            },

                            onNavigateToDetail = { order ->

                                selectedOrder = order

                                navController.navigate("detail")
                            },

                            onNavigateToDashboard = {

                                navController.navigate("dashboard") {

                                    popUpTo("dashboard") {
                                        inclusive = true
                                    }
                                }
                            },

                            onNavigateToCatalog = {
                                navController.navigate("catalog")
                            },

                            onNavigateToCreate = {
                                navController.navigate("create")
                            }
                        )
                    }

                    // ==========================================
                    // CATÁLOGO
                    // ==========================================

                    composable("catalog") {

                        ProductCatalogScreen(

                            onBack = {
                                navController.popBackStack()
                            },

                            onNavigateToDashboard = {

                                navController.navigate("dashboard") {

                                    popUpTo("dashboard") {
                                        inclusive = true
                                    }
                                }
                            },

                            onNavigateToOrders = {
                                navController.navigate("orders")
                            }
                        )
                    }

                    // ==========================================
                    // DETALLE DEL PEDIDO
                    // ==========================================

                    composable("detail") {

                        OrderDetailScreen(

                            order = selectedOrder,

                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // ==========================================
                    // CREAR PEDIDO
                    // ==========================================

                    composable("create") {

                        OrderCreateScreen(

                            onBack = {
                                navController.popBackStack()
                            },

                            onFinish = {

                                navController.navigate("orders") {

                                    popUpTo("dashboard")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}