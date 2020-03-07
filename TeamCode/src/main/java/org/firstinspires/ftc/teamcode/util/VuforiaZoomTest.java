package org.firstinspires.ftc.teamcode.util;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;
import com.vuforia.CameraField;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Arrays;
@TeleOp
public class VuforiaZoomTest extends OpMode {
    protected static final String TAG = "VuforiaZoom";

    private VuforiaLocalizer vuforia;
    private VuforiaTrackables targetsRoverRuckus;
    private ElapsedTime timer = new ElapsedTime();
    private boolean zoomedIn = false;
    private static final String VUFORIA_KEY = "AVCNSTD/////AAABmTwdHcJUL0A9vJepRybq2gMBX64uJ5fQZePzyasRKU3rcwWbBLXvtczSuR4kWsswpiYDdaQSBHwi4wJxvJE4Kq+ttr0ZEmehKbhWf9eFlaCDuhYYvnTPwLoj+xIss4YPsXUOdExoDamoFR+PTZjRUa3Shf+t56/eggATK/4aF+1C1B/1VP+jArEz2VobuZQ7DhXuH2PKK7ufYVcshWJh7NsJxhuYyNSSzH6MsOpAiKg/lmPl4TAewXXbTxaRYiQfN/shUScZc/3fBiHwaozc8Ix8qwlDPBymFtyoV9wstWnzh3sEzbnkUOCeVkLsVVCPbhfgH5s2siF724LuWFCzrmh757i1cjbGLMNbtI3qNaEe";


    @Override
    public void init() {
        //Vuforia initialization

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parametersVuforia = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parametersVuforia.vuforiaLicenseKey = VUFORIA_KEY;
        parametersVuforia.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        this.vuforia = ClassFactory.getInstance().createVuforia(parametersVuforia);

        targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        Log.v(TAG, Vuforia.getLibraryVersion());

        targetsRoverRuckus.activate();

    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void loop() {
        if (true) {
            CameraDevice camera = CameraDevice.getInstance();
            camera.setField("opti-zoom", "opti-zoom-on");
            camera.setField("zoom", "29");
            int numFields = camera.getNumFields();
            Log.v(TAG, String.valueOf(numFields));
            int[] valueCounts = new int[]{0, 0, 0, 0, 0, 0};
            for (int i = 0; i < numFields; i++) {
                CameraField cameraField = new CameraField();
                boolean success = camera.getCameraField(i, cameraField);
                String valueString = "FAIL!";

                valueCounts[cameraField.getType()]++;

                if (cameraField.getType() == CameraField.DataType.TypeString) {
                    valueString = camera.getFieldString(cameraField.getKey());
                }
                if (cameraField.getType() == CameraField.DataType.TypeInt64) {
                    long[] l = new long[3];
                    camera.getFieldInt64(cameraField.getKey(), l);
                    valueString = Arrays.toString(l);
                }
                if (cameraField.getType() == CameraField.DataType.TypeFloat) {
                    float[] f = new float[3];
                    camera.getFieldFloat(cameraField.getKey(), f);
                    valueString = Arrays.toString(f);
                }
                if (cameraField.getType() == CameraField.DataType.TypeBool) {
                    boolean[] b = new boolean[1];
                    camera.getFieldBool(cameraField.getKey(), b);
                    valueString = Arrays.toString(b);
                }
                if (cameraField.getType() == CameraField.DataType.TypeInt64Range) {
                    long[] l = new long[3];
                    camera.getFieldInt64Range(cameraField.getKey(), l);
                    valueString = Arrays.toString(l);
                }
                if (cameraField.getType() == CameraField.DataType.TypeUnknown) {
                    valueString = "Unknown";
                }


                Log.v(TAG, cameraField.getKey() + ", " + cameraField.getType() + ",  " + valueString);
            }
            Log.v(TAG, Arrays.toString(valueCounts));

        }
    }
}