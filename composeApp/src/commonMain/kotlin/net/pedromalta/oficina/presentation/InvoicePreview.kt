package net.pedromalta.oficina.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.pedromalta.oficina.domain.Invoice
import net.pedromalta.oficina.domain.InvoiceItem
import net.pedromalta.oficina.domain.Shop

@Composable
fun InvoicePreview(
    shop: Shop,
    invoice: Invoice,
    modifier: Modifier = Modifier,
    removeAction: (InvoiceItem) -> Unit
) {

    Card(
        modifier = modifier
            .padding(16.dp)
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(24.dp)
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // ---------- SHOP HEADER ----------
            Text(shop.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("${shop.address.street}, ${shop.address.number}")
            Text("${shop.address.neighborhood} - ${shop.address.city}/${shop.address.state}")
            Text("CEP: ${shop.address.zipCode}")
            Text("Tel: ${shop.phone}")
            Text("CNPJ: ${shop.CNPJ}")

            Spacer(Modifier.height(16.dp))

            // ---------- SECTIONS ----------
            InvoiceSection("Peças", invoice.parts, removeAction)
            InvoiceSection("Serviços", invoice.services, removeAction)
            InvoiceSection("Serviços Terceirizados", invoice.thirdPartyServices, removeAction)

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()

            // ---------- TOTAL ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("TOTAL", fontWeight = FontWeight.Bold)
                Text(
                    invoice.total.toString(),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun InvoiceSection(
    title: String,
    items: List<InvoiceItem>,
    removeItem: (InvoiceItem) -> Unit
) {
    if (items.isEmpty()) return

    Spacer(Modifier.height(12.dp))

    Text(
        title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )

    Spacer(Modifier.height(8.dp))

    items.forEach {
        InvoiceRow(
            item = it,
            removeItem = removeItem
        )
        Spacer(Modifier.height(6.dp))
    }
}

@Composable
fun InvoiceRow(
    item: InvoiceItem,
    removeItem: (InvoiceItem) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(item.description)

        Spacer(Modifier.width(8.dp))

        DottedLeader(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            item.price.toString(),
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
        )
        IconButton(onClick = { removeItem(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "remover")
        }
    }
}

@Composable
fun DottedLeader(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.height(1.dp)) {
        val dotWidth = 4f
        val gap = 6f
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = Color.Black,
                start = Offset(x, 0f),
                end = Offset(x + dotWidth, 0f),
                strokeWidth = 1f
            )
            x += dotWidth + gap
        }
    }
}



