//BLUE_Foundation_Park_at_Wall 1/23/2020

package org.firstinspires.ftc.teamcode;
//Import Lib
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//navX
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

//Note: When active @Disabled will remove to code from the RC's Auto menu. 
//@Disabled 
@Autonomous

public class BLUE_Foundation_Park_at_Wall extends LinearOpMode {
    
// Servo    
    Servo pushServo;
// sensors
    ColorSensor colorSensorRT;
    ColorSensor colorSensorLT;
// motors
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor liftLeft;
    private DcMotor liftRight;
    private DcMotor midShift;
// gyro objects
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
// drift vars
    private float currentDrift;
    private float counterDriftPower;
     
@Override
  public void runOpMode() {
        
  initNavx();
// get hardware objects
      colorSensorRT = hardwareMap.colorSensor.get("colorSensorRT");
      colorSensorLT = hardwareMap.colorSensor.get("colorSensorLT");
      backLeft = hardwareMap.get(DcMotor.class, "backLeft");
      backRight = hardwareMap.get(DcMotor.class, "backRight");
      liftLeft = hardwareMap.get(DcMotor.class, "liftLeft");
      liftRight = hardwareMap.get(DcMotor.class, "liftRight");
      midShift = hardwareMap.get(DcMotor.class, "midShift");
      pushServo = hardwareMap.get(Servo.class, "pushServo");  
// set motor modes and directions
      backRight.setDirection(DcMotorSimple.Direction.REVERSE);
      midShift.setDirection(DcMotorSimple.Direction.REVERSE);
      liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
      liftRight.setDirection(DcMotorSimple.Direction.REVERSE);
    
      backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
      liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
      
      
      
      midShift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      
      
      pushServo.setPosition(1);
      
waitForStart();
    
// midShift Right to blue line
      backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

      midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      midShift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      
      midShift.setPower(-.20);

      while(colorSensorLT.blue() < 35 && opModeIsActive()) {
        currentDrift = getYaw();
        counterDriftPower = (currentDrift / 90);
        backLeft.setPower(counterDriftPower);
        backRight.setPower(-counterDriftPower);
      }
      midShift.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
      sleep(500); //was set to 1000 1/16/20

// Drive strait to foundation
      backLeft.setPower(.35);
      backRight.setPower(.35);
      backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setTargetPosition(1390);
      backRight.setTargetPosition(1390);
      backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
      backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

      while(backLeft.isBusy() && backRight.isBusy() && opModeIsActive()){
        currentDrift = getYaw();
        counterDriftPower = (currentDrift / 90)*2;
        backLeft.setPower(counterDriftPower + .35);
        backRight.setPower(-counterDriftPower + .35);
      }
//lift Arm Up
      liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
      liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
      
      liftRight.setPower(.5);
      liftLeft.setPower(.5); 
      sleep(1000); //1500
      
      backLeft.setPower(0);
      backRight.setPower(0);
      sleep(500);

// Drive strait to foundation
      backLeft.setPower(.35);
      backRight.setPower(.35);
      backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setTargetPosition(200);
      backRight.setTargetPosition(200);
      backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
      backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

      while(backLeft.isBusy() && backRight.isBusy() && opModeIsActive()){
        currentDrift = getYaw();
        counterDriftPower = (currentDrift / 90)*2;
        backLeft.setPower(counterDriftPower +.35);
        backRight.setPower(-counterDriftPower +.35);
      }
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
      
        liftRight.setPower(-.1);
        liftLeft.setPower(-.1);
        sleep(200);
        liftRight.setPower(0);
        liftLeft.setPower(0);
        sleep(500);
        
        
// Drive Back with foundation
      backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      backLeft.setPower(-.5);
      backRight.setPower(-.5);
      backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); 
      backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

      while(colorSensorLT.blue()< 30 && opModeIsActive()){
        currentDrift = getYaw();
        counterDriftPower = (currentDrift / 90)*2;
        backLeft.setPower(counterDriftPower  -.5);
        backRight.setPower(-counterDriftPower -.5);
      }
      
      backLeft.setPower(0);
      backRight.setPower(0);
      
// lift Arm Up and midShift left

      liftRight.setPower(0.5);
      liftLeft.setPower(0.5);
      sleep(500);
      
      midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
      midShift.setPower(.50);
      
      midShift.setTargetPosition(1500); //1300
      
      midShift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     
     while(midShift.isBusy() && opModeIsActive()){
      backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
       backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
       
        currentDrift = getYaw();
        counterDriftPower = (currentDrift / 90);
        backLeft.setPower(counterDriftPower);
        backRight.setPower(-counterDriftPower); 
      }
      
      midShift.setPower(0);
        midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        
        liftRight.setPower(-.1);
        liftLeft.setPower(-.1);
        
        sleep(500);
        
      backLeft.setPower(0);
      backRight.setPower(0);
      liftLeft.setPower(0);
      liftRight.setPower(0);
      midShift.setPower(0);
      
      midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      midShift.setPower(.75);
      midShift.setTargetPosition(400); 
      midShift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     
     
      while(midShift.isBusy() && colorSensorRT.blue() < 35 && opModeIsActive()){
          telemetry.addData("color blue:", colorSensorRT.blue());
          telemetry.update();
  }
  midShift.setPower(0);
 midShift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  
  sleep(15000);
  
  
  
  }
private void initNavx() {
    navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
    gyro = (IntegratingGyroscope)navxMicro;
    telemetry.log().add("do not move robot navx is calibrating");
      while (navxMicro.isCalibrating() && opModeIsActive());
        telemetry.log().clear();
        telemetry.log().add("done calibrating");
}
private float getYaw() {
    Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    return angles.firstAngle;
}  
}  
