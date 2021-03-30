package com.mx.mundet.eats.ui.mvp.camera

import io.fotoapparat.configuration.Configuration
import io.fotoapparat.parameter.Flash
import io.fotoapparat.selector.*
import io.fotoapparat.util.FrameProcessor

/**
 * Created by Alexander Juárez with Date 26/03/2021
 * @author Alexander Juárez
 */

class CameraConfigurationCustom (flash : Flash) : Configuration {

    override val flashMode: FlashSelector = {
        flash
    }

    override val antiBandingMode: AntiBandingModeSelector?
        get() = TODO("Not yet implemented")
    override val exposureCompensation: ExposureSelector?
        get() = TODO("Not yet implemented")
    override val focusMode: FocusModeSelector?
        get() = TODO("Not yet implemented")
    override val frameProcessor: FrameProcessor?
        get() = TODO("Not yet implemented")
    override val jpegQuality: QualitySelector?
        get() = TODO("Not yet implemented")
    override val pictureResolution: ResolutionSelector?
        get() = TODO("Not yet implemented")
    override val previewFpsRange: FpsRangeSelector?
        get() = TODO("Not yet implemented")
    override val previewResolution: ResolutionSelector?
        get() = TODO("Not yet implemented")
    override val sensorSensitivity: SensorSensitivitySelector?
        get() = TODO("Not yet implemented")
}