package net.pedromalta.oficina

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import net.pedromalta.oficina.presentation.InvoiceMainScreen
import net.pedromalta.oficina.presentation.InvoiceViewModel
import oficina.composeapp.generated.resources.Res
import oficina.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun App() {
    MaterialTheme {
        InvoiceMainScreen(
            shopName = "Auto Mecânica Village do Sol",
            logo = painterResource(Res.drawable.compose_multiplatform),
            viewModel = InvoiceViewModel()
        )
    }
}