package org.firstinspires.ftc.teamcode.opmodes;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
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
import org.firstinspires.ftc.teamcode.util.AssetsTrajectoryManager;

import java.util.Comparator;
import java.util.List;

@Autonomous
public class RedStone extends LinearOpMode {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrivetrain drivetrain = new MecanumDrivetrain(hardwareMap);
        ClawSubsystem clawSubsystem = new ClawSubsystem(hardwareMap);
        FoundationSubsystem foundationSubsystem = new FoundationSubsystem(hardwareMap);
        LiftSubsystem liftSubsystem = new LiftSubsystem(hardwareMap);
        TapeSubsystem tapeSubsystem = new TapeSubsystem(hardwareMap);
        TensorflowVisionSubsystem visionSubsystem = new TensorflowVisionSubsystem(hardwareMap);
        List<Recognition> recognitions = null;
        int state = 0;// ENUM: 0 init,
        int stonePos = 3;
        Vector2d buildingZonePlace = new Vector2d(14.0, -35.0);
        Pose2d startPose = new Pose2d(-24, -60, Math.toRadians(90));
        drivetrain.setPoseEstimate(startPose);

        Trajectory init = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                .lineTo(new Vector2d(-24, -38.0))
                .build();

        waitForStart();

        drivetrain.followTrajectory(init);
        do{
            recognitions = visionSubsystem.getRecognitions();
        } while (recognitions.size() < 2);
        Thread.sleep(750);
        recognitions.sort(Comparator.comparingDouble(Recognition::getLeft).reversed());
        if (recognitions.get(0).getLabel().equals(TensorflowVisionSubsystem.LABEL_SECOND_ELEMENT)) {
            stonePos = 1;
        } else if (recognitions.get(1).getLabel().equals(TensorflowVisionSubsystem.LABEL_SECOND_ELEMENT)) {
            stonePos = 2;
        }

        Log.i("OPMODE", "Stone Pos:" + stonePos);

