package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TapeSubsystem {
    DcMotorEx tape;
    public TapeSubsystem(HardwareMap hardwareMap){
        this.tape = hardwareMap.get(DcMotorEx.class, "tape");
        this.tape.setDirection(DcMotorSimple.Direction.REVERSE);
        this.tape.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void out(){
        this.tape.setPower(1);
    }

    public void in(){
        this.tape.setPower(-1);
    }

    public void stop() { this.tape.setPower(0);}
}
