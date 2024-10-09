package com.serverui.ui.approaches

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.serverui.R
import com.serverui.model.home_sample.Banner
import com.serverui.model.home_sample.DashboardContentView
import com.serverui.model.home_sample.WidgetData
import com.serverui.model.home_sample.WidgetView
import com.serverui.navigation.Route
import com.serverui.navigation.getRoute
import com.serverui.ui.debug.debugBorder
import com.serverui.ui.screen.BuyCrypto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.cancellation.CancellationException

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@Composable
fun JsonUI_Three(view: DashboardContentView, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(view.data.bgcolor[0].toColorInt()))
    ) {
        AsyncImage(
            model = view.data.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
                .padding(top = 30.dp)
        ) {
            view.children.forEach { widget ->
                when (widget.type) {
                    "banner-view" -> BannerView(widget.children!!)
                    "home-balance-widget-view" -> HomeBalanceWidgetView(widget.children!!)
                    "ixfi-option-widget-view" -> IxfiOptionWidgetView(widget.children!!, navController)
                    "navigation-widget-view" -> NavigationWidgetView(widget.children!!)
                }
            }
        }
    }
}

@Composable
fun BannerView(children: List<WidgetView>) {
    children.forEach { widget ->
        if (widget.type == "banner") {
            AsyncImage(
                model = widget.data?.image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun NavigationWidgetView(children: List<WidgetView>) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
            .background(Color.White)
    ) {
        children.forEach { widget ->
            if (widget.type == "navigation-widget") {
                NavigationWidget(widget.data!!)
            }
        }
    }
}

@Composable
fun NavigationWidget(data: WidgetData) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, top = 18.dp, bottom = 7.dp)
    ) {
        val (navIcon, navText) = createRefs()

        AsyncImage(
            model = stringImage(data.image ?: ""),
            contentDescription = null,
            modifier = Modifier
                .size(61.dp)
                .constrainAs(navIcon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .clip(CircleShape)
                .background(Color(0xFFF9F9F9), CircleShape),
            contentScale = ContentScale.Fit
        )

        Text(
            text = data.text ?: "",
            color = Color(0xff000619),
            fontSize = 11.sp,
            modifier = Modifier
                .constrainAs(navText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(navIcon.bottom)
                }
                .padding(top = 5.dp),
            maxLines = 2
        )
    }
}

@Composable
fun HomeBalanceWidgetView(children: List<WidgetView>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        children.forEach { widget ->
            when (widget.type) {
                "home-balance" -> BalanceView(widget.data!!)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@Composable
fun IxfiOptionWidgetView(children: List<WidgetView>, navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .aspectRatio(2f)
            .debugBorder()
    ) {

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = 2,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            children.forEach { widget ->
                when (widget.type) {
                    "ixfi-options" -> CubeView(widget.data!!, navController)
                }
            }
        }

        children.forEach { widget ->
            when (widget.type) {
                "special-banner" -> SpecialBanner(widget.data?.bannerArray ?: emptyList())
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RowScope.SpecialBanner(banners: List<Banner>) {
    val pagerState = rememberPagerState {
        banners.size
    }
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(18.dp))
    ) {
        HorizontalPager(state = pagerState) {
            AsyncImage(
                model = banners[it].banner_url,
                contentDescription = banners[it].webview_title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AlertSliderDotsIndicator(
            totalDots = banners.size,
            selectedIndex = pagerState.currentPage,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState }.collectLatest { isDragged ->
            while (true) {
                delay(3000L)
                try {
                    pagerState.animateScrollToPage(pagerState.currentPage.inc() % banners.size)
                } catch (ignore: CancellationException) {
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRowScope.CubeView(data: WidgetData, navController: NavHostController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xffD8D8D8), shape = RoundedCornerShape(18.dp))
            .weight(1f)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                data.action?.let {
                    getRoute(context as Activity, it, navController)
                }
            }
    ) {
        AsyncImage(
            model = stringImage(data.image ?: ""),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 1.dp, end = 1.dp, bottom = 1.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 2.dp, start = 5.dp, end = 5.dp)
                .height(35.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = data.text ?: "",
                lineHeight = 17.sp,
                maxLines = 2,
                color = Color(0x99000000),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun RowScope.BalanceView(child: WidgetData) {
    var hide by remember {
        mutableStateOf(false)
    }
    ConstraintLayout(
        modifier = Modifier
            .height(70.dp)
            .weight(1f)
            .border(
                width = 1.dp,
                color = Color(0xffEFEFEF),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
    ) {
        val (tvBalance, tvLabelBalance, ivEye, ivBg) = createRefs()
        val padding8 = 8.dp

        Text(
            text = if (hide) "****" else child.balance ?: "",
            color = Color.Black,
            fontWeight = FontWeight.W600,
            modifier = Modifier.constrainAs(tvBalance) {
                start.linkTo(parent.start, padding8)
                end.linkTo(ivBg.start)
                top.linkTo(parent.top, padding8)
                width = Dimension.fillToConstraints
            },
            maxLines = 1
        )

        Text(
            text = child.text ?: "",
            color = Color(0xff939393),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(tvLabelBalance) {
                    start.linkTo(tvBalance.start)
                    top.linkTo(tvBalance.bottom)
                },
            maxLines = 1
        )

        AsyncImage(
            model = if (hide) R.drawable.ic_eye_slash else R.drawable.ic_eye_dashboard,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 5.dp, top = 4.dp)
                .size(19.dp)
                .constrainAs(ivEye) {
                    start.linkTo(tvLabelBalance.end)
                    linkTo(tvLabelBalance.top, tvLabelBalance.bottom)
                }
                .clickable {
                    hide = !hide
                },
        )

        AsyncImage(
            model = stringImage(child.image ?: ""),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(55.dp)
                .constrainAs(ivBg) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        )
    }
}

@Composable
fun AlertSliderDotsIndicator(
    totalDots: Int, selectedIndex: Int, modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(totalDots) { index ->
            Box(
                modifier = if (index == selectedIndex) {
                    Modifier
                        .height(4.dp)
                        .width(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                } else {
                    Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                }.background(color = if (index == selectedIndex) Color.Black else Color.LightGray)
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
