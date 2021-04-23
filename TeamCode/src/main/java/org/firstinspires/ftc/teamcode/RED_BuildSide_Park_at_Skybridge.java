//RED_BuildSide_Park_at_Skybridge 1/23/2020

package org.firstinspires.ftc.teamcode;

//Import Lib
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
@Disabled
@Autonomous

public class RED_BuildSide_Park_at_Skybridge extends LinearOpMode {
    
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
// move backwards to far end of the red skybridge
        backLeft.setPower(.30);
        backRight.setPower(.30);
        backLeft.setTargetPosition(1100);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setTargetPosition(1100);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        while(backLeft.isBusy() && backLeft.isBusy() && opModeIsActive()) {    
         
          currentDrift = getYaw();
          counterDriftPower = (currentDrift / 90)*2;
          backLeft.setPower(counterDriftPower + .30);
          backRight.setPower(-counterDriftPower + .30);   }   
        
        backLeft.setPower(0); 
        backRight.setPower(0); 
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);
        
//Tune 90 back faces skybridge
        backLeft.setPower(.30);
        backRight.setPower(.30);
        backLeft.setTargetPosition(515);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setTargetPosition(-515);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        while(backRight.isBusy() && backLeft.isBusy() && opModeIsActive()) {    
          
            } 
            
        backLeft.setPower(0); 
        backRight.setPower(0); 
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        sleep(1000);
        
//Drive backwards to park under skybridge        
        backLeft.setPower(-.30);
        backRight.setPower(-.30);
        
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        
        while(opModeIsActive() && colorSensorRT.red() <30 && colorSensorLT.red() <30) {    
            }   
        
        backLeft.setPower(0); 
        backRight.setPower(0); 
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);
}

// navX
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
