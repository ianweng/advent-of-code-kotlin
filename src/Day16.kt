interface Packet {
    val length: Int
    val ver: Int
    val type: Int
    fun getVerSum(): Int
    fun getValue(): Long
}

fun main() {
    class BiReader(val raw: String, var i: Int = 0) {
        fun nextInt(num: Int): Int {
            return nextString(num).toInt(2)
        }

        fun nextString(num: Int): String {
            val value = raw.substring(i, i + num)
            i += num
            return value
        }
    }

    class ValuePacket(
        override val length: Int, override val ver: Int, override val type: Int, val literalValue: Long
    ) : Packet {
        override fun getVerSum(): Int {
            return ver
        }

        override fun getValue(): Long {
            return literalValue
        }
    }

    class PacketPacket(
        override val length: Int,
        override val ver: Int,
        override val type: Int,
        val subPackets: List<Packet>,
    ) : Packet {
        override fun getVerSum(): Int {
            return ver + subPackets.sumOf { it.getVerSum() }
        }

        override fun getValue(): Long {
            if (type == 0) return subPackets.sumOf { it.getValue() }
            if (type == 1) {
                var result = subPackets[0].getValue()
                (1 until subPackets.size).forEach { result *= subPackets[it].getValue() }
                return result
            }
            if (type == 2) return subPackets.minOf { it.getValue() }
            if (type == 3) return subPackets.maxOf { it.getValue() }
            if (type == 5) return if (subPackets[0].getValue() > subPackets[1].getValue()) 1 else 0
            if (type == 6) return if (subPackets[0].getValue() < subPackets[1].getValue()) 1 else 0
            if (type == 7) return if (subPackets[0].getValue() == subPackets[1].getValue()) 1 else 0
            throw Error("unsupported type: $type")
        }
    }

    fun String.hexToBi(): String {
        return this.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")
    }

    fun parseBi(bi: String): Packet {
        val reader = BiReader(bi)
        val version = reader.nextInt(3)
        val type = reader.nextInt(3)

        if (type == 4) {
            val segments = mutableListOf<String>()
            var hasMore = true
            while (hasMore) {
                hasMore = reader.nextInt(1) == 1
                segments.add(reader.nextString(4))
            }
            return ValuePacket(reader.i, version, type, segments.joinToString("").toLong(2))
        }

        if (reader.nextInt(1) == 0) {
            val length = reader.nextInt(15)
            var subBi = reader.nextString(length)
            val subPackets = mutableListOf<Packet>()
            while (subBi.isNotEmpty()) {
                val subPacket = parseBi(subBi)
                subPackets.add(subPacket)
                subBi = subBi.substring(subPacket.length)
            }
            return PacketPacket(reader.i, version, type, subPackets)
        }

        val num = reader.nextInt(11)
        val subPackets = mutableListOf<Packet>()
        for (j in 0 until num) {
            val subBi = bi.substring(reader.i)
            val subPacket = parseBi(subBi)
            subPackets.add(subPacket)
            reader.i += subPacket.length
        }
        return PacketPacket(reader.i, version, type, subPackets)
    }

    fun part1(input: String): Int {
        return parseBi(input.hexToBi()).getVerSum()
    }

    fun part2(input: String): Long {
        return parseBi(input.hexToBi()).getValue()
    }

    println(part1("8A004A801A8002F478"))
    println(part1("620080001611562C8802118E34"))
    println(part1("C0015000016115A2E0802F182340"))
    println(part1("A0016C880162017C3686B18A3D4780"))

    println(part2("C200B40A82")) // finds the sum of 1 and 2, resulting in the value 3.
    println(part2("04005AC33890")) // finds the product of 6 and 9, resulting in the value 54.
    println(part2("880086C3E88112")) // finds the minimum of 7, 8, and 9, resulting in the value 7.
    println(part2("CE00C43D881120")) // finds the maximum of 7, 8, and 9, resulting in the value 9.
    println(part2("D8005AC2A8F0")) // produces 1, because 5 is less than 15.
    println(part2("F600BC2D8F")) // produces 0, because 5 is not greater than 15.
    println(part2("9C005AC2F8F0")) // produces 0, because 5 is not equal to 15.
    println(part2("9C0141080250320F1802104A08")) // produces 1, because 1 + 3 = 2 * 2.

    val input = readInput("Day16")[0]
    println(part1(input))
    println(part2(input))
}
