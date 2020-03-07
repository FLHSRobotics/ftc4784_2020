package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import com.qualcomm.hardware.logitech.LogitechGamepadF310;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.tensorflow.lite.TensorFlowLite;

import java.util.List;

public class TensorflowVisionSubsystem {
    private static final String TFOD_MODEL_ASSET = "detect.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Stone";
    public static final String LABEL_SECOND_ELEMENT = "Skystone";
    private static final String VUFORIA_KEY = "AVCNSTD/////AAABmTwdHcJUL0A9vJepRybq2gMBX64uJ5fQZePzyasRKU3rcwWbBLXvtczSuR4kWsswpiYDdaQSBHwi4wJxvJE4Kq+ttr0ZEmehKbhWf9eFlaCDuhYYvnTPwLoj+xIss4YPsXUOdExoDamoFR+PTZjRUa3Shf+t56/eggATK/4aF+1C1B/1VP+jArEz2VobuZQ7DhXuH2PKK7ufYVcshWJh7NsJxhuYyNSSzH6MsOpAiKg/lmPl4TAewXXbTxaRYiQfN/shUScZc/3fBiHwaozc8Ix8qwlDPBymFtyoV9wstWnzh3sEzbnkUOCeVkLsVVCPbhfgH5s2siF724LuWFCzrmh757i1cjbGLMNbtI3qNaEe";

    private VuforiaLocalizer vuforia;

    private HardwareMap hardwareMap;

    private TFObjectDetector tfod;

    public TensorflowVisionSubsystem(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            Log.e("Subsystem", "Device doesn't support tfod.");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }
    }

    public List<Recognition> getRecognitions(){
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            Log.i("SUBSYSTEM", tfod.getRecognitions().toString());
            return tfod.getRecognitions();
        }else{
            return null;
        }
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
        CameraDevice.getInstance().setField("opti-zoom", "opti-zoom-on");
        CameraDevice.getInstance().setField("zoom", "19");
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.6;
        tfodParameters.useObjectTracker = true;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void shutdown(){
        if (tfod != null) {
            tfod.shutdown();
        }
    }
}
