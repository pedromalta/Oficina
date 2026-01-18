package net.pedromalta.oficina.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.pedromalta.oficina.domain.InvoiceItemType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceMainScreen(
    shopName: String,
    logo: Painter,
    viewModel: InvoiceViewModel = remember { InvoiceViewModel() }
) {
    var description by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var showPreview by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(shopName) })
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // LEFT: Editor
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = logo,
                    contentDescription = "Logomarca",
                    modifier = Modifier.height(100.dp).align(Alignment.CenterHorizontally)
                )

                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Adicionar peças", fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Peça") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = priceInput,
                            onValueChange = { priceInput = it },
                            label = { Text("Preço") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                priceInput.toDoubleOrNull()?.let {
                                    viewModel.addItem(description, it, InvoiceItemType.PART)
                                    description = ""
                                    priceInput = ""
                                }
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("adicionar")
                        }
                    }
                }

                HorizontalDivider()

                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Adicionar serviços terceirizados", fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Serviço terceirizado") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = priceInput,
                            onValueChange = { priceInput = it },
                            label = { Text("Preço") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                priceInput.toDoubleOrNull()?.let {
                                    viewModel.addItem(description, it, InvoiceItemType.THIRD_PARTY_SERVICE)
                                    description = ""
                                    priceInput = ""
                                }
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("adicionar")
                        }
                    }
                }

                HorizontalDivider()

                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Adicionar serviços", fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Serviço") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = priceInput,
                            onValueChange = { priceInput = it },
                            label = { Text("Preço") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                priceInput.toDoubleOrNull()?.let {
                                    viewModel.addItem(description, it, InvoiceItemType.SERVICE)
                                    description = ""
                                    priceInput = ""
                                }
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("adicionar")
                        }
                    }
                }

                HorizontalDivider()

                Card(modifier = Modifier.weight(1f)) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(viewModel.parts) { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.description)
                                    Text(item.price.toString(), fontSize = 12.sp)
                                }
                                IconButton(onClick = { viewModel.removeItem(item) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "remover")
                                }
                            }
                            HorizontalDivider()
                        }

                        items(viewModel.thirdPartService) { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.description)
                                    Text(item.price.toString(), fontSize = 12.sp)
                                }
                                IconButton(onClick = { viewModel.removeItem(item) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "remover")
                                }
                            }
                            HorizontalDivider()
                        }

                        items(viewModel.services) { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.description)
                                    Text(item.price.toString(), fontSize = 12.sp)
                                }
                                IconButton(onClick = { viewModel.removeItem(item) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "remover")
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }

                Button(
                    onClick = { showPreview = true },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Preview da fatura")
                }
            }

            if (showPreview) {
                Column(modifier = Modifier.weight(1f)) {
                    InvoicePreview(
                        shopName = shopName,
                        invoice = viewModel.invoice,
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = { printInvoice(viewModel.invoice, shopName) },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        Text("imprimir")
                    }
                }
            }

        }
    }
}