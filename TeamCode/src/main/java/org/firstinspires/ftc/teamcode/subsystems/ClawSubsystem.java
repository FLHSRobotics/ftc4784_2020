package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawSubsystem {
    Servo intake;
    public ClawSubsystem(HardwareMap hardwareMap){
        this.intake = hardwareMap.get(Servo.class, "intake");

        this.intake.setPosition(0.6);
    }

    public void trigger(){
        if(this.intake.getPosition() == 0.07){
            this.intake.setPosition(0.62);
        }else{
            this.intake.setPosition(0.07);
        }
    }

    public void pick(){
        this.intake.setPosition(0.07);
    }

    public void release(){
        this.intake.setPosition(0.67);
    }

}
