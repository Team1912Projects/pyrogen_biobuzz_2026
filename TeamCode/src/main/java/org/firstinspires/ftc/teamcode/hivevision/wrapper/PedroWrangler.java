/*
 * PedroWrangler - BallWrangler backed by Pedro Pathing. Everything from the
 * base class works; pose verbs (searchAt / then / thenReturnTo) use the
 * follower's field localization, and go() owns follower.update() so paths and
 * localization both advance. Camera-relative verbs drive through
 * follower.setTeleOpDrive() so the localizer keeps running.
 */
package org.firstinspires.ftc.teamcode.hivevision.wrapper;

import com.pedropathing.follower.Follower;
import static com.pedropathing.api.Paths.line;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hivevision.driver.BallTracker;
//import org.firstinspires.ftc.teamcode.hivevision.driver.BallTracker;

public class PedroWrangler extends BallWrangler {

    private final Follower follower;
    private boolean pathing = false;

    public PedroWrangler(Follower follower, Limelight3A limelight, DcMotor intakeOrNull) {
        this(follower, new BallTracker(limelight), intakeOrNull, null);
    }

    public PedroWrangler(Follower follower, BallTracker tracker, DcMotor intakeOrNull) {
        this(follower, tracker, intakeOrNull, null);
    }

    public PedroWrangler(Follower follower, BallTracker tracker, DcMotor intakeOrNull,
                         FullSensor fullOrNull) {
        super(tracker, intakeOrNull, fullOrNull);
        this.follower = follower;
    }

    /* ---------------- lifecycle ---------------- */

    /** Arm a fresh run AND put the follower in teleop-drive mode so the
     *  camera-relative verbs (setTeleOpDrive) have an effect immediately -
     *  Pedro ignores drive input until startTeleopDrive() has been called.
     *  goToPose()/followPath still works afterwards: it moves the follower out
     *  of teleop mode for the duration of the path. */
    @Override public void start() {
        follower.manual();
        super.start();
    }

    /* ---------------- field pose (Pedro knows where it is) ---------------- */

    @Override protected boolean hasPose() { return true; }

    @Override protected RobotPose robotPose() {
        Pose p = follower.pose();
        return new RobotPose(p.x(), p.y(), p.heading());
    }

    @Override protected boolean goToPose(RobotPose target) {
        Pose cur = follower.pose();
        double endH = cur.heading() + wrap(target.headingRad - cur.heading());
        Path path = line(cur, new Pose(target.x,target.y,endH));
        follower.follow(path);
        pathing = true;
        return true;
    }

    @Override protected boolean arrivedAtPose() { return !follower.isBusy(); }

    @Override protected void stopPoseMove() {
        if (pathing) {
            follower.stop();
            follower.manual();
            pathing = false;
        }
    }

    /* ---------------- heading + rotation (Pedro heading is CCW+) ---------------- */

    @Override protected double headingRad() {
        return follower.pose().heading();
    }

    @Override protected double cwDeltaSince(double startHeadingRad) {
        return -wrap(headingRad() - startHeadingRad);
    }

    /* ---------------- motion ---------------- */

    /** BallWrangler's turn is + = CLOCKWISE (matching BallChaseController);
     *  Pedro's teleop drive is fwd +, strafe + = LEFT, turn + = CCW. */
    @Override protected void driveRobot(double fwd, double strafe, double turnCw) {
        follower.manual(fwd, -strafe, -turnCw);
    }

    @Override protected void loopHook() {
        follower.update();
    }
}