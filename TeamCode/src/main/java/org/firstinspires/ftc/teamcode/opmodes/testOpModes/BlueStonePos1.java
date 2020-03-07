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
import org.firstinspires.ftc.teamcode.subsystems.TensorflowVisionSubsystem;

import java.util.List;

@Autonomous
public class BlueStonePos1 extends LinearOpMode {
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
        // Stone 1
        Trajectory t2 = drivetrain.trajectoryBuilder(t1.end(),DriveConstants.STONE_CONSTRAINTS)
                .lineTo(new Vector2d(-28.0,28.0))
                .addDisplacementMarker(clawSubsystem::pick)
                .build();
        Trajectory t3 = drivetrain.trajectoryBuilder(t2.end())
                .lineToLinearHeading(new Vector2d(-28.0,35.0), Math.toRadians(0.0),DriveConstants.STONE_CONSTRAINTS)
                .build();
        Trajectory t4 = drivetrain.trajectoryBuilder(t3.end())
                .lineTo(buildingZonePlace)
                .addDisplacementMarker(clawSubsystem::release)
                .build();
        Trajectory t5 = drivetrain.trajectoryBuilder(t4.end(), false)
                .lineToLinearHeading(new Vector2d(-35.0,40.0), Math.toRadians(-90))
                .build();

        //stone4
        Trajectory stone4Pick = drivetrain.trajectoryBuilder(t5.end())
                .lineTo(new Vector2d(-58.5,25.0))
                .addDisplacementMarker(clawSubsystem::pick)
                .build();
        Trajectory stone4Move = drivetrain.trajectoryBuilder(stone4Pick.end())
                .lineTo(new Vector2d(-58.5,29))
                .build();
        Trajectory stone4Trans = drivetrain.trajectoryBuilder(stone4Move.end())
                .lineToLinearHeading(new Vector2d(-60,29), Math.toRadians(-1.0),DriveConstants.STONE_CONSTRAINTS)
                .build();
        Trajectory stone4Place  = drivetrain.trajectoryBuilder(stone4Trans.end())
                .lineTo(new Vector2d(0,29))
                .addDisplacementMarker(clawSubsystem::release)
                .build();

        waitForStart();

        drivetrain.followTrajectory(t1);
        drivetrain.followTrajectory(t2);
        Thread.sleep(150);
        drivetrain.followTrajectory(t3);
        drivetrain.followTrajectory(t4);
        Thread.sleep(50);
        drivetrain.followTrajectory(t5);
        drivetrain.followTrajectory(stone4Pick);
        Thread.sleep(150);
        drivetrain.followTrajectory(stone4Move);
        drivetrain.followTrajectory(stone4Trans);
        drivetrain.followTrajectory(stone4Place);
        Thread.sleep(50);
    }
}
