package amazon.sqsreader

import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

fun main(args: Array<String>) {
    val mapper = ObjectMapper()
    val sqs = AmazonSQSClientBuilder.defaultClient()

    val result = mutableListOf<String>()
    if(args.size != 2) {
        throw RuntimeException("Invalid arguments. Please provide the queue URL and the attribute you want to extract from the messages")
    }
    val queueUrl = args[0]
    val attributeName = args[1]

    do {
        val messages = sqs.receiveMessage(queueUrl).messages
        val typeRef: TypeReference<HashMap<String, String>> = object : TypeReference<HashMap<String, String>>() {}

        result.addAll(
            messages.mapNotNull {
                val entry = mapper.readValue(it.body, typeRef)
                entry[attributeName] ?: run {
                    println("Entry has no attribute called $attributeName")
                    println(entry)
                    null
                }
            }
        )
    } while (messages.isNotEmpty())

    File("result.txt").writeText(result.toString())

    println("Successfully collected ${result.size} messages")
}