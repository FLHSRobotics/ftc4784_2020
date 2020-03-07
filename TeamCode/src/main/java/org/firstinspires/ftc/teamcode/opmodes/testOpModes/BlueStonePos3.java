package org.firstinspires.ftc.teamcode.opmodes.testOpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FoundationSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TapeSubsystem;

import java.util.List;

@Autonomous
public class BlueStonePos3 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrivetrain drivetrain = new MecanumDrivetrain(hardwareMap);
        ClawSubsystem clawSubsystem = new ClawSubsystem(hardwareMap);
        FoundationSubsystem foundationSubsystem = new FoundationSubsystem(hardwareMap);
        LiftSubsystem liftSubsystem = new LiftSubsystem(hardwareMap);
        TapeSubsystem tapeSubsystem = new TapeSubsystem(hardwareMap);
        List<Recognition> recognitions = null;
        int state = 0;// ENUM: 0 init,
        Vector2d  buildingZonePlace = new Vector2d(0.0,35.0);
        Pose2d startPose = new Pose2d(-24,60,Math.toRadians(-90));
        drivetrain.setPoseEstimate(startPose);

        Trajectory t1 = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                .lineTo(new Vector2d(-24.0, 35.0))
                .build();
        // Stone 3
        Trajectory t2 = drivetrain.trajectoryBuilder(t1.end(),DriveConstants.STONE_CONSTRAINTS)
                .lineTo(new Vector2d(-46,28.0))
                .addDisplacementMarker(clawSubsystem::pick)
                .build();
        Trajectory t3 = drivetrain.trajectoryBuilder(t2.end())
                .lineTo(new Vector2d(-46,34))
                .build();
        Trajectory t4 = drivetrain.trajectoryBuilder(t3.end())
                .lineToLinearHeading(new Vector2d(-46,35.0), Math.toRadians(-4),DriveConstants.STONE_CONSTRAINTS)
                .build();
        Trajectory t5 = drivetrain.trajectoryBuilder(t4.end())
                .lineTo(buildingZonePlace)
                .addDisplacementMarker(clawSubsystem::release)
                .build();
        Trajectory t6 = drivetrain.trajectoryBuilder(t5.end())
                .lineToLinearHeading(new Vector2d(-40,30.0), Math.toRadians(-90))
                .build();

        //stone2
        Trajectory stone2Pick = drivetrain.trajectoryBuilder(t6.end())
                .lineTo(new Vector2d(-42,27.0), DriveConstants.STONE_CONSTRAINTS)
                .addDisplacementMarker(clawSubsystem::pick)
                .build();
        Trajectory stone2Move = drivetrain.trajectoryBuilder(stone2Pick.end())
                .lineTo(new Vector2d(-42,35))
                .build();
        Trajectory stone2Trans = drivetrain.trajectoryBuilder(stone2Move.end())
                .lineToLinearHeading(new Vector2d(-35,35.0), Math.toRadians(-3),DriveConstants.STONE_CONSTRAINTS)
                .build();
        Trajectory stone2Place  = drivetrain.trajectoryBuilder(stone2Trans.end())
                .lineTo(new Vector2d(0,33))
                .addDisplacementMarker(clawSubsystem::release)
                .build();

        waitForStart();

        drivetrain.followTrajectory(t1);
        drivetrain.followTrajectory(t2);
        Thread.sleep(200);
        drivetrain.followTrajectory(t3);
        drivetrain.followTrajectory(t4);
        drivetrain.followTrajectory(t5);
        Thread.sleep(50);
        drivetrain.followTrajectory(t6);
        drivetrain.followTrajectory(stone2Pick);
        Thread.sleep(200);
        drivetrain.followTrajectory(stone2Move);
        drivetrain.followTrajectory(stone2Trans);
        drivetrain.followTrajectory(stone2Place);
        Thread.sleep(50);
    }
}
