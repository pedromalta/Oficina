package net.pedromalta.oficina.presentation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.pedromalta.oficina.domain.Invoice
import net.pedromalta.oficina.domain.InvoiceItem
import net.pedromalta.oficina.domain.Shop
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterJob
import javax.imageio.ImageIO

actual fun printInvoice(
    invoice: Invoice,
    shop: Shop
) {
    AppRuntime.AppScope.launch(Dispatchers.IO) {
        val printerJob = PrinterJob.getPrinterJob()
        printerJob.jobName = "Fatura"

        val logo: BufferedImage? = runCatching {
            ImageIO.read(
                object {}.javaClass.getResourceAsStream("/logo_monochrome.png")
            )
        }.getOrNull()

        printerJob.setPrintable { graphics: Graphics, pageFormat: PageFormat, pageIndex: Int ->
            if (pageIndex > 0) return@setPrintable Printable.NO_SUCH_PAGE

            val g2d = graphics as java.awt.Graphics2D
            g2d.translate(pageFormat.imageableX, pageFormat.imageableY)

            val normalFont = g2d.font
            val boldFont = normalFont.deriveFont(java.awt.Font.BOLD)
            val monoFont = java.awt.Font(
                java.awt.Font.MONOSPACED,
                java.awt.Font.PLAIN,
                normalFont.size
            )
            val normalStroke = g2d.stroke
            val dottedStroke = java.awt.BasicStroke(
                1f,
                java.awt.BasicStroke.CAP_BUTT,
                java.awt.BasicStroke.JOIN_BEVEL,
                0f,
                floatArrayOf(2f, 4f),
                0f
            )

            val logoWidth = 80
            val logoHeight = 80

            val leftX = 20
            val rightX = pageFormat.imageableWidth.toInt() - 80

            var y = 20

            fun line(text: String, bold: Boolean = false, extraSpace: Int = 0) {
                g2d.font = if (bold) boldFont else normalFont
                g2d.drawString(text, leftX, y)
                y += 15 + extraSpace
            }

            // ---------- SHOP HEADER ----------

            // Draw logo (top-right)
            logo?.let {
                val logoX = pageFormat.imageableWidth.toInt() - logoWidth
                val logoY = 10
                g2d.drawImage(it, logoX, logoY, logoWidth, logoHeight, null)
            }

            // Text starts slightly lower so it aligns nicely
            line(shop.name, bold = true, extraSpace = 5)
            line("${shop.address.street}, ${shop.address.number}")
            line("${shop.address.neighborhood} - ${shop.address.city}/${shop.address.state}")
            line("CEP: ${shop.address.zipCode}")
            line("Tel: ${shop.phone}")
            line("CNPJ: ${shop.CNPJ}")

            y += 15

            // ---------- SECTIONS ----------
            fun printSection(title: String, items: List<InvoiceItem>) {
                if (items.isEmpty()) return

                line(title, bold = true, extraSpace = 8)

                items.forEach { item ->
                    g2d.font = normalFont

                    // Description
                    g2d.drawString(item.description, leftX, y)

                    // Price
                    val priceText = item.price.toString()

                    g2d.font = monoFont
                    val priceWidth = g2d.fontMetrics.stringWidth(priceText)
                    val priceDrawX = rightX - priceWidth
                    g2d.drawString(priceText, priceDrawX, y)
                    g2d.font = normalFont

                    // Dotted leader line
                    val descWidth = g2d.fontMetrics.stringWidth(item.description)

                    val lineStart = leftX + descWidth + 6
                    val lineEnd = priceDrawX - 6

                    if (lineEnd > lineStart) {
                        g2d.stroke = dottedStroke
                        g2d.drawLine(lineStart, y - 4, lineEnd, y - 4)
                        g2d.stroke = normalStroke
                    }

                    y += 18
                }

                y += 10
            }

            printSection("Peças", invoice.parts)
            printSection("Serviços", invoice.services)
            printSection("Serviços terceirizados", invoice.thirdPartyServices)

            y += 10

            // ---------- TOTAL ----------
            g2d.font = boldFont
            g2d.drawString("TOTAL: ${invoice.total}", leftX, y)

            Printable.PAGE_EXISTS
        }

        if (printerJob.printDialog()) {
            printerJob.print()
        }
    }
}