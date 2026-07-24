package team5427.frc.robot.subsystems.diffy;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;
import lombok.Setter;
import org.littletonrobotics.junction.Logger;
import team5427.frc.robot.Constants;
import team5427.frc.robot.subsystems.diffy.io.DiffyIO;
import team5427.frc.robot.subsystems.diffy.io.DiffyIOTalonFXBadFriction;
import team5427.frc.robot.subsystems.diffy.io.DiffyInputsAutoLogged;

public class DiffySubsystem extends SubsystemBase {

  private DiffyIO io;
  private DiffyInputsAutoLogged inputs;

  private static DiffySubsystem m_instance;

  @Getter @Setter private Rotation2d averageSetpoint;
  @Getter @Setter private Rotation2d differenceSetpoint;
  @Getter @Setter private LinearVelocity flywheelSetpoint;

  public static DiffySubsystem getInstance() {
    if (m_instance == null) {
      m_instance = new DiffySubsystem();
    }
    return m_instance;
  }

  private DiffySubsystem() {
    inputs = new DiffyInputsAutoLogged();
    switch (Constants.currentMode) {
      case REAL:
        io = new DiffyIOTalonFXBadFriction();
        break;
      case SIM:
        break;
      default:
        break;
    }
    averageSetpoint = DiffyConstants.kPivotStowPosition;
    differenceSetpoint = DiffyConstants.kPivotStowPosition;
    flywheelSetpoint = DiffyConstants.kFlywheelStowVelocity;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    io.setLeaderPivotPosition(averageSetpoint.plus(differenceSetpoint));
    io.setFollowerPivotPosition(averageSetpoint.minus(differenceSetpoint));
    io.setFlywheelVelocity(flywheelSetpoint);

    Logger.processInputs("Diffy/Inputs", inputs);
    log();
  }

  public boolean pivotAtGoal() {
    return Math.abs(
                inputs.pivotLeaderAngle.getDegrees()
                    - (averageSetpoint.plus(differenceSetpoint).getDegrees()))
            <= 4.0
        && Math.abs(
                inputs.pivotFollowerAngle.getDegrees()
                    - (averageSetpoint.minus(differenceSetpoint).getDegrees()))
            <= 4.0;
  }

  private void log() {
    Logger.recordOutput("Diffy/Pivot/PivotPosition", inputs.pivotLeaderAngle.getRadians());
    Logger.recordOutput(
        "Diffy/Pivot/PivotVelocity", inputs.pivotLeaderAngularVelocity.in(RadiansPerSecond));
    Logger.recordOutput(
        "Diffy/Pivot/PivotAcceleration",
        inputs.pivotLeaderAngularAcceleration.in(RadiansPerSecondPerSecond));
    Logger.recordOutput("Diffy/Pivot/PivotCurrent", inputs.pivotLeaderCurrent.in(Amps));
    Logger.recordOutput("Diffy/Pivot/PivotVoltage", inputs.pivotLeaderVoltage.in(Volts));

    Logger.recordOutput(
        "Diffy/Flywheel/FlywheelLinearVelocity", inputs.flywheelLinearVelocity.in(MetersPerSecond));
    Logger.recordOutput(
        "Diffy/Flywheel/FlywheelLinearAcceleration",
        inputs.flywheelLinearAcceleration.in(MetersPerSecondPerSecond));
    Logger.recordOutput("Diffy/Flywheel/FlywheelCurrent", inputs.flywheelCurrent.in(Amps));
    Logger.recordOutput("Diffy/Flywheel/FlywheelVoltage", inputs.flywheelVoltage.in(Volts));
    Logger.recordOutput(
        "Diffy/Flywheel/FlywheelTemperature", inputs.flywheelTemperature.in(Celsius));
  }
}
