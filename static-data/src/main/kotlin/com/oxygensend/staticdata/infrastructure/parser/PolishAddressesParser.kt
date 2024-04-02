package com.oxygensend.staticdata.infrastructure.parser

import com.oxygensend.staticdata.context.AddressParser
import com.oxygensend.staticdata.context.dto.ParsedAddressDto
import java.net.URI
import java.util.zip.ZipInputStream

internal class PolishAddressesParser(private val parserUri: String) : AddressParser {

    companion object {
        private const val POSTAL_CODE_IDX = 0;
        private const val CITY_NAME_IDX = 2;
        private const val VOIVODESHIP_IDX = 3;
    }

    override fun getAddresses(): List<ParsedAddressDto> {
        val csvLines = downloadCsvFile()
        val addresses = mutableMapOf<String, MutableSet<String>>()

        csvLines.drop(1).forEach { csvLine ->
            val fields = csvLine.split(';', ignoreCase = false, limit = 5)
            val voivodeship = fields[VOIVODESHIP_IDX].split(" ")[1]
            val city = "${fields[CITY_NAME_IDX]} Woj.$voivodeship"

            addresses.getOrPut(city) { mutableSetOf() }.add(fields[POSTAL_CODE_IDX])
        }

        return addresses.entries
            .map { ParsedAddressDto(it.key, it.value) }
    }


    private fun downloadCsvFile(): List<String> {
        val uri = URI.create(parserUri)
        val zipInputStream = ZipInputStream(uri.toURL().openStream())

        var entry = zipInputStream.nextEntry
        var csvFileName: String? = null
        val fileName = uri.path.removeSuffix(".zip").removePrefix("/")
        while (entry != null) {
            if (fileName == entry.name) {
                csvFileName = entry.name
                break
            }
            entry = zipInputStream.nextEntry
        }

        if (csvFileName == null) {
            throw RuntimeException("Unable to find file $fileName")
        }

        return zipInputStream.reader().use { it.readLines() }
    }
}