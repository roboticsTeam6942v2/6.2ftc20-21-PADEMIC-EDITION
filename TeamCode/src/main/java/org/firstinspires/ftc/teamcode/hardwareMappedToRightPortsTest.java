package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class hardwareMappedToRightPortsTest extends LinearOpMode {

    /*
     *Just some code to make sure the motors are mapped to the right hub ports, also to
     *get the right motor directions as well! Exclamation marks make things more fun! kinda!?!?
     */

    //Declaring the hardware variables here! >:)
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor diskLauncher;
    private DcMotor conveyor;
    private DcMotor grabbingRollerRight;
    private DcMotor grabbingRollerLeft;

    @Override
    public void runOpMode()  {

        //Mapping the hardware to right names and ports on phone!
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        diskLauncher = hardwareMap.get(DcMotor.class, "diskLauncher");
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        grabbingRollerRight = hardwareMap.get(DcMotor.class, "grabbingRollerRight");
        grabbingRollerLeft = hardwareMap.get(DcMotor.class, "grabbingRollerLeft");

        //The wheels have encoders plugged in rn but we aren't using them since this is just code
        //to make sure everything maps right!
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Reversing the drive train motors, not sure if it's the right side yet!
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

        //Reverses feeder motors, not sure if this is the right side either!
        grabbingRollerLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        grabbingRollerRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //Reversing the diskLauncher, doing it on a whim idk if it actually has to be reversed or not!
        diskLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
        conveyor.setDirection(DcMotorSimple.Direction.FORWARD);

        //Code that runs repeatedly until the stop button is pressed.
        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {

                grabbingRollerLeft.setPower(0.2);
                grabbingRollerRight.setPower(0.2);

            }

            if(gamepad1.b) {

                diskLauncher.setPower(0.1);
                conveyor.setPower(0.2);

            }

            if(gamepad1.left_bumper) {

                leftFront.setPower(0.3);
                leftRear.setPower(0.3);

            }

            if (gamepad1.right_bumper) {

                rightFront.setPower(0.3);
                rightRear.setPower(0.3);

            }

            //I wasn't sure if it'd be a good idea to just have all the drive train motors run at once
            //but it could just make it more confusing, so imma just apply power to 1 side at a time.

            //pretty sure that's all the motors, after we use this we can prolly delete it cause
            //it really doesn't have any other use beside this one time thing! I'm not sure what
            //else I should say but I feel like I want to say something.¯\_(ツ)_/¯ OH WELL!

        }
    }
}
