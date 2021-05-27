package org.firstinspires.ftc.teamcode.ultimateGoalSeason2021;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
@TeleOp

public class UltimateGoalTeleOp extends LinearOpMode {

    boolean launcherRunning  = false;
    double wheelSpeedAdapter = 0;
    boolean rollersRunning   = false;
    boolean bIsActive = false;
    boolean bLock = false;

    //Declaring the motors
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor diskLauncher;
    private DcMotor conveyor;
    private DcMotor grabbingRollerRight;
    private DcMotor grabbingRollerLeft;
    //Servo servo;


    @Override
    public void runOpMode() {

        // Name strings must match up with the config on the Robot Controller app.
        // initialize four DcMotors for the drive train, and add the speed variable
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear   = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear  = hardwareMap.get(DcMotor.class, "rightRear");
        //servo      = hardwareMap.get(Servo.class, "servo");

        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

        // maps launchers to hardware on hub ports and phone
        diskLauncher = hardwareMap.get(DcMotor.class, "diskLauncher");
        diskLauncher.setDirection(DcMotorSimple.Direction.REVERSE);//swap with REVERSE if the motor goes the wrong way

        // maps picking up and moving systems
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        conveyor.setDirection(DcMotorSimple.Direction.FORWARD);//swap with REVERSE if the motor goes the wrong way

        grabbingRollerRight = hardwareMap.get(DcMotor.class, "grabbingRollerRight");
        grabbingRollerRight.setDirection(DcMotorSimple.Direction.REVERSE);//swap with REVERSE if the motor goes the wrong way

        grabbingRollerLeft = hardwareMap.get(DcMotor.class, "grabbingRollerLeft");
        grabbingRollerLeft.setDirection(DcMotorSimple.Direction.REVERSE);//swap with REVERSE if the motor goes the wrong way

        //servo.setPosition(1);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // looping code during the game
        while(!isStopRequested()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            // declare the values of the joysticks
            double drive  = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x * 1.5; //Makes angles more accurate because the wheels have to turn more to go sideways, so it allows the bot to adapt better when it comes to going at obscure angles on the plane
            double twist  = gamepad1.right_stick_x;

            // calculates speed on each of the 4 motors
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)};

            // loop through all values in the speeds[] array and find the greatest *magnitude*.  Not the greatest velocity
            // essentially normalizing them
            double max = Math.abs(speeds[0]);
            for (double speed : speeds) {
                if (max < Math.abs(speed)) max = Math.abs(speed);
            }

            // If and only if the maximum is outside of the range we want it to be,
            // normalize all the other speeds based on the given speed value.
            if (max > 1) {
                for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
            }

            // apply the calculated values to the motors, and modify it for direction
            double originalWheelSpeedAdadpter= wheelSpeedAdapter;
            String speed="ERROR";
            if (originalWheelSpeedAdadpter==.5) {
                speed="Half Speed";
            } else if (originalWheelSpeedAdadpter==1) {
                speed="Half Speed";
            }
            telemetry.addData("Speed: ", speed );
            telemetry.update();

            if (speeds[0] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            leftFront.setPower(speeds[0]- wheelSpeedAdapter);

            if (speeds[1] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            rightFront.setPower(speeds[1]- wheelSpeedAdapter);

            if (speeds[2] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            leftRear.setPower(speeds[2]- wheelSpeedAdapter);

            if (speeds[3] < 0) {
                wheelSpeedAdapter =originalWheelSpeedAdadpter*-1;
            }
            rightRear.setPower(speeds[3]- wheelSpeedAdapter);

            // start the launching system when start is held, disable when it isn't
            /*if (gamepad1.a && !rollersRunning) {
                diskLauncher.setPower(1);
                conveyor.setPower(1);
                launcherRunning=true;
            } else if (!gamepad1.a && !rollersRunning){
                diskLauncher.setPower(0);
                conveyor.setPower(.0);
                launcherRunning=false;
            }
            telemetry.addData("Launcher Active?: ", launcherRunning);
            telemetry.update();

            // runs the speed of the drum at the speed of the right trigger press
            double rightTriggerSpeed=gamepad1.right_trigger;
            if (rightTriggerSpeed>.0001 && !launcherRunning) {
                grabbingRollerRight.setPower(rightTriggerSpeed);
                grabbingRollerLeft.setPower(rightTriggerSpeed);
                conveyor.setPower(.3);
                rollersRunning=true;
            } else if (rightTriggerSpeed<.0001 && !launcherRunning){
                diskLauncher.setPower(0);
                conveyor.setPower(.0);
                rollersRunning=false;
            }
            telemetry.addData("Rollers Active?: ", rollersRunning);
            telemetry.update();*/

            if (gamepad1.a) {

                conveyor.setPower(1);
                grabbingRollerRight.setPower(1);
                grabbingRollerLeft.setPower(1);

            } else {
                conveyor.setPower(0);
                grabbingRollerRight.setPower(0);
                grabbingRollerLeft.setPower(0);
            }

            if(gamepad1.b && !bLock && !bIsActive) {
                bLock = true;
                bIsActive = true;
                diskLauncher.setPower(1);
            } else if(gamepad1.b && !bLock && bIsActive) {
                bIsActive = false;
                bLock = true;
                diskLauncher.setPower(0.0);
            } else if(!gamepad1.b && bLock) {
                bLock = false;
            }

            }
        }
    }