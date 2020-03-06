package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FoundationSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TapeSubsystem;
import org.firstinspires.ftc.teamcode.util.AssetsTrajectoryManager;
@Autonomous
public class RedStone extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrivetrain drivetrain = new MecanumDrivetrain(hardwareMap);
        ClawSubsystem clawSubsystem = new ClawSubsystem(hardwareMap);
        FoundationSubsystem foundationSubsystem = new FoundationSubsystem(hardwareMap);
        LiftSubsystem liftSubsystem = new LiftSubsystem(hardwareMap);
        TapeSubsystem tapeSubsystem = new TapeSubsystem(hardwareMap);

        drivetrain.setPoseEstimate(new Pose2d(-24, -65, Math.toRadians(90)));

        Pose2d buildingStoneStorage = new Pose2d(20,-40), home = new Pose2d(-24,-48);

        waitForStart();

        //1
        drivetrain.followTrajectory(
                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                .forward(20)
                        .forward(14,new DriveConstraints(
                                20.0, 20.0, 0.0,
                                Math.toRadians(180.0), Math.toRadians(180.0), 0.0
                        ))
                .addDisplacementMarker(clawSubsystem::pick)
                .build()
        );
        Thread.sleep(300);
        drivetrain.followTrajectory(
                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                .back(10)
                .build()
        );
        drivetrain.followTrajectory(
                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
                        .splineTo(new Pose2d(20,-40))
                        .addDisplacementMarker(clawSubsystem::release)
                .build()
        );
        Thread.sleep(100);
        drivetrain.followTrajectory(drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate(), true)
        .splineTo(new Pose2d(-32,-33))
        .build());

        //2
//        drivetrain.followTrajectory(drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .lineTo(new Vector2d())
//                .addDisplacementMarker(clawSubsystem::pick)
//                .build()
//        );
//        Thread.sleep(300);
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .back(8)
//                            .build()
//        );
//        drivetrain.turn(Math.toRadians(-90));
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .forward(50)
//                        .addDisplacementMarker(clawSubsystem::release)
//                        .build()
//        );
//        Thread.sleep(100);
//        drivetrain.followTrajectory(drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .back(57)
//                .build());
//        drivetrain.turn(Math.toRadians(90));
//
//        //3
//        drivetrain.followTrajectory(drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .forward(11,new DriveConstraints(
//                        20.0, 20.0, 0.0,
//                        Math.toRadians(180.0), Math.toRadians(180.0), 0.0
//                ))
//                .addDisplacementMarker(clawSubsystem::pick)
//                .build()
//        );
//        Thread.sleep(300);
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .back(8)
//                        .build()
//        );
//        drivetrain.turn(Math.toRadians(-90));
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .forward(50)
//                        .addDisplacementMarker(clawSubsystem::release)
//                        .build()
//        );
//        Thread.sleep(100);
//        drivetrain.followTrajectory(drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .back(60)
//                .build());
//        drivetrain.turn(Math.toRadians(90));

//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .lineTo(new Vector2d(-24,-34))
//                        .addDisplacementMarker(clawSubsystem::pick)
//                        .build()
//        );
//        Thread.sleep(200);
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .back(12)
//                .build()
//        );
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .splineTo(buildingStoneStorage)
//                        .addDisplacementMarker(clawSubsystem::release)
//                .build()
//        );
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate(), true)
//                        .splineTo(home)
//.build()
//        );

//        drivetrain.turn(Math.toRadians(-90));
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                        .forward(72)
//                        .build()
//        );
//        drivetrain.turn(Math.toRadians(90));
//        liftSubsystem.up();
//        Thread.sleep(400);
//        liftSubsystem.stop();
//        drivetrain.followTrajectory(
//                drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .forward(15)
//                .build()
//        );
//
//        liftSubsystem.down();
//        Thread.sleep(400);
//        liftSubsystem.stop();

//        liftSubsystem.moveTo(140);
//        clawSubsystem.release();
//        Thread.sleep(100);
//        drivetrain.followTrajectory(drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate())
//                .back(50)
//                .build());
//        drivetrain.turnAsync(Math.toRadians(90));
//

    }
}
