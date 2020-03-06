package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CapperSubsystem {
    Servo capstone;
    CapperSubsystem(HardwareMap hardwareMap){
        this.capstone = hardwareMap.get(Servo.class, "capstone");
        this.capstone.setDirection(Servo.Direction.REVERSE);
        this.capstone.setPosition(0.5);
    }

    public void trigger(){
        if(this.capstone.getPosition() == 0.5){
           this.capstone.setPosition(0);
        }else{
            this.capstone.setPosition(0.5);
        }
    }
}
