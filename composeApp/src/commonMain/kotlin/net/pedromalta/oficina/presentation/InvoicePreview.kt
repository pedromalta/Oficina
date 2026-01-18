package net.pedromalta.oficina.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.pedromalta.oficina.domain.Invoice

@Composable
fun InvoicePreview(
    shopName: String,
    invoice: Invoice,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(shopName, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            HorizontalDivider()

            invoice.items.forEach { item ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(item.description, modifier = Modifier.weight(1f))
                    Text(item.price.toString())
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("TOTAL", fontWeight = FontWeight.Bold)
                Text(invoice.total.toString(), fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Thank you for your business",
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
