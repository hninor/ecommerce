package com.example.marketplacepuj.ui.features.catalogo.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Calendar: ImageVector
    get() {
        if (_Calendar != null) {
            return _Calendar!!
        }
        _Calendar = ImageVector.Builder(
            name = "Calendar",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF0F172A)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6.75f, 3f)
                verticalLineTo(5.25f)
                moveTo(17.25f, 3f)
                verticalLineTo(5.25f)
                moveTo(3f, 18.75f)
                verticalLineTo(7.5f)
                curveTo(3f, 6.2574f, 4.0074f, 5.25f, 5.25f, 5.25f)
                horizontalLineTo(18.75f)
                curveTo(19.9926f, 5.25f, 21f, 6.2574f, 21f, 7.5f)
                verticalLineTo(18.75f)
                moveTo(3f, 18.75f)
                curveTo(3f, 19.9926f, 4.0074f, 21f, 5.25f, 21f)
                horizontalLineTo(18.75f)
                curveTo(19.9926f, 21f, 21f, 19.9926f, 21f, 18.75f)
                moveTo(3f, 18.75f)
                verticalLineTo(11.25f)
                curveTo(3f, 10.0074f, 4.0074f, 9f, 5.25f, 9f)
                horizontalLineTo(18.75f)
                curveTo(19.9926f, 9f, 21f, 10.0074f, 21f, 11.25f)
                verticalLineTo(18.75f)
            }
        }.build()
        return _Calendar!!
    }

private var _Calendar: ImageVector? = null


public val Box: ImageVector
    get() {
        if (_Box != null) {
            return _Box!!
        }
        _Box = ImageVector.Builder(
            name = "Box",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(21f, 8f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1f, -1.73f)
                lineToRelative(-7f, -4f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2f, 0f)
                lineToRelative(-7f, 4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3f, 8f)
                verticalLineToRelative(8f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, 1.73f)
                lineToRelative(7f, 4f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 0f)
                lineToRelative(7f, -4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 21f, 16f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3.3f, 7f)
                lineToRelative(8.7f, 5f)
                lineToRelative(8.7f, -5f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 22f)
                verticalLineTo(12f)
            }
        }.build()
        return _Box!!
    }

private var _Box: ImageVector? = null

