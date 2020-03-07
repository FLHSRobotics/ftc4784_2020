package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftSubsystem {
    DcMotorEx lift;
    public LiftSubsystem(HardwareMap hardwareMap){
        this.lift = hardwareMap.get(DcMotorEx.class, "lift");
        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void up(){
        this.lift.setPower(0.95);
    }

    public void down(){
        this.lift.setPower(-0.7);
    }

    public void stop(){ this.lift.setPower(0);}

    public void moveTo(int encoderTicks){
        this.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.lift.setTargetPosition(encoderTicks);
        this.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.lift.setPower(1);
        while(this.lift.isBusy()){
            Log.i("SUBSYSTEM", "Lift Pos:" + this.lift.getCurrentPosition());
        };
        this.lift.setPower(0);
    }
}
