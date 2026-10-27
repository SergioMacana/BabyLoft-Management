package com.babyloft.management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babyloft.management.data.SampleData
import com.babyloft.management.data.local.BabyLoftDatabase
import com.babyloft.management.data.local.DatabaseSeeder
import com.babyloft.management.data.repository.AttributeRepository
import com.babyloft.management.data.repository.OrderRepository
import com.babyloft.management.data.repository.ProductRepository
import com.babyloft.management.ui.screens.AdminDashboardScreen
import com.babyloft.management.ui.screens.ArchivedOrdersScreen
import com.babyloft.management.ui.screens.ManageMaterialsScreen
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
                val database = remember { BabyLoftDatabase.getDatabase(applicationContext) }
                val orderRepository = remember { OrderRepository(database) }
                val productRepository = remember { ProductRepository(database) }
                val attributeRepository = remember { AttributeRepository(database) }

                LaunchedEffect(Unit) {
                    DatabaseSeeder.seedIfEmpty(database)
                }

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
                            orderRepository = orderRepository,
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
                            orderRepository = orderRepository,
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
                            },
                            onNavigateToArchived = {
                                navController.navigate("archived")
                            }
                        )
                    }

                    // ==========================================
                    // CATÁLOGO
                    // ==========================================

                    composable("catalog") {
                        ProductCatalogScreen(
                            productRepository = productRepository,
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
                            },
                            onNavigateToCreate = {
                                navController.navigate("create")
                            },
                            onNavigateToMaterials = {
                                navController.navigate("materials")
                            }
                        )
                    }

                    // ==========================================
                    // MATERIALES / TELAS / COLORES / TALLAS
                    // ==========================================

                    composable("materials") {
                        ManageMaterialsScreen(
                            attributeRepository = attributeRepository,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // ==========================================
                    // DETALLE DEL PEDIDO
                    // ==========================================

                    composable("detail") {
                        OrderDetailScreen(
                            order = selectedOrder,
                            orderRepository = orderRepository,
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
                            orderRepository = orderRepository,
                            productRepository = productRepository,
                            attributeRepository = attributeRepository,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onOrderCreated = {
                                navController.navigate("orders") {
                                    popUpTo("dashboard")
                                }
                            }
                        )
                    }

                    // ==========================================
                    // PEDIDOS ARCHIVADOS
                    // ==========================================

                    composable("archived") {
                        ArchivedOrdersScreen(
                            orderRepository = orderRepository,
                            onBack = {
                                navController.popBackStack()
                            },
                            onNavigateToDetail = { order ->
                                selectedOrder = order
                                navController.navigate("detail")
                            }
                        )
                    }
                }
            }
        }
    }
}
