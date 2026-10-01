package com.babyloft.management.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.ui.theme.PinkPrimary
import com.babyloft.management.ui.theme.SurfaceWhite
import com.babyloft.management.ui.theme.TextGray

enum class BottomBarTab {
    INICIO,
    PEDIDOS,
    CATALOGO,
}

@Composable
fun BabyLoftBottomBar(
    currentTab: BottomBarTab,
    onNavigateToDashboard: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToCreate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            color = SurfaceWhite,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Tab 1: Inicio
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToDashboard() }
                        .padding(vertical = 4.dp),
                ) {
                    Text("🏠", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Inicio",
                        color = if (currentTab == BottomBarTab.INICIO) PinkPrimary else TextGray,
                        fontWeight = if (currentTab == BottomBarTab.INICIO) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 11.sp,
                    )
                    if (currentTab == BottomBarTab.INICIO) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(1.5.dp))
                                .background(PinkPrimary),
                        )
                    } else {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

                // Tab 2: Pedidos
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToOrders() }
                        .padding(vertical = 4.dp),
                ) {
                    Text("📋", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Pedidos",
                        color = if (currentTab == BottomBarTab.PEDIDOS) PinkPrimary else TextGray,
                        fontWeight = if (currentTab == BottomBarTab.PEDIDOS) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 11.sp,
                    )
                    if (currentTab == BottomBarTab.PEDIDOS) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(1.5.dp))
                                .background(PinkPrimary),
                        )
                    } else {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

                // Tab 3: Nuevo (Floating Button on the right)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .offset(y = (-10).dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFF06292), Color(0xFFBA68C8)),
                                ),
                            )
                            .clickable { onNavigateToCreate() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nuevo",
                            tint = Color(0xFF2D2D2D),
                            modifier = Modifier.size(26.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Nuevo",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextGray,
                    )
                }
            }
        }
    }
}
