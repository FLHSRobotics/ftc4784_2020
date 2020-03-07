package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FoundationSubsystem{
    Servo leftServo, rightServo;
    public FoundationSubsystem(HardwareMap hardwareMap){
        this.leftServo = hardwareMap.get(Servo.class, "lfoundation");
        this.rightServo = hardwareMap.get(Servo.class, "rfoundation");

        this.leftServo.setDirection(Servo.Direction.REVERSE);

        this.rightServo.setPosition(0.6);
        this.leftServo.setPosition(0.6);
    }

    public void down(){
        this.leftServo.setPosition(0.04);
        this.rightServo.setPosition(0.04);
    }

    public void up(){
        this.leftServo.setPosition(0.3);
        this.rightServo.setPosition(0.3);
    }
}
