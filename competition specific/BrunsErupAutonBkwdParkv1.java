package org.firstinspires.ftc.teamcode.BrunswickErup;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aparn on 11/8/2017.
 */
@Autonomous(name = "Autonomous: BrunsParkBACK", group = "Autonomous")
public class BrunsErupAutonBkwdParkv1 extends LinearOpMode{
    BrunsErupHardware BabyElle = new BrunsErupHardware();
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder TODO: is this right?
    static final double     DRIVE_GEAR_REDUCTION    = 4.0 ;     // This is < 1.0 if geared UP TODO: MAKE SURE THIS IS RIGHT
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.8;  //TODO: make faster if team finds its moving too slow
    static final double     TURN_SPEED              = 0.6;

    enum States {
        //ResetBot,
        RunToTriangle
    }

    @Override
    public void runOpMode(){

        BabyElle.init(hardwareMap);

        telemetry.addData("", "Resetting Motors");
        telemetry.update();

        BabyElle.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BabyElle.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BabyElle.flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("", "Reset complete");
        telemetry.update();

        waitForStart();

        runStates(States.RunToTriangle); //TODO: is that even right

    }

    public void runStates(States currentState){
        switch(currentState){

            case RunToTriangle:
                telemetry.addData("", "Running to Triangle");
                telemetry.update();

                encoderDrive(-0.5, 3, 3, 5);

                telemetry.addData("", "Running to triangle complete");
                telemetry.update();
                break;

            default:
                telemetry.addData("", "Default State");
                telemetry.update();

                BabyElle.leftDrive.setPower(0);
                BabyElle.rightDrive.setPower(0);
                BabyElle.flipper.setPower(0);
                break;
        }
    }


    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = BabyElle.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = BabyElle.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            BabyElle.leftDrive.setTargetPosition(newLeftTarget);
            BabyElle.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            BabyElle.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BabyElle.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            BabyElle.leftDrive.setPower(Math.abs(speed));
            BabyElle.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (BabyElle.leftDrive.isBusy() && BabyElle.rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        BabyElle.leftDrive.getCurrentPosition(),
                        BabyElle.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            BabyElle.leftDrive.setPower(0);
            BabyElle.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            BabyElle.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BabyElle.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

}
