package net.pedromalta.oficina.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.pedromalta.oficina.domain.InvoiceItemType
import net.pedromalta.oficina.domain.MoneyAmount
import net.pedromalta.oficina.domain.Shop
import oficina.composeapp.generated.resources.Res
import oficina.composeapp.generated.resources.logo_color
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceMainScreen(
    shop: Shop,
    viewModel: InvoiceViewModel = remember { InvoiceViewModel() }
) {
    var description by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf(MoneyAmount()) }
    var showPreview by remember { mutableStateOf(true) }
    var selectedType by remember { mutableStateOf(InvoiceItemType.PART) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Header(
                        shop = shop,
                        cleanInvoice = { viewModel.clearInvoice() },
                        printInvoice = { printInvoice(viewModel.invoice, shop) }
                    )
                }
            )
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
                    .weight(0.7f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Adicionar item", fontWeight = FontWeight.Bold)

                        InvoiceItemType.entries.forEach { type ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedType = type
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedType == type,
                                    onClick = { selectedType = type }
                                )
                                Text(
                                    text = when (type) {
                                        InvoiceItemType.PART -> "Peça"
                                        InvoiceItemType.SERVICE -> "Serviço"
                                        InvoiceItemType.THIRD_PARTY_SERVICE -> "Serviço Terceirizado"
                                    }
                                )
                            }

                        }

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = {
                                Text(
                                    text = when (selectedType) {
                                        InvoiceItemType.PART -> "Peça"
                                        InvoiceItemType.SERVICE -> "Serviço"
                                        InvoiceItemType.THIRD_PARTY_SERVICE -> "Serviço Terceirizado"
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            MoneyAmountInput(
                                value = priceInput,
                                onValueChange = { newValue ->
                                    priceInput = newValue
                                },
                                label = "Preço",
                                modifier = Modifier.weight(1f).padding(end = 16.dp)
                            )

                            Button(
                                onClick = {
                                    viewModel.addItem(description, priceInput, selectedType)
                                    description = ""
                                    priceInput = MoneyAmount(0)
                                },
                                enabled = description.isNotBlank(),
                                modifier = Modifier.align(Alignment.Bottom)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("adicionar")
                            }
                        }
                    }
                }
            }

            if (showPreview) {
                Column(modifier = Modifier.weight(1f)) {
                    InvoicePreview(
                        shop = shop,
                        removeAction = { item ->
                            viewModel.removeItem(item)
                        },
                        invoice = viewModel.invoice,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    }
}

@Composable
fun Header(
    shop: Shop,
    cleanInvoice: () -> Unit,
    printInvoice: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(Res.drawable.logo_color),
                contentDescription = "G&E Logo",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                text = shop.name,
            )
        }
        Row {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
            ) {
                Text("limpar")
            }
            Button(
                onClick = printInvoice,
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
            ) {
                Text("imprimir")
            }
        }
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentSize()
                    ) {
                        Text(
                            text = "Tem certeza que deseja limpar a fatura?",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { showDialog = false }
                            ) {
                                Text(
                                    "Cancelar",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = {
                                    showDialog = false
                                    cleanInvoice()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text(
                                    "Limpar",
                                    color = MaterialTheme.colorScheme.onError
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoneyAmountInput(
    value: MoneyAmount,
    onValueChange: (MoneyAmount) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Amount"
) {
    var rawDigits by remember(value.amountInCents) {
        mutableStateOf(value.amountInCents.toString())
    }

    OutlinedTextField(
        value = rawDigits,
        onValueChange = { input ->
            val digitsOnly = input.filter { it.isDigit() }
            rawDigits = digitsOnly.ifEmpty { "0" }

            onValueChange(
                MoneyAmount(
                    amountInCents = rawDigits.toLong()
                )
            )
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        visualTransformation = MoneyVisualTransformation(),
        modifier = modifier,
        singleLine = true,
    )
}

class MoneyVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }
        val formatted = formatAsMoney(digits)

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                // Cursor always stays at the end
                return formatted.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return digits.length
            }
        }

        return TransformedText(
            AnnotatedString(formatted),
            offsetMapping
        )
    }
}