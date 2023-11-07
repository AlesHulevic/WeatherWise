package com.example.tolaaleksey.weatherinfo.classes

import java.util.UUID

class Day(val weather: Weather, val description: String, val id: UUID = UUID.randomUUID()) {
}