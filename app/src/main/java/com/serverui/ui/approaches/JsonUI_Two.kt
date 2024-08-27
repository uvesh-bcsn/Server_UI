package com.serverui.ui.approaches

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.serverui.R
import com.serverui.model.json_two.Children
import com.serverui.model.json_two.ChildrenX
import com.serverui.model.json_two.ComponentData
import com.serverui.ui.debug.debugBorder

@ExperimentalLayoutApi
@Composable
fun Json_Two() {
    var data: ComponentData? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        data = Gson().fromJson(
            jsonStr2(),
            ComponentData::class.java
        )
    }
    data?.let {
        JsonUI_Two(it)
    }
}

@ExperimentalLayoutApi
@Composable
fun JsonUI_Two(componentData: ComponentData) {
    SetUI(componentData = componentData)

}

@ExperimentalLayoutApi
@Composable
fun SetUI(componentData: Any, scope: Any? = null) {
    if (componentData is ComponentData) {
        if (componentData.type == UI_TYPE.dashboard.type) {
            MainScroll(
                children = componentData.children,
                content = { children ->
                    for (child in children) {
                        SetUI(componentData = child)
                    }
                }
            )
        }
    } else if (componentData is Children) {
        if (componentData.type == UI_TYPE.home_balance_widget_view.type) {
            BalanceRow(children = componentData.children, content = {
                it.forEach { childrenX ->
                    SetUI(componentData = childrenX, scope = this)
                }
            })
        } else if (componentData.type == UI_TYPE.ixfi_option_widget_view.type) {
            CubeRow(
                content = { childrenList ->
                    for (child in childrenList) {
                        SetUI(componentData = child, scope = this)
                    }
                },
                children = componentData.children
            )
        }
    } else if (componentData is ChildrenX) {
        if (scope is RowScope && componentData.type == UI_TYPE.home_balance.type) {
            scope.BalanceView(componentData)
        } else if (scope is FlowColumnScope) {
            scope.CubeView(componentData)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowColumnScope.CubeView(child: ChildrenX) {
    ConstraintLayout(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .border(width = 1.dp, color = Color(0xffD8D8D8), shape = RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .clickable { }
    ) {
        val childView = createRef()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .constrainAs(childView) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                    width = Dimension.preferredWrapContent
                }
        ) {
            val (buyCrypto, label) = createRefs()

            AsyncImage(
                model = stringImage(child.data.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 1.dp, end = 1.dp, bottom = 1.dp)
                    .fillMaxWidth()
                    .constrainAs(buyCrypto) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.preferredWrapContent
                    }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 2.dp, start = 5.dp, end = 5.dp)
                    .height(30.dp)
                    .constrainAs(label) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            ) {
                Text(
                    text = child.data.text,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    color = Color(0x99000000),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun CubeRow(
    content: @Composable FlowColumnScope.(List<ChildrenX>) -> Unit,
    children: List<ChildrenX>
) {
    FlowColumn(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachColumn = 2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 13.dp, end = 13.dp)
            .aspectRatio(2.05f)
    ) {
        content(children)
    }
}

@Composable
private fun RowScope.BalanceView(child: ChildrenX) {
    var hide by remember {
        mutableStateOf(false)
    }
    ConstraintLayout(
        modifier = Modifier
            .height(70.dp)
            .fillMaxHeight()
            .weight(1f)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        val (tvBalance, tvLabelBalance, ivEye, ivBg) = createRefs()
        val padding8 = 8.dp

        Text(
            text = if (hide) "****" else child.data.balance,
            color = Color.Black,
            modifier = Modifier.constrainAs(tvBalance) {
                start.linkTo(parent.start, padding8)
                end.linkTo(ivBg.start)
                top.linkTo(parent.top, padding8)
                width = Dimension.fillToConstraints
            },
            maxLines = 1
        )

        Text(
            text = child.data.text,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 4.dp)
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
                .padding(start = 4.dp, top = 4.dp)
                .size(23.dp)
                .constrainAs(ivEye) {
                    start.linkTo(tvLabelBalance.end)
                    linkTo(tvLabelBalance.top, tvLabelBalance.bottom)
                }
                .clickable {
                    hide = !hide
                },
        )

        AsyncImage(
            model = R.drawable.ic_wallet_bal,
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

fun stringImage(img: String): Int {
    return when (img) {
        "ic_wallet_bal" -> {
            R.drawable.ic_wallet_bal
        }
        "ic_points_bal" -> {
            R.drawable.ic_points_bal
        }
        "buy_crypto_home_icon" -> {
            R.drawable.buy_crypto_home_icon
        }
        "rewards_home_icon" -> {
            R.drawable.rewards_home_icon
        }
        "convert_home_icon" -> {
            R.drawable.convert_home_icon
        }
        "ixficard_home_icon" -> {
            R.drawable.ixficard_home_icon
        }
        else -> {
            0
        }
    }
}

@Composable
fun BalanceRow(
    content: @Composable RowScope.(List<ChildrenX>) -> Unit,
    children: List<ChildrenX>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 13.dp, end = 13.dp)
    ) {
        content(children)
    }
}

@Composable
fun MainScroll(
    content: @Composable ColumnScope.(List<Children>) -> Unit,
    children: List<Children>
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        content(children)
    }
}

enum class UI_TYPE(val type: String) {
    dashboard("dashboard-content-view"),
    home_balance_widget_view("home-balance-widget-view"),
    home_balance("home-balance"),
    ixfi_option_widget_view("ixfi-option-widget-view"),
    ixfi_options("ixfi-option"),
    none("none")
}

fun jsonStr2(): String {
    return "{\n" +
            "  \"type\": \"dashboard-content-view\",\n" +
            "  \"children\": [\n" +
            "    {\n" +
            "      \"type\": \"home-balance-widget-view\",\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"type\": \"home-balance\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"ixfi_points\",\n" +
            "            \"balance\": \"10 Point\",\n" +
            "            \"position\": 1,\n" +
            "            \"image\": \"ic_wallet_curve_home\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\"]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"home-balance\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"ixfi_wallet\",\n" +
            "            \"balance\": \"\$100\",\n" +
            "            \"position\": 2,\n" +
            "            \"image\": \"ic_wallet_curve_home\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\"]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"ixfi-option-widget-view\",\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"type\": \"ixfi-options\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"Dashboard\",\n" +
            "            \"position\": 1,\n" +
            "            \"image\": \"buy_crypto_home_icon\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\", \"#ECE8FF\"]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"ixfi-options\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"Rewards\",\n" +
            "            \"position\": 2,\n" +
            "            \"image\": \"rewards_home_icon\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\", \"#ECE8FF\"]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"ixfi-options\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"Steps\",\n" +
            "            \"position\": 3,\n" +
            "            \"image\": \"convert_home_icon\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\", \"#ECE8FF\"]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"ixfi-options\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"Cards\",\n" +
            "            \"position\": 4,\n" +
            "            \"image\": \"ixficard_home_icon\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\", \"#3461FF\"]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"slide_card\",\n" +
            "          \"data\": {\n" +
            "            \"text\": \"ixfi_wallet\",\n" +
            "            \"balance\": \"\$100\",\n" +
            "            \"image\": \"ic_wallet_curve_home\",\n" +
            "            \"bgcolor\": [\"#ECE8FF\"]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}"
}