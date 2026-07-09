package team5427.frc.robot.subsystems.endeffector.io;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

public interface EndEffectorIO {
  @AutoLog
  public static class EndEffectorInputs {
    public Rotation2d pivotLeaderAngle = new Rotation2d();
    public AngularVelocity pivotLeaderAngularVelocity = RadiansPerSecond.of(0.0);
    public AngularAcceleration pivotLeaderAngularAcceleration = RadiansPerSecondPerSecond.of(0.0);

    public Current pivotLeaderCurrent = Amps.of(0.0);
    public Voltage pivotLeaderVoltage = Volts.of(0.0);

    public Rotation2d pivotFollowerAngle = new Rotation2d();
    public AngularVelocity pivotFollowerAngularVelocity = RadiansPerSecond.of(0.0);
    public AngularAcceleration pivotFollowerAngularAcceleration = RadiansPerSecondPerSecond.of(0.0);

    public Current pivotFollowerCurrent = Amps.of(0.0);
    public Voltage pivotFollowerVoltage = Volts.of(0.0);

    public LinearVelocity flywheelLinearVelocity = MetersPerSecond.of(0.0);
    public LinearAcceleration flywheelLinearAcceleration = MetersPerSecondPerSecond.of(0.0);

    public Current flywheelCurrent = Amps.of(0.0);
    public Voltage flywheelVoltage = Volts.of(0.0);
    public Temperature flywheelTemperature = Celsius.of(0.0);
  }

  public default void updateInputs(EndEffectorInputsAutoLogged inputs) {}

  public default void setPivotPosition(Rotation2d angle) {}

  public default void setPivotPosition(Angle angle) {}

  public default void setPivotPosition(double degrees) {}

  public default void setFlywheelVelocity(LinearVelocity velocity) {}

  public default void setFlywheelVelocity(double metersPerSecond) {}
}
