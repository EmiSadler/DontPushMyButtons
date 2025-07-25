package SassySwitches

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

fun createSmoothHexagonPath(size: Float): Path {
    val roundedPolygon = RoundedPolygon(
        numVertices = 6,
        radius = size / 1.9f,
        centerX = size / 2,
        centerY = size / 2,
        rounding = CornerRounding(size / 13f, smoothing = 0.1f)
    )
    return roundedPolygon.toPath().asComposePath()
}

fun createSmoothTrianglePath(size: Float): Path {
    val roundedTriangle = RoundedPolygon(
        numVertices = 3,
        radius = size / 1.6f,
        centerX = size / 2,
        centerY = size / 2,
        rounding = CornerRounding(size / 10f, smoothing = 0.1f)
    )

    return roundedTriangle.toPath().asComposePath()
}

fun createSmoothPentagonPath(size: Float): Path {
    val roundedPentagon = RoundedPolygon(
        numVertices = 5,
        radius = size / 1.7f,
        centerX = size / 2,
        centerY = size / 2,
        rounding = CornerRounding(size / 10f, smoothing = 0.1f)
    )

    return roundedPentagon.toPath().asComposePath()

}