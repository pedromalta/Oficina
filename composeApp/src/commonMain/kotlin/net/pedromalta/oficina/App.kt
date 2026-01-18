package net.pedromalta.oficina

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import net.pedromalta.oficina.domain.Address
import net.pedromalta.oficina.domain.Shop
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
            shop = Shop(
                name = "Auto Mecânica Village do Sol",
                address = Address(
                    street = "Rua João Siqueira",
                    number = "15",
                    neighborhood = "Village do Sol",
                    city = "Guarapari",
                    state = "ES",
                    zipCode = "29226-752"
                ),
                phone = "(27) 99869-6550",
                CNPJ = "57.491.834/0001-64",
            ),
            viewModel = InvoiceViewModel()
        )
    }
}