        if (stonePos == 1) {
            Trajectory t1 = drivetrain.trajectoryBuilder(init.end())
                    .lineTo(new Vector2d(-24,-40))
                    .build();
            Trajectory t2 = drivetrain.trajectoryBuilder(t1.end(), DriveConstants.STONE_CONSTRAINTS)
                    .lineTo(new Vector2d(-28.0, -26.5))
                    .addDisplacementMarker(clawSubsystem::pick)
                    .build();
            Trajectory t2_2 = drivetrain.trajectoryBuilder(t2.end())
                    .lineTo(new Vector2d(-30.0, -35))
                    .build();
            Trajectory t3 = drivetrain.trajectoryBuilder(t2_2.end())
                    .lineToLinearHeading(new Vector2d(-32, -32), Math.toRadians(0.0), DriveConstants.STONE_CONSTRAINTS)
                    .addDisplacementMarker(clawSubsystem::release)
                    .build();
            Trajectory t4 = drivetrain.trajectoryBuilder(t3.end())
                    .lineTo(buildingZonePlace)
                    .build();
            Trajectory t5 = drivetrain.trajectoryBuilder(t4.end(), false)
                    .lineToLinearHeading(new Vector2d(-59, -30.0), Math.toRadians(90))
                    .build();

            Trajectory stone4Pick = drivetrain.trajectoryBuilder(t5.end())
                    .lineTo(new Vector2d(-59,-29), DriveConstants.STONE_CONSTRAINTS)
                    .addDisplacementMarker(clawSubsystem::pick)
                    .build();
            Trajectory stone4Move = drivetrain.trajectoryBuilder(stone4Pick.end())
                    .lineTo(new Vector2d(-56,-34))
                    .build();
            Trajectory stone4Trans = drivetrain.trajectoryBuilder(stone4Move.end())
                    .lineToLinearHeading(new Vector2d(-58,-31), Math.toRadians(4.0),DriveConstants.STONE_CONSTRAINTS)
                    .addDisplacementMarker(clawSubsystem::release)
                    .build();
            Trajectory stone4Place  = drivetrain.trajectoryBuilder(stone4Trans.end())
                    .lineTo(new Vector2d(10,-26))
                    .build();

            drivetrain.followTrajectory(t1);
            drivetrain.followTrajectory(t2);
            Thread.sleep(150);
            drivetrain.followTrajectory(t2_2);
            drivetrain.followTrajectory(t3);
            drivetrain.followTrajectory(t4);
            Thread.sleep(50);
            drivetrain.followTrajectory(t5);
            drivetrain.followTrajectory(stone4Pick);
            Thread.sleep(150);
            drivetrain.followTrajectory(stone4Move);
            drivetrain.followTrajectory(stone4Trans);
            drivetrain.followTrajectory(stone4Place);

        } else if (stonePos == 2) {
            // Stone 2
            Trajectory t1 = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                    .lineTo(new Vector2d(-24,-40))
                    .build();
            Trajectory t2 = drivetrain.trajectoryBuilder(t1.end(),DriveConstants.STONE_CONSTRAINTS)
                    .lineTo(new Vector2d(-37,-24.5))
                    .addDisplacementMarker(clawSubsystem::pick)
                    .build();
            Trajectory t2_2 = drivetrain.trajectoryBuilder(t2.end())
                    .lineTo(new Vector2d(-37,-32))
                    .build();
            Trajectory t3 = drivetrain.trajectoryBuilder(t2_2.end())
                    .lineToLinearHeading(new Vector2d(-37,-26), Math.toRadians(-4),DriveConstants.STONE_CONSTRAINTS)
                    .build();
            Trajectory t4 = drivetrain.trajectoryBuilder(t3.end())
                    .lineTo(buildingZonePlace)
                    .addDisplacementMarker(clawSubsystem::release)
                    .build();
            Trajectory t5 = drivetrain.trajectoryBuilder(t4.end())
                    .lineToLinearHeading(new Vector2d(-66,-25), Math.toRadians(90))
                    .build();

            //stone5
            Trajectory stone5Pick = drivetrain.trajectoryBuilder(t5.end())
                    .lineTo(new Vector2d(-66,-21.5), DriveConstants.STONE_CONSTRAINTS)
                    .addDisplacementMarker(clawSubsystem::pick)
                    .build();
            Trajectory stone5Move = drivetrain.trajectoryBuilder(stone5Pick.end())
                    .lineTo(new Vector2d(-66,-31))
                    .build();
            Trajectory stone5Trans = drivetrain.trajectoryBuilder(stone5Move.end())
                    .lineToLinearHeading(new Vector2d(-66,-28), Math.toRadians(-4),DriveConstants.STONE_CONSTRAINTS)
                    .build();
            Trajectory stone5Place  = drivetrain.trajectoryBuilder(stone5Trans.end())
                    .lineTo(new Vector2d(10,-25))
                    .addDisplacementMarker(clawSubsystem::release)
                    .build();
            drivetrain.followTrajectory(t1);
            drivetrain.followTrajectory(t2);
            Thread.sleep(200);
            drivetrain.followTrajectory(t2_2);
            drivetrain.followTrajectory(t3);
            drivetrain.followTrajectory(t4);
            Thread.sleep(50);
            drivetrain.followTrajectory(t5);
            drivetrain.followTrajectory(stone5Pick);
            Thread.sleep(200);
            drivetrain.followTrajectory(stone5Move);
            drivetrain.followTrajectory(stone5Trans);
            drivetrain.followTrajectory(stone5Place);
            Thread.sleep(50);
        } else {
            Trajectory t1 = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                    .lineTo(new Vector2d(-24,-40))
                    .build();
            // Stone 3
            Trajectory t2 = drivetrain.trajectoryBuilder(t1.end(),DriveConstants.STONE_CONSTRAINTS)
                    .lineTo(new Vector2d(-48,-25.5))
                    .addDisplacementMarker(clawSubsystem::pick)
                    .build();
            Trajectory t3 = drivetrain.trajectoryBuilder(t2.end())
                    .lineTo(new Vector2d(-48,-30.5))
                    .build();
            Trajectory t4 = drivetrain.trajectoryBuilder(t3.end())
                    .lineToLinearHeading(new Vector2d(-48,-30), Math.toRadians(4),DriveConstants.STONE_CONSTRAINTS)
                    .build();
            Trajectory t5 = drivetrain.trajectoryBuilder(t4.end())
                    .lineTo(new Vector2d(14.0, -26))
                    .addDisplacementMarker(clawSubsystem::release)
                    .build();
            Trajectory t6 = drivetrain.trajectoryBuilder(t5.end())
                    .lineToLinearHeading(new Vector2d(-46,-27), Math.toRadians(90))
                    .build();

            //stone2
            Trajectory stone2Pick = drivetrain.trajectoryBuilder(t6.end())
                    .lineTo(new Vector2d(-46,-23), DriveConstants.STONE_CONSTRAINTS)
                    .addDisplacementMarker(clawSubsystem::pick)
                    .build();
            Trajectory stone2Move = drivetrain.trajectoryBuilder(stone2Pick.end())
                    .lineTo(new Vector2d(-46,-32))
                    .build();
            Trajectory stone2Trans = drivetrain.trajectoryBuilder(stone2Move.end())
                    .lineToLinearHeading(new Vector2d(-35,-27), Math.toRadians(2),DriveConstants.STONE_CONSTRAINTS)
                    .addDisplacementMarker(clawSubsystem::release)
                    .build();
            Trajectory stone2Place  = drivetrain.trajectoryBuilder(stone2Trans.end())
                    .lineTo(new Vector2d(10,-27))
                    .build();
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
        Thread.sleep(50);
        tapeSubsystem.out();
        Thread.sleep(500);
        tapeSubsystem.stop();
        visionSubsystem.shutdown();
    }

}
